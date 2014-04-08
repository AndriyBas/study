package com.oyster.dao;

import com.oyster.dao.exception.DAOException;

import java.util.List;
import java.util.UUID;

/**
 * defines the interface for create, read, update and delete (CRUD)
 * - the four basic functions of persistent storage
 */
public interface CRUDInterface {

//    public void open(ApplicationContext applicationContext) throws DAOException;
//    public void close() throws DAOException;

    /**
     * inserts an instance into the data storage
     *
     * @param instance entity to insert
     * @param <T>      type of an instance
     * @return entity itself, maybe updated
     * @throws DAOException
     */
    public <T> T insert(T instance) throws DAOException;

    /**
     * get an instance of entity of class entityClass with
     * the current id
     *
     * @param entityClass class to wrap the entity into
     * @param id          unique id of the entity
     * @param <T>         type of an instance
     * @return found entity
     * @throws DAOException
     */
    public <T> T read(Class entityClass, UUID id) throws DAOException;

    /**
     * updates the current entity in the data storage
     *
     * @param instance entity to update
     * @param <T>      type of an instance
     * @throws DAOException
     */
    public <T> void update(T instance) throws DAOException;

    /**
     * deletes current entity from the data storage
     *
     * @param instance entity to delete
     * @param <T>      type of an instance
     * @throws DAOException
     */
    public <T> void delete(T instance) throws DAOException;

    /**
     * query for entities that satisfy conditions in DAOFilter
     *
     * @param entityClass class to wrap the entity into
     * @param filter      conditions that entities should satisfy
     * @param <T>         type of an instance
     * @return list of entities that satisfy the conditions
     * @throws DAOException
     */
    public <T> List<T> select(Class entityClass, DAOFilter filter) throws DAOException;

    /**
     * query for entities with sql code
     *
     * @param entityClass class to wrap the entity into
     * @param SQLString   sql select-where clause
     * @param <T>         type of an instance
     * @return list of entities returned from query
     * @throws DAOException
     */
    public <T> List<T> select(Class entityClass, String SQLString) throws DAOException;

}




