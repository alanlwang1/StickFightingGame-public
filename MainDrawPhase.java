import java.util.ArrayList;
import java.awt.geom.Point2D; 
import java.awt.geom.Point2D.Double;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle; 
import javafx.animation.AnimationTimer; 
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.geometry.VPos; 
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import javafx.event.EventHandler; 
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;
/**
 * class MainDrawPhase - class to model the drawphase of the game
 *
 * @author Alan Wang, Ryan Wei
 * @version 060718
 */
public class MainDrawPhase
{
    private Game game; 
    private MainStage mainStage; 
    private Scene scene;
    private Group root;
    private Canvas canvas;
    private GraphicsContext gc;
    private ImageView cursor1, cursor2;
    private Text topBanner; 
    private AnimationTimer cursorTimer;
    private boolean goUp1, goDown1, goLeft1, goRight1; //booleans controlling player1's movement
    private boolean goUp2, goDown2, goLeft2, goRight2; //booleans controlling player2's movement
    private ArrayList<Point2D.Double> selectedPoints; //Arraylists to hold points and lines
    private ArrayList<Line> createdLines;
    /**
     * Constructor to create initial MainDrawPhase
     * 
     * @param g a reference to the Game object
     * @param ms a reference the MainStage object
     */
    public MainDrawPhase(Game g, MainStage ms)
    {
        //assign reference to Game and MainStage objects
        game = g;
        mainStage = ms;
        
        //Timer for drawing cursor movement and repainting
        cursorTimer = new AnimationTimer() 
        {
            @Override
            public void handle(long now)
            {
                //reset dx, dy for both cursors
                double dx1 = 0; 
                double dy1 = 0;
                double dx2 = 0;
                double dy2 = 0;
                //calculate new dx/dy based on buttons pressed
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
                //move both cursors     
                moveCursor(cursor1, dx1, dy1); 
                moveCursor(cursor2, dx2, dy2); 

                //repaint selected points, if any 
                gc.setFill(Color.BLUE); 
                for(Point2D.Double point: selectedPoints)
                {
                    gc.fillOval(point.getX(), point.getY(), 5, 5); 
                }
            }  
        };
    }
    /**
     * method getScene - method returns the scene for this object, to display on a MainStage object
     * 
     * @return scene the scene for this object
     */
    public Scene getScene()
    {
        return scene;
    }
    /**
     * method getCreatedLines - method returns the ArrayList containing the lines created so far
     * 
     * @return the ArrayList containing the lines created so far
     */
    public ArrayList<Line> getCreatedLines()
    {
        return createdLines; 
    }
    /**
     * method startNewRound - starts a new draw phase with the 
     * provided ArrayList of lines that were created already
     * 
     * @param createdLines the ArrayList of lines that were created already
     */
    public void startNewRound(ArrayList<Line> createdLines)
    {
        //stop cursor timer while drawphase is being refreshed
        cursorTimer.stop(); 
        //create new canvas and graphics to draw points
        canvas = new Canvas(1800, 900);
        gc = canvas.getGraphicsContext2D(); 
        //banner displaying state of drawphase
        topBanner = new Text(); 
        topBanner.setText("Draw Phase: Player " + game.getGameWinner() + "'s Turn");
        topBanner.setFont(new Font(45));
        topBanner.setFill(Color.BLACK);
        topBanner.setStrokeWidth(1.5);
        topBanner.setStroke(Color.BLACK);
        topBanner.setTextOrigin(VPos.TOP);
        

        //display cursors, move to positions 
        cursor1 = new ImageView("pointer1.png");
        cursor2 = new ImageView("pointer2.png");
        
        //create root group and scene
        root = new Group(canvas, topBanner, cursor1, cursor2);
        scene = new Scene(root, 1800, 900); 
        setKeyBinds();
        
        Image image = new Image("notebook3.png", 2020, 2500, true, true);
        ImageView background = new ImageView(image);
        root.getChildren().add(background); 
        background.toBack(); 
        
        //format topBanner and cursors 
        topBanner.layoutXProperty().bind(scene.widthProperty().subtract(topBanner.prefWidth(-1)).divide(2));
        topBanner.layoutYProperty().bind(scene.heightProperty().subtract(850));

        //format cursor1 and cursor2
        cursor1.relocate(0 + cursor1.getImage().getWidth(), scene.getHeight() - cursor1.getImage().getHeight());
        cursor2.relocate(scene.getWidth() - cursor2.getImage().getWidth(), scene.getHeight() - cursor2.getImage().getHeight());
        
        //create arraylists for holding points and lines
        selectedPoints = new ArrayList<Point2D.Double>();
        this.createdLines =  createdLines; 
        //add lines from arraylist
        root.getChildren().addAll(this.createdLines); 
        //start the drawPhase
        cursorTimer.start();
        game.setDrawPhase(true); 
    }
    /**
     * method moveCursor - moves the provided cursor by deltaX and deltaY, 
     * accounting for boundaries of scene
     * 
     * @param cursor - ImageView or cursor to be moved
     * @param deltaX - amount cursor is being moved in the X direction
     * @param deltaY - amount cursor is being moved in the Y direction
     */
    public void moveCursor(ImageView cursor, double deltaX, double deltaY)
    {
        //stop cursor if it goes to the left of 0 (x)
        double newX = Math.max(cursor.getLayoutX() + deltaX, 0);
        //stop cursor if it goes past scene width
        if(newX > scene.getWidth())
        {
            newX = scene.getWidth();
        }
        //stop cursor if it goes above 0 (y)
        double newY = Math.max(cursor.getLayoutY() + deltaY, 0);
        //stop cursor if it goes past scene height
        if(newY > scene.getHeight())
        {
            newY = scene.getHeight();
        }
        //move cursor to new coordinates
        cursor.relocate(newX, newY); 
    }
    /**
     * method setKeyBinds - method sets the keybindings to be used during draw phase
     */
    public void setKeyBinds()
    {
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
                                    //if line is diagonal, create line with the greater leg 
                                    //vertical lines are created top -> bottom
                                    //horizontal lines are created left -> right
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
                                    //clear selected points used to create the line
                                    selectedPoints.clear(); 
                                    //add line to game
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
                                    //remove it from selected points
                                    Point2D.Double point = selectedPoints.get(selectedPoints.size() - 1); 
                                    selectedPoints.remove(point); 
                                }
                            }
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
                            //identical to above, but for player2
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
                                    //if line is diagonal, create line with the greater leg
                                    //vertical lines are created top -> bottom
                                    //horizontal lines are created left -> right
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
    /**
     * method playCountDownAnimation - method to play countdown animation when 
     * switching between draw phase and combat phase
     */
    public void playCountDownAnimation()
    {
        topBanner.setText("Combat Phase");
        //play countdown animation
        ImageView countdown = new ImageView("countdown.png");
        countdown.relocate(800, 350); 
        root.getChildren().add(countdown);
        CountDownAnimation cdA = new CountDownAnimation(countdown, Duration.seconds(3), 4);
        cdA.setCycleCount(1);
        cdA.setOnFinished(e ->
        {
            //start new combat phase
            mainStage.newCombatPhase(); 
        });
        cdA.play(); 
    }
}

