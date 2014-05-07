package com.oyster.dao.impl;

import com.oyster.dao.CRUDInterface;
import com.oyster.dao.DAOFilter;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.DAOAnnotationUtils;
import com.oyster.dao.exception.DAOException;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author bamboo
 * @since 4/8/14 7:37 PM
 */
public class DAOCRUDJdbc extends JdbcDaoSupport implements CRUDInterface {


/*

    CREATE TABLE `POSITION` (
            `_ID` VARCHAR(50),
    `NAME` VARCHAR(30),
    `DESCRIPTION`  VARCHAR(200),
    PRIMARY KEY (`_ID`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;



    CREATE TABLE `ADMINISTRATOR` (
            `_ID` VARCHAR(50),
    `NAME` VARCHAR(30),
    `USERNAME` VARCHAR(30),
    `EMAIL` VARCHAR(30),
    `PASSWORD` VARCHAR(30),
    `POSITION_ID`  VARCHAR(50),
    `AGE` INT(10) UNSIGNED,
    PRIMARY KEY (`_ID`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
*/


    /**
     * retrieves a Jdbc dao instance
     *
     * @param context context of the application
     * @return an instance to the Jdbc dao
     */
    public static DAOCRUDJdbc getInstance(ApplicationContext context) {
        return (DAOCRUDJdbc) context.getBean("DAOJdbc");
    }

    /**
     * insert an instance of new entity into the data storage
     *
     * @param instance entity to insert
     * @param <T>      type of the entity
     * @return
     * @throws DAOException
     */
    @Override
    public <T> T insert(T instance) throws DAOException {
        HashMap<String, String> mapStrStr = (HashMap<String, String>)
                DAOAnnotationUtils.getConvertedStoredFields(instance);

        StringBuilder sb = new StringBuilder();
        StringBuilder q = new StringBuilder();
        for (String key : mapStrStr.keySet()) {
            sb.append(", " + key);
            q.append(", " + mapStrStr.get(key));
        }

        String sql = "INSERT INTO " + DAOAnnotationUtils.getStorageName(instance.getClass())
                + " (" + sb.substring(1) + ")" + "VALUES" + " (" + q.substring(1) + ");";


        System.out.println("fuck my balls : ");
        System.out.println(sql);


        int result = getJdbcTemplate().update(sql);
        System.out.println(result);

        return instance;
    }

    /**
     * query for entity in the data storage   by it's UUID
     *
     * @param entityClass class to wrap the entity into
     * @param id          unique id of the entity
     * @param <T>         type of the entity
     * @return the entity if found, else null
     * @throws DAOException
     */
    @Override
    public <T> T read(Class entityClass, UUID id) throws DAOException {
        Field primaryKeyField = DAOAnnotationUtils.getPrimaryKey(entityClass);
        String sql = "SELECT * FROM " + DAOAnnotationUtils.getStorageName(entityClass)
                + " WHERE " + primaryKeyField.getAnnotation(Stored.class).name()
                + "=\"" + id.toString() + "\";";

        Map<String, Object> map = null;
        try {
            map = getJdbcTemplate().queryForMap(sql);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
//        System.out.println(sql);

        return DAOAnnotationUtils.mapToEntity(entityClass, map);
    }

    /**
     * update entity in the data storage
     *
     * @param instance entity to update
     * @param <T>      type of the entity
     * @throws DAOException
     */
    @Override
    public <T> void update(T instance) throws DAOException {
        HashMap<String, String> mapStrStr = (HashMap<String, String>)
                DAOAnnotationUtils.getConvertedStoredFields(instance);

        StringBuilder sb = new StringBuilder();
        for (String key : mapStrStr.keySet()) {
            sb.append(", " + key + "=" + mapStrStr.get(key));
        }

        Field primaryKeyField = DAOAnnotationUtils.getPrimaryKey(instance.getClass());
        String sql = "UPDATE " + DAOAnnotationUtils.getStorageName(instance.getClass())
                + " SET " + sb.substring(1) + " WHERE "
                + primaryKeyField.getAnnotation(Stored.class).name()
                + "=" + DAOAnnotationUtils.getStringValue(instance, primaryKeyField) + ";";

//        String sql2 = "UPDATE POSITION SET NAME='new name' WHERE _ID='\"a91136fe-08bd-476c-9d0c-7a897083e809\"'";

        System.out.println(sql);

        getJdbcTemplate().update(sql);
    }

    /**
     * delete an entity from the data storage
     *
     * @param instance entity to delete
     * @param <T>      type of the entity
     * @throws DAOException
     */
    @Override
    public <T> void delete(T instance) throws DAOException {
        Field primaryKeyField = DAOAnnotationUtils.getPrimaryKey(instance.getClass());
        String sql = "DELETE FROM " + DAOAnnotationUtils.getStorageName(instance.getClass())
                + " WHERE "
                + primaryKeyField.getAnnotation(Stored.class).name()
                + "=" + DAOAnnotationUtils.getStringValue(instance, primaryKeyField) + ";";

        getJdbcTemplate().update(sql);
    }

    /**
     * query for entities in the data storage
     *
     * @param entityClass class to wrap the entity into
     * @param filter      conditions that entities should satisfy
     * @param <T>         type of the entity
     * @return list of entities that satisfy  Filter conditions
     * @throws DAOException
     */
    @Override
    public <T> List<T> select(Class entityClass, DAOFilter filter) throws DAOException {

        String sql = "SELECT * FROM " + DAOAnnotationUtils.getStorageName(entityClass) + ";";

        return select(entityClass, filter, sql);
    }

    /**
     * query for entities in the data storage
     *
     * @param entityClass class to wrap the entity into
     * @param SQLString   sql select-where clause
     * @param <T>         type of the entity
     * @return list of entities that satisfy the sql-clause
     * @throws DAOException
     */
    @Override
    public <T> List<T> select(Class entityClass, String SQLString) throws DAOException {
        DAOFilter filter = new DAOFilter() {
            @Override
            public <T> boolean accept(T entity) {
                return true;
            }
        };
        return select(entityClass, filter, SQLString);
    }

    /**
     * helper function to perform different types of select
     *
     * @param entityClass class to wrap the entity into
     * @param filter      conditions that entities should satisfy
     * @param sql         sql select-where clause
     * @param <T>         type of the entity
     * @return list of entities that satisfy the sql-clause and Filter conditions
     */
    private <T> List<T> select(Class entityClass, DAOFilter filter, String sql) {

        List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql);


        ArrayList<T> arrayList = new ArrayList<T>();

        for (int i = 0; i < list.size(); i++) {
            T instance = DAOAnnotationUtils.mapToEntity(entityClass, list.get(i));
            if (filter.accept(instance)) {
                arrayList.add(instance);
            }
        }

        return arrayList;
    }
}
