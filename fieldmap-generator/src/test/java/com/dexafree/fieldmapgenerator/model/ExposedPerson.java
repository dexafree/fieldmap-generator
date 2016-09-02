package com.dexafree.fieldmapgenerator.model;

import com.dexafree.fieldmapgenerator.annotation.Expose;

/**
 * For testing purposes
 */
public class ExposedPerson {

    @Expose
    private String name;

    @Expose
    public int age;

    String address;

    public ExposedPerson(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

}
