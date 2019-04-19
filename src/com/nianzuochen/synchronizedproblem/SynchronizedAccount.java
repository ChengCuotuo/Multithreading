package com.nianzuochen.synchronizedproblem;

/**
 * Created by lei02 on 2019/4/17.
 * 使用 synchronized 实现线程同步，也就是对临界资源进行排他性的使用
 * 实际上是隐式的调用了加锁和解锁的方法
 */
public class SynchronizedAccount {
    private int balance = 0;

    public int getBalance(){
        return balance;
    }

    public synchronized void deposit(int amount) {
        int newBalance = balance + amount;

        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        balance = newBalance;
    }
}
