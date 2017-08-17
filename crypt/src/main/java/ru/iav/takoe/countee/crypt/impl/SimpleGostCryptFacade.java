package ru.iav.takoe.countee.crypt.impl;

import javax.annotation.ParametersAreNonnullByDefault;

import ru.iav.takoe.countee.crypt.CryptFacade;

@ParametersAreNonnullByDefault
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
