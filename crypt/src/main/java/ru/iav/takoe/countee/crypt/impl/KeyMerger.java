package ru.iav.takoe.countee.crypt.impl;

/**
 * Created by takoe on 02.02.17.
 */
class KeyMerger {

    private static Encryptor defaultKeyHolder = SimpleGostEncryptor.defaultInstance();

    String mergeWithDefaultKey(String key) {
        return defaultKeyHolder.encrypt(key);
    }

}
