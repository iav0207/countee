package ru.iav.takoe.countee.da;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface DataImporter<S> {

    boolean importData(S source, String password);

}
