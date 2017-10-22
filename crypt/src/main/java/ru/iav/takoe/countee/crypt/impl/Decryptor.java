package ru.iav.takoe.countee.crypt.impl;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
interface Decryptor {

    String decrypt(String cypher);

}
