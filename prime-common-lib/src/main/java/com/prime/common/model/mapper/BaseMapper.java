package com.prime.common.model.mapper;

public interface BaseMapper<V,E,D> {
    D convertToDTO(final E entity);

    void mapToEntity(final V vo, final E entity);
}
