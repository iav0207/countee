package ru.takoe.iav.countee.android.util;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

@ParametersAreNonnullByDefault
public class KeyboardUtil {

    private KeyboardUtil() {}

    public static void hideKeyboard(Context context, @Nullable View view) {
        if (!isNull(view)) {
            getKeyboardManager(context).hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);
        }
    }

    private static InputMethodManager getKeyboardManager(Context context) {
        return (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
    }

}
