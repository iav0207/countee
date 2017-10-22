package ru.iav.takoe.countee.da.impl;

import javax.annotation.ParametersAreNonnullByDefault;

import org.joda.time.DateTime;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.vo.Cost;

import static org.testng.Assert.assertEquals;
import static ru.iav.takoe.countee.test.CounteeTestUtils.cost;
import static ru.iav.takoe.countee.utils.DateUtils.month;
import static ru.iav.takoe.countee.utils.DateUtils.today;

@ParametersAreNonnullByDefault
public class DateCostMultimapTest {

    private DateCostMultimap multimap;

    @BeforeMethod
    public void init() {
        multimap = new DateCostMultimap();
    }

    @Test
    public void shouldAddCostsWithEqualTimestamps() throws Exception {
        DateTime date = month(today());
        Cost cost1 = cost(date);
        Cost cost2 = cost(date);

        multimap.put(date, cost1);
        multimap.put(date, cost2);

        assertEquals(multimap.size(), 2);
    }

}
