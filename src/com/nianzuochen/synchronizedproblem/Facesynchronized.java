package com.nianzuochen.synchronizedproblem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lei02 on 2019/4/17.
 * 线程同步用来协调相互协作的线程
 * 这个例子没有实现线程安全，对临界资源的抢用，使得结果错误
 * 当间隔时间达到 0.5s 的时候 balance = 1
 */
public class Facesynchronized {
    private static Account account = new Account();
    private static SynchronizedAccount synchronizedAccount = new SynchronizedAccount();
    private static LockAccount lockAccount = new LockAccount();
    private static SemaphoreAccount semaphoreAccount = new SemaphoreAccount();

    public static void main(String[] args) {
        ExecutorService executorService1 = Executors.newCachedThreadPool();
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        ExecutorService executorService3 = Executors.newCachedThreadPool();
        ExecutorService executorService4 = Executors.newCachedThreadPool();

        for (int i = 0; i < 100; i++) {
            executorService1.execute(new AddPennyTask());
            executorService2.execute(new SynchronizedAddPennyTask());
            executorService3.execute(new LockAddPennyTask());
            executorService4.execute(new SemaphoreAddPennyTask());

        }
        //非线程安全的执行
        executorService1.shutdown();
        //等到线程池中的所有的任务完成
        while(!executorService1.isTerminated()) {
        }
        System.out.println("What is balance? " + account.getBalance());
        //What is balance? 3

        //synchronized 同步线程的执行
        executorService2.shutdown();
        //等到线程池中的所有的任务完成
        while(!executorService2.isTerminated()) {
        }
        System.out.println("What is balance? " + synchronizedAccount.getBalance());
        //What is balance? 100

        //lock加锁线程的执行
        executorService3.shutdown();
        while (!executorService3.isTerminated()){
        }
        System.out.println("What is balance? " + lockAccount.getBalance());
        //What is balance? 100

        //使用信号量 semphare
        //lock加锁线程的执行
        executorService4.shutdown();
        while (!executorService4.isTerminated()){
        }
        System.out.println("What is balance? " + semaphoreAccount.getBalance());
        //What is balance? 100
    }

    public static class AddPennyTask implements Runnable{
        @Override
        public void run() {
            account.deposit(1);
        }
    }

    public static class SynchronizedAddPennyTask implements Runnable{
        @Override
        public void run() {
            synchronizedAccount.deposit(1);
        }
    }

    public static class LockAddPennyTask implements Runnable{
        @Override
        public void run() {
            lockAccount.deposit(1);
        }
    }

    public static class SemaphoreAddPennyTask implements Runnable{
        @Override
        public void run() {
            semaphoreAccount.deposit(1);
        }
    }
}
