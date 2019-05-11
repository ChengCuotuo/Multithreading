package com.nianzuochen.operatingsystem.higestresponserationnext;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 时间：20190511
 * 内容：高响应比优先算法
 * 介绍：高响应比优先算法即考虑类作业的等待时间，又考虑作业运行时间的调度算饭，因此即照顾了短作业
 *      又使长作业的等待时间过长。
 *          实现的方式是：为每个作业引入一个动态优先级，即优先级是可以改变的，令它随等待时间延长而增加
 *      这将使长作业的优先级在等待的期间不断的增加，等到足够的时间之后，必然有机会获得处理机。
 *      优先权=（等待时间+要求服务时间）/要求服务时间
 */

public class HighestResponseRationNext {
    public static ArrayList<Task> tasks = new ArrayList<>();   //存储执行的作业

    public static void main(String [] args) throws ParseException {
        //获取一定数量的作业，指定其到达时间和预期的运行时间，使用高响应比优先算法，决定作业运行的顺序
        Task task1 = new Task("A",8.00, 2.00);
        Task task2 = new Task("B",8.50, 0.50);
        Task task3 = new Task("C",9.00, 0.10);
        Task task4 = new Task("D",9.50, 0.20);

        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);

        algorithm();
    }

    //高响应比优先算法
    public static void algorithm(){
        //计算并重新设置各作业的优先权
        //每次循环运行一个作业
        boolean remove = false;
        Task nowTask;
        Task nextExecute = tasks.get(0);
        double finish = nextExecute.getArrive();

        Thread execute = new Thread(nextExecute);
        execute.start();
        //执行完毕之后移出该任务对象
        while (!remove) {
            if (!execute.isAlive()) {
                finish += nextExecute.getMillis();
                tasks.remove(nextExecute);
                System.out.println("finishDate " + finish);
                remove = true;
            }
        }

        while (!tasks.isEmpty()) {
            //获取队列的迭代器
            Iterator iterator = tasks.iterator();
            nextExecute = (Task)iterator.next();
            //就该任务的优先级
            nextExecute.setPriority((finish - nextExecute.getArrive() + nextExecute.getMillis()) / nextExecute.getMillis());
            while (iterator.hasNext()) {
                //下一个任务看作当前的任务
                nowTask = (Task)iterator.next();
                nowTask.setPriority((finish - nowTask.getArrive() + nowTask.getMillis()) / nowTask.getMillis());
                //如果当前的任务的优先级高于下一个执行的任务，那么下一个执行的任务就是当前的任务
                if (nowTask.getPriority() > nextExecute.getPriority()) {
                    nextExecute = nowTask;
                }
            }

            remove = false;
            // 现在 nextExecute 就是优先级最高的任务，也就是本次需要执行的任务
            execute = new Thread(nextExecute);
            execute.start();
            //执行完毕之后移出该任务对象
            while (!remove) {
                if (!execute.isAlive()) {
                    finish += nextExecute.getMillis();
                    tasks.remove(nextExecute);
                    System.out.println("finishDate " + finish);
                    remove = true;
                }
            }

        }
    }
}
