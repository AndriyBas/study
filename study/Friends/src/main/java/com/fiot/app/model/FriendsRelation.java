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

    CREATE TABLE `FRIEND_RELATION_TBL` (
            `friend_relation_id` VARCHAR(50),
    `first_id` VARCHAR(50),
    `second_id` VARCHAR(50),
    PRIMARY KEY (`friend_relation_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


    INSERT INTO FRIEND_RELATION_TBL ( friend_relation_id, first_id, second_id)
    VALUES ("77703571-a1d2-4aef-8ab2-1494a2404fd6",  "abb03571-a1d2-4aef-8ab2-2494a2404fd5", "bab03571-a1d2-4aef-8ab2-2494a2404fd5");


 */

@Stored(name = "FRIEND_RELATION_TBL")
public class FriendsRelation {
    @Primary
    @Stored(name = "friend_relation_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "first_id", converter = UUIDConverter.class)
    private UUID firstID;

    @Stored(name = "second_id", converter = UUIDConverter.class)
    private UUID secondID;

    public FriendsRelation() {
    }

    public FriendsRelation(UUID id, UUID firstID, UUID secondID) {
        this.id = id;
        this.firstID = firstID;
        this.secondID = secondID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFirstID() {
        return firstID;
    }

    public void setFirstID(UUID firstID) {
        this.firstID = firstID;
    }

    public UUID getSecondID() {
        return secondID;
    }

    public void setSecondID(UUID secondID) {
        this.secondID = secondID;
    }
}
