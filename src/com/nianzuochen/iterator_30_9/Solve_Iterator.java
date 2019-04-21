package com.nianzuochen.iterator_30_9;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lei02 on 2019/4/21.
 * 解决的方式时使用同步集合 Collections.synchronizedSet(new HashSet<>())
 * 也就是相当于在原来的集合对象前面加上 synchronized
 */
public class Solve_Iterator {
    private static Set<Long> set = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        Iterator_multiThread.setSet(set);
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(new Iterator_multiThread.AddNum());
        service.execute(new Iterator_multiThread.ReadNum());
        service.shutdown();
    }

}
