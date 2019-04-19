package com.nianzuochen.synchronizedproblem;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lei02 on 2019/4/17.
 */
public class LockAccount {
    private static Lock lock = new ReentrantLock();
    private int balance = 0;

    public int getBalance(){
        return balance;
    }

    public void deposit(int amount) {
        lock.lock();
        try {
            int newBalance = balance + amount;

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            balance = newBalance;
        } finally {
            lock.unlock();
        }

    }
}
