package ru.iav.takoe.countee.da;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface DataExporter {

    String exportAllData(@Nullable String password);

}
