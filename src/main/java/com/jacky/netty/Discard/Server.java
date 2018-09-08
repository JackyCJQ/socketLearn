package com.jacky.netty.Discard;

import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
    public static void main(String[] args) throws Exception {
        //处理连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //实际处理业务操作
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //创建一个辅助类，对server进行一系列的配置
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workGroup)
                //具体类型的通道
                .channel(NioServerSocketChannel.class)
                //具体的事件处理器
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //设置处理handler可以设置多个
                        pipeline.addLast(new ServerHandler());
                    }
                })
                //影响新的连接
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.SO_RCVBUF, 32 * 1024)//服务器接受缓冲区大小
                .option(ChannelOption.SO_SNDBUF, 32 * 1024)//服务器发送缓冲区大小
                //保持连接
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        //绑定指定的端口并监听
        ChannelFuture sync = b.bind(9000).sync();
        sync.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }
}
