package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * @author bamboo
 * @since 4/15/14 10:47 PM
 */

/*

    CREATE TABLE `GROUP_TBL` (
            `_id` VARCHAR(50),
    `name` VARCHAR(30),
    `chipher`  VARCHAR(50),
    PRIMARY KEY (`_ID`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 */
@Stored(name = "GROUP_TBL")
public class Group {

    @Primary
    @Stored(name = "_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "chipher")
    private String chipher;

    @Stored(name = "name")
    private String name;

    public Group() {
    }

    public Group(UUID id, String chipher, String name) {
        this.id = id;
        this.chipher = chipher;
        this.name = name;
    }


    public Group(String chipher, String name) {
        this.chipher = chipher;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChipher() {
        return chipher;
    }

    public void setChipher(String chipher) {
        this.chipher = chipher;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
