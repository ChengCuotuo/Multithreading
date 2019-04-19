package com.nianzuochen.synchronizedtest_30_4;

/**
 * Created by lei02 on 2019/4/19.
 */
public class Multithreading_not_syn {
    private static int sum = 0;
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sum += 1;
                }
            }).start();
        }

        System.out.println(sum);
        //483
    }
}
