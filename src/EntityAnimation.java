
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EntityAnimation {
	static String action = "zombie1";
    private Image IMAGE = new Image("images/" + action + "_walk.png");
    private StackPane root1;
    private int y_cor;
    private TranslateTransition walking;
    private GridPane pane;
    private int speed;
    
    
    private static final int COLUMNS  =   9;
    private int COUNT    =  6;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 90;
    private static final int HEIGHT   = 86;

    public EntityAnimation(StackPane stage, int y, int speed) {
    	this.root1 = stage;
    	this.y_cor = y;
    	this.speed = speed;
    }

    public void start() {
        
    	
        final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        
        
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        
        
        this.pane = new GridPane();
        this.pane.setVgap(10);
        this.pane.setHgap(10);
        this.pane.add(imageView, 0, 0);
        
        // move the zombie from right to left
        this.walking = new TranslateTransition();
        //this.walking.setDuration(Duration.millis(2000));
        this.walking.setNode(pane);
        this.walking.setFromX(1500);
        this.walking.setToX(300);
        this.walking.setFromY(this.y_cor);
       
        this.walking.setDuration(Duration.seconds(this.speed));
        this.walking.play();
        
        // A Group object has no layout of children easier to use here
        this.pane.setMouseTransparent(true);
        this.root1.getChildren().add(pane);
        
        this.walking.setOnFinished(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent event) {
            	
                Death();
                
            }
        });
        
        
    }
    
    public void Death() {
    	
    	
    	this.walking.stop();
    	this.pane.getChildren().remove(0);
    	this.COUNT = 9;
    	IMAGE = new Image("images/" + action + "_death.png");
    	final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                this.COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        
        
        
        animation.setCycleCount(1);
        animation.play();
        
        this.pane.add(imageView, 0, 0);
        animation.setOnFinished(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent event) {
            	
                Delete();
            }  
            
        });
       
        
        // A Group object has no layout of children easier to use here
        pane.setMouseTransparent(true);

    }
        
    public void Delete() {
        	this.pane.getChildren().remove(0);
    }
}