package com.jacky.netty.Echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        //绑定端口 启动服务器
        new EchoServer(9000).start();
    }

    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        /**
         * EventLoopGroup里面可以包含多个EventLoop，EventLoop通常处理多个Channel中的事件
         * EventLoop负责管理channel，每个channel表示一个socket或某种可执行IO操作的组件
         * netty中所有IO操作都是异步执行的
         */
        //创建Event-LoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建Server-Bootstrap
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    //指定所使用的nio传输channel
                    .channel(NioServerSocketChannel.class)
                    //使用指定的端口地址设置套接字地址
                    .localAddress(new InetSocketAddress(9000))
                    /**
                     * ChannelInitializer本身也是一个Channelhandler，在添加完其他handler后，会
                     * 自动移除掉
                     */
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            //异步的绑定服务器，调用sync()方法阻塞等待直到绑定完成
            ChannelFuture channelFuture = b.bind().sync();
            //服务器端的channel阻塞，并且阻塞当前线程直到它完成
            channelFuture.channel().closeFuture().sync();
            System.out.println("server :start ");
        } finally {
            group.shutdownGracefully().sync();
        }
    }

}
