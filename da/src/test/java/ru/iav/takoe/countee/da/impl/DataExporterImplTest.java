package ru.iav.takoe.countee.da.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.reporters.Files;
import ru.iav.takoe.countee.crypt.impl.SimpleGostCryptFacade;
import ru.iav.takoe.countee.persistence.file.LocalReader;
import ru.iav.takoe.countee.persistence.file.LocalWriter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static ru.iav.takoe.countee.da.impl.DataExporterImpl.EOF;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomInteger;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

/**
 * Created by takoe on 07.02.17.
 */
public class DataExporterImplTest {

    @Mock
    private CostFileNamesFactory fileNamesFactory;

    @Mock
    private LocalReader reader;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private LocalWriter writer;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private SimpleGostCryptFacade cryptFacade;

    @InjectMocks
    private DataExporterImpl dataExporter;

    private String returnedByReader = "FILE_CONTENT";

    @BeforeClass
    public void init() {
        initMocks(this);
    }

    @BeforeMethod
    public void reset() {
        doReturn(generateFilesList()).when(fileNamesFactory).getAllCostFiles();
        letReaderReturn(returnedByReader);
    }

    @Test
    public void shouldConcatenateContentsOfAllFilesWithEofSymbol() throws Exception {
        String password = getRandomString();
        String exportResult = dataExporter.exportAllData(password);

        String decryptedResult = cryptFacade.decrypt(exportResult, password);
        for (String eachFileContent : decryptedResult.split(EOF)) {
            assertEquals(eachFileContent, returnedByReader);
        }
    }

    @Test
    public void shouldNotFailIfPasswordIsEmpty() throws Exception {
        assertNotNull(dataExporter.exportAllData(""));
    }

    @Test
    public void shouldNotFailIfPasswordIsNull() throws Exception {
        assertNotNull(dataExporter.exportAllData(null));
    }

    @Test
    public void shouldNotFailIfContentsAreEmpty() throws Exception {
        letReaderReturn("");
        assertNotNull(dataExporter.exportAllData(getRandomString()));
    }

    @Test
    public void shouldFileExportProduceEquivalentContentAsSimpleStringExport() throws Exception {
        String password = getRandomString();
        File target = createFile("out");
        dataExporter.exportAllData(target, password);
        String expectedFileContent = dataExporter.exportAllData(password);

        assertEquals(Files.readFile(target), expectedFileContent);
    }

    private void letReaderReturn(String s) {
        doReturn(s).when(reader).read(any(File.class));
    }

    private List<File> generateFilesList() {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < getRandomInteger(50); i++) {
            files.add(createFile(String.valueOf(i)));
        }
        return files;
    }

    private File createFile(String key) {
        return new File("io/test" + key);
    }

}
