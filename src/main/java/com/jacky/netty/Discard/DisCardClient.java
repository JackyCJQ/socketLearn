package com.jacky.netty.Discard;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * keeps sending random data to the specified address
 */
public class DisCardClient {
    static int PORT = 9000;
    static String HOST = "127.0.0.1";
    static int SIZE = 256;

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new DisCardClientHandler());
                        }
                    });
            ChannelFuture future = b.connect(HOST, PORT).sync();
            future.channel().writeAndFlush(Unpooled.copiedBuffer("jacky".getBytes())).addListener(ChannelFutureListener.CLOSE);

            ChannelFuture channelFuture = future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
