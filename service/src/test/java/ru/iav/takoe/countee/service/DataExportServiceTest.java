package ru.iav.takoe.countee.service;

import javax.annotation.ParametersAreNonnullByDefault;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.da.impl.DataExporterImpl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

@ParametersAreNonnullByDefault
public class DataExportServiceTest {

    @Mock
    private DataExporterImpl dataExporter;

    @InjectMocks
    private DataExportService service;

    @BeforeClass
    public void init() {
        initMocks(this);
    }

    @Test
    public void shouldOnlyCallDataExporter() throws Exception {
        String expected = getRandomString(100);
        doReturn(expected).when(dataExporter).exportAllData(anyString());

        assertEquals(service.exportAllData(getRandomString()), expected);
    }

}
