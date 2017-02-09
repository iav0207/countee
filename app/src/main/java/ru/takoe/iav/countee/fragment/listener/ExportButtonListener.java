package ru.takoe.iav.countee.fragment.listener;

import android.content.Context;
import android.content.DialogInterface;
import ru.iav.takoe.countee.service.DataExportService;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.view.ViewProvider;

import static ru.takoe.iav.countee.fragment.util.ClipboardUtil.copyToClipboard;

/**
 * Created by takoe on 09.02.17.
 */
public class ExportButtonListener extends SettingsFragmentButtonListener {

    private DataExportService dataExportService = DataExportService.getInstance();

    public ExportButtonListener(Context context, ViewProvider viewProvider) {
        super(context, viewProvider);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        hideKeyboard();
        String password = getPasswordFromTextInput();

        dialogInterface.dismiss();
        showSnackbar(R.string.data_export_wait_msg);

        String exportedData = dataExportService.exportAllData(password);
        copyToClipboard(context, "Countee export", exportedData);

        showSnackbar(R.string.data_exported_msg);
    }

}
