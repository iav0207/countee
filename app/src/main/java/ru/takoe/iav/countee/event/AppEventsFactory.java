package ru.takoe.iav.countee.event;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AppEventsFactory {

    public static DataImportedEvent dataImported() {
        return new DataImportedEvent();
    }

    private AppEventsFactory() {}

}
