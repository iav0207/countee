package ru.iav.takoe.statbm.json;

import org.testng.annotations.DataProvider;
import ru.iav.takoe.statbm.json.vo.TestObject;

import static ru.iav.takoe.statbm.utils.TestUtils.getRandomString;

/**
 * Created by takoe on 24.07.16.
 */
public class JsonParserTestData {

    @DataProvider(name = "getJsonToParse")
    public static Object[][] getJsonToParse() {
        return new Object[][] {
                {
                    "{\n" +
                        "  \"uuid\": \"5870d857-2a91-4032-a449-7808fefa3b71\",\n" +
                        "  \"string\": \"vJK6DqFUZF\",\n" +
                        "  \"integer\": 1549795924,\n" +
                        "  \"aBoolean\": true,\n" +
                        "  \"date\": \"Jul 24, 2016 2:47:54 PM\"\n" +
                        "}",
                        getTestObject("vJK6DqFUZF", 1549795924, true)
                }
        };
    }

    @DataProvider(name = "getInvalidJson")
    public static Object[][] getInvalidJson() {
        return new Object[][] {
                {getRandomString(), null},
        };
    }

    @DataProvider(name = "getEmptyString")
    public static Object[][] getEmptyString() {
        return new Object[][] {
                {null, null},
                {"", null},
                {"    ", null}
        };
    }

    private static TestObject getTestObject(String string, Integer integer, Boolean aBoolean) {
        TestObject object = new TestObject();

        object.setString(string);
        object.setInteger(integer);
        object.setaBoolean(aBoolean);

        return object;
    }

}
