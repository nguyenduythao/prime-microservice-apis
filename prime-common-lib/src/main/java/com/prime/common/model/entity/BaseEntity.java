package com.prime.common.model.entity;

import com.prime.common.constant.BaseConstant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity extends AuditField implements Serializable {

    private final static long serialVersionUID = 1L;

    @Column(name = "created_operator")
    private Integer createdOperator = BaseConstant.ADMINISTRATOR_CODE;

    @Column(name = "last_updated_operator")
    private Integer lastUpdatedOperator = BaseConstant.ADMINISTRATOR_CODE;
}
