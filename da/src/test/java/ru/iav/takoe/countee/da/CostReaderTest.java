package ru.iav.takoe.countee.da;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.json.JsonParser;
import ru.iav.takoe.countee.persistence.file.LocalReader;
import ru.iav.takoe.countee.vo.Cost;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomBigDecimal;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomDateOfLastYear;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomInteger;

public class CostReaderTest {

    @Mock
    private CostFileNamesFactory costFileNamesFactory;

    @Mock
    private JsonParser jsonParser;

    @Mock
    private LocalReader localReader;

    @Spy
    private CostsCache cache;

    @InjectMocks
    private CostReader costReader;

    @BeforeClass
    public void init() {
        initMocks(this);
    }

    @BeforeMethod
    public void reset() {
        Mockito.reset(costFileNamesFactory, jsonParser, localReader, cache);
    }

    @Test
    public void shouldReturnEmptyListIfListOfFilesIsEmpty() throws Exception {
        doReturn(Collections.EMPTY_LIST).when(costFileNamesFactory).getAllCostFiles();
        assertResultIsEmptyList();
    }

    @Test
    public void shouldSilentlyReturnEmptyListIfAnyExceptionIsThrown() throws Exception {
        letFileFactoryReturnAllFiles();
        letParserThrowRuntimeException();
        assertResultIsEmptyList();
    }

    @Test
    public void shouldReturnListOfAllCostsReturnedByParserIfEverythingIsOk() throws Exception {
        letFileFactoryReturnAllFiles();
        doReturn("json").when(localReader).read(any(File.class));
        List<Cost> allCosts = listOfCosts();
        doReturn(allCosts).when(cache).getAllCosts();
        assertEquals(costReader.readAllCosts(), allCosts);
    }

    @Test
    public void shouldSilentlyReturnEmptyListForThisMonthIfAnyExceptionIsThrown() throws Exception {
        letFileFactoryReturnAllFiles();
        letParserThrowRuntimeException();
        assertResultIsEmptyList();
    }

    @Test
    public void shouldReturnListOfMonthCostsReturnedByParserIfEverythingIsOk() throws Exception {
        letFileFactoryReturnAllFiles();
        doReturn("json").when(localReader).read(any(File.class));
        List<Cost> listOfCostsForThisMonth = listOfCosts();
        doReturn(listOfCostsForThisMonth).when(cache).getCostsForThisMonth();
        assertEquals(costReader.readCostsForThisMonth(), listOfCostsForThisMonth);
    }

    private void assertResultIsEmptyList() throws Exception {
        List<Cost> result = costReader.readAllCosts();
        assertNotNull(result);
        assertEquals(result.size(), 0);
    }

    private void letFileFactoryReturnAllFiles() {
        doReturn(listOfFiles()).when(costFileNamesFactory).getAllCostFiles();
        doReturn(file()).when(costFileNamesFactory).getActualFile();
    }

    private void letParserReturn(CostsData costsData) {
        doReturn(costsData).when(jsonParser).deserialize(anyString(), any(Class.class));
    }

    private void letParserThrowRuntimeException() {
        doThrow(new RuntimeException()).when(jsonParser).deserialize(anyString(), any(Class.class));
    }

    private List<File> listOfFiles() {
        return Collections.singletonList(file());
    }

    private File file() {
        return new File("file");
    }

    private CostsData costsData(List<Cost> listOfCosts) {
        CostsData costsData = new CostsData();
        costsData.setDescriptor(new LinkedHashMap<String, Cost>());
        for (Cost each : listOfCosts) {
            costsData.getDescriptor().put(each.getUuid().toString(), each);
        }
        return costsData;
    }

    private List<Cost> listOfCosts() {
        List<Cost> costs = new ArrayList<>();
        for (int i = 0; i < getRandomInteger(10); i++) {
            costs.add(cost());
        }
        return costs;
    }

    private Cost cost() {
        Cost cost = mock(Cost.class);
        doReturn(UUID.randomUUID()).when(cost).getUuid();
        doReturn(getRandomDateOfLastYear()).when(cost).getTimestamp();
        doReturn(getRandomBigDecimal()).when(cost).getAmount();
        return cost;
    }

}
