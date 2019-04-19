package com.nianzuochen.testhtread;

/**
 * Created by lei02 on 2019/4/17.
 */

//打印从 1 开始到指定结束的数字
public class PrintNumber implements Runnable {
    private int lastNum;    //打印的数字的上限

    public PrintNumber(int lastNum) {
        this.lastNum = lastNum;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= lastNum; i++) {
                System.out.print(i);
                //每打印一个数字等待 0.1 s
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

    }
}
