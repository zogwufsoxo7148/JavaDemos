package com.example.javademos.demos.concurrentProgramming.multiThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 使用Callable和FutureTask创建多线程
 */
class CallableTask implements Callable<Integer> {
    // 1. 实现Callable接口并重写call方法
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= 5; i++) {
            sum += i;
            System.out.println(Thread.currentThread().getName() + " 正在计算，i = " + i);
            Thread.sleep(500);  // 模拟任务耗时
        }
        return sum;  // 返回计算结果
    }
}

public class TestCallable {
    public static void main(String[] args) {
        // 2. 创建CallableTask实例
        CallableTask callableTask = new CallableTask();

        // 3. 将Callable实例封装到FutureTask中
        FutureTask<Integer> futureTask = new FutureTask<>(callableTask);

        // 4. 使用Thread类来执行FutureTask
        Thread thread = new Thread(futureTask, "线程1");
        thread.start();

        try {
            // 5. 获取任务执行的结果
            Integer result = futureTask.get();  // 这是阻塞的，直到任务完成
            System.out.println("计算结果是：" + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
