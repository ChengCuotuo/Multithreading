package com.nianzuochen.caranimation_30_2;

/**
 * Created by lei02 on 2019/4/19.
 */
//使用CarPane产生两个小车，使用W,S加减上面的车的速度
//UP DOWN控制下面车的速度
//鼠标按钮按下和松开，开始和暂停
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class DriveCars extends Application
{
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

        pane.setOnKeyPressed(e -> {
            switch (e.getCode())
            {
                case UP:
                    car2.increaseRate(); break;
                case DOWN:
                    car2.decreaseRate(); break;
                case W:
                    car1.increaseRate(); break;
                case S:
                    car1.decreaseRate(); break;
            }
        });

        Scene scene = new Scene(pane, w, 250);
        primaryStage.setTitle("Drive Cars");
        primaryStage.setScene(scene);
        primaryStage.show();

        pane.requestFocus();
    }
}
