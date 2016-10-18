package com.dexafree.fieldmapgenerator.model;

import com.dexafree.fieldmapgenerator.annotation.ExposeField;

/**
 * For testing purposes
 */
public class ExposedPerson {

    @ExposeField
    private String name;

    @ExposeField
    public int age;

    String address;

    public ExposedPerson(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

}
