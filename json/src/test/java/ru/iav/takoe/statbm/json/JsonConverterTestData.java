package ru.iav.takoe.statbm.json;

import ru.iav.takoe.statbm.json.vo.TestObject;

import java.util.UUID;

import static ru.iav.takoe.statbm.utils.DateUtils.now;
import static ru.iav.takoe.statbm.utils.TestUtils.getRandomBoolean;
import static ru.iav.takoe.statbm.utils.TestUtils.getRandomInteger;
import static ru.iav.takoe.statbm.utils.TestUtils.getRandomString;

/**
 * Created by takoe on 24.07.16.
 */
class JsonConverterTestData {

    static TestObject getTestObject() {
        TestObject object = new TestObject();
        object.setUuid(UUID.randomUUID());
        object.setaBoolean(getRandomBoolean());
        object.setInteger(getRandomInteger());
        object.setString(getRandomString());
        object.setDate(now());
        return object;
    }

}
