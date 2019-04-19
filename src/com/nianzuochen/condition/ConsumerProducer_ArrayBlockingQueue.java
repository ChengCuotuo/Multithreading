package com.nianzuochen.condition;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lei02 on 2019/4/18.
 * 使用阻塞队列完成消费者和生产者问题
 */
public class ConsumerProducer_ArrayBlockingQueue {
    private static ArrayBlockingQueue<Integer> buffer =
            new ArrayBlockingQueue<Integer>(2);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new ConsumerProducer.ProducerTask());
        executorService.execute(new ConsumerProducer.ConsumerTask());
        executorService.shutdown();
    }

    //生产者线程
    public static class ProducerTask implements Runnable {
        @Override
        public void run() {
            try {
                int i = 0;
                while (true) {
                    System.out.println("Producer writes " + i);
                    buffer.put(i++);
                    Thread.sleep((int)(Math.random() * 1000));
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    //消费者线程
    public static class ConsumerTask implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println("\t\tConsumer reads " + buffer.take());
                    Thread.sleep((int)(Math.random() * 1000));
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

/*
Producer writes 0
		Consumer reads 0
Producer writes 1
Producer writes 2
Wait for notFull condition
		Consumer reads 1
		Consumer reads 2
Producer writes 3
		Consumer reads 3
Producer writes 4
		Consumer reads 4
		Wait for notEmpty condition
		Consumer reads 0
		Wait for notEmpty condition
		Consumer reads 0
Producer writes 5
Producer writes 6
Wait for notFull condition
		Consumer reads 5
Producer writes 7
Wait for notFull condition
		Consumer reads 6
*/