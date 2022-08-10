package com.prime.common.service;

import java.util.Optional;

public interface BaseService<E, I> {
    Optional<E> findById(I id);

    E getById(I id);

    E create(E entity);

    E update(E entity);

    void delete(E entity);
}
