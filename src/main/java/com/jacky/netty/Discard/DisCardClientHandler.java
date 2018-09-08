package com.jacky.netty.Discard;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutorGroup;

public class DisCardClientHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf buffer = (ByteBuf) msg;
            System.out.println(buffer.toString(CharsetUtil.UTF_8));
        } finally {
            ReferenceCountUtil.release(msg);
        }


    }
    /*
    private ByteBuf content;
    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        this.ctx = ctx;
        //initialize the message
        content = ctx.alloc().directBuffer(DisCardClient.SIZE).writeZero(DisCardClient.SIZE);
        //send the initial message
        generateTraffic();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        content.release();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //discard it
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


    long counter;

    public void generateTraffic() {
        ctx.writeAndFlush(content).addListener(trafficGenerator);
    }

    private final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            if (channelFuture.isSuccess()) {
                generateTraffic();
            } else {
                channelFuture.cause().printStackTrace();
                ;
                channelFuture.channel().close();
            }
        }
    };*/


}
