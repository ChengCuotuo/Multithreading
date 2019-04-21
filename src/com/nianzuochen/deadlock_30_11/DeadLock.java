package com.nianzuochen.deadlock_30_11;

/**
 * Created by lei02 on 2019/4/21.
 * 死锁，两个对象分别得到了一个不同于另一个对象的资源，之后，两个对象需要得到对方的资源才能继续运行，
 * 这种相互争夺资源，而又得不到，就会出现死锁。
 */
public class DeadLock implements Runnable{
    // 用 flag 来标记开始的时候哪个对象先被锁定
    private int flag = 1;
    //全局变量，各个对象共用的资源
    private static Object o1 = new Object(), o2 = new Object();

    public DeadLock(int flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        if (flag == 0) {
            synchronized (o1) {
                System.out.println("flag=" + flag);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                synchronized (o2){
                    System.out.println("get o2");
                }
            }
        } else if (flag == 1) {
            synchronized (o2) {
                System.out.println("flag=" +flag);
                try {
                    Thread.sleep(500);
                } catch(InterruptedException ex) {
                    ex.printStackTrace();
                }
                synchronized (o1) {
                    System.out.println("get o1");
                }
            }
        }
    }

    public static void main(String[] args) {
        DeadLock lock1 = new DeadLock(0);
        DeadLock lock2 = new DeadLock(1);

        new Thread(lock1).start();
        new Thread(lock2).start();

        // 仅仅会执行到获取到各自的第一个资源
        //flag=0
        //flag=1
    }
}
