package ru.iav.takoe.countee.da.impl;

import java.io.File;

import javax.annotation.ParametersAreNonnullByDefault;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.da.exception.DataNotImportedException;
import ru.iav.takoe.countee.persistence.file.LocalReader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

@ParametersAreNonnullByDefault
public class MergeFileDataImporterTest {

    @Mock private LocalReader reader;
    @Mock private CostBulkSaver costBulkSaver;

    @InjectMocks private MergeFileDataImporter dataImporter;

    @BeforeMethod
    public void init() {
        initMocks(this);
    }

    @Test(expectedExceptions = DataNotImportedException.class)
    public void shouldThrowDataNotImportedExceptionIfReaderThrewException()
            throws Exception
    {
        doThrow(new RuntimeException())
                .when(reader)
                .read(anyFile());

        dataImporter.importData(new File(""), getRandomString());
    }

    @Test(expectedExceptions = DataNotImportedException.class)
    public void shouldThrowDataNotImportedExceptionIfCostBulkSaverThrewException()
            throws Exception
    {
        doReturn("")
                .when(reader)
                .read(anyFile());
        doThrow(new RuntimeException())
                .when(costBulkSaver)
                .save(anyBulk());

        dataImporter.importData(new File(""), getRandomString());
    }

    private static File anyFile() {
        return any(File.class);
    }

    private static DateCostMultimap anyBulk() {
        return any(DateCostMultimap.class);
    }

}
