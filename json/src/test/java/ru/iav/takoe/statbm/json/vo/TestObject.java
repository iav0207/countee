package ru.iav.takoe.statbm.json.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by takoe on 24.07.16.
 */
public class TestObject implements Serializable {

    private UUID uuid;

    private String string;

    private Integer integer;

    private Boolean aBoolean;

    private Date date;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public Boolean getaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
