package ru.takoe.iav.countee.android.util;

import javax.annotation.ParametersAreNonnullByDefault;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

@ParametersAreNonnullByDefault
public class DisplayUtil {

    private DisplayUtil() {}

    public static Display getDefaultDisplay(Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }

    public static int getLinearLayoutMinHeight(Context context) {
        return getScreenSize(context).y;
    }

    public static Point getScreenSize(Context context) {
        Point screeSize = new Point();
        getDefaultDisplay(context).getSize(screeSize);
        return screeSize;
    }

}
