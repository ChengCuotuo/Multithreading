package com.nianzuochen.testhtread;

/**
 * Created by lei02 on 2019/4/17.
 */
//打印指定次数的字符
public class PrintChar implements Runnable {
    private char charToPrint;   //用来打印的字符
    private int times;          //确定打印的次数

    public PrintChar(char charToPrint, int times) {
        this.charToPrint = charToPrint;
        this.times = times;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < times; i++) {
                System.out.print(charToPrint);
                //每打印一个字符等待 0.1 s
                Thread.sleep(100);
            }
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }

    }
}

