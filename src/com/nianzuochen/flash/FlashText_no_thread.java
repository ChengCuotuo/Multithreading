package com.nianzuochen.flash;

/**
 * Created by lei02 on 2019/4/21.
 * 闪烁的文字
 */
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.Pane;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class FlashText_no_thread extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        Pane pane = new Pane();
        Text text = new Text(100, 100, "Programming");
        text.setFont(Font.font("MyFont", 25));
        //文本字体颜色变换动画
        EventHandler<ActionEvent> changeColor = e-> {
            Color color = Color.color(Math.random(), Math.random(), Math.random());
            text.setFill(color);
            text.setStroke(color);
        };
        Timeline change = new Timeline(new KeyFrame(Duration.millis(4000), changeColor));
        change.setCycleCount(Timeline.INDEFINITE);
        pane.getChildren().add(text);
        //字体透明度变换动画
        FadeTransition animation = new FadeTransition(Duration.millis(2000), text);
        animation.setFromValue(0.1);
        animation.setToValue(1.0);
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setAutoReverse(true);

        change.play();
        animation.play();

        Scene scene = new Scene(pane, 400, 200);
        primaryStage.setTitle("Twinkle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
