package com.heyatiminkodlar.jdbc.praktika10.domain;

/**
 * Created by root on 11/30/16.
 */
public class Group {
    private int id;
    private String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Group [id=" + id + ", name=" + name + "]";
    }
}
