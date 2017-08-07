package ru.takoe.iav.countee.fragment.listener;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.fragment.loader.ExportDataLoader;
import ru.takoe.iav.countee.view.ViewProvider;

import static ru.takoe.iav.countee.fragment.util.ClipboardUtil.copyToClipboard;

@ParametersAreNonnullByDefault
public class ExportButtonListener extends SettingsFragmentButtonListener
        implements LoaderManager.LoaderCallbacks<String> {

    private final LoaderManager loaderManager;

    @Inject
    public ExportButtonListener(Context context,
            ViewProvider viewProvider,
            LoaderManager loaderManager)
    {
        super(context, viewProvider);
        this.loaderManager = loaderManager;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        loaderManager.initLoader(ExportDataLoader.ID, null, this);

        hideKeyboard();
        dialogInterface.dismiss();
        showToast(R.string.data_export_wait_msg);
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new ExportDataLoader(getPasswordFromTextInput());
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String exportedData) {
        Log.i("LOAD_FINISHED", "BACKGROUND LOAD FINISHED !!!");
        copyToClipboard(context, "Countee export", exportedData);
        showToast(R.string.data_exported_msg);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        // not implemented
    }
}
