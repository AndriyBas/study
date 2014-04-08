package com.oyster.dao;

/**
 * helper class to use instead of select-where clause
 */
public interface DAOFilter {
    /**
     * Determines if the entity satisfies some conditions or not
     *
     * @param entity instance of object to perform check
     * @param <T>    type of entity
     * @return true - if include the entity, false - if not
     */
    public <T> boolean accept(T entity);
}
