package ru.iav.takoe.statbm.da;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.iav.takoe.statbm.da.exception.PersistenceException;
import ru.iav.takoe.statbm.json.JsonConverter;
import ru.iav.takoe.statbm.persistence.file.FileFactory;
import ru.iav.takoe.statbm.persistence.file.LocalWriter;
import ru.iav.takoe.statbm.vo.Cost;
import ru.iav.takoe.statbm.vo.CostFactory;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;
import static ru.iav.takoe.statbm.utils.TestUtils.getRandomBigDecimal;
import static ru.iav.takoe.statbm.utils.TestUtils.getRandomString;

/**
 * Created by takoe on 26.07.16.
 */
public class CostSaverTest {

    @Mock
    private FileFactory fileFactory;

    @Mock
    private JsonConverter jsonConverter;

    @Mock
    private LocalWriter writer;

    @InjectMocks
    private CostSaver costSaver;

    private CostFactory costFactory = CostFactory.getInstance();

    @BeforeClass
    public void init() {
        initMocks(this);
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void shouldThrowExceptionIfSerializationFails() throws Exception {
        doThrow(new RuntimeException()).when(jsonConverter).serialize(anyCost());
        costSaver.save(createCost());
    }

    @Test(expectedExceptions = PersistenceException.class)
    public void shouldThrowExceptionIfFileWritingFails() throws Exception {
        doReturn(getRandomString()).when(jsonConverter).serialize(anyCost());
        doThrow(new RuntimeException()).when(writer).append(anyString(), any(File.class));
        costSaver.save(createCost());
    }

    @Test
    public void shouldBeSilentIfEverythingIsOk() throws Exception {
        doReturn(getRandomString()).when(jsonConverter).serialize(anyCost());
        doNothing().when(writer).append(anyString(), any(File.class));
        costSaver.save(createCost());
    }

    private Cost createCost() {
        return costFactory.create(getRandomBigDecimal(), getRandomString());
    }

    private static Cost anyCost() {
        return any(Cost.class);
    }

}