package com.nianzuochen.synchronizedproblem;

/**
 * Created by lei02 on 2019/4/17.
 */
public class Account {
    private int balance = 0;

    public int getBalance(){
        return balance;
    }

    public void deposit(int amount) {
        int newBalance = balance + amount;

        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        balance = newBalance;
    }
}
