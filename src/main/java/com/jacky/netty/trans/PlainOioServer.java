package com.jacky.netty.trans;

import com.jacky.netty.Echo.EchoClient;
import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class PlainOioServer {
    //阻塞的方式实现客户端
    public void server(int port) throws Exception {
        final ServerSocket socket = new ServerSocket(port);
        try {
            while (true) {
                final Socket clientSocket = socket.accept();
                System.out.println("accept connection form " + clientSocket);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream out;
                        try {
                            out = clientSocket.getOutputStream();
                            out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                            out.flush();
                            clientSocket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            try {
                                clientSocket.close();
                            } catch (IOException e1) {
                            }
                        }
                    }
                }).start();
            }
        } catch (IOException e) {

        }
    }

    public void NioServer(int port) throws Exception {
        System.out.println("Listening for connections on port :" + port);
        ServerSocketChannel serverSocketChannel;
        Selector selector;

        serverSocketChannel = ServerSocketChannel.open();
        //服务端socket
        ServerSocket ss = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        ss.bind(address);
        serverSocketChannel.configureBlocking(false);
        selector = Selector.open();

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());

        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> key = keys.iterator();
            while (key.hasNext()) {
                SelectionKey key1 = key.next();
                key.remove();
                try {
                    if (key1.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key1.channel();
                        SocketChannel client = server.accept();
                        System.out.println("accept connection from " + client);
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_WRITE);
                    }
                    if (key1.isWritable()) {
                        SocketChannel client = (SocketChannel) key1.channel();
                        ByteBuffer buffer = (ByteBuffer) key1.attachment();
                        while (buffer.hasRemaining()) {
                            if (client.write(buffer) == 0) {
                                break;
                            }
                        }
                        client.close();
                    }
                } catch (IOException e) {
                    key1.cancel();
                    try {
                        key1.channel().close();
                    } catch (IOException eq) {

                    }
                }
            }

        }


    }

}
