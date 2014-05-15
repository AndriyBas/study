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
@Stored(name = "FRIEND_RELATION_TBL")
public class FriendsRelation {
    @Primary
    @Stored(name = "friend_RELATION_id", converter = UUIDConverter.class)
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
