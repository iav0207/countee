package ru.takoe.iav.countee.fragment.listener;

import javax.annotation.ParametersAreNonnullByDefault;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;
import ru.takoe.iav.countee.fragment.util.KeyboardUtil;
import ru.takoe.iav.countee.view.ViewProvider;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

@ParametersAreNonnullByDefault
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

    void showToast(int resId) {
        if (!isNull(viewProvider)) {
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
        }
    }

    void hideKeyboard() {
        KeyboardUtil.hideKeyboard(context, editText);
    }

}
