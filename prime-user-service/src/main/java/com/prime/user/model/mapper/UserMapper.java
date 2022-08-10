package com.prime.user.model.mapper;

import com.prime.common.dto.user.UserDTO;
import com.prime.common.enums.user.UserStatus;
import com.prime.common.vo.user.RegisterUserVO;
import com.prime.user.model.entity.UserEntity;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO convertToDTO(final UserEntity entity) {
        return UserDTO.builder()
                .userId(entity.getUserId())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .userStatus(entity.getUserStatus()).build();
    }

    public void mapToEntity(final RegisterUserVO vo, final UserEntity entity) {
        entity.setUsername(vo.getUsername());
        entity.setEmail(vo.getEmail());
        entity.setPassword(passwordEncoder.encode(vo.getPassword()));
        entity.setPasswordChangedTime(new Date());
        entity.setLastPassword(MD5Encoder.encode(vo.getPassword().getBytes(StandardCharsets.UTF_8)));
        entity.setUserStatus(UserStatus.DISABLED);
    }
}
