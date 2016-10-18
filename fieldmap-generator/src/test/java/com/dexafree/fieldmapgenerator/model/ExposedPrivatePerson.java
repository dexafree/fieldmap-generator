package com.dexafree.fieldmapgenerator.model;

import com.dexafree.fieldmapgenerator.annotation.ExposeField;

/**
 * For testing purposes
 */
public class ExposedPrivatePerson {

    @ExposeField
    private String name;

    @ExposeField("person_age")
    private int age;

    @ExposeField
    private String address;

    public ExposedPrivatePerson(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

}
