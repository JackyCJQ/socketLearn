package com.jacky.netty.Echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * ChannelInboundHandler用于接受消息，并决定如何区处理接受到的消息
 */
//ChannelInboundHandlerAdapter不会自动释放消息
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        //将消息打印到控制台
        System.out.println(in.toString(CharsetUtil.UTF_8));
        //将收到的消息写给发送者，而不冲刷出战消息
        ChannelFuture write = ctx.write(in);
        write.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("write success");
                } else {
                    System.out.println("wtire fail");
                    future.cause().printStackTrace();
                }
            }
        });
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将所有收到的客户端的消息冲刷回客户端，然后关闭Channel
        //writeAndFlush 会自动释放消息 并且服务端会主动关闭连接
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印异常
        cause.printStackTrace();
        //关闭channel
        ctx.close();
    }
}
