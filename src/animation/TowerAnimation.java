package animation;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TowerAnimation extends Node{
	private String action = "zombie1";
    private Image IMAGE = new Image("images/" + action + "_walk.png");
    private StackPane root1;
    private int y_cor;
    private TranslateTransition walking;
    private BorderPane towerPane;
    private GridPane pane;
    private int speed;
    private String mode;
    
    
    private static final int COLUMNS  =   9;
    private int COUNT    =  6;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 90;
    private static final int HEIGHT   = 86;

    public TowerAnimation(StackPane stage, int y, int speed, String mode, String action, int count) {
    	this.root1 = stage;
    	this.y_cor = y;
    	this.speed = speed;
    	this.mode = mode;
    	this.action = action;
    	this.COUNT = count;
    }

    public void start() {
        
    	if(mode.equals("_attack")) {
    		attack();
    	}else if(mode.equals("_walk")) {
    		walk();
    	}else if(mode.equals("_death")) {
    		Death();
    	}else {
    		
    	}
        
        
        
    }
    
    public void walk() {
    	
    	IMAGE = new Image("images/" + this.action + this.mode+".png");
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
        this.mode = "_death";
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
    	IMAGE = new Image("images/" + this.action + this.mode+".png");
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
    
    public void attack() {
    	
    	IMAGE = new Image("images/" + this.action + this.mode+".png");
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
        
        
        this.towerPane = new BorderPane();
       
        this.towerPane.setCenter(imageView);
        
        // A Group object has no layout of children easier to use here
        this.towerPane.setMouseTransparent(true);
        //this.root1.getChildren().add(pane);
        
        
    	
    }
    
    public BorderPane getPane() {
    	
    	return this.towerPane;
    }

	@Override
	protected boolean impl_computeContains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BaseBounds impl_computeGeomBounds(BaseBounds arg0, BaseTransform arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected NGNode impl_createPeer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object impl_processMXNode(MXNodeAlgorithm arg0, MXNodeAlgorithmContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
