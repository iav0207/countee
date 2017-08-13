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
import static ru.iav.takoe.countee.da.impl.Constants.EOF;
import static ru.iav.takoe.countee.logging.LogService.logInfo;

@ParametersAreNonnullByDefault
public class DataExporterImpl implements DataExporter {

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
        logInfo("Starting data export. Target file: " + target);
        writer.clear(target);
        for (File source : getAllCostFiles()) {
            String contentToWrite = cryptFacade.encrypt(reader.read(source), defaultString(password)) + EOF;
            writer.append(contentToWrite, target);
        }
        logInfo("Successfully finished data export to file " + target);
        return true;
    }

    private List<File> getAllCostFiles() {
        return fileNamesFactory.getAllCostFiles();
    }

}
