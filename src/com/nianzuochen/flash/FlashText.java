package com.nianzuochen.flash;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Created by lei02 on 2019/4/17.
 * 显示闪烁的文本
 */
public class FlashText extends Application{
    private String text = "";
    @Override
    public void start(Stage primaryStage) {
        //主面板
        StackPane pane = new StackPane();
        //显示文本的标签
        Label lbText = new Label("Programming is fun");
        //lbText.setStyle("-fx-background-color: red");
        lbText.setFont(Font.font(16));
        lbText.setTextFill(Color.BLUE);
        pane.getChildren().add(lbText);

        //下面使用线程的方式，每隔 0.5s 在 hello world! 和 空白之间交替
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (lbText.getText().trim().length() == 0) {
                            text = "hello world!";
                        } else {
                            text = "";
                        }

                        //使用 Platform.runLater() 方法来使用非应用程序线程修改应用程序的线程
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                lbText.setText(text);
                            }
                        });
                        //间隔为 0.5s
                        Thread.sleep(500);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        Scene scene = new Scene(pane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("flashtext");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
