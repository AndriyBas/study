package com.fiot.kursach.app.model;

import com.fiot.kursach.dao.annotation.Primary;
import com.fiot.kursach.dao.annotation.Stored;
import com.fiot.kursach.dao.annotation.utils.converter.IntConverter;
import com.fiot.kursach.dao.annotation.utils.converter.LongConverter;
import com.fiot.kursach.dao.annotation.utils.converter.UUIDConverter;
import java.util.UUID;

/**
 * Граничний клас, що представляє таблицю WORKER_INFO_TBL у базі даних
 *
 * @author bamboo
 */

@Stored(name = "WORKER_INFO_TBL")
public class WorkerInfo {

    @Primary
    @Stored(name = "worker_info_id", converter = UUIDConverter.class)
    private UUID id;

    @Stored(name = "position")
    private String position;

    @Stored(name = "salary", converter = IntConverter.class)
    private int salary;

    @Stored(name = "date_hired", converter = LongConverter.class)
    private long dateHired;

    public WorkerInfo() {
    }

    public WorkerInfo(UUID id, String position, int salary, long dateHired) {
        this.id = id;
        this.position = position;
        this.salary = salary;
        this.dateHired = dateHired;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
