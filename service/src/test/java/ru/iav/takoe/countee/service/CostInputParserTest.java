package ru.iav.takoe.countee.service;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.service.exception.CostInputValidationException;
import ru.iav.takoe.countee.vo.Cost;

import static org.testng.Assert.assertEquals;

public class CostInputParserTest {

    private CostInputParser parser;

    @BeforeClass
    public void init() {
        parser = CostInputParser.getInstance();
    }

    @Test(dataProvider = "getValidStrings", dataProviderClass = CostInputParserTestData.class)
    public void shouldParseValidStringsToCosts(String input, Cost expected) throws Exception {
        Cost actual = parser.parseAsCost(input);
        assertSameCost(actual, expected);
    }

    @Test(expectedExceptions = CostInputValidationException.class,
            dataProvider = "getInvalidStrings", dataProviderClass = CostInputParserTestData.class)
    public void shouldThrowExceptionOnInvalidInputString (String input, Cost expected) throws Exception {
        Cost actual = parser.parseAsCost(input);
    }

    private static void assertSameCost(Cost actual, Cost expected) {
        assertEquals(actual.getAmount(), expected.getAmount());
        assertEquals(actual.getComment(), expected.getComment());
    }

}
