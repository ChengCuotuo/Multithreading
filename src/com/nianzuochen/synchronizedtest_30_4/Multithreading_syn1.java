package com.nianzuochen.synchronizedtest_30_4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lei02 on 2019/4/19.
 */
public class Multithreading_syn1{
    private static int sum = 0;

    public static synchronized void addSum() {
        sum += 1;
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    addSum();
                }
            });
        }
        service.shutdown();
        // 如果没有这句，打印的时候可能线程还没有执行完毕，所以打印出来的并不一定是 1000
        while (!service.isTerminated()) {

        }

        System.out.println(sum); //1000
    }
}
