package ru.iav.takoe.countee.crypt.impl;

import ru.iav.takoe.countee.crypt.vo.Block64;

import javax.annotation.Nonnull;

import static ru.iav.takoe.countee.crypt.impl.SimpleGostAlgorithm.Mode.DECRYPTION;
import static ru.iav.takoe.countee.crypt.impl.SimpleGostAlgorithm.Mode.ENCRYPTION;
import static ru.iav.takoe.countee.crypt.utils.ByteIntUtils.byteArrayLengthFit;

/**
 * Created by takoe on 01.02.17.
 */
class SimpleGostAlgorithm {

    enum Mode {ENCRYPTION, DECRYPTION}

    private static final KeyFitter keyFitter = new KeyFitter();

    private FeistelCypher feistelCypher;

    private Mode mode;

    private int initialTextByteArrayLength;

    private Block64[] textBlocks;

    private int[] keyBlocks;

    static SimpleGostAlgorithm newDecryptor(byte[] cypher, byte[] key) {
        return newInstance(DECRYPTION, cypher, key);
    }

    static SimpleGostAlgorithm newEncryptor(String text, String key) {
        return newInstance(ENCRYPTION, text.getBytes(), key.getBytes());
    }

    private static SimpleGostAlgorithm newInstance(@Nonnull Mode mode, byte[] text, byte[] key) {
        int[] keyAsInts = keyFitter.getKeyIntsFixedLength(key);
        Block64[] textAsBlocks = Block64.getBlocksFromBytes(text);
        return new SimpleGostAlgorithm(mode, textAsBlocks, keyAsInts, text.length);
    }

    private SimpleGostAlgorithm(Mode mode, Block64[] textBlocks, int[] keyBlocks, int initialTextByteArrayLength) {
        this.mode = mode;
        this.textBlocks = textBlocks;
        this.keyBlocks = keyBlocks;
        this.initialTextByteArrayLength = initialTextByteArrayLength;
        feistelCypher = FeistelCypher.on(this);
    }

    byte[] execute() {
        Block64[] resultBlocks = feistelCypher.executeSubstitutionLoops();
        byte[] resultBytes = Block64.getBytes(resultBlocks);
        return byteArrayLengthFit(resultBytes, initialTextByteArrayLength);
    }

    boolean isEncryptionMode() {
        return ENCRYPTION.equals(mode);
    }

    Block64[] getTextBlocks() {
        return textBlocks;
    }

    int[] getKeyBlocks() {
        return keyBlocks;
    }
}
