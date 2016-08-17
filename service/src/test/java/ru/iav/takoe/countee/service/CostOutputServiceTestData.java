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
public class CostOutputServiceTestData {

    @DataProvider(name = "getCostLists")
    public static Object[][] getCostLists() throws Exception {
        return new Object[][] {
                {asList(
                        cost(1, "a", "1.01")
                ),                              "01.01\n" +
                                                "1 a\n\n"},

                {asList(
                        cost(1, "a", "10.01"),
                        cost(-350, "cbdW", "1.01"),
                        cost(100, "aaaa", "1.01")
                ),
                                                "10.01\n" +
                                                "1 a\n\n" +
                                                "01.01\n" +
                                                "-350 cbdW\n" +
                                                "100 aaaa\n\n"
                                                },

                {emptyList(),                   ""},

                {null,                          ""}
        };
    }

    private static Cost cost(Integer amount, String comment, String dateString) throws Exception {
        Cost cost = CostFactory.getInstance().create(BigDecimal.valueOf(amount), comment);
        cost.setTimestamp(CostOutputService.dateFormat.parse(dateString));
        return cost;
    }

}