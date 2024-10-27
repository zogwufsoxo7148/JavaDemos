package com.example.javademos.demos.concurrentProgramming.multiThread;

/**
 * 演示volatile关键字的用法
 */
public class VolatileExample {
    private int x = 0;
    private volatile boolean v = false;

    // writer方法，修改x和v的值
    public void writer() {
        x = 42;
        v = true;
        System.out.println("Writer: x set to 42, v set to true");
    }

    // reader方法，根据v的值读取x
    public void reader() {
        if (v) {
            System.out.println("Reader: v is true, x = " + x);
        } else {
            System.out.println("Reader: v is false, no access to x");
        }
    }

    public static void main(String[] args) {
        VolatileExample example = new VolatileExample();

        // 创建writer线程
        Thread writerThread = new Thread(() -> {
            try {
                // 延迟一段时间，让reader先检查v的值
                Thread.sleep(100);
                example.writer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 创建reader线程
        Thread readerThread = new Thread(() -> {
            while (!example.v) {
                // 循环等待v变为true
            }
            example.reader();
        });

        // 启动两个线程
        writerThread.start();
        readerThread.start();

        // 等待线程结束
        try {
            writerThread.join();
            readerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}