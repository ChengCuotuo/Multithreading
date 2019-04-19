package com.nianzuochen.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by lei02 on 2019/4/19.
 * 并行编程，查找最大的数
 */
public class ParalleMax {
    public static void main(String[] args) {
        final int N = 9000000;
        int[] list = new int[N];
        for (int i = 0; i < list.length; i++) {
            list[i] = i;
        }

        long startTime = System.currentTimeMillis();
        System.out.println("\nThe maximal number is " + max(list));
        long endTime =  System.currentTimeMillis();
        System.out.println("\nThe number of processors is " + Runtime.getRuntime().availableProcessors());
        System.out.println("Time is " + (endTime - startTime) + " milliseconds");

        //The maximal number is 8999999
        //The number of processors is 4
        //Time is 180 milliseconds
    }

    public static int max(int[] list) {
        //实例化并行编程的对象
        RecursiveTask<Integer> task = new MaxTask(list, 0, list.length);
        //并行编程的线程池
        ForkJoinPool  pool = new ForkJoinPool();
        return pool.invoke(task);
    }
    //实现并行查找最大的值
    public static class MaxTask extends RecursiveTask<Integer> {
        private final int THRESHOLD = 1000;
        private int low;
        private int high;
        private int[] list;

        public MaxTask(int[] list, int low, int high) {
            this.low = low;
            this.high = high;
            this.list = list;
        }
        //当需要排序的数组长度大于 THRESHOLD 就将它划分成两部分，分别进行查找最大值
        @Override
        public Integer compute() {
            if (high - low < THRESHOLD) {
                int max = list[0];
                for (int i = low; i < high; i++) {
                    if (list[i] > max) {
                        max = list[i];
                    }
                }
                return new Integer(max);
            } else {
                int mid = (low + high) / 2;
                RecursiveTask<Integer> left = new MaxTask(list, low, mid);
                RecursiveTask<Integer> right = new MaxTask(list, mid, high);
                //分解执行
                left.fork();
                right.fork();
                //将分解执行后的结果进行合并处理，找到最大值
                return new Integer(Math.max(left.join().intValue(), right.join().intValue()));
            }
        }
    }
}
