package com.nianzuochen.synchronizedproblem;

import java.util.concurrent.Semaphore;

/**
 * Created by lei02 on 2019/4/18.
 */
public class SemaphoreAccount {
    private static Semaphore semaphore = new Semaphore(1);

    private int balance = 0;

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {
        try {
            semaphore.acquire();
            int newBalance = balance + amount;
            Thread.sleep(10);
            balance = newBalance;
        }catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}
