package com.oyster.app.model;

import com.oyster.dao.annotation.Primary;
import com.oyster.dao.annotation.Stored;
import com.oyster.dao.annotation.utils.converter.IntConverter;
import com.oyster.dao.annotation.utils.converter.LongConverter;

/**
 * @author bamboo
 * @since 4/15/14 11:06 PM
 */

@Stored(name = "WORKER_INFO_TBL")
public class WorkerInfo {

    @Primary
    @Stored(name = "_id", converter = LongConverter.class)
    private long id;

    @Stored(name = "position")
    private String position;

    @Stored(name = "salary", converter = IntConverter.class)
    private int salary;

    @Stored(name = "date_hired", converter = LongConverter.class)
    private long dateHired;

    public WorkerInfo(long id, String position, int salary, long dateHired) {
        this.id = id;
        this.position = position;
        this.salary = salary;
        this.dateHired = dateHired;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public long getDateHired() {
        return dateHired;
    }

    public void setDateHired(long dateHired) {
        this.dateHired = dateHired;
    }
}
