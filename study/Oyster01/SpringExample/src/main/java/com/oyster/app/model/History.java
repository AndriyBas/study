package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.LongConverter;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * @author bamboo
 * @since 4/15/14 10:45 PM
 */

@Stored(name = "HISTORY_TBL")
public class History {

    @Primary
    @Stored(name = "_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "author_id", converter = UUIDConverter.class)
    private UUID authorId;

    @Stored(name = "action")
    private String action;

    public History() {}

    public History(UUID id, UUID authorId, String action) {
        this.id = id;
        this.authorId = authorId;
        this.action = action;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
