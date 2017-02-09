package ru.takoe.iav.countee.fragment.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by takoe on 09.02.17.
 */
public class ClipboardUtil {

    public static void copyToClipboard(Context context, String label, String data) {
        getClipboardManager(context).setPrimaryClip(ClipData.newPlainText(label, data));
    }

    private static ClipboardManager getClipboardManager(Context context) {
        return (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

}
