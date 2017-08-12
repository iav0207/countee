package ru.iav.takoe.countee.da;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface DataImporter {

    boolean importData(String key, String password);

}
