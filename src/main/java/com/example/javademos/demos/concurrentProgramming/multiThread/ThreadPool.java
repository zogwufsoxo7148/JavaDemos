package com.example.javademos.demos.concurrentProgramming.multiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用线程池创建多线程
 */
public class ThreadPool{
    public static void main(String[] args) {
        // 1. 创建一个固定大小为3的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // 2. 提交多个任务给线程池执行
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executorService.submit(() -> {
                System.out.println("任务 " + taskId + " 开始执行 by " + Thread.currentThread().getName());
                try {
                    // 模拟任务执行耗时
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务 " + taskId + " 执行完毕 by " + Thread.currentThread().getName());
            });
        }

        // 3. 关闭线程池，等待所有任务完成后终止
        executorService.shutdown();
    }
}
