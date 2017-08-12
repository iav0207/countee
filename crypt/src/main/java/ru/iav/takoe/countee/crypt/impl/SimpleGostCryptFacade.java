package ru.iav.takoe.countee.crypt.impl;

import ru.iav.takoe.countee.crypt.CryptFacade;

/**
 * Created by takoe on 06.02.17.
 */
public class SimpleGostCryptFacade implements CryptFacade {

    @Override
    public String encrypt(String text, String key) {
        return SimpleGostEncryptor.withKey(key).encrypt(text);
    }

    @Override
    public String decrypt(String text, String key) {
        return SimpleGostDecryptor.withKey(key).decrypt(text);
    }

}
