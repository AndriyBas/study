package com.fiot.app.model;

import com.fiot.dao.annotation.Primary;
import com.fiot.dao.annotation.Stored;
import com.fiot.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * Граничний клас, що представляє таблицю ABSENCE_TBL у базі даних
 *
 * @author bamboo
 */

/*

    CREATE TABLE `AIRPORT_TBL` (
            `airport_id` VARCHAR(50),
    `airport_code` VARCHAR(50),
    `airport_name` VARCHAR(100),
    PRIMARY KEY (`airport_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


    INSERT INTO FRIEND_RELATION_TBL ( friend_relation_id, first_id, second_id)
    VALUES ("77703571-a1d2-4aef-8ab2-1494a2404fd6",  "abb03571-a1d2-4aef-8ab2-2494a2404fd5", "bab03571-a1d2-4aef-8ab2-2494a2404fd5");


 */

/**
 * Граничний клас, що представляє таблицю FRIEND_RELATION_TBL у базі даних,
 * відповідає за звязки друзів
 */

@Stored(name = "AIRPORT_TBL")
public class Airport {
    @Primary
    @Stored(name = "airport_id", converter = UUIDConverter.class)
    private UUID id;


    @Stored(name = "airport_code")
    private String code;

    @Stored(name = "airport_name")
    private String name;

    public Airport() {
    }

    public Airport(UUID id, String code, String name) {
        this.id = id;
        this.name = name;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", getCode(), getName());
    }
}
