package caranimation_not_multithreading;

/**
 * Created by lei02 on 2019/4/19.
 */
//CarPane，使小车可以按照指定的x,y绘制，并且可以加减速，开始暂停，还有定时器
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.layout.Pane;

public class CarPane extends Pane
{
    private double x;                   // x 轴坐标
    private double y;                   // y 轴坐标
    private double w;                   // 面板的总长度
    private double time;                // 动画的时间间隔
    private PathTransition aRoof;       //车顶的动画
    private PathTransition aBody;       //车身的动画
    private PathTransition aWheel1;     //轮子1的动画
    private PathTransition aWheel2;     //轮子2的动画
    private Polygon roof;               // 小车的车顶
    private Rectangle body;             //小车的车身
    private Circle wheel1;              //小车的轮子1
    private Circle wheel2;              //小车的轮子2
    private Duration duration;          // 动画的时间间隔对象

    public CarPane()
    {
        this(0, 100, 400, 5000);
    }

    public CarPane(double w)
    {
        this(0, 100, w, 5000);
    }

    public CarPane(double x, double y)
    {
        this(x, y, 400, 5000);
    }

    public CarPane(double x, double y, double w, double time)
    {
        this.x = x;
        this.y = y;
        this.time = time;
        this.w = w;
        aRoof = new PathTransition();
        aBody = new PathTransition();
        aWheel1 = new PathTransition();
        aWheel2 = new PathTransition();
        duration = new Duration(time);
        roof = new Polygon();
        body = new Rectangle();
        wheel1 = new Circle();
        wheel2 = new Circle();
        paintCar();
    }
    //加速
    public void increaseRate()
    {
        System.out.println("increase");
        aRoof.setRate(aRoof.getRate() + 0.1);
        aBody.setRate(aBody.getRate() + 0.1);
        aWheel1.setRate(aWheel1.getRate() + 0.1);
        aWheel2.setRate(aWheel2.getRate() + 0.1);
    }
    //减速
    public void decreaseRate()
    {
        System.out.println("decrease");
        aRoof.setRate(aRoof.getRate() - 0.1 < 0.0 ? 0.0 : aRoof.getRate() - 0.1);
        aBody.setRate(aBody.getRate() - 0.1 < 0.0 ?  0.0 : aBody.getRate() - 0.1);
        aWheel1.setRate(aWheel1.getRate() - 0.1 < 0.0 ? 0.0 : aWheel1.getRate() - 0.1);
        aWheel2.setRate(aWheel2.getRate() - 0.1 < 0.0 ? 0.0 : aWheel2.getRate() - 0.1);
    }
    //动画开始
    public void play()
    {
        aRoof.play();
        aBody.play();
        aWheel1.play();
        aWheel2.play();
    }
    //动画暂停
    public void pause()
    {
        aRoof.pause();
        aBody.pause();
        aWheel1.pause();
        aWheel2.pause();
    }
    //画小车
    public void paintCar()
    {
        roof.setFill(Color.RED);
        roof.setStroke(Color.BLACK);
        ObservableList<Double> list = roof.getPoints();
        list.add(x + 10);
        list.add(y - 20);
        list.add(x + 20);
        list.add(y - 30);
        list.add(x + 30);
        list.add(y - 30);
        list.add(x + 40);
        list.add(y - 20);
        Line lR = new Line(x + 25, y - 25, w - 25, y - 25);

        body.setX(x);
        body.setY(y - 20);
        body.setWidth(50);
        body.setHeight(10);
        body.setFill(Color.RED);
        body.setStroke(Color.BLACK);
        Line lB = new Line(x + 25, y - 15, w - 25, y - 15);

        wheel1.setCenterX(x + 15);
        wheel1.setCenterY(y - 5);
        wheel1.setRadius(5);
        wheel1.setFill(Color.BLACK);
        Line lW1 = new Line(x + 15, y - 5, w - 35, y - 5);

        wheel2.setCenterX(x + 35);
        wheel2.setCenterY(y - 5);
        wheel2.setRadius(5);
        wheel2.setFill(Color.BLACK);
        Line lW2 = new Line(x + 35, y - 5, w - 15, y - 5);

        super.getChildren().addAll(roof, body, wheel1, wheel2);

        aRoof.setDuration(duration);
        aRoof.setPath(lR);
        aRoof.setNode(roof);
        aRoof.setCycleCount(Timeline.INDEFINITE);
        aRoof.play();

        aBody.setDuration(duration);
        aBody.setPath(lB);
        aBody.setNode(body);
        aBody.setCycleCount(Timeline.INDEFINITE);
        aBody.play();

        aWheel1.setDuration(duration);
        aWheel1.setPath(lW1);
        aWheel1.setNode(wheel1);
        aWheel1.setCycleCount(Timeline.INDEFINITE);
        aWheel1.play();

        aWheel2.setDuration(duration);
        aWheel2.setPath(lW2);
        aWheel2.setNode(wheel2);
        aWheel2.setCycleCount(Timeline.INDEFINITE);
        aWheel2.play();
    }
}
