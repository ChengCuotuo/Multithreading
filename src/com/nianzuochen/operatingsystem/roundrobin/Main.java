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
        int time = 0;   //总时间
        Task firstTask = tasks.get(0);
        queue.offer(firstTask);
        firstTask.subtractService();     //完成一次服务
        if (firstTask.getService() == 0) {
            tasks.remove(firstTask);
        }
        //非空表示还有任务没有完成
        while (!tasks.isEmpty() || !queue.isEmpty()) {
            if (!queue.isEmpty()) {
                //为了公平，在时间片结束的时候先执行才此期间等待的任务，再将当前未执行完的任务执行
                time++;
                //添加最近一个时间片执行时候等待的任务
                for (int i = 0; i < tasks.size(); i++) {
                    if ((tasks.get(i).getArrive() <= time) &&
                            (tasks.get(i).getService() > 0) && (!tasks.get(i).equals(firstTask))) {
                        queue.offer(tasks.get(i));
                        //错误修改，当将任务添加到队列之后，也就意味着这个任务会被执行，那么它的 service 要减一
                        //否则就会重复添加，所以减一操作在添加过程中
                        tasks.get(i).subtractService();     //完成一次服务
                        //如果服务已经完成就移出该任务
                        if (tasks.get(i).getService() == 0) {
                            tasks.remove(tasks.get(i));
                        }
                    }
                }
                //如果最近时间片执行的任务没有完成再加到执行队列中
                if (firstTask.getService() > 0) {
                    queue.offer(firstTask);
                    firstTask.subtractService();     //完成一次服务
                    if (firstTask.getService() == 0) {
                        tasks.remove(firstTask);
                    }

                }
                firstTask = queue.poll();       //获取并移出队首
                //将执行的任务名称记录下来
                order.append(firstTask.getName());
            }

        }
    }
}
