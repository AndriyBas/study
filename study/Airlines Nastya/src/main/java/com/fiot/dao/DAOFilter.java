package com.fiot.dao;

/**
 * допоміжний інтерфейс для відбирання сутностей за певними параметрами
 */
public interface DAOFilter {
    /**
     * перевіряє, чи сутність задовільняє дані умови
     *
     * @param entity сутність для перевірки
     * @param <T>    тип сутності
     * @return true - якщо сутність задовільняє умови, false - якщо ні
     */
    public <T> boolean accept(T entity);
}
