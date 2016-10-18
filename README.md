# FieldMapGenerator [![Travis Build](https://travis-ci.org/dexafree/fieldmap-generator.svg)](https://travis-ci.org/dexafree/fieldmap-generator) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.dexafree/fieldmap-generator/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.github.dexafree/fieldmap-generator)

FieldMapGenerator is a small utility that will help you convert your objects to a `Map<String, String>`, which will 
contain the fields declared in your object.

It comes with a handy annotation, `@ExposeField`, which will enable you to choose which fields will be exported, and even
choose the name that they will have once exported (aka, the key of the Map).

It's very useful when you want to send objects through Retrofit, but you need to do it with a `@FormUrlEncoded` POST request, 
as it can be passed as a `@FieldMap`.

## Usage

The `FieldMapGenerator` class exposes two static methods:

1. `toMap(Object object, FieldMapGenerator.MODE mode)`:
    * Object object: The object to be converted to `HashMap<String, String>`.
    * MODE mode: It can be one of 
         * FULL_OBJECT: The full object will be serialized, including protected and private fields.
         * ONLY_VISIBLE: Only visible fields (public) will be serialized.
         * ONLY_ANNOTATED: Only fields containing @ExposeField annotation will be serialized.
2. `toMap(Object object)`. Default behaviour. Uses the `ONLY_ANNOTATED` mode.

### The `@ExposeField` annotation

You can annotate any field with `@ExposeField`. This annotation receives an optional parameter, which will be used as the field
name. It can be used as `@ExposeField("field_name")`.

Take into account that if there's a duplication in the Map keys (you name two fields with the same name), an exception
will be thrown as a warning.

> ### NOTE
> If any of the fields have the @ExposeField annotation with a field name, and is exported, the given name will always be used.
>
> Example: A private field with @ExposeField("name") will not be exported if ONLY_VISIBLE mode is used, but if any other mode
> is used, the Map key for that field will be "name".
>
> You can take a look at the tests for further examples.

### Quick sample

**MyRequest.java**

```java
public class MyRequest {
    
    @ExposeField("username")
    private String username;
    
    @ExposeField("user_age")
    private int age;
    
    // Constructor, getters, setters...
    
}
```

**MyInteractor.java**

```java
MyRequest request = new MyRequest("myUserName", 30);
Map<String, String> fields = FieldMapGenerator.toMap(request);
retrofitService.send(fields);
```


## Tests

For running the tests, you can do it with the following command:

```
./gradlew test --tests "*Test"
```

## Installation

You can:
 
1. Download the source code and copy the classes to your project
2. Download the [JAR from Maven Central](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22fieldmap-generator%22) 
3. If you use Gradle as your build system, include the next statement under your dependencies section:

```groovy
dependencies {
    // ...
    compile 'com.github.dexafree:fieldmap-generator:1.0.2'
}
```

## License

This library is released under Apache V2 license.

```
Copyright 2016 Dexafree <contacto@dexa-dev.com>.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```