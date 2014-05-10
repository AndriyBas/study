package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * @author bamboo
 * @since 4/15/14 10:45 PM
 */

/*

    CREATE TABLE `HISTORY_TBL` (
            `history_id` VARCHAR(50),
    `author_id` VARCHAR(50),
    `action`  VARCHAR(200),
    PRIMARY KEY (`history_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

 */

@Stored(name = "HISTORY_TBL")
public class History {

    @Primary
    @Stored(name = "history_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "author_id", converter = UUIDConverter.class)
    private UUID authorId;

    @Stored(name = "action")
    private String action;

    private Profile author;

    public History() {
    }

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

    @Override
    public String toString() {
        return String.format("%s %s : %s", getAuthor().getFirstName(), getAuthor().getSecondName(), getAction());
    }

    public Profile getAuthor() {
        return author;
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }
}
