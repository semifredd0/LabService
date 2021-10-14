package com.uniba.di.dfmdevelop.labservice.dao;

import java.util.List;

public interface BaseDao<T>{

    T save(final T domainObject);

    List<T> findAll();

    T getById(final Long id);

    void delete(T domainObject);

    void delete(Long id);
}
