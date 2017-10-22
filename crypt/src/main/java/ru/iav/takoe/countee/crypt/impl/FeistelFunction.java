package ru.iav.takoe.countee.crypt.impl;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
class FeistelFunction {

    private static final int MODULO = (int) 2e32;

    private static SBlock sBlock = new SBlock();

    int apply(int txt, int subkey) {
        txt = getBase32Sum(txt, subkey);
        txt = sBlock.substitute(txt);
        txt = Integer.rotateLeft(txt,11);       // Shift register
        return txt;
    }

    private int getBase32Sum(int a, int b) {
        long sum = (a + b) % MODULO;
        return (int) sum;
    }

}
