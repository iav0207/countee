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
        createEditText();
    }

    public EditText newEditText() {
        createEditText();
        return editText;
    }

    private void createEditText() {
        editText = new EditText(context);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    ViewProvider getViewProvider() {
        return viewProvider;
    }

    String getPasswordFromTextInput() {
        return editText.getText().toString();
    }

    void showToast(int resId) {
        if (!isNull(viewProvider)) {
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
        }
    }

    void showToast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    void hideKeyboard() {
        KeyboardUtil.hideKeyboard(context, editText);
    }

}
