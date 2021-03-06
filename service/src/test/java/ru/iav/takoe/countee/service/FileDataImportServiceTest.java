package ru.iav.takoe.countee.service;

import java.io.File;

import javax.annotation.ParametersAreNonnullByDefault;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.da.DataImporter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

@ParametersAreNonnullByDefault
public class FileDataImportServiceTest {

    @Mock
    private DataImporter<File> dataImporter;

    @Mock
    private MonthOutputService monthOutputService;

    @InjectMocks
    private FileDataImportService service;

    @BeforeMethod
    public void reset() {
        initMocks(this);
    }

    @Test
    public void shouldCallImporter() throws Exception {
        File file = mock(File.class);
        String pwd = getRandomString();
        service.importData(file, pwd);

        verify(dataImporter).importData(same(file), eq(pwd));
    }

    @Test
    public void shouldInvalidateMonthOutputServiceCache() throws Exception {
        service.importData(mock(File.class), getRandomString());

        verify(monthOutputService).invalidate();
    }

    @Test
    public void shouldInvalidateMonthOutputServiceCacheEvenIfImportFails() throws Exception {
        doThrow(new RuntimeException()).when(dataImporter).importData(any(File.class), anyString());

        try {
            service.importData(mock(File.class), getRandomString());
            Assert.fail();
        } catch (RuntimeException expected) {
            verify(monthOutputService).invalidate();
        }
    }

}
