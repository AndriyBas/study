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

    CREATE TABLE `FLIGHT_TBL` (
            `flight_id` VARCHAR(50),
    `dep_air_id` VARCHAR(50),
    `arr_air_id` VARCHAR(50),

    `dep_time` BIGINT UNSIGNED ZEROFILL,
    `arr_time` BIGINT UNSIGNED ZEROFILL,

    `class_type` VARCHAR(50),
    `price` INT UNSIGNED ZEROFILL,


    PRIMARY KEY (`flight_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 */

/**
 * Граничний клас, що представляє таблицю USER_TBL у базі даних,
 * відповідає за профілі користувачів
 */

@Stored(name = "FLIGHT_TBL")
public class Flight {

    @Primary
    @Stored(name = "flight_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "dep_air_id", converter = UUIDConverter.class)
    private UUID depAirportID;

    @Stored(name = "arr_air_id", converter = UUIDConverter.class)
    private UUID arrAirportID;

    @Stored(name = "dep_time", converter = Long.class)
    private long depTime;

    @Stored(name = "arr_time", converter = Long.class)
    private long arrTime;

    @Stored(name = "class_type")
    private String classType;

    @Stored(name = "price", converter = Integer.class)
    private int price;

    private Airport depAirport;
    private Airport arrAirport;


    public Flight() {
    }

    public Flight(UUID id,
                  UUID depAirportID,
                  UUID arrAirportID,
                  long depTime,
                  long arrTime,
                  String classType,
                  int price) {
        this.id = id;
        this.depAirportID = depAirportID;
        this.arrAirportID = arrAirportID;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.classType = classType;
        this.price = price;
    }

    public Airport getArrAirport() {
        return arrAirport;
    }

    public void setArrAirport(Airport arrAirport) {
        this.arrAirport = arrAirport;
    }

    public Airport getDepAirport() {
        return depAirport;
    }

    public void setDepAirport(Airport depAirport) {
        this.depAirport = depAirport;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getDepAirportID() {
        return depAirportID;
    }

    public void setDepAirportID(UUID depAirportID) {
        this.depAirportID = depAirportID;
    }

    public UUID getArrAirportID() {
        return arrAirportID;
    }

    public void setArrAirportID(UUID arrAirportID) {
        this.arrAirportID = arrAirportID;
    }

    public long getDepTime() {
        return depTime;
    }

    public void setDepTime(long depTime) {
        this.depTime = depTime;
    }

    public long getArrTime() {
        return arrTime;
    }

    public void setArrTime(long arrTime) {
        this.arrTime = arrTime;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
