package ru.iav.takoe.countee.da.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import ru.iav.takoe.countee.persistence.file.FileFactory;

import static java.util.Arrays.asList;
import static ru.iav.takoe.countee.utils.DateUtils.now;
import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

public class CostFileNamesFactory {

    private static final String OUTPUT_PATH = "/costs/";

    private static final String FILE_NAME_FORMAT = "MMyy";

    private static final String EXTENSION = ".cnt";

    private FileFactory fileFactory;

    @Inject
    public CostFileNamesFactory(FileFactory fileFactory) {
        this.fileFactory = fileFactory;
    }

    @Nonnull
    List<File> getAllCostFiles() {
        File[] costFiles = fileFactory.getFileForName(OUTPUT_PATH).listFiles();
        return isNull(costFiles) ? new ArrayList<File>() : asList(costFiles);
    }

    File getActualFile() {
        return fileFactory.create(getActualFileName());
    }

    private static String getActualFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat(FILE_NAME_FORMAT);
        return OUTPUT_PATH + sdf.format(now()) + EXTENSION;
    }

}
