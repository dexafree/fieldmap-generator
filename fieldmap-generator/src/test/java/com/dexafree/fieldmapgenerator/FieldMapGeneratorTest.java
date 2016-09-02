package com.dexafree.fieldmapgenerator;

import com.dexafree.fieldmapgenerator.model.ExposedNamedPerson;
import com.dexafree.fieldmapgenerator.model.ExposedPerson;
import com.dexafree.fieldmapgenerator.model.ExposedPrivatePerson;
import com.dexafree.fieldmapgenerator.model.PrivatePerson;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class FieldMapGeneratorTest {

    private final static String PERSON_NAME = "Test";
    private final static int PERSON_AGE = 30;
    private final static String PERSON_ADDRESS = "Fake Street, 123";

    private final static String DEFAULT_NAME_FIELD_NAME = "name";
    private final static String DEFAULT_AGE_FIELD_NAME = "age";
    private final static String DEFAULT_ADDRESS_FIELD_NAME = "address";

    @Test
    public void allFieldsExportedWithFullObjectMode() throws Exception {

        PrivatePerson privatePerson = new PrivatePerson(PERSON_NAME, PERSON_AGE, PERSON_ADDRESS);

        Map<String, String> fields = FieldMapGenerator.toMap(privatePerson, FieldMapGenerator.MODE.FULL_OBJECT);

        int expectedSize = 3;
        assertEquals(expectedSize, fields.size());

        assertTrue(fields.containsKey(DEFAULT_NAME_FIELD_NAME));
        assertTrue(fields.containsKey(DEFAULT_AGE_FIELD_NAME));
        assertTrue(fields.containsKey(DEFAULT_ADDRESS_FIELD_NAME));

        assertEquals(fields.get(DEFAULT_NAME_FIELD_NAME), PERSON_NAME);
        assertEquals(Integer.parseInt(fields.get(DEFAULT_AGE_FIELD_NAME)), PERSON_AGE);
        assertEquals(fields.get(DEFAULT_ADDRESS_FIELD_NAME), PERSON_ADDRESS);
    }

    @Test
    public void noFieldsExportedWithOnlyVisibleModeAndFullPrivateModel() throws Exception {

        PrivatePerson privatePerson = new PrivatePerson(PERSON_NAME, PERSON_AGE, PERSON_ADDRESS);

        Map<String, String> fields = FieldMapGenerator.toMap(privatePerson, FieldMapGenerator.MODE.ONLY_VISIBLE);

        int expectedSize = 0;
        assertEquals(expectedSize, fields.size());
    }

    @Test
    public void noFieldsExportedWithOnlyAnnotatedModeAndFullPrivateModel() throws Exception {
        PrivatePerson privatePerson = new PrivatePerson(PERSON_NAME, PERSON_AGE, PERSON_ADDRESS);

        Map<String, String> fields = FieldMapGenerator.toMap(privatePerson, FieldMapGenerator.MODE.ONLY_ANNOTATED);

        int expectedSize = 0;
        assertEquals(expectedSize, fields.size());
    }

    @Test
    public void onlyAnnotatedFieldsExportedWithOnlyAnnotatedMode() throws Exception {
        ExposedPerson exposedPerson = new ExposedPerson(PERSON_NAME, PERSON_AGE, PERSON_ADDRESS);

        Map<String, String> fields = FieldMapGenerator.toMap(exposedPerson, FieldMapGenerator.MODE.ONLY_ANNOTATED);

        int expectedSize = 2;
        assertEquals(expectedSize, fields.size());

        assertTrue(fields.containsKey(DEFAULT_NAME_FIELD_NAME));
        assertTrue(fields.containsKey(DEFAULT_AGE_FIELD_NAME));

        assertEquals(fields.get(DEFAULT_NAME_FIELD_NAME), PERSON_NAME);
        assertEquals(Integer.parseInt(fields.get(DEFAULT_AGE_FIELD_NAME)), PERSON_AGE);
    }

    @Test
    public void correctExportedNamesWithAllVisibilities() throws Exception {
        ExposedNamedPerson exposedNamedPerson = new ExposedNamedPerson(PERSON_NAME, PERSON_AGE, PERSON_ADDRESS);

        Map<String, String> fields = FieldMapGenerator.toMap(exposedNamedPerson, FieldMapGenerator.MODE.ONLY_ANNOTATED);

        int expectedSize = 3;
        assertEquals(expectedSize, fields.size());

        assertTrue(fields.containsKey("person_name"));
        assertTrue(fields.containsKey("person_age"));
        assertTrue(fields.containsKey(DEFAULT_ADDRESS_FIELD_NAME));

        assertEquals(fields.get("person_name"), PERSON_NAME);
        assertEquals(Integer.parseInt(fields.get("person_age")), PERSON_AGE);
        assertEquals(fields.get(DEFAULT_ADDRESS_FIELD_NAME), PERSON_ADDRESS);
    }

    @Test
    public void noFieldsExportedWithOnlyVisibleModeAndPrivateFullyAnnotatedModel() throws Exception {
        ExposedPrivatePerson privatePerson = new ExposedPrivatePerson(PERSON_NAME, PERSON_AGE, PERSON_ADDRESS);

        Map<String, String> fields = FieldMapGenerator.toMap(privatePerson, FieldMapGenerator.MODE.ONLY_VISIBLE);

        int expectedSize = 0;
        assertEquals(expectedSize, fields.size());
    }

}