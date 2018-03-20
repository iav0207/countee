package ru.iav.takoe.countee.crypt.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

@ParametersAreNonnullByDefault
class SimpleGostEncryptor extends SimpleGostAlgorithmExecutor implements Encryptor {

    private static final int SPACE_BYTE_LEN = " ".getBytes().length;

    public static SimpleGostEncryptor withKey(String key) {
        return new SimpleGostEncryptor(key);
    }

    static SimpleGostEncryptor defaultInstance() {
        return new SimpleGostEncryptor();
    }

    private SimpleGostEncryptor(String key) {
        super(key);
    }

    private SimpleGostEncryptor() {
        super();
    }

    @Override
    @Nonnull
    public String encrypt(@Nullable String text) {
        if (text == null) {
            return StringUtils.EMPTY;
        }
        String paddedText = bytesPadding(text);
        byte[] resultBytes = SimpleGostAlgorithm.newEncryptor(paddedText, key).execute();
        return new String(Base64.encodeBase64(resultBytes));
    }

    private String bytesPadding(String s) {
        int len = s.getBytes().length;
        if (len % 8 == 0) {
            return s;
        }
        int spaces = (8 - (len % 8)) / SPACE_BYTE_LEN;
        return StringUtils.rightPad(s, s.length() + spaces, ' ');
    }

}
