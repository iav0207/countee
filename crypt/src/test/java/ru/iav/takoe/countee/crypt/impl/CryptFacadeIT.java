package ru.iav.takoe.countee.crypt.impl;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static ru.iav.takoe.countee.logging.LogService.logInfo;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

/**
 * Created by takoe on 06.02.17.
 */
public class CryptFacadeIT {

    private SimpleGostCryptFacade simpleGostCryptFacade = new SimpleGostCryptFacade();

    @Test(dataProvider = "shouldBeSymmetricData")
    public void shouldBeSymmetricAfterStringsTrimmed(String text, String key) throws Exception {
        String result = runSymmetricTest(text, key);
        assertEquals(result, text.trim());
    }

    @Test
    public void shouldReturnEmptyStringIfInputWasNull() throws Exception {
        String result = runSymmetricTest(null, getRandomString());
        assertEquals(result, "");
    }

    private String runSymmetricTest(String text, String key) {
        String cypher = simpleGostCryptFacade.encrypt(text, key);
        String result = simpleGostCryptFacade.decrypt(cypher, key);
        logInfo(String.format("\nOpen text: %s\nCypher: %s\nResult: %s\n", text, cypher, result));
        return result;
    }

    @DataProvider(name = "shouldBeSymmetricData")
    public static Object[][] shouldBeSymmetricData() {
        return new Object[][] {
                text(getRandomString(64)),
                text(getRandomString()),
                text(getRandomString(11)),
                text(getRandomString() + " " + getRandomString()),
                text(getRandomString() + "    "),
                text(" " + getRandomString()),
                text("Добрый вечер 1293862?*&"),
                text(""),
                text(" ")
        };
    }

    private static Object[] text(String s) {
        return new Object[] {s, getRandomString()};
    }

}