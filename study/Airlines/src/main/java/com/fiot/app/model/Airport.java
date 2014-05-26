package com.fiot.app.model;

import com.fiot.dao.annotation.Primary;
import com.fiot.dao.annotation.Stored;
import com.fiot.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;


/*
    CREATE TABLE `AIRPORT_TBL` (
            `airport_id` VARCHAR(50),
    `airport_code` VARCHAR(50),
    `airport_name` VARCHAR(100),
    PRIMARY KEY (`airport_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


    INSERT INTO AIRPORT_TBL ( airport_id, airport_code, airport_name)
    VALUES ("11103571-a1d2-4aef-8ab2-1494a2404fd6",  "KBP", "Boryspil International");

    INSERT INTO AIRPORT_TBL ( airport_id, airport_code, airport_name)
    VALUES ("11203571-a1d2-4aef-8ab2-1494a2404fd6",  "LGW", "London Gatwick");

    INSERT INTO AIRPORT_TBL ( airport_id, airport_code, airport_name)
    VALUES ("11303571-a1d2-4aef-8ab2-1494a2404fd6",  "LCY", "London City");

    INSERT INTO AIRPORT_TBL ( airport_id, airport_code, airport_name)
    VALUES ("11403571-a1d2-4aef-8ab2-1494a2404fd6",  "LHR", "London Heathrow");

    INSERT INTO AIRPORT_TBL ( airport_id, airport_code, airport_name)
    VALUES ("11503571-a1d2-4aef-8ab2-1494a2404fd6",  "LBG", "Paris-Le Bourget");

    INSERT INTO AIRPORT_TBL ( airport_id, airport_code, airport_name)
    VALUES ("11603571-a1d2-4aef-8ab2-1494a2404fd6",  "ORY", "Paris-Orly");

    INSERT INTO AIRPORT_TBL ( airport_id, airport_code, airport_name)
    VALUES ("11703571-a1d2-4aef-8ab2-1494a2404fd6",  "SNY", "Sidney");

    INSERT INTO AIRPORT_TBL ( airport_id, airport_code, airport_name)
    VALUES ("11803571-a1d2-4aef-8ab2-1494a2404fd6",  "LGA", "La Guardia");

 */

/**
 * Граничний клас, що представляє таблицю AIRPORT_TBL у базі даних,
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
