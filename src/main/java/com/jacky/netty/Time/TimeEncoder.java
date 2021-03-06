package com.jacky.netty.Time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class TimeEncoder extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        TiemUnit time = new TiemUnit();
        ByteBuf m = ctx.alloc().buffer(4);
        m.writeInt((int) time.value());
        ctx.write(m, promise);
    }
}
