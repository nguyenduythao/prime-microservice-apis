package com.prime.auth.service;

import com.prime.auth.common.constant.AuthErrorCode;
import com.prime.auth.dao.UserDao;
import com.prime.auth.exception.LockedTemporaryException;
import com.prime.auth.model.dto.CustomUserDetails;
import com.prime.common.constant.BaseConstant;
import com.prime.common.constant.ErrorCategory;
import com.prime.common.constant.RedisCacheConstants;
import com.prime.common.dto.user.UserDTO;
import com.prime.common.exception.ErrorCode;
import com.prime.common.util.ErrorMessage;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
import java.util.concurrent.TimeUnit;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private ErrorMessage errorMessage;

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

        if (!userDetails.isAccountNonLockedTemporary()) {
            Date expireLockTime = DateUtils.addMinutes(user.getLockedTime(), BaseConstant.ACTIVE_ACCOUNT_TOKEN_EXPIRE_TIME);
            long diffTimeMillis = expireLockTime.getTime() - System.currentTimeMillis();
            long diffTimeMinutes = TimeUnit.MINUTES.convert(diffTimeMillis, TimeUnit.MILLISECONDS);
            ErrorCode errorCode = errorMessage.getError(AuthErrorCode.LOCKED_TEMPORARY, null, new Long[]{diffTimeMinutes});
            errorCode.setCategory(ErrorCategory.BUSINESS);
            throw new LockedTemporaryException(errorCode);
        }

        List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(user.getRoleName()));
        userDetails.setAuthorities(roles);
        return userDetails;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = RedisCacheConstants.LOAD_USER_BY_USERNAME, key = "#user.username"),
            @CacheEvict(cacheNames = RedisCacheConstants.LOAD_USER_BY_USERNAME, key = "#user.email"),
    })
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
