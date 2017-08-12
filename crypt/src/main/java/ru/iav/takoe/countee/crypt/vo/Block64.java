package ru.iav.takoe.countee.crypt.vo;

import com.google.common.base.Objects;

import static ru.iav.takoe.countee.crypt.utils.ByteIntUtils.bytesToInts;
import static ru.iav.takoe.countee.crypt.utils.ByteIntUtils.intsToBytes;
import static ru.iav.takoe.countee.crypt.utils.ByteIntUtils.lengthOfShorterArray;

public class Block64 {

	public final static int
		LENGTH_BITS = 64,
		LENGTH_BYTES = 8,
		LENGTH_INTS = 2;

	private int upperHalf, lowerHalf;
	
	public Block64(int upperHalf, int lowerHalf) {
		setWhole(upperHalf,lowerHalf);
	}

	public Block64() {
		setWhole(0,0);
	}

	public static Block64[] getBlocksFromSting(String s) {
		return getBlocksFromBytes(s.getBytes());
	}

	public static Block64[] getBlocksFromBytes(byte[] byteArray) {
        return getBlocksFromInts(bytesToInts(byteArray));
	}

	private static Block64[] getBlocksFromInts(int[] intArray) {
		int blockArrayLength = lengthOfShorterArray(intArray,2);
		Block64[] blockArray = new Block64[blockArrayLength];

		for (int i = 0; i < blockArrayLength; i++) {
			int upperHalf = intArray[2*i];
			int lowerHalf = (2*i+1 < intArray.length) ?
					intArray[2*i+1] : 0; 
			blockArray[i] = new Block64(upperHalf,lowerHalf);
		}
		return blockArray;
	}

    private static int[] getInts(Block64[] blockArray) {
		int[] intArray = new int[blockArray.length*2];
		for (int i = 0; i < blockArray.length; i++) {
			intArray[2*i] = blockArray[i].getUpperHalf();
			intArray[2*i+1] = blockArray[i].getLowerHalf();
		}
		return intArray;
	}

	public static byte[] getBytes(Block64[] blockArray) {
		return intsToBytes(getInts(blockArray));
	}

	public int getUpperHalf() {
		return upperHalf;
	}

	public int getLowerHalf() {
		return lowerHalf;
	}

	public int[] getWholeAsInts() {
        return new int[] {upperHalf,lowerHalf};
	}

	private void setWhole(int upperHalf, int lowerHalf) {
		this.upperHalf = upperHalf;
		this.lowerHalf = lowerHalf;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Block64 block64 = (Block64) o;
		return upperHalf == block64.upperHalf &&
				lowerHalf == block64.lowerHalf;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(upperHalf, lowerHalf);
	}
}
