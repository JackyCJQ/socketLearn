package com.jacky.bio;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final static int port = 8765;

    public static void main(String[] args) {
        ServerSocket serverSock = null;
        try {
            serverSock = new ServerSocket(port);
            System.out.println("server start:");
            //进行阻塞 一直等到有连接进来
            Socket socket = serverSock.accept();
            new Thread(new ServerHandler(socket)).start();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (serverSock != null) {
                try {
                    serverSock.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
