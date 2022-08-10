package com.prime.user.repository;

import com.prime.user.model.entity.OAuthClientDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthClientDetailsRepository extends JpaRepository<OAuthClientDetailsEntity, String> {
}