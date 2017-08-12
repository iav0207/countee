package ru.takoe.iav.countee.android.util;

import javax.annotation.ParametersAreNonnullByDefault;

import android.support.design.widget.Snackbar;
import android.view.View;

@ParametersAreNonnullByDefault
public class SnackbarUtil {

    private SnackbarUtil() {}

    public static void showSnackbar(View view, int stringId) {
        Snackbar.make(view, stringId, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackbar(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

}
