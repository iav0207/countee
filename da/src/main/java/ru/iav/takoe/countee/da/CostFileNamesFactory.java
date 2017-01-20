package ru.iav.takoe.countee.da;

import ru.iav.takoe.countee.persistence.file.FileFactory;

import javax.annotation.Nonnull;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static ru.iav.takoe.countee.utils.DateUtils.now;
import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

/**
 * Created by takoe on 25.07.16.
 */
class CostFileNamesFactory {

    private static CostFileNamesFactory instance;

    private static final String outputPath = "/costs/";

    private static final String fileNameFormat = "MMyy";

    private static final String extension = ".cnt";

    private FileFactory fileFactory;

    private CostFileNamesFactory() {
        fileFactory = FileFactory.getInstance();
    }

    static CostFileNamesFactory getInstance() {
        if (isNull(instance)) {
            instance = new CostFileNamesFactory();
        }
        return instance;
    }

    @Nonnull
    List<File> getAllCostFiles() {
        return Stream.of(fileFactory.getFileForName(outputPath).listFiles()).collect(toList());
    }

    File getActualFile() {
        return fileFactory.create(getActualFileName());
    }

    private static String getActualFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat(fileNameFormat);
        return outputPath + sdf.format(now()) + extension;
    }

}
