package ru.takoe.iav.countee.fragment.loader;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import android.support.v4.content.AsyncTaskLoader;
import ru.iav.takoe.countee.service.DataExportService;
import ru.takoe.iav.countee.application.CounteeApp;

@ParametersAreNonnullByDefault
public class ExportDataLoader extends AsyncTaskLoader<String> {

    public static final int ID = 14986237;

    @Inject DataExportService dataExportService;

    private final String password;

    @Inject
    public ExportDataLoader(String password) {
        super(CounteeApp.getInstance().getApplicationContext());

        CounteeApp.getInstance()
                .getSettingsFragmentComponent()
                .injectInto(this);

        this.password = password;
    }

    @Override
    public void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {

        // writing to file here

        return dataExportService.exportAllData(password);
    }

    @Override
    public int getId() {
        return ID;
    }

}
