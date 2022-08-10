package com.prime.auth.dao;

import com.prime.common.dto.user.UserDTO;

import java.util.Date;

public interface UserDao {
    UserDTO getByUsernameOrEmail(String identity);

    void updateFailedAttempts(int failAttempts, String username, Date lockedTime);

}
