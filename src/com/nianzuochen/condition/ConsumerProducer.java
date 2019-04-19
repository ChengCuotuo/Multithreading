package com.nianzuochen.condition;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lei02 on 2019/4/18.
 * 模拟生产者和消费者问题，这是两个线程之间的合作关系
 * 需要使用 condition 来完成两个线程之间的合作
 * 设置 notFull 和 notEmpty 两个条件
 */
public class ConsumerProducer {
    private static Buffer buffer = new Buffer();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new ProducerTask());
        executorService.execute(new ConsumerTask());
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
                    buffer.write(i++);
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
                    System.out.println("\t\tConsumer reads " + buffer.read());
                    Thread.sleep((int)(Math.random() * 1000));
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    //一个缓冲区，容量为 1
    public static class Buffer {
        //限制队列中的数据量
        private static final int CAPACITY = 1;
        private LinkedList<Integer> queue = new LinkedList<>();

        //创建一个锁
        private static Lock lock = new ReentrantLock();

        //创建两个条件 condition
        private static Condition notEmpty = lock.newCondition();
        private static Condition notFull = lock.newCondition();
        //向队列中添加指定的内容，如果队列已满，则让线程等待，直到倍唤醒，才向其中写入数据
        public void write(int value) {
            lock.lock();
            try {
                while (queue.size() == CAPACITY) {
                    System.out.println("Wait for notFull condition");
                    notFull.await();
                }
                //写入数据
                queue.offer(value);
                //此时非空，唤醒非空等待线程
                notEmpty.signal();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        //读取队列中的数据，当队列为空的时候，线程进入等待状态，直到被唤醒，才读数据
        public int read() {
            int value = 0;
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    System.out.println("\t\tWait for notEmpty condition");
                    notEmpty.wait();
                }
                //读取数据，此时已空
                value = queue.remove();
                //唤醒非满等待线程
                notFull.signal();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
                return value;
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
Producer writes 5
Wait for notFull condition
		Consumer reads 4
		Consumer reads 5
*/