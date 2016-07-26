package ru.iav.takoe.countee.persistence.file;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

/**
 * Created by takoe on 24.07.16.
 */
public class LocalWriterTest {

    private static final String testFileName = "test/localWriter.test";

    private LocalWriter writer;

    @BeforeClass
    public void init() {
        writer = LocalWriter.getInstance();
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
        return FileFactory.getFileForName(testFileName);
    }

    private String getExecutionPath() {
        return (new File("").getAbsolutePath()).replaceAll("\\\\","/") + "/";
    }

}