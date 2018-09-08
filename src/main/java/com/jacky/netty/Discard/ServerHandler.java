package com.jacky.netty.Discard;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

public class ServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf buffer = (ByteBuf) msg;
            System.out.println(buffer.toString(CharsetUtil.UTF_8));

            ctx.writeAndFlush(Unpooled.copiedBuffer("jacky".getBytes()));
        } finally {
            ReferenceCountUtil.release(msg);
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
        ctx.close();
    }
}
