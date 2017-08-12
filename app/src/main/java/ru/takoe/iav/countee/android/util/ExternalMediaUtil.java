package ru.takoe.iav.countee.android.util;

import javax.annotation.ParametersAreNonnullByDefault;

import android.os.Environment;

import static ru.iav.takoe.countee.logging.LogService.logInfo;

@ParametersAreNonnullByDefault
public class ExternalMediaUtil {

    private ExternalMediaUtil() {}

    /**
     * Method to check whether external media available and writable. This is
     * adapted from
     * http://developer.android.com/guide/topics/data/data-storage.html
     * #filesExternal
     */
    public static void checkExternalMedia() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWritable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWritable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWritable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWritable = false;
        }
        logInfo("\n\nExternal Media: readable=" + mExternalStorageAvailable
                + " writable=" + mExternalStorageWritable);
    }

}
