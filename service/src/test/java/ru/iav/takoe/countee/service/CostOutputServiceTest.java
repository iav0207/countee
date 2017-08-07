package ru.iav.takoe.countee.service;

import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.da.Reader;
import ru.iav.takoe.countee.vo.Cost;

import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

public class CostOutputServiceTest {

    @Mock
    private Reader<Cost> costReader;

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
        doReturn(costs).when(costReader).readForThisMonth();
        assertEquals(service.getCurrentMonthOutput(), expected);
    }

}
