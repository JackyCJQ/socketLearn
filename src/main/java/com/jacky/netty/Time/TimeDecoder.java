package com.jacky.netty.Time;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解决传输拆包，沾包问题
 * 每当有新的数据接受的时候，都会调用decode（）方法来处理内部的那个累积缓冲
 */
public class TimeDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //当in中缓冲累积到足够数据时，开始处理
        if(in.readableBytes()<4){
            return;
        }
        out.add(in.readBytes(4));
    }
}
