package com.jacky.test;

import sun.applet.Main;

import java.nio.IntBuffer;

public class TestBuffer {
    public static void main(String[] args) {
      /*  //申请10个int类型的数据缓冲区
        IntBuffer buf = IntBuffer.allocate(10);
        buf.put(1);
        buf.put(2);
        buf.put(3);
        System.out.println("复位之前各个位置：" + buf);
        //进行操作，准备读取数据,一般要读的时候才进行这个操作
        buf.flip();
        System.out.println("复位之后各个位置：" + buf);
        System.out.println(buf.get(1));
        System.out.println("获取具体的下标的元素：" + buf);
        System.out.println(buf.get());
        System.out.println("获取一个元素元素：游标会下移" + buf);
        //复位操作之后只能更改lim之前的位置
        buf.put(2, 4);
        System.out.println(buf);*/

        int[] arr = new int[]{1, 2, 3};
      /*  IntBuffer buffer=IntBuffer.wrap(arr);
        System.out.println(buffer);*/
       /* IntBuffer buffer = IntBuffer.wrap(arr, 0, 2);
        System.out.println(buffer);*/

        IntBuffer buffer = IntBuffer.allocate(10);
        buffer.put(arr);
//        System.out.println(buffer);
//        IntBuffer buffer1 = buffer.duplicate();
//        System.out.println(buffer1);
//        buffer.position(1);
//        System.out.println(buffer);
//        //剩余的位置是从position开始算起
//        System.out.println(buffer.remaining());
        buffer.flip();
        int[] arr2 = new int[buffer.remaining()];
        //是从 pos到lim

        buffer.get(arr2);
        for (int i = 0; i < arr2.length; i++) {
            System.out.println(arr2[i]);
        }


    }

}
