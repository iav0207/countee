package ru.iav.takoe.countee.crypt.impl;

import javax.annotation.ParametersAreNonnullByDefault;

import ru.iav.takoe.countee.crypt.vo.Block64;

@ParametersAreNonnullByDefault
class FeistelCypher {

    private static FeistelFunction feistelFunction = new FeistelFunction();

    private Block64[] textBlocks;

    private int[] keyBlocks;

    private boolean isEncryptionMode;

    public static FeistelCypher on(SimpleGostAlgorithm algorithm) {
        return new FeistelCypher(algorithm);
    }

    private FeistelCypher(SimpleGostAlgorithm algorithm) {
        this.textBlocks = algorithm.getTextBlocks();
        this.keyBlocks = algorithm.getKeyBlocks();
        this.isEncryptionMode = algorithm.isEncryptionMode();
    }

    FeistelCypher(Block64[] textBlocks, int[] keyBlocks, boolean isEncryptionMode) {
        this.textBlocks = textBlocks;
        this.keyBlocks = keyBlocks;
        this.isEncryptionMode = isEncryptionMode;
    }

    Block64[] executeSubstitutionLoops() {
        final int roundsNum = 32;
        Block64[] out = new Block64[textBlocks.length];
        for (int m = 0; m < textBlocks.length; m++) {
            int lowerHalf = textBlocks[m].getLowerHalf();
            int upperHalf = textBlocks[m].getUpperHalf();

            for (int i = 0; i < roundsNum; i++) {
                int shuffle = applyFeistelFunction(lowerHalf, keyBlocks[subkeyIndex(i)]);
                shuffle = upperHalf ^ shuffle;

                if (i < roundsNum - 1) {
                    // Switch
                    upperHalf = lowerHalf;
                    lowerHalf = shuffle;
                } else {
                    upperHalf = shuffle;
                }
            }
            out[m] = new Block64(upperHalf, lowerHalf);
        }
        return out;
    }

    private int subkeyIndex(int iteration) {
        if (isEncryptionMode)		// Encryption key blocks consequence
            return (iteration<24) ? (iteration%8) : (7-(iteration%8));
        else                	    // Decryption key blocks consequence
            return (iteration<8)  ? (iteration%8) : (7-(iteration%8));
    }

    private int applyFeistelFunction(int text, int keyBlock) {
        return feistelFunction.apply(text, keyBlock);
    }

}
