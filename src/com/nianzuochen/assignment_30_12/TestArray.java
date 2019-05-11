package com.nianzuochen.assignment_30_12;

/**
 * Created by lei02 on 2019/4/22.
 * 测试，数组是引用类型，相当于C里面的指针，即使是在类中传递，还是对原来的产生影响
 */
public class TestArray {
    public static void main(String[] args) {
        int[] list = new int[100];
        //insertArray(list);
        Assignment assignment = new Assignment();
        assignment.assign(list);
        printArray(list);
    }
    //在自己的类中对数组进行赋值
    public static void insertArray (int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random() * 100) + 1;
        }
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }
}

//在其他类中对数组进行赋值
class Assignment {
    public void assign(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 100) + 1;
        }
    }
}

