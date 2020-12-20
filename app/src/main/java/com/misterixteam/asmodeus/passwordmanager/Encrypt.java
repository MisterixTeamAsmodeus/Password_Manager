package com.misterixteam.asmodeus.passwordmanager;

public class Encrypt {
    private static byte[] getInputBytes(String input) {
        byte[] inputBytes = new byte[input.toCharArray().length];
        for (int i = 0; i < inputBytes.length; i++){
            inputBytes[i] = (byte) input.charAt(i);
        }
        return inputBytes;
    }

    public static String encode(String input, String key) {
        byte[] inputBytes = input.getBytes();
        byte[] keyBytes = String.valueOf(key.hashCode()).getBytes();
        byte[] outputBytes = new byte[inputBytes.length];
        encryptBytes(inputBytes, outputBytes, keyBytes);
        return getOutputEncode(outputBytes);
    }

    private static String getOutputEncode(byte[] outputBytes) {
        StringBuilder output = new StringBuilder();
        for (byte b: outputBytes) {
            output.append((char) b);
        }
        return output.toString();
    }

    public static String decode(String input, String key) {
        byte[] inputBytes = getInputBytes(input);
        byte[] outputBytes = new byte[inputBytes.length];
        byte[] keyBytes = String.valueOf(key.hashCode()).getBytes();
        encryptBytes(inputBytes, outputBytes, keyBytes);
        return new String(outputBytes);
    }

    private static void encryptBytes(byte[] inputBytes, byte[] outputBytes, byte[] keyBytes) {
        for (int i = 0; i < inputBytes.length; i++) {
            outputBytes[i] = (byte) (inputBytes[i] ^ keyBytes[i % keyBytes.length]);
        }
    }
}
