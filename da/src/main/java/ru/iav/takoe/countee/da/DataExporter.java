package ru.iav.takoe.countee.da;

import java.io.File;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface DataExporter {

    String exportAllData(@Nullable String password);

    boolean exportAllData(File target, @Nullable String password);

}
