package ru.iav.takoe.countee.da.impl;

import javax.annotation.ParametersAreNonnullByDefault;

import org.joda.time.DateTime;
import org.testng.annotations.DataProvider;
import ru.iav.takoe.countee.vo.Cost;

import static ru.iav.takoe.countee.test.CounteeTestUtils.cost;

@ParametersAreNonnullByDefault
public class CostBulkSaverTestData {

    private static final DateTime TODAY = DateTime.now().withTimeAtStartOfDay();

    private static final DateTime MONTH_AGO = TODAY.minusMonths(1);

    static DateCostMultimap emptyBulk() {
        return bulkOf();
    }

    static DateCostMultimap bulkOf(Cost... costs) {
        DateCostMultimap multimap = new DateCostMultimap();
        for (Cost cost : costs) {
            multimap.put(new DateTime(cost.getTimestamp()), cost);
        }
        return multimap;
    }

    @DataProvider(name = "bulkSave")
    public static Object[][] bulkSave() {
        return new Object[][]{
                {
                        bulkOf(
                                cost(MONTH_AGO),
                                cost(TODAY),
                                cost(MONTH_AGO),
                                cost(TODAY)
                        ),
                        null
                },

        };
    }



}
