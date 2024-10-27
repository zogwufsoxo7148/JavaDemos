package com.example.javademos.demos.InterviewQuestion;

import java.util.ArrayList;
import java.util.List;

public class ThreadSafeQueue<T> {
    private final List<T> queue;

    // 构造方法初始化队列
    public ThreadSafeQueue() {
        this.queue = new ArrayList<>();
    }

    /**
     * 向队列末尾添加一个元素，线程安全
     *
     * @param element 要添加到队列的元素
     */
    public synchronized void add(T element) {
        if (element == null) {
            throw new IllegalArgumentException("队列中不允许添加空元素。");
        }
        queue.add(element);
        notifyAll(); // 通知所有等待的线程有新元素被添加
    }

    /**
     * 移除队列头部的一个元素，线程安全
     *
     * @return 被移除的元素
     * @throws InterruptedException 如果线程在等待时被中断
     */
    public synchronized T remove() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // 队列为空时等待
        }
        return queue.remove(0);
    }

    /**
     * 获取队列的当前大小
     *
     * @return 队列中元素的数量
     */
    public synchronized int size() {
        return queue.size();
    }

    /**
     * 检查队列是否为空
     *
     * @return 如果队列为空则返回 true，否则返回 false
     */
    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        ThreadSafeQueue<Integer> queue = new ThreadSafeQueue<>();

        // 生产者线程，向队列中添加元素
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.add(i);
                System.out.println("生产者添加元素: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("生产者线程被中断。");
                }
            }
        });

        // 消费者线程，从队列中移除元素
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    int value = queue.remove();
                    System.out.println("消费者移除元素: " + value);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("消费者线程被中断。");
                }
            }
        });

        // 启动生产者和消费者线程
        producer.start();
        consumer.start();

        // 等待生产者和消费者线程结束
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("主线程被中断。");
        }
    }
}
