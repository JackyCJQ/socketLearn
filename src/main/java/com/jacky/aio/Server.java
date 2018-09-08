package com.jacky.aio;

import com.sun.rmi.rmid.ExecPermission;

import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    //线程池
    private ExecutorService executors;
    //线程组
    private AsynchronousChannelGroup threadGroup;
    public AsynchronousServerSocketChannel assc;

    public Server(int port) {
        try {
            executors = Executors.newCachedThreadPool();
            threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executors, 1);

            //创建服务器通道
            assc = AsynchronousServerSocketChannel.open(threadGroup);
            assc.bind(new InetSocketAddress(port));
            System.out.println("server start,port:" + port);
            //这里不进行阻塞
            assc.accept(this, new ServerCompletionHandler());
            //阻塞服务器端
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8765);
    }
}
