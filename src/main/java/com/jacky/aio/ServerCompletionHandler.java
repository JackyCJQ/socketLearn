package com.jacky.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {

    @Override
    public void completed(AsynchronousSocketChannel result, Server attachment) {
        //当有一个客户端介入的时候，直接调用Server.accept()方法，这样反复执行下去，保证多个客户端都可以阻塞
        attachment.assc.accept(attachment, this);
        read(result);

    }

    @Override
    public void failed(Throwable exc, Server attachment) {
        exc.printStackTrace();
    }


    private void read(final AsynchronousSocketChannel asc) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        asc.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                System.out.println("server-》" + "收到客户端的数据长度为：" + result);
                String resultData = new String(attachment.array()).trim();
                System.out.println("收到客户端的数据为：" + resultData);
                String response = "服务器响应，收到了客户端发来的数据：" + resultData;
                write(asc, response);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                exc.printStackTrace();
            }
        });

    }

    private void write(AsynchronousSocketChannel asc, String response) {

        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(response.getBytes());
            buffer.flip();
            asc.write(buffer).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
