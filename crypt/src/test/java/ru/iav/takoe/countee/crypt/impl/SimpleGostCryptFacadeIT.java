package ru.iav.takoe.countee.crypt.impl;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static ru.iav.takoe.countee.logging.LogService.logInfo;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

/**
 * Created by takoe on 06.02.17.
 */
public class SimpleGostCryptFacadeIT {

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
        long tic = System.currentTimeMillis();
        String cypher = simpleGostCryptFacade.encrypt(text, key);
        String result = simpleGostCryptFacade.decrypt(cypher, key);
        long toc = System.currentTimeMillis();
        logInfo(String.format("\nOpen text: %s\nCypher: %s\nResult: %s", text, cypher, result));
        logInfo(String.format("Time elapsed: %d ms", toc - tic));
        return result;
    }

    @DataProvider(name = "shouldBeSymmetricData")
    public static Object[][] shouldBeSymmetricData() {
        return new Object[][] {
                text(getRandomString(64)),
                text(getRandomString()),
                text(getRandomString(1_111_111)),
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