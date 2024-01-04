package com.apv.cryption;

import java.io.IOException;
import java.util.Locale;

public class Runner {
    private enum Function {UNKNOWN, ENCRYPT, DECRYPT}
    
    public static void main(String[] args) {
        if (args.length != 5) throw new RuntimeException("Missing arguments. encrypt|decrypt  cipherfile  passcode  infile outfile");
        final String functionStr = args[0].toLowerCase(Locale.ROOT);
        final Function function = functionStr.equals("encrypt") ? Function.ENCRYPT
                : functionStr.toLowerCase(Locale.ROOT).equals("decrypt") ? Function.DECRYPT
                : Function.UNKNOWN;
        final BinaryFile cipherFile = new BinaryFile(args[1]);
        final String passcode = args[2];
        final BinaryFile infile =  new BinaryFile(args[3]);
        final BinaryFile outfile =  new BinaryFile(args[4]);
        final byte[] cipher;
        try {
            cipher = cipherFile.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final Cryptor cryption = new Cryptor(cipher, passcode);
        final byte[] buffer;
        try {
            buffer = infile.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final byte[] processedBuffer;
        switch (function) {
            case ENCRYPT:
                processedBuffer = cryption.encrypt(buffer);
                break;
            case DECRYPT:
                processedBuffer = cryption.decrypt(buffer);
                break;
            default:
                throw new RuntimeException("Function must be either encrypt or decrypt");
        }
        try {
            outfile.write(processedBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
