package ru.iav.takoe.countee.crypt.impl;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
class KeyMerger {

    private static Encryptor defaultKeyHolder = SimpleGostEncryptor.defaultInstance();

    String mergeWithDefaultKey(String key) {
        return defaultKeyHolder.encrypt(key);
    }

}
