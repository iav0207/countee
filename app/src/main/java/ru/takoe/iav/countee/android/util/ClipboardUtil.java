package ru.takoe.iav.countee.android.util;

import javax.annotation.ParametersAreNonnullByDefault;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

@ParametersAreNonnullByDefault
public class ClipboardUtil {

    private ClipboardUtil() {}

    public static void copyToClipboard(Context context, String label, String data) {
        getClipboardManager(context).setPrimaryClip(ClipData.newPlainText(label, data));
    }

    private static ClipboardManager getClipboardManager(Context context) {
        return (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

}
