package com.prime.common.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class PaginationDTO implements Serializable {

    private static final long serialVersionUID = 7692236786021610162L;

    private int totalPage;
    private int totalRecord;
    private int pageSize;
    private int pageNumber;

    public PaginationDTO(int totalRecord, int size, int limit, int offset) {
        int totalPages = totalRecord / limit + totalRecord % limit > 0 ? 1 : 0;
        this.totalPage = totalPages;
        this.totalRecord = totalRecord;
        this.pageSize = size;
        this.pageNumber = offset;
    }
}
