package ru.iav.takoe.countee.da.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import ru.iav.takoe.countee.crypt.CryptFacade;
import ru.iav.takoe.countee.da.DataExporter;
import ru.iav.takoe.countee.persistence.file.LocalReader;
import ru.iav.takoe.countee.persistence.file.LocalWriter;

import static org.apache.commons.lang3.StringUtils.defaultString;

@ParametersAreNonnullByDefault
public class DataExporterImpl implements DataExporter {

    static final String EOF = "6a58c043-284f-49f1-a681-64d7a1391633";

    private final CostFileNamesFactory fileNamesFactory;

    private final LocalReader reader;

    private final LocalWriter writer;

    private final CryptFacade cryptFacade;

    @Inject
    public DataExporterImpl(CostFileNamesFactory fileNamesFactory,
            LocalReader reader,
            LocalWriter writer,
            CryptFacade cryptFacade)
    {
        this.fileNamesFactory = fileNamesFactory;
        this.reader = reader;
        this.writer = writer;
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

    @Override
    public boolean exportAllData(File target, @Nullable String password) {
        for (File source : getAllCostFiles()) {
            String contentToWrite = cryptFacade.encrypt(reader.read(source) + EOF, defaultString(password));
            writer.append(contentToWrite, target);
        }
        return true;
    }

    private List<File> getAllCostFiles() {
        return fileNamesFactory.getAllCostFiles();
    }

}
