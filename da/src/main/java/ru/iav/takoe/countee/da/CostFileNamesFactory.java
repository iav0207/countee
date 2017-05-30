package ru.iav.takoe.countee.da;

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

    private static CostFileNamesFactory instance;

    private static final String outputPath = "/costs/";

    private static final String fileNameFormat = "MMyy";

    private static final String extension = ".cnt";

    private FileFactory fileFactory;

    public CostFileNamesFactory() {
        fileFactory = FileFactory.getInstance();
    }

    @Inject
    public CostFileNamesFactory(FileFactory fileFactory) {
        this.fileFactory = fileFactory;
    }

    public static CostFileNamesFactory getInstance() {
        if (isNull(instance)) {
            instance = new CostFileNamesFactory();
        }
        return instance;
    }

    @Nonnull
    List<File> getAllCostFiles() {
        File[] costFiles = fileFactory.getFileForName(outputPath).listFiles();
        return isNull(costFiles) ? new ArrayList<File>() : asList(costFiles);
    }

    File getActualFile() {
        return fileFactory.create(getActualFileName());
    }

    private static String getActualFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat(fileNameFormat);
        return outputPath + sdf.format(now()) + extension;
    }

}
