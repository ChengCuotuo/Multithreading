package com.nianzuochen.operatingsystem.higestresponserationnext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间：20190511
 * 内容：模拟作业，包含作业的到达时间，和作业的预期工作时长
 */

public class Task implements Runnable{
    private String name;    //记录该作业名称
    private double arrive;     //作业到达时间
    private double millis;    //预期执行的时长
    private double priority;   //优先级
    public Task(String name, double arrive, double millis) throws ParseException {
        this.name = name;
        this.arrive = arrive;
        this.millis = millis;
        priority = 1;
    }

    @Override
    public void run() {
        System.out.println("starting " + name);
        System.out.println("priority " + name + ": "+ priority);
        try {
            Thread.sleep((int)millis * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("task " + name + " finished");
    }

    public String getName() {
        return name;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    public double getPriority() {
        return priority;
    }

    public double getArrive() {
        return arrive;
    }

    public double getMillis () {
        return millis;
    }
}
