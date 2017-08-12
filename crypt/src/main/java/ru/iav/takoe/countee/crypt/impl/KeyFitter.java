package ru.iav.takoe.countee.crypt.impl;

import javax.annotation.Nonnull;

import static com.google.common.base.Strings.isNullOrEmpty;
import static ru.iav.takoe.countee.crypt.impl.Constants.DEFAULT_KEY;
import static ru.iav.takoe.countee.crypt.impl.Constants.KEY_LENGTH_IN_BYTES;
import static ru.iav.takoe.countee.crypt.impl.Constants.KEY_STRING_LENGTH;
import static ru.iav.takoe.countee.crypt.utils.ByteIntUtils.byteArrayLengthFitCyclic;
import static ru.iav.takoe.countee.crypt.utils.ByteIntUtils.bytesToInts;
import static ru.iav.takoe.countee.logging.LogService.logDebug;
import static ru.iav.takoe.countee.logging.LogService.logWarning;

/**
 * Created by takoe on 01.02.17.
 */
class KeyFitter {

    String fitLength(String key) {
        if (isNullOrEmpty(key)) {
            return DEFAULT_KEY;
        } else if (key.length() > KEY_STRING_LENGTH) {
            return key.substring(0, KEY_STRING_LENGTH);
        } else if (key.length() < KEY_STRING_LENGTH) {
            return stringLoop(key);
        } else {
            return key;
        }
    }

    private String stringLoop(@Nonnull String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; sb.length() < KEY_STRING_LENGTH; i++) {
            sb.append(sb.charAt(i % s.length()));
        }
        return sb.toString();
    }

    int[] getKeyIntsFixedLength(byte[] key) {
        byte[] keyFixed = byteArrayLengthFitCyclic(key, KEY_LENGTH_IN_BYTES);
        if (keyFixed.length != key.length)
            warningKeySizeChanged(keyFixed);
        return bytesToInts(keyFixed, 8);
    }

    private static void warningKeySizeChanged(byte[] newKey) {
        logWarning("Warning! The key length has been corrected during process.");
        logDebug(String.format("The new %d bit key: %s\n", newKey.length*8, new String(newKey)));
    }

}
