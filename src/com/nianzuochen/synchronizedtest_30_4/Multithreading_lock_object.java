package com.nianzuochen.synchronizedtest_30_4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lei02 on 2019/4/21.
 * 测试对于对象的同步方法，是将一个 Integer 对象，执行1000个线程来对其进行 +1 操作
 */
public class Multithreading_lock_object {
    private static Integer sum = new Integer(0);

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        ExecutorService service = Executors.newCachedThreadPool();
        //使用显示的加锁和解锁方法，实现线程的同步
        for (int i = 0; i < 1000; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    sum += 1;
                    lock.unlock();
                }
            });
        }

        service.shutdown();
        while (!service.isTerminated()) {}
        System.out.println(sum);
        //1000
    }
}
