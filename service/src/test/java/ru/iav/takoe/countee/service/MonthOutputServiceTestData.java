package ru.iav.takoe.countee.service;

import org.joda.time.DateTime;
import org.testng.annotations.DataProvider;
import ru.iav.takoe.countee.vo.Cost;

import java.util.ArrayList;
import java.util.List;

import static ru.iav.takoe.countee.service.utils.CounteeTestUtils.cost;
import static ru.iav.takoe.countee.utils.DateUtils.month;

public class MonthOutputServiceTestData {

    @DataProvider(name = "spreadCount")
    public static Object[][] spreadCount() {
        return new Object[][] {
                {list(date(8, 2014), date(1, 2015)),    5},
                {list(date(8, 2018), date(8, 2015)),    3*12},
                {list(date(8, 1990)),                   0},
                {withNull(list(date(8, 1990))),         0}
        };
    }

    @DataProvider(name = "specifiedMonth")
    public static Object[][] specifiedMonth() {     // minus 1
        return new Object[][] {
            {
                list(date(12, 2014), date(1, 2015)),
                    month(date(12, 2014))
            },
                {
                    list(date(7, 2014), date(7, 2015), date(7, 2015), date(8, 2015)),
                        month(date(7, 2015))
                },
                {
                        list(date(6, 2016), date(6, 2015), date(7, 2017)),
                        null // no results
                }
        };
    }

    private static DateTime date(int month, int year) {
        return new DateTime(year, month, 1, 1, 1);
    }

    private static List<Cost> list(DateTime... dates) {
        List<Cost> list = new ArrayList<>();
        for (DateTime each : dates) {
            list.add(cost(each));
        }
        return list;
    }

    private static List<Cost> withNull(List<Cost> list) {
        list.add(null);
        return list;
    }

}
