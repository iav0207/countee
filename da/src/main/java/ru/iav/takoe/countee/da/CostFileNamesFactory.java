package ru.iav.takoe.countee.da;

import ru.iav.takoe.countee.persistence.file.FileFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static ru.iav.takoe.countee.utils.DateUtils.now;

/**
 * Created by takoe on 25.07.16.
 */
class CostFileNamesFactory {

    private static final String outputPath = "/costs/";

    private static final String fileNameFormat = "MMyy";

    private static final String extension = ".cnt";

    private static FileFactory fileFactory = FileFactory.getInstance();

    static List<File> getAllCostFiles() {
        File[] costFiles = fileFactory.getFileForName(outputPath).listFiles();
        return costFiles == null ? new ArrayList<File>() : asList(costFiles);
    }

    static File getActualFile() {
        return fileFactory.create(getActualFileName());
    }

    static String getActualFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat(fileNameFormat);
        return outputPath + sdf.format(now()) + extension;
    }

}
