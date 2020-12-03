package com.team.olpokhorochdemo.model;

/**
 * Created by Amit on 02,December,2020
 */
public class Person {
    private String name;
    private int age;
    private String objectId;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, int age, String objectId) {
        this.name = name;
        this.age = age;
        this.objectId = objectId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
