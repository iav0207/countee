package ru.iav.takoe.countee.da.impl;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.iav.takoe.countee.crypt.impl.SimpleGostCryptFacade;
import ru.iav.takoe.countee.persistence.file.LocalReader;
import ru.iav.takoe.countee.persistence.file.LocalWriter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static ru.iav.takoe.countee.da.impl.Constants.EOF;
import static ru.iav.takoe.countee.test.CounteeTestUtils.createFile;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomInteger;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

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
    public void reset() throws Exception {
        doReturn(generateFilesList()).when(fileNamesFactory).getAllCostFiles();
        letReaderReturn(returnedByReader);
    }

    @Test
    public void shouldConcatenateContentsOfAllFilesWithEofSymbol() {
        String password = getRandomString();
        String exportResult = dataExporter.exportAllData(password);

        for (String eachFileExportResult : exportResult.split(EOF)) {
            String decryptedExportedFileContent =
                    cryptFacade.decrypt(eachFileExportResult, password);
            assertEquals(decryptedExportedFileContent, returnedByReader);
        }
    }

    @Test
    public void shouldNotFailIfPasswordIsEmpty() {
        assertNotNull(dataExporter.exportAllData(""));
    }

    @Test
    public void shouldNotFailIfPasswordIsNull() {
        assertNotNull(dataExporter.exportAllData(null));
    }

    @Test
    public void shouldNotFailIfContentsAreEmpty() {
        letReaderReturn("");
        assertNotNull(dataExporter.exportAllData(getRandomString()));
    }

    @Test
    public void shouldFileExportProduceEquivalentContentAsSimpleStringExport() throws Exception {
        String password = getRandomString();
        File target = createFile("out");
        dataExporter.exportAllData(target, password);
        String expectedFileContent = dataExporter.exportAllData(password);

        List<String> lines = Files.readAllLines(target.toPath(), Charset.defaultCharset());
        assertFalse(lines.isEmpty());
        String actualFileContent = lines.get(0);

        assertEquals(actualFileContent, expectedFileContent);
    }

    private void letReaderReturn(String s) {
        doReturn(s).when(reader).read(any(File.class));
    }

    private List<File> generateFilesList() throws Exception {
        List<File> files = new ArrayList<>();
        for (int i = 0; i < getRandomInteger(50); i++) {
            files.add(createFile(String.valueOf(i)));
        }
        return files;
    }

}
