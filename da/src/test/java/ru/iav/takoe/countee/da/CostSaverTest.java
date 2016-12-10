package ru.iav.takoe.countee.da;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.da.exception.CostNotSavedException;
import ru.iav.takoe.countee.json.JsonConverter;
import ru.iav.takoe.countee.persistence.file.LocalWriter;
import ru.iav.takoe.countee.vo.Cost;
import ru.iav.takoe.countee.vo.CostFactory;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomBigDecimal;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

/**
 * Created by takoe on 26.07.16.
 */
public class CostSaverTest {

    @Mock
    private JsonConverter jsonConverter;

    @Mock
    private LocalWriter writer;

    @Mock
    private CostReader costReader;

    @Mock
    private CostsCache cache;

    @InjectMocks
    private CostSaver costSaver;

    private CostFactory costFactory = CostFactory.getInstance();

    @BeforeClass
    public void init() {
        initMocks(this);
    }

    @BeforeMethod
    public void reset() {
        Mockito.reset(cache);
    }

    @Test(expectedExceptions = CostNotSavedException.class)
    public void shouldThrowExceptionIfSerializationFails() throws Exception {
        doThrow(new RuntimeException()).when(jsonConverter).serialize(anyCost());
        costSaver.save(createCost());
    }

    @Test(expectedExceptions = CostNotSavedException.class)
    public void shouldThrowExceptionIfFileWritingFails() throws Exception {
        doReturn(getRandomString()).when(jsonConverter).serialize(anyCost());
        doThrow(new RuntimeException()).when(writer).append(anyString(), anyFile());
        costSaver.save(createCost());
    }

    @Test
    public void shouldBeSilentIfEverythingIsOk() throws Exception {
        configureDependenciesToRunOk();
        costSaver.save(createCost());
    }

    @Test
    public void shouldInvalidateCacheOnCostSaving() throws Exception {
        configureDependenciesToRunOk();
        verify(cache, never()).invalidate();
        costSaver.save(createCost());
        verify(cache, times(1)).invalidate();
    }

    private void configureDependenciesToRunOk() {
        doReturn(getRandomString()).when(jsonConverter).serialize(anyCost());
        doReturn(new CostsData()).when(costReader).getDeserializedData(anyFile());
        doNothing().when(writer).append(anyString(), anyFile());
    }

    private Cost createCost() {
        return costFactory.create(getRandomBigDecimal(), getRandomString());
    }

    private static Cost anyCost() {
        return any(Cost.class);
    }
    
    private static File anyFile() {
        return any(File.class);
    }

}