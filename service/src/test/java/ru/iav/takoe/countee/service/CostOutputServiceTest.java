package ru.iav.takoe.countee.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.da.CostReader;
import ru.iav.takoe.countee.vo.Cost;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

/**
 * Created by takoe on 16.08.16.
 */
public class CostOutputServiceTest {

    @Mock
    private CostReader costReader;

    @Mock
    private MonthOutputService monthOutputService;

    @InjectMocks
    private CostOutputService service;

    @BeforeClass
    public void init() {
        initMocks(this);
    }

    @Test(dataProvider = "getCostLists", dataProviderClass = CostOutputServiceTestData.class)
    public void shouldReturnListOfCostsFromReader(List<Cost> costs, String expected) throws Exception {
        doReturn(costs).when(costReader).readCostsForThisMonth();
        assertEquals(service.getCurrentMonthOutput(), expected);
    }

}