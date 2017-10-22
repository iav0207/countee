package ru.iav.takoe.countee.crypt.impl;

import javax.annotation.ParametersAreNonnullByDefault;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static ru.iav.takoe.countee.logging.LogService.logInfo;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

@ParametersAreNonnullByDefault
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

    @Test(enabled = false,  // manual run
            dataProvider = "hugeString")
    public void testHugeInput(String text, String key) throws Exception {
        String result = runSymmetricTestNoLogging(text, key);
        assertEquals(result, text.trim());
    }

    private String runSymmetricTest(String text, String key) {
        return runSymmetricTest(text, key, true);
    }

    private String runSymmetricTestNoLogging(String text, String key) {
        return runSymmetricTest(text, key, false);
    }

    private String runSymmetricTest(String text, String key, boolean isLoggingEnabled) {
        long tic = System.currentTimeMillis();
        String cypher = simpleGostCryptFacade.encrypt(text, key);
        String result = simpleGostCryptFacade.decrypt(cypher, key);
        long toc = System.currentTimeMillis();
        if (isLoggingEnabled) {
            logInfo(String.format("\nOpen text: %s\nCypher: %s\nResult: %s", text, cypher, result));
        }
        logInfo(String.format("Time elapsed: %d ms", toc - tic));
        return result;
    }

    @DataProvider(name = "shouldBeSymmetricData")
    public static Object[][] shouldBeSymmetricData() {
        return new Object[][] {
                text(getRandomString(64)),
                text(getRandomString()),
                text(getRandomString() + " " + getRandomString()),
                text(getRandomString() + "    "),
                text(" " + getRandomString()),
                text("Добрый вечер 1293862?*&"),
                text(""),
                text(" ")
        };
    }

    @DataProvider(name = "hugeString")
    public static Object[][] hugeString() {
        return new Object[][] { text(getRandomString(11_111_111)) };
    }

    private static Object[] text(String s) {
        return new Object[] {s, getRandomString()};
    }

}
