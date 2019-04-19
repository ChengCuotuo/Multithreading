package com.nianzuochen.condition;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lei02 on 2019/4/17.
 * 使用线程通信，处理存取钱的问题
 * 当取钱的数量大于存钱的数量，取钱的线程就要等待，也就是阻塞
 * 当存钱的时候要唤醒取钱的线程
 */

public class ThreadCooperation {
    private static Account account = new Account();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new DepositTask());
        executorService.execute(new WithdrawTask());

        executorService.shutdown();
        System.out.println("Thread 1\t\tTread 2\t\tBalance");
    }

    //存钱线程
    public static class DepositTask implements Runnable{
        @Override
        public void run() {
            try {
                while(true) {
                    account.deposit((int)(Math.random() * 10) + 1);
                    Thread.sleep(500);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    //取钱线程
    public static class WithdrawTask implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    account.withdraw((int)(Math.random() * 10) + 1);
                    Thread.sleep(500);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    //账户
    public static class Account{
        private static Lock lock = new ReentrantLock();
        private static Condition newDeposit = lock.newCondition();

        private int balance = 0;

        public int getBalance() {
            return balance;
        }

        public void deposit(int amount) {
            lock.lock();
            try {
                while(balance < amount) {
                    System.out.println("\t\tWait for a deposit");
                    newDeposit.await();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();
            }

            balance -= amount;
            System.out.println("\t\tWithdraw " + amount + "\t\t" + getBalance());
        }

        public void withdraw(int amount) {
            lock.lock();
            try {
                balance += amount;
                System.out.println("Deposit " + amount + "\t\t\t\t" + getBalance());
                newDeposit.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}

/*
 Thread 1 Tread 2		Balance
		Wait for a deposit
Deposit 6				6
		Withdraw 6		0
		Wait for a deposit
Deposit 7				7
		Withdraw 6		1
Deposit 5				6
		Wait for a deposit
Deposit 5				11
		Withdraw 8		3
Deposit 3				6
		Withdraw 6		0
		Wait for a deposit
Deposit 4				4
		Withdraw 4		0
Deposit 10				10
		Withdraw 7		3
Deposit 9				12
		Withdraw 6		6
		Withdraw 2		4
Deposit 2				6
Deposit 9				15
		Withdraw 7		8
* */