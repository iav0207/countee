package ru.iav.takoe.countee.json;

import ru.iav.takoe.countee.json.vo.TestObject;

import java.util.UUID;

import static ru.iav.takoe.countee.utils.DateUtils.now;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomBoolean;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomInteger;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

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
