package com.example.javademos.demos.concurrentProgramming.multiThread;

/**
 * 自定义线程类
 */
public class MyThread extends Thread {
    private String taskName;

    // 构造器，用于接收线程的任务名称
    public MyThread(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println(taskName + " - 开始执行任务");

        // 模拟任务执行，例如打印1到5的数字
        for (int i = 1; i <= 5; i++) {
            System.out.println(taskName + " - 计数: " + i);
            try {
                // 每次计数暂停500毫秒，模拟任务耗时
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(taskName + " - 线程被中断");
                e.printStackTrace();
            }
        }

        System.out.println(taskName + " - 任务执行完毕");
    }

    public static void main(String[] args) {
        // 创建多个线程实例
        MyThread thread1 = new MyThread("线程1");
        MyThread thread2 = new MyThread("线程2");
        MyThread thread3 = new MyThread("线程3");

        // 启动线程
        thread1.start();
        thread2.start();
        thread3.start();

        // 主线程等待所有子线程完成
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new ThreadLocal<String>();

        System.out.println("所有线程任务已完成");
    }
}