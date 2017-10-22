package ru.iav.takoe.countee.da.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.vo.Cost;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.iav.takoe.countee.test.CounteeTestUtils.getListOfRandomCosts;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomInteger;

public class CostsCacheTest {

    private CostsCache cache = new CostsCache();

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
            assertEquals(result.contains(each),
                    thisMonthStart().isBefore(dateOf(each)));
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
        return DateTime.now()
                .withDayOfMonth(1)
                .withTimeAtStartOfDay();
    }

    private static DateTime dateOf(Cost cost) {
        return new DateTime(cost.getTimestamp());
    }

    private List<Cost> listOfCosts() {
        return getListOfRandomCosts(getRandomInteger(10_000));
    }

}
