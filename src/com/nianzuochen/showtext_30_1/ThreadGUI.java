package com.nianzuochen.showtext_30_1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * Created by lei02 on 2019/4/19.
 */
public class ThreadGUI extends Application {
    private TextArea ta = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        ta.setEditable(false);
        ta.setPrefWidth(800);
        ta.setPrefHeight(400);
        //设置文本自动换行
        ta.setWrapText(true);
        ScrollPane pane = new ScrollPane(ta);

        //非应用程序线程修改主线程时需要使用 Platform.runLater()
        //应用线程中添加三个新的线程
        new Thread(new PrintChar('a', 1000, ta)).start();
        new Thread(new PrintChar('b', 1000, ta)).start();
        new Thread(new PrintNumber(1000, ta)).start();

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("print_char_num");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

//这个任务时在指定的 textarea 中添加字符，因为是在 javafx 中运行，并u并不是主线程，所以
//添加字符的功能要在 Platform.runLater()中实现
class PrintChar implements Runnable {
    private TextArea ta;
    private int times;
    private char charToPrint;

    public PrintChar(char charToPrint, int times, TextArea ta) {
        this.charToPrint = charToPrint;
        this.times = times;
        this.ta = ta;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < times; i ++) {
                //添加文本是在非主线程中运行的
               Platform.runLater(new Runnable() {
                   @Override
                   public void run() {
                       ta.appendText(charToPrint + "");
                   }
               });

                Thread.sleep(10);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

class PrintNumber implements Runnable{
    private TextArea ta;
    private int lastNum;
    private int start = 1;

    public PrintNumber (int lastNum, TextArea ta) {
        this.lastNum = lastNum;
        this.ta = ta;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= lastNum; i++) {
                //使用 lambda 表达式
                Platform.runLater(() -> {
                    ta.appendText(start++ + "");
                });
                Thread.sleep(50);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}