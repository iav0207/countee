package ru.iav.takoe.countee.crypt.impl;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.bind.DatatypeConverter;

import static ru.iav.takoe.countee.utils.ObjectUtils.isNull;

/**
 * Created by takoe on 01.02.17.
 */
class SimpleGostEncryptor extends SimpleGostAlgorithmExecutor implements Encryptor {

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
        if (isNull(text)) {
            return StringUtils.EMPTY;
        }
        text = appendSpacesToFillLastBlock(text);
        byte[] resultBytes = SimpleGostAlgorithm.newEncryptor(text, key).execute();
        if (true) {
            return new String(Base64.encodeBase64(resultBytes));        // Android compatible
        } else {
            return DatatypeConverter.printBase64Binary(resultBytes);
        }
    }

    private String appendSpacesToFillLastBlock(String s) {
        while (s.getBytes().length % 8 != 0) {
            s += " ";
        }
        return s;
    }

}
