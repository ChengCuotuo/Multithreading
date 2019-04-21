package com.nianzuochen.caranimation_30_2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by lei02 on 2019/4/21.
 *使用CarPane产生两个小车，使用W,S加减上面的车的速度
 *UP DOWN控制下面车的速度
 *鼠标按钮按下和松开，开始和暂停
 */
public class DriveCarsByThread extends Application{

    @Override
    public void start(Stage primaryStage)
    {
        double  w = 1000;
        VBox pane = new VBox(5);
        CarPane car1 = new CarPane(w);
        pane.getChildren().add(car1);
        CarPane car2 = new CarPane(w);
        pane.getChildren().add(car2);

        car1.setOnMousePressed(e ->  {
            car1.pause();
            car2.pause();
        });

        pane.setOnMouseReleased(e -> {
            car1.play();
            car2.play();
        });

        //当点击按钮的时候创建新的线程取控制小车的速度
        pane.setOnKeyPressed(e -> {
            switch (e.getCode())
            {
                case UP:
                    //car2.increaseRate(); break;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            car2.increaseRate();
                        }
                    });
                    break;
                case DOWN:
                    //car2.decreaseRate(); break;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            car2.decreaseRate();
                        }
                    });
                    break;
                case W:
                    //car1.increaseRate(); break;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            car1.increaseRate();
                        }
                    });
                    break;
                case S:
                    //car1.decreaseRate(); break;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            car1.decreaseRate();
                        }
                    });
                    break;
            }
        });

        Scene scene = new Scene(pane, w, 250);
        primaryStage.setTitle("Drive Cars");
        primaryStage.setScene(scene);
        primaryStage.show();

        pane.requestFocus();
    }

}
