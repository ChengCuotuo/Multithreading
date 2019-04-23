package com.nianzuochen.genericsort_30_13;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by lei02 on 2019/4/23.
 * 使用泛型编写通用的 并行合并算法
 */
public class GenericParallelMergeSort {
    public static void main(String[] args) {
        final int SIZE = 7000000;
        Double[] list = new Double[SIZE];

        for (int i = 0; i < list.length; i++) {
            list[i] = (Math.random() * 10000000);
        }

        long startTime = System.currentTimeMillis();
        parallelMergeSort(list);
        long endTime = System.currentTimeMillis();
        System.out.println("\nParallel time with " + Runtime.getRuntime().availableProcessors()
                + " processors is " + (endTime - startTime) + " milliseconds");
        // Integer Parallel time with 4 processors is 10207 milliseconds
        // Double Parallel time with 4 processors is 11262 milliseconds

    }

    // 指定了 E 是 Comparable的子类型，指定了进行比较的元的 E 类型
    public static <E extends Comparable<E>> void parallelMergeSort(E[] list) {
        //创建并行线程的线程池
        ForkJoinPool pool = new ForkJoinPool();
        //创建并行任务
        SortTask maintask = new SortTask(list);
        //执行
        pool.invoke(maintask);
    }

    //继承自 RecursiveAction 的类，提供了进行并行排序的方法，同时指定其中的数据类型 E
    public static class SortTask<E extends Comparable<E>> extends RecursiveAction {
        //阈值，小于这个数的用基本的合并排序
        private final int THRESHOLD = 500;
        //存放需要排序的泛型数组
        private E[] list;

        public SortTask(E[] list) {
            this.list = list;
        }

        @Override
        public void compute() {
            if (list.length < THRESHOLD) {
                //Arrays提供了支持泛型排序的，是通过 Comparable 对象的 compareTo 进行比较的
                Arrays.sort(list);
            } else {
                E[] firstHalf = (E[])new Comparable[list.length / 2];
                System.arraycopy(list, 0, firstHalf, 0, list.length / 2);

                int secondHalfLength = list.length - list.length / 2;
                E[] secondHalf = (E[])new Comparable[secondHalfLength];
                System.arraycopy(list, list.length / 2, secondHalf, 0, secondHalfLength);
                //分解给定任务，并在完成之后返回
                invokeAll(new SortTask(firstHalf), new SortTask(secondHalf));
                //将返回后的两个数组进行合并
                merge(firstHalf, secondHalf, list);
            }
        }

        public <E extends Comparable<E>> void merge(E[] list1, E[] list2, E[] temp) {
            int current1 = 0;
            int current2 = 0;
            int current3 = 0;

            while (current1 < list1.length && current2 < list2.length) {
                if (list1[current1].compareTo(list2[current2]) > 0) {
                    temp[current3++] = list1[current1++];
                } else {
                    temp[current3++] = list2[current2++];
                }
            }

            while (current1 < list1.length) {
                temp[current3++] = list1[current1++];
            }

            while (current2 < list2.length) {
                temp[current3++] = list2[current2++];
            }
        }
    }
}
