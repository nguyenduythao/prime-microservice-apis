package com.prime.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = -2720005158446368618L;

    @JsonInclude(value = Include.NON_NULL)
    private T data;

    @JsonInclude(value = Include.NON_NULL)
    private PaginationDTO meta;

    public Response(T data) {
        this.data = data;
    }

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    public Date getTime() {
        return new Date();
    }
}