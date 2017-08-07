package ru.iav.takoe.countee.da.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import ru.iav.takoe.countee.crypt.CryptFacade;
import ru.iav.takoe.countee.da.DataExporter;
import ru.iav.takoe.countee.persistence.file.LocalReader;

import static org.apache.commons.lang3.StringUtils.defaultString;

/**
 * Created by takoe on 07.02.17.
 */
public class DataExporterImpl implements DataExporter {

    static final String EOF = "6a58c043-284f-49f1-a681-64d7a1391633";

    private final CostFileNamesFactory fileNamesFactory;

    private final LocalReader reader;

    private final CryptFacade cryptFacade;

    @Inject
    public DataExporterImpl(CostFileNamesFactory fileNamesFactory,
            LocalReader reader,
            CryptFacade cryptFacade)
    {
        this.fileNamesFactory = fileNamesFactory;
        this.reader = reader;
        this.cryptFacade = cryptFacade;
    }

    @Nonnull
    @Override
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
