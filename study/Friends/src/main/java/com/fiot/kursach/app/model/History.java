package com.fiot.kursach.app.model;

import com.fiot.kursach.dao.annotation.Primary;
import com.fiot.kursach.dao.annotation.Stored;
import com.fiot.kursach.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * Граничний клас, що представляє таблицю HISTORY_TBL у базі даних
 *
 * @author bamboo
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
