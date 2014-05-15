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


    CREATE TABLE `REQUEST_TBL` (
            `request_id` VARCHAR(50),
    `sender_id` VARCHAR(50),
    `receiver_id` VARCHAR(50),
    PRIMARY KEY (`request_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 */

@Stored(name = "REQUEST_TBL")
public class Requests {

    @Primary
    @Stored(name = "request_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "sender_id", converter = UUIDConverter.class)
    private UUID senderID;

    @Stored(name = "receiver_id", converter = UUIDConverter.class)
    private UUID receiverID;

    public Requests() {
    }

    public Requests(UUID id, UUID senderID, UUID receiverID) {
        this.id = id;
        this.senderID = senderID;
        this.receiverID = receiverID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSenderID() {
        return senderID;
    }

    public void setSenderID(UUID senderID) {
        this.senderID = senderID;
    }

    public UUID getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(UUID receiverID) {
        this.receiverID = receiverID;
    }
}
