package ru.iav.takoe.countee.service;

import org.testng.annotations.DataProvider;
import ru.iav.takoe.countee.vo.Cost;
import ru.iav.takoe.countee.vo.CostFactory;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

/**
 * Created by takoe on 16.08.16.
 */
public class ReadCostServiceTestData {

    @DataProvider(name = "getCostLists")
    public static Object[][] getCostLists() {
        return new Object[][] {
                {asList(
                        cost(1, "a")
                ),                              "1 a\n"},

                {asList(
                        cost(1, "a"),
                        cost(-350, "cbdW")
                ),                              "1 a\n" +
                                                "-350 cbdW\n"},

                {emptyList(),                   ""},

                {null,                          ""}
        };
    }

    private static Cost cost(Integer amount, String comment) {
        return CostFactory.getInstance().create(BigDecimal.valueOf(amount), comment);
    }

}
