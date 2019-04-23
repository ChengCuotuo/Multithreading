package com.nianzuochen.assignment_30_12;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by lei02 on 2019/4/21.
 * 使用并行编程实现对9000000各元素的赋值
 * 开始的思路是将数组不断地划分成大小为500的新数组并记录相对于原来数组的位置，对这些数组进行赋值之后，
 * 再用它们来赋值原数组，但是这些小数组在多次 Fork 之后失效了。
 * 改成直接得到需要在原数组上进行赋值的 start 和 end 值，也就是相当于多线程同时使用一个数组的不同位置，
 * 就是在喂牛，不同的人负责相互不重叠的相邻的几头牛。
 */
public class ParallelAssignment {
    private static double[] nums = new double[2000];

    //非并行生成随机数
    public static void assignment() {
        long starttime = System.currentTimeMillis();
        for (int i = 0; i < 9000000; i++) {
            nums[i] = Math.random() * 100 + 1;
        }
        long endtime = System.currentTimeMillis();
        System.out.println("use: " + (endtime - starttime) + "millis");
        //print();
        //use: 617millis
    }

    public static void main(String[] args) {
        //assignment(); // use: 617millis
        parallelAssignValues(nums); // use: 12millis
    }

    //实现并行地给一个数组进行赋值操作
    public static void parallelAssignValues(double[] list) {
        long starttime = System.currentTimeMillis();
        Assignment task = new Assignment(0, list.length, list);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
        long endtime = System.currentTimeMillis();
        System.out.println("use: " + (endtime - starttime) + "millis");
        print();
        //use: 12millis
    }

    public static class Assignment extends RecursiveAction {
        //当需要排序地数组长度小于500地时候就直接进行赋值，否则进行划分
        private final int THRESHOLD = 500;
        // 确定需要进行赋值地数组范围
        private int start;
        private int end;
        private double[] list;
        //生成随机数组
        Random random = new Random(System.currentTimeMillis());

        public Assignment (int start, int end, double[] list) {
            this.start = start;
            this.end = end;
            this.list = list;
        }

        public Assignment (int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void compute() {
//            if (list.length <= THRESHOLD) {
            if (end - start <= THRESHOLD) {
                //System.out.println(list.length)
//                for (int i = 0; i < list.length; i++) {
//                    //list[i] = random.nextDouble() * 100.0;
//                    list[i] = Math.random() * 100 + 1;
//                }
//                //将赋值的内容放在了这里
//                System.arraycopy(list, 0, nums, start, list.length);
                for (int i = start; i < end; i++) {
                    nums[i] = random.nextDouble() * 100.0;
                }
            } else {
                int half = (start + end) / 2;
                //double[] firstHalf = new double[half - start];
                //double[] secondHalf = new double[end - half];
                //invokeAll(new Assignment(start, half, firstHalf), new Assignment(half, end, secondHalf));
                invokeAll(new Assignment(start, half), new Assignment(half, end));
                //System.out.println(start + " " + firstHalf.length + " " + half + " " + secondHalf.length + " " + end);
                //要注意这里面 目标数组不是 list 而是 nums
                //使用 System.arraycopy() 并不能实现复制数组中地内容，是因为浅复制吗？使用 for 循环还是错的
//                System.arraycopy(firstHalf, 0, nums, start, firstHalf.length);
//                System.arraycopy(secondHalf, 0, nums, half, secondHalf.length);
//                for (int i = 0; i < firstHalf.length; i ++) {
//                    nums[start++] = firstHalf[i];
//                }
//
//                for (int i = 0; i < secondHalf.length; i++) {
//                    nums[half++] = secondHalf[i];
//                }
            }
        }
    }

    public static void print() {
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
            if (i % 100 == 0) {
                System.out.println();
            }
        }
    }
}
