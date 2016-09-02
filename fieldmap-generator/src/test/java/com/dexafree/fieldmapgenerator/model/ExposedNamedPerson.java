package com.dexafree.fieldmapgenerator.model;

import com.dexafree.fieldmapgenerator.annotation.Expose;

/**
 * For testing purposes
 */
public class ExposedNamedPerson {

    @Expose("person_name")
    private String name;

    @Expose("person_age")
    public int age;

    @Expose
    String address;

    public ExposedNamedPerson(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

}
