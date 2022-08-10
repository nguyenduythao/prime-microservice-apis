package com.prime.auth.model.dto;

import com.prime.common.dto.user.UserDTO;
import com.prime.common.enums.user.UserStatus;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Data
public class CustomUserDetails implements Serializable, UserDetails {

    private static final long serialVersionUID = -5547624071389992173L;

    private UserDTO user;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    public Integer getUserId() {
        return this.user.getUserId();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if (UserStatus.USER_LOCKED.getCode().equals(this.user.getUserStatus())) return false;
        if (UserStatus.SYSTEMS_LOCKED.getCode().equals(this.user.getUserStatus())) return false;
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return UserStatus.ACTIVATED.equals(this.user.getUserStatus());
    }
}
