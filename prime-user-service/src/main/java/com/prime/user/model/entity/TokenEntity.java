package com.prime.user.model.entity;

import com.prime.common.enums.TokenType;
import com.prime.common.model.entity.AuditField;
import com.prime.user.model.converter.TokenConverter;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Entity
@Table(name = "prime_token")
public class TokenEntity extends AuditField implements Serializable {

    private static final long serialVersionUID = 101059097991712324L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Integer tokenId;

    @Column(name = "token", unique = true, nullable = false)
    private String token;

    @Column(name = "expire_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime;

    @Column(name = "token_type", nullable = false)
    @Convert(converter = TokenConverter.class)
    private TokenType tokenType;

    @Column(name = "reference_id", nullable = false)
    private Integer referenceId;
}
