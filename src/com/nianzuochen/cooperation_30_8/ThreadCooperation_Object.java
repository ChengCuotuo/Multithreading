package com.nianzuochen.cooperation_30_8;

import com.nianzuochen.condition.ThreadCooperation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lei02 on 2019/4/21.
 * 线程之间的协作，不再使用 条件Condition 而是使用对象的 wait() 和 notifyAll()
 */
public class ThreadCooperation_Object {
    private static ThreadCooperation.Account account = new ThreadCooperation.Account();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new ThreadCooperation.DepositTask());
        executorService.execute(new ThreadCooperation.WithdrawTask());

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
        // 将 balance 使用 Integer 对象，使用对象的 wait() 和 notifyAll() 来实现线程之间的通信
        private Integer balance = 0;

        public int getBalance() {
            return balance;
        }

        public void deposit(int amount) {
            lock.lock();
            try {
                while(balance < amount) {
                    System.out.println("\t\tWait for a deposit");
                    //使用对象的 wait() 方法，让该线程进入等待状态
                    balance.wait();
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
                // 唤醒所有等待的线程
                balance.notifyAll();
            } finally {
                lock.unlock();
            }
        }
    }
}
/*
Thread 1		Tread 2		Balance
		Wait for a deposit
Deposit 2				2
		Wait for a deposit
Deposit 1				3
		Wait for a deposit
Deposit 10				13
		Withdraw 10		3
		Wait for a deposit
Deposit 5				8
		Withdraw 5		3
		Wait for a deposit
Deposit 2				5
		Wait for a deposit
Deposit 7				12
		Withdraw 7		5
		Wait for a deposit
Deposit 1				6
		Wait for a deposit
Deposit 2				8
		Wait for a deposit
Deposit 8				16
		Withdraw 10		6
* */