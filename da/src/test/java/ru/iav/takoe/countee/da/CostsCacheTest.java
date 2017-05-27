package ru.iav.takoe.countee.da;

import org.joda.time.DateTime;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.vo.Cost;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomBigDecimal;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomDateOfLastYear;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomInteger;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

public class CostsCacheTest {

    private CostsCache cache = CostsCache.getInstance();

    @BeforeMethod
    public void reset() {
        cache.invalidate();
    }

    @Test
    public void shouldClearAllDataOnInvalidation() throws Exception {
        cache.put(listOfCosts());
        assertFalse(cache.isEmpty());
        assertFalse(cache.getAllCosts().isEmpty());
        cache.invalidate();
        assertTrue(cache.isEmpty());
        assertTrue(cache.getAllCosts().isEmpty());
    }

    @Test
    public void shouldContainAllAddedCosts() throws Exception {
        List<Cost> allCosts = listOfCosts();
        cache.put(allCosts);
        List<Cost> result = cache.getAllCosts();
        assertTrue(result.containsAll(allCosts));
    }

    @Test
    public void shouldReturnCostsForThisMonth() throws Exception {
        List<Cost> allCosts = listOfCosts();
        cache.put(allCosts);
        List<Cost> result = cache.getCostsForThisMonth();
        for (Cost each : allCosts) {
            assertEquals(result.contains(each), thisMonthStart().isBefore(dateOf(each)));
        }
    }

    @Test
    public void shouldClearAllPreviousDataOnPut() throws Exception {
        List<Cost> previousCosts = listOfCosts();
        List<Cost> newCosts = listOfCosts();
        cache.put(previousCosts);
        cache.put(newCosts);
        List<Cost> result = cache.getAllCosts();
        for (Cost each : previousCosts) {
            assertFalse(result.contains(each));
        }
        assertTrue(result.containsAll(newCosts));
    }

    private static DateTime thisMonthStart() {
        return DateTime.now().withDayOfMonth(1).withTimeAtStartOfDay();
    }

    private static DateTime dateOf(Cost cost) {
        return new DateTime(cost.getTimestamp());
    }

    private List<Cost> listOfCosts() {
        List<Cost> list = new ArrayList<>();
        for (int i = 0; i < getRandomInteger(10000); i++) {
            list.add(cost());
        }
        return list;
    }

    private Cost cost() {
        Cost cost = mock(Cost.class);
        doReturn(UUID.randomUUID()).when(cost).getUuid();
        doReturn(getRandomDateOfLastYear()).when(cost).getTimestamp();
        doReturn(getRandomBigDecimal()).when(cost).getAmount();
        doReturn(getRandomString()).when(cost).getComment();
        return cost;
    }

}
