package com.apv.cryption;

import java.io.*;

public class BinaryFile extends File {
    
    public BinaryFile(String filename) {
        super(filename);
    }
    
    public byte[] load() throws RuntimeException, IOException {
        if (!exists()) throw new RuntimeException(String.format("File '%s' does not exist.", this.getName()));
        if (!isFile()) throw new RuntimeException(String.format("File '%s' is not a standard file.", this.getName()));
        if (!canRead()) throw new RuntimeException(String.format("File '%s' cannot be read.", this.getName()));
        byte[] data = new byte[(int) length()];
        try (DataInputStream dis = new DataInputStream(new FileInputStream(this))) {
            dis.readFully(data);
        } catch (IOException e) {
            throw e;
        }
        return data;
    }

    public void write(byte[] buffer) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(this.getName()))) {
            dos.write(buffer);
        } catch (IOException e) {
            throw e;
        }
    }
}
