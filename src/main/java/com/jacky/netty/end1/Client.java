package com.jacky.netty.end1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class Client {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //自定义分割符
                        ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
                        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, buf));
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new ClientHandler());
                    }
                });
        ChannelFuture channelFuture = b.connect("127.0.0.1", 9000).sync();
        channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("bbb$_".getBytes()));
        channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("ccc$_".getBytes()));
        channelFuture.channel().closeFuture().sync();
        group.shutdownGracefully();
    }
}
