package ru.iav.takoe.countee.crypt.impl;

import javax.annotation.ParametersAreNonnullByDefault;

import org.apache.commons.codec.binary.Base64;
import ru.iav.takoe.countee.crypt.utils.BytesToStringParser;

@ParametersAreNonnullByDefault
class SimpleGostDecryptor extends SimpleGostAlgorithmExecutor implements Decryptor {

    private static BytesToStringParser parser = new BytesToStringParser();

    public static SimpleGostDecryptor withKey(String key) {
        return new SimpleGostDecryptor(key);
    }

    private SimpleGostDecryptor(String key) {
        super(key);
    }

    @Override
    public String decrypt(String cypher) {
        final byte[] cypherBytes;
        cypherBytes = Base64.decodeBase64(cypher.getBytes());       // Android compatible
        byte[] endTextBytes = SimpleGostAlgorithm.newDecryptor(cypherBytes, key.getBytes()).execute();
        return parser.parseBytesAsText(endTextBytes).trim();
    }

}
