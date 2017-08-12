package ru.iav.takoe.countee.crypt.impl;

import org.apache.commons.codec.binary.Base64;
import ru.iav.takoe.countee.crypt.utils.BytesToStringParser;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by takoe on 01.02.17.
 */
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
        if (true) {
            cypherBytes = Base64.decodeBase64(cypher.getBytes());       // Android compatible
        } else {
            cypherBytes = DatatypeConverter.parseBase64Binary(cypher);
        }
        byte[] endTextBytes = SimpleGostAlgorithm.newDecryptor(cypherBytes, key.getBytes()).execute();
        return parser.parseBytesAsText(endTextBytes).trim();
    }

}