package com.jacky.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {

    public static void main(String[] args) throws Exception {

        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        EventLoopGroup worjGroup = new NioEventLoopGroup();
        final ServerHandler serverHandler = new ServerHandler();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosGroup, worjGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(serverHandler);
                        }
                    });
            //start the server
            ChannelFuture f = b.bind(9000).sync();
            //wit until the server socket is closed
            f.channel().closeFuture().sync();
        } finally {
            boosGroup.shutdownGracefully();
            worjGroup.shutdownGracefully();
        }

    }


}
