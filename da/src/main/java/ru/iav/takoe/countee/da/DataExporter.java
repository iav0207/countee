package ru.iav.takoe.countee.da;

import ru.iav.takoe.countee.crypt.impl.SimpleGostCryptFacade;
import ru.iav.takoe.countee.persistence.file.LocalReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * Created by takoe on 07.02.17.
 */
public class DataExporter {

    static final String EOF = "6a58c043-284f-49f1-a681-64d7a1391633";

    private CostFileNamesFactory fileNamesFactory = CostFileNamesFactory.getInstance();

    private LocalReader reader = LocalReader.getInstance();

    private SimpleGostCryptFacade cryptFacade = new SimpleGostCryptFacade();

    @Nonnull
    public String exportAllData(@Nullable String password) {
        StringBuilder sb = new StringBuilder();
        for (File file : getAllCostFiles()) {
            sb.append(reader.read(file));
            sb.append(EOF);
        }
        return cryptFacade.encrypt(sb.toString(), defaultString(password));
    }

    private List<File> getAllCostFiles() {
        return fileNamesFactory.getAllCostFiles();
    }

}
