package com.nianzuochen.threadpool;
import com.nianzuochen.testhtread.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lei02 on 2019/4/17.
 * 使用线程池处理多线程的问题
 */
public class ExecutorDemo {
    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new PrintChar('a', 100));
        executorService.execute(new PrintChar('b', 100));
        executorService.execute(new PrintNumber(100));

        executorService.shutdown();
        //b1a2ab3ba4ab5ba6ba7ba8ab9ba10ba11ab12ba13ba14ba15ba16ba17ab18ba19ba20ba21ba22ba23ba24ba25ba26ba27ba28ba29ba30ba31ba32ba33ba34ba35ba36ba37ba38ba39ba40ba41ba42ba43ba44ba45ba46ba47ba48ba49ba50ba51ba52ba53ba54ba55ba56ba57ba58ba59ba60ba61ba62ab63ba64ba65ab66ba67ab68ba69ba70ab71ba72ab73ab74ab75ab76ba77ba78ba79ab80ab81ab82ba83ab84ab85ab86ba87ab88ba89ba90ab91ab92ba93ba94ab95ab96ab97ab98ab99ab100ba
    }
}
