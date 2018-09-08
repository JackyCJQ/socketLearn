package com.jacky.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestFileChannel {


    public void read() throws IOException {
        FileInputStream in = new FileInputStream("/Users/jacky/a.txt");
        FileChannel fileChannel = in.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = -1;
        while (true) {
            read = fileChannel.read(buffer);
            if (read != -1) {
                System.out.println(new String(buffer.array()));
                buffer.clear();
            } else {
                break;
            }
        }
        in.close();
        fileChannel.close();
    }

    public void write()throws  IOException{
        FileOutputStream out=new FileOutputStream("/Users/jacky/a.txt",true);
        FileChannel outChanel=out.getChannel();

        ByteBuffer buffer=ByteBuffer.allocate(1024);
        for (int i = 0; i < 20; i++) {
            buffer.put("a".getBytes());
        }
        buffer.flip();
        outChanel.write(buffer);
        outChanel.close();
        out.close();

    }


    public static void main(String[] args) throws IOException {
        TestFileChannel test = new TestFileChannel();
        test.write();
        test.read();

    }
}
