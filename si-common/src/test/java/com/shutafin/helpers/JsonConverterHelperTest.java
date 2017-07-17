package com.shutafin.helpers;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
public class JsonConverterHelperTest {

    private static final String JSON = "{\"age\":18,\"name\":\"Peter\",\"available\":false}";
    private static final DummyUserForObjectMapper DUMMY_USER = new DummyUserForObjectMapper(18, "Peter", Boolean.FALSE);


    @Test
    public void objectToJsonTest_Positive() {

        JsonConverterHelper<DummyUserForObjectMapper> converterHelper = new JsonConverterHelper<>();
        String result = converterHelper.getJson(DUMMY_USER);
        assertNotNull(result);
    }

    @Test
    public void jsonToObjectTest_Positive() {
        JsonConverterHelper<DummyUserForObjectMapper> converterHelper = new JsonConverterHelper<>();
        DummyUserForObjectMapper object = converterHelper.getObject(JSON, DummyUserForObjectMapper.class);
        assertNotNull(object);
        assertNotNull(object.getName());
        assertNotNull(object.getAge());
        assertNotNull(object.getAvailable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void malformedJsonToObjectTest_Positive() {
        JsonConverterHelper<DummyUserForObjectMapper> converterHelper = new JsonConverterHelper<>();
        converterHelper.getObject(JSON.substring(1, JSON.length()-1), DummyUserForObjectMapper.class);

    }



}
