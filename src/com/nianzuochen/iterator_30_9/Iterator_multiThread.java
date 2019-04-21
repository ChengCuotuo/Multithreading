package com.nianzuochen.iterator_30_9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by lei02 on 2019/4/21.
 * 验证迭代器具有快速失效的特性，第一个线程每秒向散列表中添加数，
 * 第二个线程在每秒前遍历三列表中的值
 */
public class Iterator_multiThread {
    private static Set<Long> set = new HashSet<>();

    public static void setSet (Set newset) {
        set = newset;
    }
    public static void main(String[] args) {
        new Thread(new ReadNum()).start();
        new Thread(new AddNum()).start();
        //Exception in thread "Thread-0" java.util.ConcurrentModificationException
    }

    //向散列表中添加数字
    public static class AddNum implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    set.add(System.currentTimeMillis());
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    //读取散列表中的数据
    public static class ReadNum implements Runnable {
        @Override
        public void run() {
            Iterator iterator;
            try {
                while (true) {
                    iterator = set.iterator();
                    while(iterator.hasNext()) {
                        System.out.print(iterator.next() + " ");
                    }
                    System.out.println();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
