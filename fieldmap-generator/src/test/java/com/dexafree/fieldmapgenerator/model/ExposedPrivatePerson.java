package com.dexafree.fieldmapgenerator.model;

import com.dexafree.fieldmapgenerator.annotation.Expose;

/**
 * For testing purposes
 */
public class ExposedPrivatePerson {

    @Expose
    private String name;

    @Expose("person_age")
    private int age;

    @Expose
    private String address;

    public ExposedPrivatePerson(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

}
