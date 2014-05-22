package com.fiot.app.model;

import com.fiot.dao.annotation.Primary;
import com.fiot.dao.annotation.Stored;
import com.fiot.dao.annotation.utils.converter.UUIDConverter;

import java.util.UUID;

/**
 * Граничний клас, що представляє таблицю MESSAGE_TBL у базі даних
 *
 * @author matiash
 */

/*
    CREATE TABLE `MESSAGE_TBL` (
            `message_id` VARCHAR(50),
    `author_id` VARCHAR(50),
    `text` VARCHAR(300),
    PRIMARY KEY (`message_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


 INSERT INTO MESSAGE_TBL ( text, message_id, author_id)
 VALUES ( "вау2", "04bb48f0-3d54-4770-b208-3d2078928d6c", "45677e3c-24a9-4e31-997b-88084a9cccca");
 */

/**
 * Граничний клас, що представляє таблицю REQUEST_TBL у базі даних,
 * відповідає за заявки у друзі
 */
@Stored(name = "MESSAGE_TBL")
public class Message {

    @Primary
    @Stored(name = "message_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "author_id", converter = UUIDConverter.class)
    private UUID authotID;

    private User author;

    @Stored(name = "text")
    private String text;

    public Message() {
    }

    public Message(UUID id, UUID authotID, String text) {
        this.id = id;
        this.authotID = authotID;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAuthotID() {
        return authotID;
    }

    public void setAuthotID(UUID authotID) {
        this.authotID = authotID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) ", getText(), (author == null ? "недоступно" : author.toString()));
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
