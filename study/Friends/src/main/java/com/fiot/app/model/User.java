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

/*

    CREATE TABLE `USER_TBL` (
            `user_id` VARCHAR(50),
    `first_name` VARCHAR(40),
    `second_name` VARCHAR(40),
    `email` VARCHAR(40),
    `password` VARCHAR(40),
    PRIMARY KEY (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    INSERT INTO USER_TBL ( user_id, first_name, second_name, email, password)
    VALUES ( "abb03571-a1d2-4aef-8ab2-2494a2404fd5", "Льоша", "Краєвий", "krab@gmail.com", "password");

    INSERT INTO USER_TBL ( user_id, first_name, second_name, email, password)
    VALUES ( "bab03571-a1d2-4aef-8ab2-2494a2404fd5", "Андрій", "Бас", "bas@gmail.com", "password");

    INSERT INTO USER_TBL ( user_id, first_name, second_name, email, password)
    VALUES ( "eee03571-a1d2-4aef-8ab2-2494a2404fd5", "Андрій", "Будяшевський", "budu@gmail.com", "password");

    INSERT INTO USER_TBL ( user_id, first_name, second_name, email, password)
    VALUES ( "cbac7e3c-24a9-4e31-997b-88084a9ca5df", "Олег", "Коломієць", "kolo@gmail.com", "password");

    INSERT INTO USER_TBL ( user_id, first_name, second_name, email, password)
    VALUES ( "45677e3c-24a9-4e31-997b-88084a9ca5df", "Данило", "Мулявко", "dan@gmail.com", "password");

 */

/**
 * Граничний клас, що представляє таблицю USER_TBL у базі даних,
 * відповідає за профілі користувачів
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

    @Override
    public String toString() {
        return String.format("%s %s", getFirstName(), getSecondName());
    }
}
