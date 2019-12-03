
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
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
    
    
    
    private static final int COLUMNS  =   9;
    private int COUNT    =  6;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 90;
    private static final int HEIGHT   = 86;

    public EntityAnimation(StackPane stage, int y) {
    	this.root1 = stage;
    	this.y_cor = y;
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
        
        
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.add(imageView, 0, 0);
        
        // move the zombie from right to left
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(2000));
        translateTransition.setNode(pane);
        translateTransition.setFromX(1500);
        translateTransition.setToX(300);
        translateTransition.setFromY(this.y_cor);
       
        translateTransition.setDuration(Duration.seconds(25));
        translateTransition.play();
        
        // A Group object has no layout of children easier to use here
        pane.setMouseTransparent(true);
        this.root1.getChildren().add(pane);
    }
    
    public void Death() {
    	
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
        
        
        
        animation.setCycleCount(animation.INDEFINITE);
        animation.play();
        
        
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.add(imageView, 0, 0);
        
        // move the zombie from right to left
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(2000));
        translateTransition.setNode(pane);
        translateTransition.setFromX(300);
        translateTransition.setToX(300);
        translateTransition.setFromY(this.y_cor);
       
        translateTransition.setDuration(Duration.seconds(25));
        translateTransition.play();
        
        // A Group object has no layout of children easier to use here
        pane.setMouseTransparent(true);
        this.root1.getChildren().add(pane);
    }
}