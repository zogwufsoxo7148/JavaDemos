package com.example.javademos.demos.concurrentProgramming.multiThread.ThreadModel;

public class ProducerConsumerExample {

    public static void main(String[] args) {
        Data data = new Data();

        // 生产者线程
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    data.produce(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 消费者线程
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    data.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}

class Data {
    private int value;
    private boolean hasData = false;

    public synchronized void produce(int value) throws InterruptedException {
        while (hasData) {
            wait(); // 等待数据被消费
        }
        this.value = value;
        hasData = true;
        System.out.println("Produced: " + value);
        notify(); // 唤醒消费线程
    }

    public synchronized int consume() throws InterruptedException {
        while (!hasData) {
            wait(); // 等待数据被生产
        }
        hasData = false;
        System.out.println("Consumed: " + value);
        notify(); // 唤醒生产线程
        return value;
    }
}