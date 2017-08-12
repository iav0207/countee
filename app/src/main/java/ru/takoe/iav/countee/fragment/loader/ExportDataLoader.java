package ru.takoe.iav.countee.fragment.loader;

import java.io.File;
import java.io.IOException;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import android.os.Environment;
import android.support.v4.content.AsyncTaskLoader;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.joda.time.format.DateTimeFormat;
import ru.iav.takoe.countee.service.DataExportService;
import ru.takoe.iav.countee.application.CounteeApp;
import ru.takoe.iav.countee.properties.ApplicationProperties;

import static org.joda.time.DateTime.now;
import static ru.iav.takoe.countee.logging.LogService.logError;
import static ru.iav.takoe.countee.logging.LogService.logInfo;
import static ru.takoe.iav.countee.android.util.ExternalMediaUtil.checkExternalMedia;

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

        checkExternalMedia();

        String storageRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        String relativePath = ApplicationProperties.getExportedDataPath();
        File outDir = new File(Joiner.on(File.separator).join(storageRoot, relativePath));

        File exportFile = new File(outDir, generateFileName());
        try {
            Files.createParentDirs(exportFile);
            Files.touch(exportFile);
            String filePath = exportFile.getAbsolutePath();
            logInfo("Exporting to file: " + filePath);
            dataExportService.exportAllData(exportFile, password);
            String msg = "Written to file: " + filePath;
            logInfo(msg);
            return msg;
        } catch (IOException ex) {
            logError("Failed to export data to file " + exportFile.getAbsolutePath(), ex);
            return "fail";
        }
    }

    private String generateFileName() {
        String timestamp = DateTimeFormat.forPattern(ApplicationProperties.getDateFormat()).print(now());
        return String.format(ApplicationProperties.getExportFileNameBase(), timestamp);
    }

    @Override
    public int getId() {
        return ID;
    }

}
