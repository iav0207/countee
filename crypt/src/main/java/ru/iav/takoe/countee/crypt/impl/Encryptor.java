package ru.iav.takoe.countee.crypt.impl;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
interface Encryptor {

    String encrypt(String text);

}
