package ru.iav.takoe.countee.da.impl;

import java.io.File;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import ru.iav.takoe.countee.crypt.CryptFacade;
import ru.iav.takoe.countee.json.JsonParser;
import ru.iav.takoe.countee.persistence.file.LocalReader;

@ParametersAreNonnullByDefault
public class ReplaceFileDataImporter extends FileDataImporter {

    @Inject
    ReplaceFileDataImporter(
            LocalReader reader,
            JsonParser jsonParser,
            CryptFacade cryptFacade)
    {
        super(reader, jsonParser, cryptFacade);
    }

    @Override
    public boolean importData(File source, String password) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}
