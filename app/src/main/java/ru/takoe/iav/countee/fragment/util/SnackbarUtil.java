package ru.takoe.iav.countee.fragment.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by takoe on 09.02.17.
 */
public class SnackbarUtil {

    public static void showSnackbar(View view, int stringId) {
        Snackbar.make(view, stringId, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackbar(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

}
