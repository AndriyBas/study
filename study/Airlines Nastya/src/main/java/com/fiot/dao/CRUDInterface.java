package com.fiot.dao;

import com.fiot.dao.exception.DAOException;

import java.util.List;
import java.util.UUID;

/**
 * визначає CRUD-інтерфейс для базових операцій із постійним місцем збереження
 */
public interface CRUDInterface {

    /**
     * виконує базову операцію вставлення для даної сутності
     *
     * @param instance сутність
     * @param <T>      тип сутності
     * @return саму сутність
     * @throws DAOException
     */
    public <T> T insert(T instance) throws DAOException;

    /**
     * виконує базову операцію зчитування для даної сутності
     *
     * @param entityClass клас сутності
     * @param id          ключ сутності
     * @param <T>         тип сутності
     * @return саму сутність
     * @throws DAOException
     */
    public <T> T read(Class entityClass, UUID id) throws DAOException;

    /**
     * виконує базову операцію оновлення для даної сутності
     *
     * @param instance сутність
     * @param <T>      тип сутності
     * @throws DAOException
     */
    public <T> void update(T instance) throws DAOException;

    /**
     * виконує базову операцію видалення для даної сутності
     *
     * @param instance сутність
     * @param <T>      тип сутності
     * @throws DAOException
     */
    public <T> void delete(T instance) throws DAOException;

    /**
     * виконує пошук сутностей у  постійному хранилищі
     *
     * @param entityClass клас сутності
     * @param filter      фільтр
     * @param <T>         тип сутності
     * @return список знайдених сутностей, що задовільняють даному фільтру
     * @throws DAOException
     */
    public <T> List<T> select(Class entityClass, DAOFilter filter) throws DAOException;

    /**
     * виконує пошук сутностей у базі даних
     *
     * @param entityClass клас сутності
     * @param SQLString   SQL-запит до бази даних
     * @param <T>         тип сутності
     * @return список знайдених сутностей
     * @throws DAOException
     */
    public <T> List<T> select(Class entityClass, String SQLString) throws DAOException;

}




