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
    private BorderPane towerPane;
    private int speed;
    private String mode;
    private Animation animation;
    private int attack = 1;
    private int x;
    
    
    private static final int COLUMNS  =   9;
    private int COUNT    =  6;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 90;
    private static final int HEIGHT   = 86;

    public TowerAnimation(StackPane stage, int y, int speed, String mode, String action, int count, int x) {
    	this.root1 = stage;
    	this.y_cor = y;
    	this.speed = speed;
    	this.mode = mode;
    	this.action = action;
    	this.COUNT = count;
    	this.x = x;
    }

    public void start() {
        
    	if(mode.equals("_attack")) {
    		attack();
    		this.attack = 1;
    	}else {
    		this.attack = 0;
    	}
         
    }
      
    public void Delete() {
        	this.towerPane.setCenter(null);
    }
    
    public void attack() {
    	
    	IMAGE = new Image("images/" + this.action + this.mode+".png");
    	final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

         this.animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        
        
        this.animation.setCycleCount(Animation.INDEFINITE);
        this.animation.play();
        
        String action = "weapon4";
        int dif = 600;
    	Projectile projectile = new Projectile(this.root1, this.y_cor+10, this.speed, this.mode,action, 8, 1, 300 +(150 * this.x) + 60, dif);
    	projectile.start();
    	projectile.translate();
    	
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
