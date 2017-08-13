package ru.iav.takoe.countee.da.impl;

import java.io.File;

import javax.annotation.ParametersAreNonnullByDefault;

import ru.iav.takoe.countee.crypt.CryptFacade;
import ru.iav.takoe.countee.da.DataImporter;
import ru.iav.takoe.countee.json.JsonParser;
import ru.iav.takoe.countee.persistence.file.LocalReader;

/**
 * Imports costs from the specified file on disk.
 */
@ParametersAreNonnullByDefault
abstract class FileDataImporter implements DataImporter<File> {

    final LocalReader reader;
    final JsonParser jsonParser;
    final CryptFacade cryptFacade;

    FileDataImporter(
            LocalReader reader,
            JsonParser jsonParser,
            CryptFacade cryptFacade)
    {
        this.reader = reader;
        this.jsonParser = jsonParser;
        this.cryptFacade = cryptFacade;
    }

}
