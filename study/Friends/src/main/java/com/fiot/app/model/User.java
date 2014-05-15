package com.fiot.app.model;

import com.fiot.dao.annotation.Primary;
import com.fiot.dao.annotation.Stored;
import com.fiot.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * Граничний клас, що представляє таблицю PROFILE_TBL у базі даних
 *
 * @author bamboo
 */


@Stored(name = "USER_TBL")
public class User {

    @Primary
    @Stored(name = "user_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "first_name")
    private String firstName = "";

    @Stored(name = "second_name")
    private String secondName = "";

    @Stored(name = "email")
    private String email = "";

    @Stored(name = "password")
    private String password;

    public User() {
    }

    public User(String firstName, UUID id, String secondName, String email, String password) {
        this.firstName = firstName;
        this.id = id;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

/*
    @Override
    public String toString() {
        return String.format("%s %s", getFirstName(), getSecondName());
    }
*/
}
