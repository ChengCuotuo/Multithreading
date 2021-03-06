package com.nianzuochen.testhtread;

/**
 * Created by lei02 on 2019/4/17.
 * 练习多线程的运行
 */
public class Print {
    public static void main(String[] args){
        Runnable printA = new PrintChar('a', 100);
        Runnable printB = new PrintChar('b', 100);
        Runnable print100 = new PrintNumber(100);

        Thread thread1 = new Thread(printA);
        Thread thread2 = new Thread(printB);
        Thread thread3 = new Thread(print100);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

