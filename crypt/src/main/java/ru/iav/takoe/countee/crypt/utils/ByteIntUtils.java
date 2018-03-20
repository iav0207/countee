package ru.iav.takoe.countee.crypt.utils;


import static com.google.common.base.Preconditions.checkArgument;

public class ByteIntUtils {

    private ByteIntUtils () {
    }
	
	public static int[] bytesToInts(byte[] byteArray) {
	    if (byteArray.length ==0) {
	        return new int[0];
        }
		int intArrayLength = lengthOfShorterArray(byteArray,4);
		return bytesToInts(byteArray,intArrayLength);
	}

    public static int[] bytesToInts(byte[] byteArray, int intsLength) {
		int bytesLength = byteArray.length;
		checkLengthPositivity(intsLength);
		checkLengthNotLessThan(4*intsLength,bytesLength);

        StringBuilder s = new StringBuilder();
        int[] intArray = new int[intsLength];
		int bytesNewLength = makeLengthMultipleTo(bytesLength,4);
		byte[] byteArrayResized = byteArrayLengthFit(byteArray,bytesNewLength);

        for (int i = 0; i < bytesNewLength; i++) {
            s.append(intToBinaryString((int) byteArrayResized[i], 8));
            if (i%4 == 3) {
                intArray[i/4] = (int) Long.parseLong(s.toString(),2);
                s = new StringBuilder();
            }
        }
        return intArray;
    }
    
    public static byte[] intsToBytes(int[] intArray) {
        int n = intArray.length;
        String s;
        byte[] byteArray = new byte[4*n];
        for (int i = 0; i < n; i++) {
            s = intToBinaryString(intArray[i],32);
            for (int j = 0; j < 4; j++) {
                byteArray[4*i+j] = (byte) Integer.parseInt(
                        s.substring(8*j,8*(j+1)), 2);
            }
        }
        return byteArray;
    }
    
    public static String intToBinaryString(int n, int stringLength) {
		checkLengthPositivity(stringLength);
        StringBuilder s = new StringBuilder(Integer.toBinaryString(n));
        // Fit string length
            // Leading zeros addition
        while (s.length() < stringLength)
            s.insert(0, "0");
            // Leaving required length by cutting upper bits
        s = new StringBuilder(s.substring(s.length() - stringLength, s.length()));
        return s.toString();
    }
        
	public static byte[] byteArrayLengthFit(byte[] byteArray, int fitTo) {
		checkLengthPositivity(fitTo);
        byte[] out = new byte[fitTo];
        for (int i = 0; i < fitTo; i++)
            out[i] = (i < byteArray.length) ? byteArray[i] : 0;
        return out;
    }

    public static byte[] byteArrayLengthFitCyclic(byte[] byteArray, int fitTo) {
		checkLengthPositivity(fitTo);
        byte[] out = new byte[fitTo];
        for (int i = 0; i < fitTo; i++)
            out[i] = byteArray[i%byteArray.length];
        return out;
    }

    public static int lengthOfShorterArray(int[] array, int xTimesSmaller) {
    	return lengthOfShorterArray(array.length,xTimesSmaller);
    }
    
    private static int lengthOfShorterArray(byte[] array, int xTimesSmaller) {
    	return lengthOfShorterArray(array.length,xTimesSmaller);
    }
    
    private static int lengthOfShorterArray(int length, int xTimesSmaller) {
        if (length == 0) {
            return 0;
        }
		checkLengthPositivity(length);
		checkPositivity(xTimesSmaller);
		return length/xTimesSmaller + ( (length%xTimesSmaller == 0) ? 0:1 );
	}

	private static int makeLengthMultipleTo(int length, int multipleTo) {
		checkLengthPositivity(length);
		checkPositivity(multipleTo);
		int extension = multipleTo - (length % multipleTo);
		extension *= (length%multipleTo == 0) ? 0:1;
		return length + extension;
	}

    private static void checkPositivity(int n)  {
        if (n < 1)
            throw new IllegalArgumentException(
                    "\nThe argument must be positive.");
	}
    
	private static void checkLengthPositivity(int length)  {
        checkArgument(length >= 0, "Length should be positive");
	}
    
	private static void checkLengthNotLessThan(int length, int notLessThan) {
        checkArgument(length >= notLessThan,
                "Length must be greater or equal to %d, but was %d", notLessThan, length);
	}

}
