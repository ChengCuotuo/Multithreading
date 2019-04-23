package com.nianzuochen.forkjoin;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by lei02 on 2019/4/19.
 * 并行编程实现排序
 * if (the program is small) {
 *     solve it sequentially;
 * } else {
 *     divide the problems into nonoverlapping subproblems;
 *     solve the subproblems concurrently;
 *     combine the results from subproblems to solve whole problem;
 * }
 */
public class ParallelMergeSort {
    public static void main(String[] args) {
        final int SIZE = 7000000;
        int[] list1 = new int[SIZE];
        int[] list2 = new int[SIZE];

        for (int i = 0; i < list1.length; i++) {
            list1[i] = list2[i] = (int)(Math.random() * 10000000);
        }

        long startTime = System.currentTimeMillis();
        paralleMergeSort(list1);
        long endTime = System.currentTimeMillis();
        System.out.println("\nParallel time with " + Runtime.getRuntime().availableProcessors()
            + " processors is " + (endTime - startTime) + " milliseconds");

        startTime = System.currentTimeMillis();
        MergeSort.mergeSort(list2);
        endTime = System.currentTimeMillis();
        System.out.println("\nSequential time with " + Runtime.getRuntime().availableProcessors()
                + " processors is " + (endTime - startTime) + " milliseconds");
        //Runtime.getRuntime().availableProcessors() 可用处理器的Java虚拟机的数量

        //Parallel time with 4 processors is 1383 milliseconds
        //Sequential time with 4 processors is 3218 milliseconds
    }
    //启用并行编程
    public static void paralleMergeSort(int[] list){
        //并行排序的主任务
        RecursiveAction mainTask = new SortTask(list);
        //放入并行排序的线程池
        ForkJoinPool pool = new ForkJoinPool();
        //执行主任务，并等待完成
        pool.invoke(mainTask);
    }
    // SortTask 实现并行编程
    public static class SortTask extends RecursiveAction {
        // 分解的最小的排序单位，threshold 阈值
        private final int THRESHOLD = 500;
        private int[] list;

        public SortTask(int[] list) {
            this.list = list;
        }

        @Override
        public void compute() {
            if (list.length < THRESHOLD) {
                //System.out.println(list.length);
                Arrays.sort(list);
            } else {
                int[] firstHalf = new int[list.length / 2];
                System.arraycopy(list, 0, firstHalf, 0, list.length / 2);

                int secondHalfLength = list.length - list.length / 2;
                int[] secondHalf = new int[secondHalfLength];
                System.arraycopy(list, list.length / 2, secondHalf, 0, secondHalfLength);
                //分解给定任务，并在完成之后返回
                invokeAll(new SortTask(firstHalf), new SortTask(secondHalf));
                //将返回后的两个数组进行合并
                MergeSort.merge(firstHalf, secondHalf, list);
            }
        }
    }
}
