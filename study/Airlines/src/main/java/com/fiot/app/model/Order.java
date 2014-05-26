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

    CREATE TABLE `ORDER_TBL` (
            `order_id` VARCHAR(50),
    `user_id` VARCHAR(50),
    `flight_id` VARCHAR(50),
    PRIMARY KEY (`order_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


    INSERT INTO REQUEST_TBL ( request_id, sender_id, receiver_id)
    VALUES ("66603571-a1d1-6aef-8ab2-1494a2404fd6",  "eee03571-a1d2-4aef-8ab2-2494a2404fd5", "abb03571-a1d2-4aef-8ab2-2494a2404fd5");

 */

/**
 * Граничний клас, що представляє таблицю ORDER_TBL у базі даних,
 * відповідає за заявки у друзі
 */
@Stored(name = "ORDER_TBL")
public class Order {

    @Primary
    @Stored(name = "order_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "user_id", converter = UUIDConverter.class)
    private UUID userID;

    @Stored(name = "flight_id", converter = UUIDConverter.class)
    private UUID flightID;

    public Order() {
    }

    public Order(UUID id, UUID userID, UUID flightID) {
        this.id = id;
        this.userID = userID;
        this.flightID = flightID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public UUID getFlightID() {
        return flightID;
    }

    public void setFlightID(UUID flightID) {
        this.flightID = flightID;
    }
}
