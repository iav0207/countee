package ru.iav.takoe.countee.persistence.file;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertEquals;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

/**
 * Created by takoe on 25.07.16.
 */
public class FileInOutIT {

    private static final String testFileName = "test/FileInOutIT.test";

    private File testFile = FileFactory.getInstance().getFileForName(testFileName);

    private FileFactory fileFactory;

    private LocalReader reader;

    private LocalWriter writer;

    @BeforeClass
    public void init() {
        fileFactory = FileFactory.getInstance();
        reader = LocalReader.getInstance();
        writer = LocalWriter.getInstance();
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
