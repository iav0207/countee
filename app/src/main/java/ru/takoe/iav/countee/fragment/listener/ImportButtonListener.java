package ru.takoe.iav.countee.fragment.listener;

import java.io.File;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.fragment.loader.ImportDataLoader;
import ru.takoe.iav.countee.view.ViewProvider;

@ParametersAreNonnullByDefault
public class ImportButtonListener extends SettingsFragmentButtonListener
        implements LoaderManager.LoaderCallbacks<Boolean> {

    private final LoaderManager loaderManager;
    private File importSource;

    @Inject
    public ImportButtonListener(Context context,
            ViewProvider viewProvider,
            LoaderManager loaderManager)
    {
        super(context, viewProvider);
        this.loaderManager = loaderManager;
    }

    public ImportButtonListener withSourceFile(String fileName) {
        importSource = new File(fileName);
        return this;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int whichButton) {
        hideKeyboard();
        showToast(R.string.data_import_wait_msg);

        loaderManager.initLoader(ImportDataLoader.ID, null, this);

        dialogInterface.dismiss();
    }

    @Override
    public Loader<Boolean> onCreateLoader(int id, Bundle args) {
        return new ImportDataLoader(importSource, getPasswordFromTextInput());
    }

    @Override
    public void onLoadFinished(Loader<Boolean> loader, Boolean result) {
        if (result) {
            Log.i("load_finish", "Background import finished successfully.");
            showToast(R.string.data_imported_msg);
        } else {
            Log.e("load_finish", "Background import failed.");
            showToast(R.string.data_not_imported_msg);
        }
    }

    @Override
    public void onLoaderReset(Loader<Boolean> loader) {
        // not implemented
    }
}
