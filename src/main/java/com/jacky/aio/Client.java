package com.jacky.aio;

import sun.tools.jar.CommandLine;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

public class Client implements Runnable {
    private AsynchronousSocketChannel asc;

    public Client() throws Exception {
        asc = AsynchronousSocketChannel.open();
    }

    public void connect() {
        asc.connect(new InetSocketAddress("127.0.0.1", 8765));
    }

    public void write(String request) {
        try {
            asc.write(ByteBuffer.wrap(request.getBytes()));
            read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void read() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            //调用get阻塞在这里
            asc.read(buffer).get();
            buffer.flip();
            byte[] respByte = new byte[buffer.remaining()];
            //写到数组里面
            buffer.get(respByte);
            System.out.println(new String(respByte, "utf-8").trim());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        while (true) {

        }
    }

    public static void main(String[] args) throws Exception {
        Client c1 = new Client();
        c1.connect();
        Client c2 = new Client();
        c2.connect();
        Client c3 = new Client();
        c3.connect();
        new Thread(c1, "c1").start();
        new Thread(c2, "c2").start();
        new Thread(c3, "c3").start();
        Thread.sleep(1000);
        c1.write("c1 aaa");
        c2.write("c2 bbbb");
        c3.write("c3 cccc");
    }
}
