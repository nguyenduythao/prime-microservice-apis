package com.prime.user.repository;

import com.prime.common.enums.TokenType;
import com.prime.user.model.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

    Optional<TokenEntity> findByTokenAndTokenType(String token, TokenType type);

    void deleteByReferenceId(Integer userId);

    void deleteByToken(String token);
}
