package ru.iav.takoe.countee.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.vo.Cost;

import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class CostCommentsServiceTest {

    @Mock
    private CostReader reader;

    @InjectMocks
    private CostCommentsService service;

    private Set<String> result;

    @BeforeClass
    public void init() {
        initMocks(this);
    }

    @BeforeMethod
    public void reset() {
        Mockito.reset(reader);
    }

    @Test
    public void shouldReturnEmptySetIfReaderReturnedNull() throws Exception {
        letReaderReturn(null);
        execute();
        assertResultIsEmpty();
    }

    @Test
    public void shouldReturnEmptySetIfReaderReturnedEmptyList() throws Exception {
        letReaderReturn(new ArrayList<Cost>());
        execute();
        assertResultIsEmpty();
    }

    @Test(dataProvider = "lowerCaseComments", dataProviderClass = CostCommentsServiceTestData.class)
    public void shouldReturnAllCommentsDistinct(List<Cost> costs, Set<String> expected) throws Exception {
        runTestWithData(costs, expected);
    }

    @Test(dataProvider = "differentCaseComments", dataProviderClass = CostCommentsServiceTestData.class)
    public void shouldCastCommentsToLowerCase(List<Cost> costs, Set<String> expected) throws Exception {
        runTestWithData(costs, expected);
    }

    @Test(dataProvider = "disorderedComments", dataProviderClass = CostCommentsServiceTestData.class)
    public void shouldArrangeCommentsAlphabetically(List<Cost> costs, List<String> expected) throws Exception {
        letReaderReturn(costs);
        execute();
        Iterator<String> iteratorExpected = expected.iterator();
        Iterator<String> iteratorActual = result.iterator();
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(iteratorActual.next(), iteratorExpected.next());
        }
        assertEquals(result.size(), expected.size());
    }

    @Test(dataProvider = "filterZeroTotals", dataProviderClass = CostCommentsServiceTestData.class)
    public void shouldFilterCommentsOfCostsWithZeroTotalAmount(List<Cost> costs, Set<String> expected)
            throws Exception {
        runTestWithData(costs, expected);
    }

    private void runTestWithData(List<Cost> costs, Set<String> expected) {
        letReaderReturn(costs);
        execute();
        assertResultIsEqualTo(expected);
    }

    private void execute() {
        result = service.getAllCommentsSet();
    }

    private void letReaderReturn(List<Cost> costs) {
        doReturn(costs).when(reader).readAllCosts();
    }

    private void assertResultIsEmpty() {
        assertNotNull(result);
        assertEquals(result.size(), 0);
    }

    private void assertResultIsEqualTo(Set<String> expected) {
        assertEquals(result, expected);
    }

}
