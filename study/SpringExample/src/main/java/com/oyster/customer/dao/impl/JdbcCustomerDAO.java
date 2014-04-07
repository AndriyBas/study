package com.oyster.customer.dao.impl;

import com.oyster.customer.dao.CustomerDAO;
import com.oyster.customer.model.Customer;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcCustomerDAO extends JdbcDaoSupport implements CustomerDAO {

//    CREATE TABLE `customer` (
//            `CUST_ID` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
//    `NAME` VARCHAR(100) NOT NULL,
//    `AGE` INT(10) UNSIGNED NOT NULL,
//    PRIMARY KEY (`CUST_ID`)
//    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

    public void insert(Customer customer) {

        String sql = "INSERT INTO customer " +
                "(CUST_ID, NAME, AGE) VALUES (?, ?, ?)";

        getJdbcTemplate().update(sql, new Object[]{
                customer.getCustId(),
                customer.getName(),
                customer.getAge()});

    }

    public Customer findByCustomerId(final int custId) {

        String sql = "SELECT * FROM customer WHERE CUST_ID = ?";

        Customer customer = getJdbcTemplate().queryForObject(
                sql,
                new Object[]{custId},
                new RowMapper<Customer>() {
                    @Override
                    public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
                        Customer c = new Customer(
                                custId,
                                resultSet.getString("NAME"),
                                resultSet.getInt("AGE"));
                        return null;
                    }
                }
        );

        return customer;
    }
}




