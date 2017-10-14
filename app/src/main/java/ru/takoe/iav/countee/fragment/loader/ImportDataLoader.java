package ru.takoe.iav.countee.fragment.loader;

import java.io.File;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import android.support.v4.content.AsyncTaskLoader;
import ru.iav.takoe.countee.da.exception.DataNotImportedException;
import ru.iav.takoe.countee.service.FileDataImportService;
import ru.takoe.iav.countee.application.CounteeApp;

import static ru.iav.takoe.countee.logging.LogService.logError;
import static ru.iav.takoe.countee.logging.LogService.logInfo;

@ParametersAreNonnullByDefault
public class ImportDataLoader extends AsyncTaskLoader<Boolean> {

    public static final int ID = 987640185;

    @Inject FileDataImportService dataImportService;

    private final File source;
    private final String password;

    public ImportDataLoader(File source, String password) {
        super(CounteeApp.getInstance().getApplicationContext());

        CounteeApp.getInstance()
                .getSettingsFragmentComponent()
                .injectInto(this);

        this.source = source;
        this.password = password;
    }

    @Override
    public void onStartLoading() {
        forceLoad();
    }

    @Override
    public Boolean loadInBackground() {
        String path = source.getPath();
        logInfo("Importing from file: " + path);

        Boolean result;
        try {
            result = dataImportService.importData(source, password);
            logInfo("Data import finished.");
        } catch (DataNotImportedException ex) {
            result = Boolean.FALSE;
            logError("Data import failed", ex);
        }
        return result;
    }

    @Override
    public int getId() {
        return ID;
    }
}
