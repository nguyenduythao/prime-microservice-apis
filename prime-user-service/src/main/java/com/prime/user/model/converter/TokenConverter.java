package com.prime.user.model.converter;


import com.prime.common.enums.TokenType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class TokenConverter implements AttributeConverter<TokenType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TokenType tokenType) {
        if (tokenType == null)
            return null;
        return tokenType.getCode();
    }

    @Override
    public TokenType convertToEntityAttribute(Integer code) {
        if (code == null)
            return null;
        return TokenType.fromCode(code);
    }

}
