package ru.iav.takoe.countee.da;

import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.crypt.impl.SimpleGostCryptFacade;
import ru.iav.takoe.countee.persistence.file.LocalReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static ru.iav.takoe.countee.da.DataExporter.EOF;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomInteger;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

/**
 * Created by takoe on 07.02.17.
 */
public class DataExporterTest {

    @Mock
    private CostFileNamesFactory fileNamesFactory;

    @Mock
    private LocalReader reader;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private SimpleGostCryptFacade cryptFacade;

    @InjectMocks
    private DataExporter dataExporter = new DataExporter();

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

    private void letReaderReturn(String s) {
        doReturn(s).when(reader).read(any(File.class));
    }

    private List<File> generateFilesList() {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < getRandomInteger(50); i++) {
            files.add(new File("io/test" + i));
        }
        return files;
    }

}