package com.jacky.nio;

import sun.applet.Main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class Server implements Runnable {
    //1.多路复用器
    private Selector selector;

    private ByteBuffer readbuf = ByteBuffer.allocate(1024);


    public Server(int port) {

        try {
            //1.打开多路复用器
            selector = Selector.open();
            //2.打开服务器通道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));
            //监听事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server start,port:" + port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                //必须要让多路复用器开始监听
                this.selector.select();
                Iterator<SelectionKey> keyIterator = this.selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {

                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();
                    //如果是有效的
                    if (key.isValid()) {
                        //如果为阻塞状态
                        if (key.isAcceptable()) {
                            this.accept(key);
                        }
                        //如果是为可读状态
                        if (key.isReadable()) {
                            this.read(key);
                        }
                    }


                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void accept(SelectionKey key) {
        try {
            //获取这个key对应的通道
            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
            //阻塞接收
            SocketChannel accept = channel.accept();
            System.out.println("接受一个新的连接");
            accept.configureBlocking(false);
            //改变状态重新注入
            accept.register(selector, SelectionKey.OP_READ);

        } catch (Exception e) {

        }
    }

    public void read(SelectionKey key) {
        try {
            this.readbuf.clear();
            SocketChannel channel = (SocketChannel) key.channel();
            int count = channel.read(this.readbuf);
            if (count == -1) {
                key.channel().close();
                key.cancel();
                return;
            }
            this.readbuf.flip();
            byte[] bytes = new byte[readbuf.remaining()];
            readbuf.get(bytes);
            String body = new String(bytes).trim();
            System.out.println("server:" + body);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new Server(8765)).start();
    }
}
