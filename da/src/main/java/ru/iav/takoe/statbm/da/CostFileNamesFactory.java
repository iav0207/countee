package ru.iav.takoe.statbm.da;

import java.text.SimpleDateFormat;

import static ru.iav.takoe.statbm.utils.DateUtils.now;

/**
 * Created by takoe on 25.07.16.
 */
class CostFileNamesFactory {

    private static final String outputPath = "costs/";

    private static final String fileNameFormat = "MMyy";

    private static final String extension = ".cnt";

    static String getFileNameToSaveCostTo() {
        SimpleDateFormat sdf = new SimpleDateFormat(fileNameFormat);
        return outputPath + sdf.format(now()) + extension;
    }

}
