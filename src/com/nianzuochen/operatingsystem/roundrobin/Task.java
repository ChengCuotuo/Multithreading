package com.nianzuochen.operatingsystem.roundrobin;

/**
 * 时间：20190511
 * 内容：时间片轮转算法中的任务
 *      到达时间合服务时间都用整数表示
 */

public class Task {
    private String name;    //任务名称
    private int arrive;     //到达时间
    private int service;    //服务时间

    public Task(String name, int arrive, int service) {
        this.name = name;
        this.arrive = arrive;
        this.service = service;
    }

    public void subtractService() {
        this.service -= 1;
    }

    public int getService() {
        return service;
    }

    public int getArrive() {
        return arrive;
    }

    public String getName() {
        return name;
    }
}
