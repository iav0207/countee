package ru.iav.takoe.countee.persistence.file;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.reporters.Files;

import static java.nio.file.Files.deleteIfExists;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

public class LocalWriterTest {

    private static final String testFileName = "test/localWriter.test";

    private FileFactory fileFactory;
    private LocalWriter writer;
    
    private File file;
    
    private String lineOne, lineTwo;

    @BeforeClass
    public void init() {
        writer = new LocalWriter();
        fileFactory = new FileFactory();
    }
    
    @BeforeMethod
    public void reset() throws Exception {
        deleteIfExists(getTestFile().toPath());

        file = getTestFile();
        lineOne = getRandomString();
        lineTwo = getRandomString();
    }

    @Test
    public void shouldReplacePreviouslyWrittenStringOnClearWrite() throws Exception {
        writer.clearWrite(lineOne, file);
        writer.clearWrite(lineTwo, file);

        String content = Files.readFile(file);
        assertTrue(content.contains(lineTwo));

        assertFalse(content.contains(lineOne));
    }

    @Test
    public void shouldAppend() throws Exception {
        writer.append(lineOne, file);
        writer.append(lineTwo, file);

        String content = Files.readFile(file);
        assertTrue(content.contains(lineTwo));

        assertTrue(content.contains(lineOne));
    }

    private File getTestFile() {
        return fileFactory.getFileForName(testFileName);
    }

    private String getExecutionPath() {
        return (new File("").getAbsolutePath()).replaceAll("\\\\","/") + "/";
    }

}
