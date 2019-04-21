package com.nianzuochen.synchronizedtest_30_4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lei02 on 2019/4/19.
 * 同步方法1000个线程处加法
 */
public class Multithreading_syn1{
    private static int sum = 0; //递增的数

    //同步方法，每次调用自动将 sum + 1
    public static synchronized void add() {
        sum += 1;
    }

    public static void main(String[] args) {
        //线程池，用来高效的处理多线程
        ExecutorService service = Executors.newCachedThreadPool();
        //用 1000 个线程来调用 add 方法
        for (int i = 0; i < 1000; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    add();
                }
            });
        }

        //等待线程池中得线程都被执行
        service.shutdown();
        //线程池中的所有的任务是否都已经终止
        while(!service.isTerminated()) {}
        System.out.println(sum);
        //1000
    }
}
