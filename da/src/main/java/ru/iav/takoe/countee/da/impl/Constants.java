package ru.iav.takoe.countee.da.impl;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Constants {

    /**
     * Files content delimiter. Should never be modified.
     */
    static final String EOF = "6a58c043-284f-49f1-a681-64d7a1391633";

    /**
     * Substring length to be processed by importer at a time.
     * Can be configured. Notice, that it's better be a power of two.
     */
    static final int IMPORT_BUFFER_SIZE = 262144;    // Math.pow(2, 18)

}
