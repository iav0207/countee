package ru.iav.takoe.countee.persistence.file;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.iav.takoe.countee.utils.TestUtils.getRandomString;

/**
 * Created by takoe on 25.07.16.
 */
public class FileFactoryTest {

    private static final String testFileName = "test/fileFactory.test";

    private static final File testFile = FileFactory.getInstance().getFileForName(testFileName);

    private FileFactory fileFactory;

    private LocalReader reader;

    private LocalWriter writer;

    @BeforeClass
    public void init() {
        fileFactory = FileFactory.getInstance();
        reader = LocalReader.getInstance();
        writer = LocalWriter.getInstance();
    }

    @AfterMethod
    public void reset() {
        fileFactory.delete(testFileName);
    }

    @Test
    public void shouldCreateFileIfDoesNotExist() throws Exception {
        assertFalse(testFile.exists());
        File result = fileFactory.create(testFileName);
        assertTrue(testFile.exists());
        assertEquals(result, testFile);
    }

    @Test
    public void shouldNotChangeFileContentOnCreateIfItExisted() throws Exception {
        assertTrue(testFile.createNewFile());
        String content = getRandomString();
        writer.append(content, testFile);
        fileFactory.create(testFileName);
        assertTrue(testFile.exists());
        assertEquals(reader.read(testFile), content);
    }

    @Test
    public void shouldReturnFalseIfFileBeingDeletedDoesNotExist() throws Exception {
        assertFalse(testFile.exists());
        assertFalse(fileFactory.delete(testFileName));
    }

    @Test
    public void shouldReturnTrueIfFileWasSuccessfullyDeleted() throws Exception {
        fileFactory.create(testFileName);
        assertTrue(testFile.exists());
        assertTrue(fileFactory.delete(testFileName));
        assertFalse(testFile.exists());
    }

}