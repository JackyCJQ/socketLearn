package com.jacky.bio2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    final static String ADDRESS = "127.0.0.1";
    final static int port = 8765;

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket(ADDRESS, port);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            //自动冲刷是有条件的 需要以换行符为界限 进行冲刷
            out.println("接受到客户端的请求");
            String response = in.readLine();
            System.out.println("Client:" + response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    in = null;
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    out = null;
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    socket = null;
                }
            }
        }
    }
}
