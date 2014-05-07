package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.LongConverter;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * @author bamboo
 * @since 4/15/14 10:32 PM
 */

/*


    CREATE TABLE `PROFILE_TBL` (
            `_id` VARCHAR(50),
    `name` VARCHAR(30),
    `surname` VARCHAR(30),
    `password` VARCHAR(30),
    `birthday` BIGINT ZEROFILL,
    PRIMARY KEY (`_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 */

@Stored(name = "PROFILE_TBL")
public class Profile {

    @Primary
    @Stored(name = "_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "name")
    private String name;

    @Stored(name = "surname")
    private String surname;

    @Stored(name = "password")
    private String password;

    @Stored(name = "birthday", converter = LongConverter.class)
    private long birthday;

    public Profile() {
    }

    public Profile(UUID id, String name, String surname, String password, long birthday) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.birthday = birthday;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
