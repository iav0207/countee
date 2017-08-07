package ru.iav.takoe.countee.crypt;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface CryptFacade {

    String encrypt(String text, String key);

    String decrypt(String text, String key);

}
