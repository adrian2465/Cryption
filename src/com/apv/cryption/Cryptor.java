package com.apv.cryption;

public class Cryptor {
    private final byte[] cipher;
    private final int cipherLen;
    private final String passcode;
    private final byte salt;
    
    public Cryptor(byte[] cipher, String passcode) {
        if (passcode.isEmpty()) throw new RuntimeException("Passcode cannot be empty.");
        this.cipherLen = cipher.length;
        if (this.cipherLen == 0) throw new RuntimeException("cipher cannot be emmpty.");
        this.cipher = cipher;
        this.passcode = passcode;
        byte salt1 = (byte) 0;
        for (char c : passcode.toCharArray()) salt1 ^= c;
        this.salt = salt1;
    }
    
    public byte[] encrypt(byte[] cleartext) {
        final int passcodeLen = passcode.length();
        final byte[] encrypted = new byte[cleartext.length+1];
        encrypted[0] = salt;
        for (int i = 0; i < cleartext.length; i++)
            encrypted[i+1] = (byte) (cleartext[i] ^ cipher[(i % cipherLen)] ^ salt ^ passcode.charAt(i % passcodeLen));
        return encrypted;
    }
    
    public byte[] decrypt(byte[] encrypted) {
        final int passcodeLen = passcode.length();
        final byte[] decrypted = new byte[encrypted.length-1];
        final byte receivedSalt = encrypted[0];
        if (receivedSalt != salt) throw new IllegalArgumentException("Encrypted string doesn't match passcode or cipher.");
        for (int i = 0; i < decrypted.length; i++)
            decrypted[i] = (byte) (passcode.charAt(i % passcodeLen) ^ salt ^ cipher[(i % cipherLen)] ^ encrypted[i+1]);
        return decrypted;
    }

  
}
