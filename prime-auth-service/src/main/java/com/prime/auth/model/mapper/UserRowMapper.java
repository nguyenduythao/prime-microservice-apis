package com.prime.auth.model.mapper;

import com.prime.common.dto.user.UserDTO;
import com.prime.common.enums.user.UserStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserDTO> {
    @Override
    public UserDTO mapRow(ResultSet rs, int row) throws SQLException {
        return UserDTO.builder()
                .userId(rs.getInt("user_id"))
                .username(rs.getString("username"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .lastPassword(rs.getString("last_password"))
                .failedAttempt(rs.getInt("failed_attempt"))
                .lockedTime(rs.getTimestamp("locked_time"))
                .roleName(rs.getString("role_name"))
                .passwordChangedTime(rs.getTimestamp("password_changed_time"))
                .userStatus(UserStatus.fromCode(rs.getInt("user_status")))
                .build();
    }
}
