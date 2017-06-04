package ru.iav.takoe.countee.persistence.file;

import java.io.File;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LocalReaderTest {

    private LocalReader reader;

    @BeforeClass
    public void init() {
        reader = new LocalReader();
    }

    @Test
    public void shouldWriteStringToFile() throws Exception {
        File file = getTestFile();
        System.out.println(reader.read(file));
    }

    private File getTestFile() {
        String fileName = "test.txt";
        return new File(getOutputPath() + fileName);
    }

    private String getOutputPath() {
        String outputPath = getExecutionPath() + "io/test/";
        File outputDirectory = new File(outputPath);
        if (!outputDirectory.exists()) {
            if (!outputDirectory.mkdirs()) {
                return null;
            }
        }
        return outputPath;
    }

    private String getExecutionPath() {
        return (new File("").getAbsolutePath()).replaceAll("\\\\","/") + "/";
    }

}
