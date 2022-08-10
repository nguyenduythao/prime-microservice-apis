package com.prime.auth.dao;

import com.prime.auth.model.mapper.UserRowMapper;
import com.prime.common.dto.user.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    private String GET_USER_QUERY = "SELECT u.*, r.role_name FROM prime_user as u INNER JOIN prime_role r ON u.role_id = r.role_id WHERE u.username = ? OR u.email = ?";

    private String UPDATE_FAILED_ATTEMPTS_QUERY = "UPDATE prime_user SET failed_attempt = ?, locked_time = ? WHERE username = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDTO getByUsernameOrEmail(String identity) {
        List<UserDTO> userDTOList = jdbcTemplate
                .query(GET_USER_QUERY, new UserRowMapper(), identity, identity);
        if (CollectionUtils.isEmpty(userDTOList)) {
            return null;
        }
        return userDTOList.get(0);
    }

    @Override
    public void updateFailedAttempts(int failAttempts, String username, Date lockedTime) {
        try {
            jdbcTemplate
                    .update(UPDATE_FAILED_ATTEMPTS_QUERY, failAttempts, lockedTime, username);
        } catch (Exception e) {
            log.error("Exception updateFailedAttempts: {}", e.getMessage());
        }
    }
}
