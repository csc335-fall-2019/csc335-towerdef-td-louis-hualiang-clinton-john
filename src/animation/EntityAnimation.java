package animation;

import java.util.ArrayList;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import model.TDModel;
import model.entity.Entity;

public class EntityAnimation extends Node{
	private String action = "zombie1";
    private Image IMAGE = new Image("images/" + action + "_walk.png");
    private StackPane root1;
    private int y_cor;
    private TranslateTransition walking;
    private GridPane pane;
    private double speed;
    private String mode;
    private int death;
    private int walk;
    private int attack;
    private Animation animation;
    private Timeline translation;
    private int start = 1570;
    private int difference = 50;
    private boolean isTranslating;
    private boolean isDead = false;
    private double rate;
    public double tic = 0;
    public double duration = 1;
    private TDModel model;
    public int col;
    public Entity zom;
    public int row;
    private int gameSpeed = 1;
    

    private double move;

    
    private int COLUMNS  =   9;
    private int COUNT    =  6;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 90;
    private static final int HEIGHT   = 86;

    public EntityAnimation(StackPane stage, int y, double speed, String mode, String action, int count, int death, int walk, int attack, TDModel model, int col, Entity zom, int row) {
    	this.root1 = stage;
    	this.y_cor = y;
    	this.speed = speed * this.gameSpeed;
    	this.mode = mode;
    	this.action = action;
    	this.COUNT = count;
    	this.death = death;
    	this.walk = walk;
    	this.attack = attack;
    	this.rate = (1/this.speed)/this.gameSpeed;
    	this.move = 0;
    	this.pane = new GridPane();
        
        pane.setTranslateX(this.start);
        this.model = model;
        this.col = col;
        this.zom = zom;
        this.row = row;
        
        
        this.pane.setMouseTransparent(true);
        this.root1.getChildren().add(pane);
    }

    public void start() {
    	if(mode.equals("_attack")) {
    		attack();
    	}else if(mode.equals("_walk")) {
    		walk();
    	}else if(mode.equals("_death")) {
    		Death();
    	}
    }
    
    public void translate() {
    	this.translation = new Timeline();
        
        this.translation.setCycleCount(Timeline.INDEFINITE);
        
        KeyFrame moveBall = new KeyFrame(Duration.seconds(this.rate),
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                    	
                    	//TranslateTransition transition = getTranslation();
                    	TDModel model = getModel();
                    	
                    	int y_cor = getY();
                    	double x = check1();
                    	double y = getSpeed();
                    	
                    	pane.setTranslateX(pane.getTranslateX() - 1);
                    	pane.setTranslateY(y_cor);
                    	
                    	if(x % y == 0) {
                    		incrMove();
                    		minusStart();
                    		if(getMove() % 150 == 0) {
                    			
                    			if(col == 0) {
                    				Death();
                    			}else {
                    				model.updateSpot(col, row, zom);
                            		col -= 1;
                    			}
                        		
                        	}
                    	}
                    	inc2();
                    }
                    
        		});
        this.translation.getKeyFrames().add(moveBall);
        this.translation.play();
        
        }
    
    
    public void walk() {
    	if (this.animation != null) {
    		this.animation.stop();
        	this.pane.getChildren().remove(0);
    	}
    	this.COUNT = this.walk;
    	this.COLUMNS = this.walk;
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
        
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        
        this.pane.add(imageView, 0, 0);
    }
    
    
    public void Death() {
    	this.translation.pause();
    	this.mode = "_death";
    	this.animation.stop();
    	this.pane.getChildren().remove(0);
    	this.COUNT = this.death;
    	this.COLUMNS = this.death;
    	IMAGE = new Image("images/" + this.action + this.mode+".png");
    	final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        this.animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                this.COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        
        this.animation.setCycleCount(1);
        this.animation.play();
        
        this.pane.add(imageView, 0, 0);
        this.animation.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Delete();
            }
        });
       
        // A Group object has no layout of children easier to use here
        pane.setMouseTransparent(true);
    }
    
    
    public void Delete() {
        	this.root1.getChildren().remove(this.pane);
    }
    
    
    public void attack() {
    	this.mode = "_attack";
    	this.animation.stop();
    	this.pane.getChildren().remove(0);
    	this.COUNT = this.attack;
    	this.COLUMNS = this.attack;
    	IMAGE = new Image("images/" + this.action + this.mode+".png");
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
    
    
    /**
     * Purpose: Resets move to 0.
     */
    public void resetMove() {
    	this.move = 0;
    }
    
    public void minusStart() {
    	this.start -= this.speed;
    }
    
    public void incrMove() {
    	this.move += this.speed;
    }
    
	/************************** Private Fields Block ***************************/
	
    private void changeTranslate() {
    	this.isTranslating = false;
    }
    
    
    
    
	/************************ Getters and Setters Block ************************/
    
    public GridPane getPane() {
    	return this.pane;
    }
    
    public double getSpeed() {
    	return this.speed;
    }
    
    public void setMode(String mode) {
    	this.mode = mode;
    }
    
    public String getMode() {
    	return this.mode;
    }
    
    public Animation getAnimation() {
    	return this.animation;
    }
    
    public Timeline getTranslation() {
    	return this.translation;
    }
    
    public void setDeath() {
    	this.isDead = true;
    }
    
   public int getStart() {
	   return this.start;
   }
   
   public double getRate() {
	   return this.rate;
   }
   
   public double getMove() {
	   return this.move;
   }
   
   public void setDifference(int dif) {
	   this.difference = dif;
   }
   
   public int getDif() {
	   return this.difference;
   }
   
   public int getY() {
	   return this.y_cor;
   }

   public double check() {
   	return this.duration;
   }
   
   public double check1() {
   	return this.tic;
   }
   
   public void inc2() {
   	this.tic += 1;
   }
   
   public TDModel getModel() {
	   return this.model;
   }
   
   public void setSpeed(int speed) {
	   this.gameSpeed = speed;
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