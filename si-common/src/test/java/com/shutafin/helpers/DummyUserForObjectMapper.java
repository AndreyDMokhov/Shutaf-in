package com.shutafin.helpers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DummyUserForObjectMapper {

    @JsonProperty(value = "age")
    private Integer age;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "available")
    private Boolean isAvailable;

    public DummyUserForObjectMapper() {
    }

    public DummyUserForObjectMapper(Integer age, String name, Boolean isAvailable) {
        this.age = age;
        this.name = name;
        this.isAvailable = isAvailable;
    }


    public Integer getAge() {
        return age;
    }
    public String getName() {
        return name;
    }


    public Boolean getAvailable() {
        return isAvailable;
    }
}
