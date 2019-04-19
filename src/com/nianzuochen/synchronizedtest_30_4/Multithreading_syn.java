package com.nianzuochen.synchronizedtest_30_4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lei02 on 2019/4/19.
 */
public class Multithreading_syn {
    private static Sum sum = new Sum();

    public static class AddNum implements Runnable {
        @Override
        public void run() {
            sum.addNum();
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            service.execute(new AddNum());
        }
        service.shutdown();
        while (!service.isTerminated()) {
        }

        System.out.println(sum.getSum());
        //1000
    }
}

class Sum {
    private int sum = 0;
    public synchronized void addNum() {
        sum += 1;
    }

    public int getSum () {
        return sum;
    }
}
