package ru.takoe.iav.countee.fragment.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

/**
 * Created by takoe on 09.02.17.
 */
public class KeyboardUtil {

    public static void hideKeyboard(Context context, View view) {
        if (!isNull(view)) {
            getKeyboardManager(context).hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);
        }
    }

    private static InputMethodManager getKeyboardManager(Context context) {
        return (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
    }

}
