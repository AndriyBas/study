package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.LongConverter;

/**
 * @author bamboo
 * @since 4/15/14 10:47 PM
 */

@Stored(name = "GROUP_ID")
public class Group {

    @Primary
    @Stored(name = "_id", converter = LongConverter.class)
    private long id;

    @Stored(name = "chipher", converter = LongConverter.class)
    private long chipher;

    @Stored(name = "name")
    private String name;

    public Group(long id, long chipher, String name) {
        this.id = id;
        this.chipher = chipher;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getChipher() {
        return chipher;
    }

    public void setChipher(long chipher) {
        this.chipher = chipher;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
