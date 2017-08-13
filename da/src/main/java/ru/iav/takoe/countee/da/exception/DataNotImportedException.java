package ru.iav.takoe.countee.da.exception;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DataNotImportedException extends RuntimeException {

    public DataNotImportedException() {
        super();
    }

    public DataNotImportedException(Throwable cause) {
        super(cause);
    }

}
