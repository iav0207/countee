package ru.iav.takoe.countee.persistence.file;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

public class LocalWriterTest {

    private static final String testFileName = "test/localWriter.test";

    private FileFactory fileFactory;

    private LocalWriter writer;

    @BeforeClass
    public void init() {
        writer = new LocalWriter();
        fileFactory = new FileFactory();
    }

    @Test
    public void shouldWriteStringToFile() throws Exception {
        File file = getTestFile();
        String s1 = getRandomString();
        String s2 = getRandomString();
        writer.append(s1, file);
        writer.append(s2, file);
    }

    private File getTestFile() {
        return fileFactory.getFileForName(testFileName);
    }

    private String getExecutionPath() {
        return (new File("").getAbsolutePath()).replaceAll("\\\\","/") + "/";
    }

}
