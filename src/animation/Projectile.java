package animation;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Projectile {
	private String action = "weapon4";
    private StackPane root1;
    private int y_cor;
    private TranslateTransition walking;
    private GridPane pane;
    private int speed;
    private String mode;
    private int attack;
    private Animation animation;
    private TranslateTransition translation;
    private int x;
    private int difference = 150;
    private int rate;
    
    
    private int COLUMNS  =   9;
    private int COUNT    =  6;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 45;
    private static final int HEIGHT   = 45;

    public Projectile(StackPane stage, int y, int speed, String mode, String action, int count, int attack, int x, int dif) {
    	this.root1 = stage;
    	this.y_cor = y + 10;
    	this.speed = speed;
    	this.mode = mode;
    	this.action = action;
    	this.COUNT = count;
    	this.x = x + 10;
    	this.attack = attack;
    	this.rate = 150/this.speed;
    	this.difference = dif;
    	this.pane = new GridPane();
        this.pane.setVgap(10);
        this.pane.setHgap(10);
    	
    	
    }

    public void start() {
        
    	if(mode.equals("_attack")) {
    		attack();
    	}else {
    		
    	}
        
        
        
    }
    
    public void translate() {
    	// move the zombie from right to left
        this.walking = new TranslateTransition();
        //this.walking.setDuration(Duration.millis(2000));
        this.walking.setNode(pane);
        this.walking.setFromX(this.x);
        this.walking.setToX(this.x + this.difference);
        this.walking.setFromY(this.y_cor);
       
        this.walking.setDuration(Duration.seconds((this.difference/150) * this.speed));
        this.walking.play();
        
        // A Group object has no layout of children easier to use here
        this.pane.setMouseTransparent(true);
        this.root1.getChildren().add(pane);
        this.mode = "_attack";
        this.walking.setOnFinished(new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent event) {
            	
                Delete();
                
            }
        });
    }
    
    public void Delete() {
        	this.pane.getChildren().remove(0);
    }
    
    public void attack() {
    	

    	
    	this.COLUMNS = this.COUNT;
    	Image IMAGE = new Image("images/" + this.action +".png");
    	final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        this.animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                this.COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );

        this.animation.setCycleCount(Animation.INDEFINITE);
        this.animation.play();
        
       
        this.pane.add(imageView, 0, 0);

       
        // A Group object has no layout of children easier to use here
        pane.setMouseTransparent(true);

    }
    
    public GridPane getPane() {
    	
    	return this.pane;
    }
    
    public void setMode(String mode) {
    	this.mode = mode;
    }
    
    public Animation getAnimation() {
    	return this.animation;
    }
    
    public TranslateTransition getTranslation() {
    	return this.translation;
    }
    
    public int getStart() {
    	return this.x;
    }
   
   public int getRate() {
	   return this.rate;
   }
   
   public void setDifference(int dif) {
	   this.difference = dif;
   }
   
   public int getDif() {
	   return this.difference;
   }
    

	
}

