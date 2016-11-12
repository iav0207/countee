package ru.iav.takoe.countee.da;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.da.exception.PersistenceException;
import ru.iav.takoe.countee.json.JsonConverter;
import ru.iav.takoe.countee.persistence.file.LocalWriter;
import ru.iav.takoe.countee.vo.Cost;
import ru.iav.takoe.countee.vo.CostFactory;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
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
        doThrow(new RuntimeException()).when(writer).append(anyString(), anyFile());
        costSaver.save(createCost());
    }

    @Test
    public void shouldBeSilentIfEverythingIsOk() throws Exception {
        doReturn(getRandomString()).when(jsonConverter).serialize(anyCost());
        doReturn(new CostsData()).when(costReader).getDeserializedData(anyFile());
        doNothing().when(writer).append(anyString(), anyFile());
        costSaver.save(createCost());
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