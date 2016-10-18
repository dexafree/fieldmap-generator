package com.dexafree.fieldmapgenerator.model;

import com.dexafree.fieldmapgenerator.annotation.ExposeField;

/**
 * For testing purposes
 */
public class ExposedNamedPerson {

    @ExposeField("person_name")
    private String name;

    @ExposeField("person_age")
    public int age;

    @ExposeField
    String address;

    public ExposedNamedPerson(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

}
