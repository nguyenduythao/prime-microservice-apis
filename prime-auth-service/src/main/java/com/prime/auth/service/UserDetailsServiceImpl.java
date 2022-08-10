package com.prime.auth.service;

import com.prime.common.constant.RedisCacheConstants;
import com.prime.auth.dao.UserDao;
import com.prime.auth.model.dto.CustomUserDetails;
import com.prime.common.constant.BaseConstant;
import com.prime.common.dto.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    @Cacheable(cacheNames = RedisCacheConstants.LOAD_USER_BY_USERNAME, key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userDao.getByUsernameOrEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found");
        }
        CustomUserDetails userDetails = new CustomUserDetails();
        userDetails.setUser(user);
        if (!userDetails.isEnabled()) {
            throw new DisabledException("User status is disabled.");
        }
        if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("User was locked.");
        }
        List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(user.getRoleName()));
        userDetails.setAuthorities(roles);
        return userDetails;
    }

    @CacheEvict(cacheNames = RedisCacheConstants.LOAD_USER_BY_USERNAME, allEntries = true)
    public synchronized void increaseFailedAttempts(UserDTO user) {
        int count = 1;
        if (user.getFailedAttempt() != null) {
            count = user.getFailedAttempt() + 1;
        }

        Date lockedTime = user.getLockedTime();
        if (count == BaseConstant.MAX_FAILED_ATTEMPTS) {
            lockedTime = new Date();
        }
        userDao.updateFailedAttempts(count, user.getUsername(), lockedTime);
    }

    @CacheEvict(cacheNames = RedisCacheConstants.LOAD_USER_BY_USERNAME, key = "#username")
    public synchronized void resetFailedAttempts(String username) {
        userDao.updateFailedAttempts(0, username, null);
    }

    public UserDTO findByUsernameOrEmail(String identity) {
        return userDao.getByUsernameOrEmail(identity);
    }
}
