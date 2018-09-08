package com.jacky.nio;

import com.sun.org.apache.bcel.internal.generic.FNEG;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

    public static void main(String[] args) throws IOException {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8765);
        SocketChannel sc = null;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            sc = SocketChannel.open();
            sc.connect(address);
            while (true) {
                byte[] bytes = new byte[1024];
                System.in.read(bytes);
                buffer.put(bytes);

                buffer.flip();
                sc.write(buffer);
                buffer.clear();
            }
        } catch (Exception e) {

        }finally {
            if(sc!=null){
                sc.close();

            }
        }

    }


}
