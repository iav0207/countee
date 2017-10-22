package ru.iav.takoe.countee.service;

import org.testng.annotations.Test;
import ru.iav.takoe.countee.service.exception.CostInputValidationException;
import ru.iav.takoe.countee.vo.Cost;
import ru.iav.takoe.countee.vo.CostFactory;
import ru.iav.takoe.countee.vo.CostValidator;

import static org.mockito.Mockito.spy;
import static org.testng.Assert.assertEquals;

public class CostInputParserTest {

    private CostInputValidator validator = new CostInputValidator();

    private CostFactory costFactory = spy(new CostFactory(new CostValidator()));

    private CostInputParser parser = new CostInputParser(validator, costFactory);

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
