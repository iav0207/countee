package ru.iav.takoe.countee.persistence.file;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

public class FileInOutIT {

    private static final String testFileName = "test/FileInOutIT.test";

    private File testFile;

    private FileFactory fileFactory;

    private LocalReader reader;

    private LocalWriter writer;

    @BeforeClass
    public void init() {
        fileFactory = new FileFactory();
        reader = new LocalReader();
        writer = new LocalWriter();

        testFile = fileFactory.getFileForName(testFileName);
    }

    @BeforeMethod
    public void reset() {
        fileFactory.delete(testFileName);
        fileFactory.create(testFileName);
    }

    @AfterClass
    public void shutdown() {
        fileFactory.delete(testFileName);
    }

    @Test
    public void shouldBeSymmetric() throws Exception {
        String input = getRandomString(100);
        writer.append(input, testFile);
        String result = reader.read(testFile);

        assertEquals(result, input);
    }

}
