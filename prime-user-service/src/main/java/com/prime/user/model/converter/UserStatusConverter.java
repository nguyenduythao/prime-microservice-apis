package com.prime.user.model.converter;

import com.prime.common.enums.user.UserStatus;

import javax.persistence.AttributeConverter;

public class UserStatusConverter implements AttributeConverter<UserStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserStatus userStatus) {
        if (userStatus == null)
            return null;
        return userStatus.getCode();
    }

    @Override
    public UserStatus convertToEntityAttribute(Integer code) {
        if (code == null)
            return null;
        return UserStatus.fromCode(code);
    }
}
