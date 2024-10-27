package com.example.javademos.demos.concurrentProgramming.multiThread;

/**
 * 使用Runnable接口创建多线程
 */
// 1. 实现Runnable接口的类
class RunnableThread implements Runnable {
    // 2. 重写run()方法，定义线程要执行的任务
    @Override
    public void run() {
        // 在线程中执行的任务
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " 正在执行，i = " + i);
            try {
                // 模拟任务耗时
                Thread.sleep(1000);  // 线程休眠1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class TestRunnable {
    public static void main(String[] args) {
        // 3. 创建RunnableThread实例
        RunnableThread runnableTask = new RunnableThread();

        // 4. 将Runnable实例传递给Thread对象
        Thread thread1 = new Thread(runnableTask, "线程1");
        Thread thread2 = new Thread(runnableTask, "线程2");

        // 5. 启动线程
        thread1.start();
        thread2.start();
    }
}
