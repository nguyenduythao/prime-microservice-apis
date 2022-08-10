package com.prime.user.model.mapper;

import com.prime.common.dto.user.UserDetailDTO;
import com.prime.common.enums.Gender;
import com.prime.common.vo.user.UserDetailVO;
import com.prime.user.model.entity.UserDetailEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDetailMapper {

    public UserDetailDTO convertToDTO(UserDetailEntity entity) {
        return UserDetailDTO.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .dob(entity.getDob())
                .gender(entity.getGender().getName())
                .address(entity.getAddress())
                .build();
    }

    public void mapToEntity(final UserDetailVO userDetailVO, final UserDetailEntity userDetailEntity) {
        userDetailEntity.setFirstName(userDetailVO.getFirstName());
        userDetailEntity.setLastName(userDetailVO.getLastName());
        userDetailEntity.setDob(userDetailVO.getDob());
        userDetailEntity.setGender(Gender.valueOf(userDetailVO.getGender().toUpperCase()));
        userDetailEntity.setAddress(userDetailVO.getAddress());
    }
}
