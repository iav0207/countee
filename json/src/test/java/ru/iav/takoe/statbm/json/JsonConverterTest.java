package ru.iav.takoe.statbm.json;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.statbm.json.vo.TestObject;

import java.math.BigDecimal;
import java.util.UUID;

import static org.testng.Assert.*;
import static ru.iav.takoe.statbm.json.JsonConverterTestData.getTestObject;
import static ru.iav.takoe.statbm.utils.DateUtils.now;
import static ru.iav.takoe.statbm.utils.TestUtils.*;

/**
 * Created by takoe on 24.07.16.
 */
public class JsonConverterTest {

    private JsonConverter converter;

    private TestObject object;

    private String json;

    @BeforeClass
    public void init() {
        converter = JsonConverter.getInstance();
        object = getTestObject();
        json = converter.serialize(object);
    }

    @Test
    public void shouldContainAllFields() {
        assertTrue(json.contains("uuid"));
        assertTrue(json.contains("string"));
        assertTrue(json.contains("integer"));
        assertTrue(json.contains("aBoolean"));
        assertTrue(json.contains("date"));
    }

    @Test
    public void shouldContainUuidString() {
        assertTrue(json.contains(object.getUuid().toString()));
    }

    @Test
    public void shouldContainStringValue() {
        assertTrue(json.contains(object.getString()));
    }

    @Test
    public void shouldContainIntegerValue() {
        assertTrue(json.contains(object.getInteger().toString()));
    }

    @Test
    public void shouldContainBooleanValue() {
        assertTrue(json.contains(object.getaBoolean().toString()));
    }

    @Test
    public void shouldNotIncludeNullField() {
        TestObject object = getTestObject();
        object.setString(null);
        String json = converter.serialize(object);

        assertFalse(json.contains("string"));
    }

}