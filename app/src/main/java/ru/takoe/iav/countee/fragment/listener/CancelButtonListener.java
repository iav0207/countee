package ru.takoe.iav.countee.fragment.listener;

import android.content.Context;
import android.content.DialogInterface;
import ru.takoe.iav.countee.view.ViewProvider;

/**
 * Created by takoe on 09.02.17.
 */
public class CancelButtonListener extends SettingsFragmentButtonListener {

    public CancelButtonListener(Context context, ViewProvider viewProvider) {
        super(context, viewProvider);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        hideKeyboard();
        dialogInterface.cancel();
    }
}
