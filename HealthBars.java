import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.scene.shape.MoveTo; 
import javafx.scene.shape.Path; 
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Rectangle2D; 
import javafx.animation.AnimationTimer;
import java.util.concurrent.TimeUnit;
import javafx.beans.property.BooleanProperty;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

public class HealthBars extends Application
{
    public void start(Stage mainStage) throws Exception
    {
        Rectangle r1=new Rectangle();
        r1.setX(0);
        r1.setY(0);
        r1.setHeight(50);
        r1.setWidth(400);
        r1.setFill(javafx.scene.paint.Color.GREEN);
         

        Rectangle r2=new Rectangle();
        r2.setX(1400);
        r2.setY(0);
        r2.setHeight(50);
        r2.setWidth(400);
        r2.setFill(javafx.scene.paint.Color.GREEN);
        
        Text t2 = new Text(0, 25, "Player1 Health");
        t2.setFont(new Font(20));
        
        Text t1 = new Text(1674, 25, "Player2 Health");
        t1.setFont(new Font(20));
        
        Group root = new Group(r1,r2);
        root.getChildren().add(t1);
        root.getChildren().add(t2);
        
        
        Scene scene = new Scene(root,1800,900);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke)
            {
                switch(ke.getCode())
                {
                    case R:
                    r1.setFill(javafx.scene.paint.Color.RED);
                    damage1(r1);
                    break;
                    case F: 
                    r2.setFill(javafx.scene.paint.Color.RED);
                    damage2(r2);
                    break;
                }
            }
        });
        
        mainStage.setScene(scene);
        mainStage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
    
    public void damage1(Rectangle r)
    {
        r.setWidth(r.getWidth() -133.3333333333333);
    }
    
    public void damage2(Rectangle r)
    {
        r.setWidth(r.getWidth() -133.33333333333333);
        r.setX(r.getX() + 133.33333333333333333);
    }
}