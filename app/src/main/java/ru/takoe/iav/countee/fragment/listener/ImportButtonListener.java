package ru.takoe.iav.countee.fragment.listener;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.fragment.loader.ExportDataLoader;
import ru.takoe.iav.countee.view.ViewProvider;

@ParametersAreNonnullByDefault
public class ImportButtonListener extends SettingsFragmentButtonListener
        implements LoaderManager.LoaderCallbacks<String> {

    private final LoaderManager loaderManager;

    @Inject
    public ImportButtonListener(Context context,
            ViewProvider viewProvider,
            LoaderManager loaderManager)
    {
        super(context, viewProvider);
        this.loaderManager = loaderManager;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        if (false) {    // TODO implement
            loaderManager.initLoader(ExportDataLoader.ID, null, this);
        }
        hideKeyboard();
        showToast(R.string.data_import_not_implemented_msg);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
