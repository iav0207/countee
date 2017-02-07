package ru.iav.takoe.countee.crypt.impl;

/**
 * Created by takoe on 06.02.17.
 */
public class SimpleGostCryptFacade {

    public String encrypt(String text, String key) {
        return SimpleGostEncryptor.withKey(key).encrypt(text);
    }

    public String decrypt(String text, String key) {
        return SimpleGostDecryptor.withKey(key).decrypt(text);
    }

}
