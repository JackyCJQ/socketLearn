package com.jacky.netty.Time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

public class TimeEncoder1 extends MessageToByteEncoder<TiemUnit> {
    @Override
    protected void encode(ChannelHandlerContext ctx, TiemUnit msg, ByteBuf out) throws Exception {
        out.writeInt((int) msg.value());
    }

    public static void main(String[] args) {
        ByteBuf buf1= Unpooled.buffer(4);
        buf1.writeInt(1);
        buf1.writeInt(2);
        System.out.println(buf1.toString());
    }
}
