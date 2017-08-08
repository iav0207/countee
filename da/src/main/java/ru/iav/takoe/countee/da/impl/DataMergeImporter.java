package ru.iav.takoe.countee.da.impl;

import javax.annotation.ParametersAreNonnullByDefault;

import ru.iav.takoe.countee.da.DataImporter;

@ParametersAreNonnullByDefault
public class DataMergeImporter implements DataImporter {
    @Override
    public boolean importData(String key, String password) {
        return false;
    }
}
