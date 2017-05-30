package ru.iav.takoe.countee.json;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.json.exception.DeserializationException;
import ru.iav.takoe.countee.json.vo.TestObject;
import ru.iav.takoe.countee.json.vo.TestObjectWithNoFields;

import static org.testng.Assert.*;

public class JsonParserTest {

    private JsonParser parser;

    @BeforeClass
    public void init() {
        parser = JsonParser.getInstance();
    }

    @Test(dataProvider = "getJsonToParse", dataProviderClass = JsonParserTestData.class)
    public void shouldParseFields(String json, TestObject expected) throws Exception {
        TestObject actual = parse(json);

        assertEquals(actual.getString(), expected.getString());
        assertEquals(actual.getaBoolean(), expected.getaBoolean());
        assertEquals(actual.getInteger(), expected.getInteger());
        assertNotNull(actual.getUuid());
        assertNotNull(actual.getDate());
    }

    @Test(dataProvider = "getJsonToParse", dataProviderClass = JsonParserTestData.class)
    public void shouldStaySilentOnTypeMismatch(String json, TestObject object) throws Exception {
        parser.deserialize(json, TestObjectWithNoFields.class);
    }

    @Test(expectedExceptions = DeserializationException.class,
            dataProvider = "getInvalidJson", dataProviderClass = JsonParserTestData.class)
    public void shouldThrowExceptionOnInvalidJson(String json, TestObject object) throws Exception {
        parse(json);
    }

    @Test(dataProvider = "getEmptyString", dataProviderClass = JsonParserTestData.class)
    public void shouldReturnNullOnEmptyOrNullString(String json, TestObject object) throws Exception {
        assertNull(parse(json));
    }

    private TestObject parse(String json) {
        return parser.deserialize(json, TestObject.class);
    }

}
