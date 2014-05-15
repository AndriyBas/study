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
 * Клас реалізує CRUDInterface для доступу до локальної бази даних на комп’ютері
 *
 * @author bamboo
 */
public class DAOCRUDJdbc extends JdbcDaoSupport implements CRUDInterface {

    /**
     * отримує екземпляр JDBC dao
     *
     * @param context контекст додатку
     * @return екземпляр Jdbc dao
     */
    public static DAOCRUDJdbc getInstance(ApplicationContext context) {
        return (DAOCRUDJdbc) context.getBean("DAOJdbc");
    }

    /**
     * додає екземпляр нової сутності у сховище даних
     *
     * @param instance сутність для вставки
     * @param <T>      тип сутності
     * @return саму сутність
     * @throws DAOException
     */
    @Override
    public <T> T insert(T instance) throws DAOException {
        return performInsertOrReplace(instance, "INSERT");
    }

    private <T> T performInsertOrReplace(T instance, String keyWord) {

        HashMap<String, String> mapStrStr = (HashMap<String, String>)
                DAOAnnotationUtils.getConvertedStoredFields(instance);

        StringBuilder sb = new StringBuilder();
        StringBuilder q = new StringBuilder();
        for (String key : mapStrStr.keySet()) {
            sb.append(", " + key);
            q.append(", " + mapStrStr.get(key));
        }

        String sql = keyWord + " INTO " + DAOAnnotationUtils.getStorageName(instance.getClass())
                + " (" + sb.substring(1) + ")" + "VALUES" + " (" + q.substring(1) + ");";

        System.out.println(sql);
        getJdbcTemplate().update(sql);

        return instance;
    }

    /**
     * замінює екземпляр сутності у сховищі даних
     *
     * @param instance сутність для вставки
     * @param <T>      тип сутності
     * @return саму сутність
     * @throws DAOException
     */
    public <T> T replace(T instance) throws DAOException {
        return performInsertOrReplace(instance, "REPLACE");
    }

    /**
     * запит сутності у сховищі даних по його UUID
     *
     * @param entityClass клас-обгортка сутності
     * @param id          унікальний ідентифікатор сутності
     * @param <T>         тип сутності
     * @return сутність, якщо така існує, інакше null
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
        System.out.println(sql);

        return DAOAnnotationUtils.mapToEntity(entityClass, map);
    }

    /**
     * оновлює сутність у сховищі даних
     *
     * @param instance сутність для оновлення
     * @param <T>      тип сутності
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

        System.out.println(sql);

        getJdbcTemplate().update(sql);
    }

    /**
     * видаляє сутність із сховища даних
     *
     * @param instance сутність для видалення
     * @param <T>      тип сутності
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
     * запит для доступу до сховища даних
     *
     * @param entityClass клас-обгортка сутності
     * @param filter      умови, яким повинна відповідати сутність
     * @param <T>         тип сутності
     * @return список сутностей, що відповідають умовам фільтру
     * @throws DAOException
     */
    @Override
    public <T> List<T> select(Class entityClass, DAOFilter filter) throws DAOException {
        return select(entityClass, filter, null);
    }

    /**
     * запит для доступу до сховища даних
     *
     * @param entityClass клас-обгортка сутності
     * @param SQLString   sql select
     * @param <T>         тип сутності
     * @return список сутностей, що відповідають the sql-clause
     * @throws DAOException
     */
    @Override
    public <T> List<T> select(Class entityClass, String SQLString) throws DAOException {
        return select(entityClass, null, SQLString);
    }

    /**
     * допоміжна функція  для пертворення різних типів запитів
     *
     * @param entityClass клас-обгортка сутності
     * @param filter      умови, яким повинна відповідати сутність
     * @param sql         sql select-where clause
     * @param <T>         тип сутності
     * @return список сутностей, що відповідають the sql-clause і умовам фільтру
     */
    private <T> List<T> select(Class entityClass, DAOFilter filter, String sql) {

        if (sql == null || sql.trim().length() == 0) {
            sql = "SELECT * FROM " + DAOAnnotationUtils.getStorageName(entityClass) + ";";
        }

        if (filter == null) {
            filter = new DAOFilter() {
                @Override
                public <T> boolean accept(T entity) {
                    return true;
                }
            };
        }

        List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql);


        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

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
