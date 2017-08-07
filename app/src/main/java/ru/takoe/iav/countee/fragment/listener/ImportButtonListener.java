package ru.takoe.iav.countee.fragment.listener;

import javax.annotation.ParametersAreNonnullByDefault;

import android.content.Context;
import android.content.DialogInterface;
import ru.takoe.iav.countee.R;
import ru.takoe.iav.countee.view.ViewProvider;

@ParametersAreNonnullByDefault
public class ImportButtonListener extends SettingsFragmentButtonListener {

    public ImportButtonListener(Context context, ViewProvider viewProvider) {
        super(context, viewProvider);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        hideKeyboard();
        showToast(R.string.data_import_not_implemented_msg);
    }

}
