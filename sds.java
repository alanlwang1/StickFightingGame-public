import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import javafx.scene.shape.*;

public class sds extends Application {

  private ArrayList<Shape> nodes;

  public static void main(String[] args) { launch(args); }

  @Override public void start(Stage primaryStage) {
    primaryStage.setTitle("Drag circles around to see collisions");
    Group root = new Group();
    Scene scene = new Scene(root, 400, 400);
        Rectangle r1=new Rectangle();
        r1.setX(-900);
        r1.setY(-450);
        r1.setHeight(200);
        r1.setWidth(400);
        Rectangle r2=new Rectangle();
        r2.setX(900);
        r2.setY(-450);
        r2.setHeight(200);
        r2.setWidth(400);
    root.getChildren().add(r1);
    root.getChildren().add(r2);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}