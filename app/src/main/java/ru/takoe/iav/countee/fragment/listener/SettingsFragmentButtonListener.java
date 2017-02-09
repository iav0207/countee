package ru.takoe.iav.countee.fragment.listener;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.text.InputType;
import android.widget.EditText;
import ru.takoe.iav.countee.fragment.util.KeyboardUtil;
import ru.takoe.iav.countee.view.ViewProvider;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

/**
 * Created by takoe on 09.02.17.
 */
public abstract class SettingsFragmentButtonListener implements DialogInterface.OnClickListener {

    final Context context;

    private EditText editText;

    private ViewProvider viewProvider;

    SettingsFragmentButtonListener(Context context, ViewProvider viewProvider) {
        this.context = context;
        this.viewProvider = viewProvider;

        editText = new EditText(context);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public SettingsFragmentButtonListener withViewProvider(ViewProvider viewProvider) {
        this.viewProvider = viewProvider;
        return this;
    }

    public EditText getEditText() {
        return editText;
    }

    String getPasswordFromTextInput() {
        return editText.getText().toString();
    }

    void showSnackbar(int resId) {
        if (!isNull(viewProvider)) {
            Snackbar.make(viewProvider.getNavigationView(), resId, Snackbar.LENGTH_SHORT).show();
        }
    }

    void hideKeyboard() {
        KeyboardUtil.hideKeyboard(context, editText);
    }

}
