package ru.iav.takoe.countee.crypt.impl;

import static ru.iav.takoe.countee.crypt.utils.ByteIntUtils.intToBinaryString;

class SBlock {

    int substitute(int in) {
        String sIn = intToBinaryString(in, 32);
        StringBuilder sOut = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            String sub = sIn.substring(4*i, 4*(i+1));
            int node = Integer.parseInt(sub, 2);
            sub = intToBinaryString(SubstitutionsTable.sTab[i][node], 4);
            sOut.append(sub);
        }
        // Parsing as Long to avoid exception caused by length
        return (int) Long.parseLong(sOut.toString(), 2);
    }

}
