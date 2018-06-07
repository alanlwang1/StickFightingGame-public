import java.util.ArrayList;
import java.awt.geom.Point2D; 
import java.awt.geom.Point2D.Double;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle; 
import javafx.animation.AnimationTimer; 
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.*;
import javafx.geometry.VPos; 
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.stage.Stage;
import javafx.event.EventHandler; 
import javafx.scene.input.KeyEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;
/**
 * Write a description of class MainDrawPhase here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MainDrawPhase
{
    private Game game;
    private MainStage mainStage;
    private Scene scene;
    private Group root;
    private Canvas canvas;
    private GraphicsContext gc; 
    private Player player1;
    private Player player2;
    private ImageView cursor1, cursor2;
    private Text topBanner; 
    private Text turnBanner; 
    private AnimationTimer cursorTimer;
    private boolean lineCreated; 
    private boolean goUp1, goDown1, goLeft1, goRight1; //booleans controlling player1's movement
    private boolean goUp2, goDown2, goLeft2, goRight2; //booleans controlling player2's movement
    private ArrayList<Point2D.Double> selectedPoints; 
    private ArrayList<Line> createdLines;
    /**
     * Constructor to create initial MainDrawPhase
     */
    public MainDrawPhase(Game g, MainStage ms)
    {
        game = g;
        mainStage = ms;

        canvas = new Canvas(1800, 900);
        gc = canvas.getGraphicsContext2D(); 

        topBanner = new Text(); 
        topBanner.setText("Draw Phase: Player 1's Turn");
        topBanner.setFont(new Font(45));
        topBanner.setFill(Color.BLACK);
        topBanner.setStrokeWidth(1.5);
        topBanner.setStroke(Color.BLACK);
        topBanner.setTextOrigin(VPos.TOP);

        //display cursors, move to positions 
        cursor1 = new ImageView("pointer1.png");
        cursor2 = new ImageView("pointer2.png");
        
                //Timer for drawing cursor movement and repainting
        cursorTimer = new AnimationTimer() 
        {
            @Override
            public void handle(long now)
            {
                double dx1 = 0; 
                double dy1 = 0;
                double dx2 = 0;
                double dy2 = 0;
                if (goUp1)
                    dy1 -= 5;
                if (goLeft1)
                    dx1 -= 5;
                if (goDown1)
                    dy1 += 5;
                if (goRight1)
                    dx1 += 5;
                if (goUp2)
                    dy2 -= 5;
                if (goLeft2)
                    dx2 -= 5;
                if (goDown2)
                    dy2 += 5;
                if (goRight2)
                    dx2 += 5; 
                moveCursor(cursor1, dx1, dy1); 
                moveCursor(cursor2, dx2, dy2); 

                //repaint selected points, if any 
                gc.setFill(Color.WHITE); 
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFill(Color.BLUE); 
                for(Point2D.Double point: selectedPoints)
                {
                    gc.fillOval(point.getX(), point.getY(), 5, 5); 
                }
            }  
        };
        //add listener for draw phase start/end
        game.getDrawProperty().addListener(new ChangeListener<Boolean>()
            {
                @Override 
                public void changed(ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal)
                {
                    if (newVal.booleanValue() == false)
                    {
                        cursorTimer.stop();
                        startCombat(); 
                    }
                }
            });    
        //add listener for turn change
        game.getTurnProperty().addListener(new ChangeListener<Number>()
            {
                @Override
                public void changed(ObservableValue<? extends Number> o, Number oldVal, Number newVal)
                {
                    if(newVal.intValue() == 1)
                    {
                        topBanner.setText("Draw Phase: Player 1's Turn");
                    }
                    else
                        topBanner.setText("Draw Phase: Player 2's Turn");
                }
            });
            
        //create scene and send 
        root = new Group(canvas, topBanner, cursor1, cursor2);
        scene = new Scene(root, 1800, 900); 

        //format topBanner and cursors 
        topBanner.layoutXProperty().bind(scene.widthProperty().subtract(topBanner.prefWidth(-1)).divide(2));
        topBanner.layoutYProperty().bind(scene.heightProperty().subtract(850));

        //format cursor1 and cursor2
        cursor1.relocate(0 + cursor1.getImage().getWidth(), scene.getHeight() - cursor1.getImage().getHeight());
        cursor2.relocate(scene.getWidth() - cursor2.getImage().getWidth(), scene.getHeight() - cursor2.getImage().getHeight());

        //instantiate arraylists to hold points and lines
        selectedPoints = new ArrayList<Point2D.Double>();
        createdLines = new ArrayList<Line>(); 
        //start the game
        setKeyBinds();
        runGame(); 
    }
    public MainDrawPhase(Game g, MainStage ms, ArrayList<Line> lines, int currentTurn)
    {
        game = g;
        game.setMaxTurns(1); 
        game.setCurrentTurn(currentTurn);  
        mainStage = ms;

        canvas = new Canvas(1800, 900);
        gc = canvas.getGraphicsContext2D(); 

        topBanner = new Text(); 
        topBanner.setText("Draw Phase: Player " + currentTurn + "'s Turn");
        topBanner.setFont(new Font(45));
        topBanner.setFill(Color.BLACK);
        topBanner.setStrokeWidth(1.5);
        topBanner.setStroke(Color.BLACK);
        topBanner.setTextOrigin(VPos.TOP);

        //display cursors, move to positions 
        cursor1 = new ImageView("pointer1.png");
        cursor2 = new ImageView("pointer2.png");
        
        //Timer for drawing cursor movement and repainting
        cursorTimer = new AnimationTimer() 
        {
            @Override
            public void handle(long now)
            {
                double dx1 = 0; 
                double dy1 = 0;
                double dx2 = 0;
                double dy2 = 0;
                if (goUp1)
                    dy1 -= 5;
                if (goLeft1)
                    dx1 -= 5;
                if (goDown1)
                    dy1 += 5;
                if (goRight1)
                    dx1 += 5;
                if (goUp2)
                    dy2 -= 5;
                if (goLeft2)
                    dx2 -= 5;
                if (goDown2)
                    dy2 += 5;
                if (goRight2)
                    dx2 += 5; 
                moveCursor(cursor1, dx1, dy1); 
                moveCursor(cursor2, dx2, dy2); 

                //repaint selected points, if any 
                gc.setFill(Color.WHITE); 
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                gc.setFill(Color.BLUE); 
                for(Point2D.Double point: selectedPoints)
                {
                    gc.fillOval(point.getX(), point.getY(), 5, 5); 
                }
            }  
        };
        //add listener for draw phase start/end
        game.getDrawProperty().addListener(new ChangeListener<Boolean>()
            {
                @Override 
                public void changed(ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal)
                {
                    if (newVal.booleanValue() == false)
                    {
                        cursorTimer.stop();
                        startCombat(); 
                    }
                }
            });    
            
        //create scene and send 
        root = new Group(canvas, topBanner, cursor1, cursor2);
        scene = new Scene(root, 1800, 900); 

        //format topBanner and cursors 
        topBanner.layoutXProperty().bind(scene.widthProperty().subtract(topBanner.prefWidth(-1)).divide(2));
        topBanner.layoutYProperty().bind(scene.heightProperty().subtract(850));

        //format cursor1 and cursor2
        cursor1.relocate(0 + cursor1.getImage().getWidth(), scene.getHeight() - cursor1.getImage().getHeight());
        cursor2.relocate(scene.getWidth() - cursor2.getImage().getWidth(), scene.getHeight() - cursor2.getImage().getHeight());

        //instantiate arraylists to hold points and lines
        selectedPoints = new ArrayList<Point2D.Double>();
        createdLines = lines;
        root.getChildren().addAll(createdLines);
        //start the game
        setKeyBinds();
        runGame(); 
    }
    public Scene getScene()
    {
        return scene;
    }

    public void runGame()
    {

        //add bottom line
        Line bottomLine = new Line(0, canvas.getHeight() - 100, canvas.getWidth(), canvas.getHeight() - 100); 
        bottomLine.setStrokeWidth(10); 
        root.getChildren().add(bottomLine); 
        createdLines.add(bottomLine);
        
        //start the drawPhase
        cursorTimer.start();
        game.setDrawPhase(true); 
    }
    public void startCombat()
    {
        //change top banner
        topBanner.setText("Combat Phase");
        //countdown
        //hide cursors
        //root.getChildren().remove(cursor1);
        //root.getChildren().remove(cursor2);
        ImageView countdown = new ImageView("countdown.png");
        countdown.relocate(800, 350); 
        root.getChildren().add(countdown);
        CountDownAnimation cdA = new CountDownAnimation(countdown, Duration.seconds(3), 4);
        cdA.setCycleCount(1);
        cdA.setOnFinished(e -> 
        {
            MainCombatPhase mcp = new MainCombatPhase(game, mainStage, createdLines);
            Scene mainCombatScene = mcp.getScene();
            mainStage.changeScene(mainCombatScene); 
        });
        cdA.play();
    }
    public void moveCursor(ImageView cursor, double deltaX, double deltaY)
    {
        double newX = Math.max(cursor.getLayoutX() + deltaX, 0);
        if(newX > scene.getWidth())
        {
            newX = scene.getWidth();
        }
        double newY = Math.max(cursor.getLayoutY() + deltaY, 0);
        if(newY > scene.getHeight())
        {
            newY = scene.getHeight();
        }
        cursor.relocate(newX, newY); 
    }
    public void setKeyBinds()
    {
        //add keybindings for first player
        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
            {
                @Override
                public void handle(KeyEvent event)
                {
                    switch(event.getCode()) 
                    {
                        case W:
                            goUp1 = true;
                            break;
                        case A:
                            goLeft1 = true;
                            break;
                        case S:
                            goDown1 = true;
                            break;
                        case D:
                            goRight1 = true;
                            break; 
                        case R:
                            //draw circle at current position/confirm line for cursor1
                            if(game.getCurrentTurn() == 1)
                            {
                                //if two points are not selected already
                                if(selectedPoints.size() < 2)
                                {
                                    Point2D.Double newPoint = new Point2D.Double(cursor1.getLayoutX(), cursor1.getLayoutY());
                                    selectedPoints.add(newPoint);
                                }
                                //add line with two selected points as endpoints
                                else
                                {
                                    Line line;
                                    Point2D.Double point1 = selectedPoints.get(0);
                                    Point2D.Double point2 = selectedPoints.get(1);
                                    
                                    double differenceX = Math.abs(point1.getX() - point2.getX());
                                    double differenceY = Math.abs(point2.getY() - point2.getY());
                                    
                                    if(differenceX > differenceY)
                                        if(point1.getX() < point2.getX())
                                            line = new Line(point1.getX(), point1.getY(), point2.getX(), point1.getY());
                                        else
                                            line = new Line(point2.getX(), point1.getY(), point1.getX(), point1.getY());
                                    else
                                        if(point1.getY() < point2.getY())
                                            line = new Line(point1.getX(), point1.getY(), point1.getX(), point2.getY());
                                         else
                                            line = new Line(point1.getX(), point2.getY(), point1.getX(), point1.getY());
                                    line.setStrokeWidth(10);
                                    selectedPoints.clear(); 
                                    root.getChildren().add(line);
                                    createdLines.add(line); 
                                    game.incrementLine();
                                }
                            }
                            break;
                        case F:
                            //if drawing, cancel line, erase last point; 
                            if(game.getCurrentTurn() == 1)
                            {
                                //if there is a point(s) selected
                                if(selectedPoints.size() > 0)
                                {
                                    Point2D.Double point = selectedPoints.get(selectedPoints.size() - 1); 
                                    selectedPoints.remove(point); 
                                }
                            }
                            break;
                        case K:
                            //remove this later -- for skipping draw phase
                            game.setDrawPhase(false); 
                            break;
                        case UP:
                            goUp2 = true;
                            break;
                        case LEFT:
                            goLeft2 = true;
                            break;
                        case RIGHT:
                            goRight2 = true;
                            break;
                        case DOWN:
                            goDown2 = true;
                            break; 
                        case SHIFT:
                            if(game.getCurrentTurn() == 2)
                            {
                                //if two points are not selected already
                                if(selectedPoints.size() < 2)
                                {
                                    Point2D.Double newPoint = new Point2D.Double(cursor2.getLayoutX(), cursor2.getLayoutY());
                                    selectedPoints.add(newPoint); 
                                }
                                //add line with two selected points as endpoints
                                else
                                {                                    
                                    Line line;
                                    Point2D.Double point1 = selectedPoints.get(0);
                                    Point2D.Double point2 = selectedPoints.get(1);
                                    
                                    double differenceX = Math.abs(point1.getX() - point2.getX());
                                    double differenceY = Math.abs(point2.getY() - point2.getY());
                                    
                                    if(differenceX > differenceY)
                                        if(point1.getX() < point2.getX())
                                            line = new Line(point1.getX(), point1.getY(), point2.getX(), point1.getY());
                                        else
                                            line = new Line(point2.getX(), point1.getY(), point1.getX(), point1.getY());
                                    else
                                        if(point1.getY() < point2.getY())
                                            line = new Line(point1.getX(), point1.getY(), point1.getX(), point2.getY());
                                         else
                                            line = new Line(point1.getX(), point2.getY(), point1.getX(), point1.getY());
                                    line.setStrokeWidth(10);
                                    selectedPoints.clear(); 
                                    root.getChildren().add(line);
                                    createdLines.add(line); 
                                    game.incrementLine();
                                }
                            }
                            break; 
                        case CONTROL:
                            //if drawing, cancel line, erase last point; 
                            if(game.getCurrentTurn() == 2)
                            {
                                //if there is a point(s) selected
                                if(selectedPoints.size() > 0)
                                {
                                    Point2D.Double point = selectedPoints.get(selectedPoints.size() - 1); 
                                    selectedPoints.remove(point); 
                                }
                            } 
                            break;
                    }
                }          
            }); 
        scene.setOnKeyReleased(new EventHandler<KeyEvent>()
            {
                @Override
                public void handle(KeyEvent event)
                {
                    switch(event.getCode()) 
                    {
                        case W:   
                            goUp1 = false;
                            break;
                        case A:
                            goLeft1 = false;
                            break;
                        case S:
                            goDown1 = false;
                            break;
                        case D:
                            goRight1 = false;
                            break; 
                        case UP:
                            goUp2 = false;
                            break;
                        case LEFT:
                            goLeft2 = false;
                            break;
                        case DOWN:
                            goDown2 = false;
                            break;
                        case RIGHT:
                            goRight2 = false;
                            break;
                    }
                }
            });
    }
}

