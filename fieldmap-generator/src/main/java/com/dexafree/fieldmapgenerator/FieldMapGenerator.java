/*
 * Copyright (C) 2016 Dexafree <contacto@dexa-dev.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dexafree.fieldmapgenerator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.dexafree.fieldmapgenerator.annotation.Expose;

/**
 * Class that will convert a given object (of any kind) and iterate over its fields, converting them to a
 * {@literal Map<String, String>}.
 *
 * It offers different working modes ({@link MODE}), that can be passed to the "toMap" method.
 * Read the {@link MODE} description for better understanding
 *
 * @author Dexafree {@literal <contacto@dexa-dev.com>}
 */
public class FieldMapGenerator {

    /**
     * Enum that defines the different serialization modes that this generator has
     *
     * FULL_OBJECT: The full object will be serialized, including protected and private fields
     * ONLY_VISIBLE: Only visible fields (public) will be serialized
     * ONLY_ANNOTATED: Only fields containing {@link Expose} annotation will be serialized
     */
    enum MODE {
        FULL_OBJECT,
        ONLY_VISIBLE,
        ONLY_ANNOTATED
    }

    /**
     * Converts a given object to a {@literal Map<String, String>} containing the attributes acording to the given mode
     * @param object Object to be serialized into a {@literal Map<String, String>}
     * @param mode {@link MODE} that indicates which fields will be serialized
     * @return Map containing the fields
     */
    public static Map<String, String> toMap(Object object, MODE mode){

        // Initialize the result to an empty HashMap
        Map<String, String> map = new HashMap<String, String>();

        // Get the object's base class, in order to get the declared fields
        Class<?> c = object.getClass();
        Field[] fields = c.getDeclaredFields();

        // Iterate over every possible field
        for( Field field : fields ){

            // Mode checks
            if(mode == MODE.ONLY_ANNOTATED){

                // If we are in the ONLY_ANNOTATED mode and the field is not annotated, skip it
                if(!isFieldAnnotated(field)){
                    continue;
                } else if (!field.isAccessible()){

                    // If the field is annotated, but it's not accessible, make it accessible
                    field.setAccessible(true);
                }
            } else if (mode == MODE.ONLY_VISIBLE){

                // If we are in the ONLY_VISIBLE mode and the field is not accessible, skip it
                if(!field.isAccessible()){
                    continue;
                }
            } else if (mode == MODE.FULL_OBJECT){

                // If we are in the FULL_OBJECT mode and the field is not accessible, make it accessible anyway
                if(!field.isAccessible()){
                    field.setAccessible(true);
                }
            }

            try {

                // Obtain the field name and value, and add it to the result
                String fieldName = getFieldName(field);

                // Check if there is a field named that way that has been already included
                if(map.containsKey(fieldName)){
                    throw new IllegalStateException("The " + fieldName + " key is duplicated. Please check your " +
                            "@Expose namings, and ensure that no other field is named that way");
                }

                String fieldValue = field.get(object).toString();

                map.put(fieldName, fieldValue);
            } catch (IllegalArgumentException e1) {
            } catch (IllegalAccessException e1) {
            }
        }
        return map;
    }


    /**
     * Converts a given object to a {@literal Map<String, String>} containing only the visible attributes
     * @param object Object to be serialized into a {@literal Map<String, String>}
     * @return Map containing the fields
     */
    public static Map<String, String> toMap(Object object){
        return toMap(object, MODE.ONLY_ANNOTATED);
    }


    /**
     * Checks if a given Field contains the {@link Expose} annotation
     * @param field Field which will be tested
     * @return true if the field contains the {@link Expose} annotation
     */
    private static boolean isFieldAnnotated(Field field){

        // Get the annotations that the field has
        Annotation[] annotations = field.getAnnotations();

        // Iterate over the annotations
        for(Annotation annotation : annotations){

            // If the field contains the annotation, return true and exit the loop
            if(annotation instanceof Expose){
                return true;
            }
        }

        // The field does not contain the annotation
        return false;
    }


    /**
     * Given a field, this extracts the name of the field. If the field is not annotated, the real name will be used.
     * Otherwise, if the field contains a {@link Expose} annotation, and has been given a name, the given name
     * will be used.
     *
     * @param field Field whose name will be extracted
     * @return Name of the field, giving priority to the annotated one if exists
     */
    private static String getFieldName(Field field){

        // Recover the original field name
        String originalFieldName = field.getName();

        // Get the annotations given to that field
        Annotation[] annotations = field.getAnnotations();

        // By default, the fieldName is set to the original one
        String fieldName = originalFieldName;

        // Iterate over the annotations
        for(Annotation annotation : annotations){

            // If the UrlEncode annotation is found, check if it has been given a name
            if(annotation instanceof Expose){

                // Downcast the annotation
                Expose castedAnnotation = (Expose) annotation;
                String annotationValue = castedAnnotation.value();

                // Check if we are getting the default value
                if(!"".equalsIgnoreCase(annotationValue)){

                    // The field was actually given a name. Use that one
                    fieldName = annotationValue;
                }

                break;
            }
        }
        return fieldName;
    }
}
