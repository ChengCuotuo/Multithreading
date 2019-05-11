package com.nianzuochen.operatingsystem.roundrobin;

/**
 * 时间：20190511
 * 内容：时间片轮转算法
 *      假定时间片的长度为 1 也就是和任务中的服务时间是相同的计量单位
 *      在分时系统中最常用的就是时间片轮转调度算法。
 *          系统根据 FCFS 先来先服务策略，将所有的就绪进程排成一个就绪队列，并可设置每隔一段
 *       时间间隔，就产生一次中断，就激活系统中的进程调度程序，完成一次调度，将 CPU 分配给队首进程，
 *       令其执行。
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static StringBuilder order = new StringBuilder();    //记录执行的时间片内容
    public static ArrayList<Task> tasks = new ArrayList<>();    //存储工作
    public static Queue<Task> queue = new LinkedList<>();       //执行队列

    public static void main(String[] args) {
        Task task1 = new Task("A", 0, 3);
        Task task2 = new Task("B", 2, 6);
        Task task3 = new Task("C", 4, 4);
        Task task4 = new Task("D", 6, 5);
        Task task5 = new Task("E", 8, 2);

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        tasks.add(task5);

        algorithm();
        System.out.println(order);
    }

    //进行时间片轮转算法
    public static void algorithm() {
        int time;       //总时间

    }
}
