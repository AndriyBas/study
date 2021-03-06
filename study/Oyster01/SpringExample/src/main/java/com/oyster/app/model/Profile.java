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
            `profile_id` VARCHAR(50),
    `first_name` VARCHAR(30),
    `second_name` VARCHAR(30),
    `password` VARCHAR(30),
    `birthday` BIGINT ZEROFILL,
    PRIMARY KEY (`profile_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 */

@Stored(name = "PROFILE_TBL")
public class Profile {

    @Primary
    @Stored(name = "profile_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "first_name")
    private String firstName = "";

    @Stored(name = "second_name")
    private String secondName = "";

    @Stored(name = "password")
    private String password;

    @Stored(name = "birthday", converter = LongConverter.class)
    private long birthday;

    public Profile() {
    }

    public Profile(UUID id, String firstName, String secondName, String password, long birthday) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.password = password;
        this.birthday = birthday;
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

    @Override
    public String toString() {
        return String.format("%s %s", getFirstName(), getSecondName());
    }
}
