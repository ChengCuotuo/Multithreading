package com.nianzuochen.testhtread;

/**
 * Created by lei02 on 2019/4/17.
 */
public class testjoin {
    public static void main(String[] args){
        Thread thread1 = new Thread(new PrintChar('a', 40));
        thread1.start();
        try {
            for (int i = 1; i <= 100; i++) {
                System.out.print(i);
                if (i == 50) {
                    thread1.join();
                }
                Thread.sleep(50);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
//1a2a34a56a78a910a1112a1314a1516a1718a1920a2122a2324a2526a2728a2930a3132a3334a3536a3738a3940a4142a4344a4546a47a4849a50aaaaaaaaaaaaaa51525354555657585960616263646566676869707172737475767778798081828384858687888990919293949596979899100
