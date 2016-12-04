package ru.iav.takoe.countee.model.filter.impl;

import org.testng.annotations.Test;
import ru.iav.takoe.countee.vo.Cost;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by takoe on 30.11.16.
 */
public class CostCommentFilterTest {

    @Test(dataProvider = "simpleSet", dataProviderClass = CostCommentFilterTestData.class)
    public void shouldLeaveOnlyCostsWithCommentInTheSet(List<Cost> given, List<Cost> expected) throws Exception {
        testEquality(CostCommentFilterTestData.simpleFilter.filter(given), expected);
    }

    @Test(dataProvider = "nullableSet", dataProviderClass = CostCommentFilterTestData.class)
    public void shouldNotFailIfSomeOfCostsOrCommentsAreNull(List<Cost> given, List<Cost> expected) throws Exception {
        testEquality(CostCommentFilterTestData.simpleFilter.filter(given), expected);
    }

    @Test(dataProvider = "simpleSet", dataProviderClass = CostCommentFilterTestData.class)
    public void shouldNotFailIfSomeOfStringsInTheFilterAreNull(List<Cost> given, List<Cost> expected) throws Exception {
        testEquality(CostCommentFilterTestData.nullableFilter.filter(given), expected);
    }

    @Test
    public void shouldReturnEmptyListIfGivenListIsEmpty() throws Exception {
        assertEmpty(CostCommentFilterTestData.nullableFilter.filter(new ArrayList<Cost>()));
    }

    @Test
    public void shouldReturnEmptyListIfGivenListIsNull() throws Exception {
        assertEmpty(CostCommentFilterTestData.nullableFilter.filter(null));
    }

    @Test(dataProvider = "upperCaseSet", dataProviderClass = CostCommentFilterTestData.class)
    public void shouldCompareIgnoringCase(List<Cost> given, List<Cost> expected) throws Exception {
        testEquality(CostCommentFilterTestData.simpleFilter.filter(given), expected);
    }

    private static void testEquality(List<Cost> actual, List<Cost> expected) {
        assertEquals(actual.size(), expected.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(actual.get(i).getComment(), expected.get(i).getComment());
        }
    }

    private static void assertEmpty(List<Cost> list) {
        assertNotNull(list);
        assertEquals(list.size(), 0);
    }

    private static void throwNotImplementedException() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}