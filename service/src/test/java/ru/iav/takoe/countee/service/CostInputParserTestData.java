package ru.iav.takoe.countee.service;

import java.math.BigDecimal;

import org.testng.annotations.DataProvider;
import ru.iav.takoe.countee.vo.Cost;
import ru.iav.takoe.countee.vo.CostFactory;
import ru.iav.takoe.countee.vo.CostValidator;

public class CostInputParserTestData {

    private static CostFactory costFactory = new CostFactory(new CostValidator());

    @DataProvider(name = "getValidStrings")
    public static Object[][] getValidStrings() {
        return new Object[][] {
                {"5 abc",           cost(5d, "abc")},
                {"-5.0 t",          cost(-5d, "t")},
                {"-5 ta_5",         cost(-5d, "ta_5")},
        };
    }

    @DataProvider(name = "getInvalidStrings")
    public static Object[][] getInvalidStrings() {
        return new Object[][] {
                {"a abc",           null},
                {"0  ",             null},
                {"-5 t a",          null},
                {"201 d 1",         null},
                {"0.1 ${#+",        null},
                {"201 --1",         null},
                {" 2 a",            null},
                {"2 a ",            null}
        };
    }

    private static Cost cost(Double amount, String comment) {
        return costFactory.create(BigDecimal.valueOf(amount), comment);
    }

}
