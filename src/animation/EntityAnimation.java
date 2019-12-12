package animation;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.TDModel;
import model.entity.Entity;

/**
 * Purpose: EntityAnimation for usage with enemy Entity types.
 * 
 * <pre>
 * Visual animation, movement, and event-driven progression for enemy Entities.
 * </pre>
 * 
 * @author Hualiang Qin
 * @author Louis Galluzzi
 * @author Clinton Kral
 * @author John Stockey
 */
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
    private KeyFrame moveZom;
    private double move;
    private int COLUMNS  =   9;
    private int COUNT    =  6;
    
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 90;
    private static final int HEIGHT   = 86;
    
    /**
     * Purpose: Constructs a new enemy animation based on the passed in parameters.
     * 
     * @param stage A StackPane for showing the animation.
     * @param y An int of the row position.
     * @param speed A double for the speed of the projectile.
     * @param mode A String of the mode to animate in.
     * @param action A String of the animation to build and show.
     * @param count An int of the number of frames for the general animation.
     * @param death An int of the number of frames for the death animation.
     * @param walk An int of the number of frames for the walking animation.
     * @param attack An int of the number of frames for the attacking animation.
     * @param model A TDModel to call updates on when the animation finishes progressions.
     * @param col An int of the column the paired Entity exists on.
     * @param zom An Entity, the paired Entity.
     * @param row An int of the row the paired Entity exists on.
     */
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
    
    /**
     * Purpose: Starts the animation depending on mode.
     */
    public void start() {
    	if(mode.equals("_attack")) {
    		attack();
    	}else if(mode.equals("_walk")) {
    		walk();
    	}else if(mode.equals("_death")) {
    		Death();
    	}
    }
    
    /**
     * Purpose: Animates the Entity and updates a TDModel for game progression.
     */
    public void translate() {
    	this.translation = new Timeline();
        this.translation.setCycleCount(Timeline.INDEFINITE);
        
        this.moveZom = new KeyFrame(Duration.seconds(this.rate),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                    	// Paired Entity state
                    	TDModel model = getModel();
                    	
                    	int y_cor = getY();
                    	double x = check1();
                    	double y = getSpeed();
                    	
                    	pane.setTranslateX(pane.getTranslateX() - 1);
                    	pane.setTranslateY(y_cor);
                    	
                    	// Move model state
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
        
        // Run the animation
        this.translation.getKeyFrames().add(this.moveZom);
        this.translation.play();
        }
    
    /**
     * Purpose: Defines a new walking animation.
     */
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
    
    /**
     * Purpose: Defines a new death animation.
     */
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
            	//System.out.println("Death delete");
                Delete();
            }
        });
       
        // A Group object has no layout of children easier to use here
        pane.setMouseTransparent(true);
    }
    
    /**
     * Purpose: Removes the visual and state representation.
     */
    public void Delete() {
        	this.root1.getChildren().remove(this.pane);
        	this.model.removeEntity(this.zom, this.row, this.col, false);
    }
    
    /**
     * Purpose: Defines a new attacking animation.
     */
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
    
    /**
     * Purpose: Decrements start by the speed for event tracking.
     */
    public void minusStart() {
    	this.start -= this.speed;
    }
    
    /**
     * Purpose: Increments move my the speed for event tracking.
     */
    public void incrMove() {
    	this.move += this.speed;
    }
    
	/************************** Private Fields Block ***************************/
	
    /**
     * Purpose: Defines the visual movement as having ended.
     */
    private void changeTranslate() {
    	this.isTranslating = false;
    }
    
	/************************ Getters and Setters Block ************************/
    
    /**
     * Getter for pane.
     * 
     * @return GridPane
     */
    public GridPane getPane() {
    	return this.pane;
    }
    
    /**
     * Getter for speed.
     * 
     * @return double
     */
    public double getSpeed() {
    	return this.speed;
    }
    
    /**
     * Setter for mode.
     * 
     * @param mode A String
     */
    public void setMode(String mode) {
    	this.mode = mode;
    }
    
    /**
     * Getter for mode.
     * 
     * @return String
     */
    public String getMode() {
    	return this.mode;
    }
    
    /**
     * Getter for animation.
     * 
     * @return Animation
     */
    public Animation getAnimation() {
    	return this.animation;
    }
    
    /**
     * Getter for translation.
     * 
     * @return Timeline
     */
    public Timeline getTranslation() {
    	return this.translation;
    }
    
    /**
     * Sets isDead to be true.
     */
    public void setDeath() {
    	this.isDead = true;
    }
    
    /**
     * Getter for isDead.
     * 
     * @return boolean
     */
    public boolean isDead() {
    	return this.isDead;
    }
   
    /**
     * Getter for start.
     * 
     * @return int
     */
	public int getStart() {
		return this.start;
	}
   
	/**
     * Getter for rate.
     * 
     * @return double
     */
	public double getRate() {
		return this.rate;
	}
	
	/**
     * Getter for move.
     * 
     * @return double
     */
	public double getMove() {
		return this.move;
	}
	
	/**
     * Setter for difference.
     * 
     * @param dif An int
     */
	public void setDifference(int dif) {
		this.difference = dif;
	}
	
	/**
     * Getter for difference.
     * 
     * @return int
     */
	public int getDif() {
		return this.difference;
	}
	
	/**
     * Getter for y_cor.
     * 
     * @return int
     */
	public int getY() {
		return this.y_cor;
	}
	
	/**
     * Getter for check.
     * 
     * @return double
     */
	public double check() {
		return this.duration;
	}
	
	/**
     * Getter for check1.
     * 
     * @return double
     */
	public double check1() {
		return this.tic;
	}
	
	/**
     * Increments tic by 1.
     */
	public void inc2() {
		this.tic += 1;
	}
	
	/**
     * Getter for model.
     * 
     * @return TDModel
     */
	public TDModel getModel() {
		return this.model;
	}
	
	/**
     * Setter for speed.
     * 
     * @param speed An int
     */
	public void setSpeed(int speed) {
		this.gameSpeed = speed;
	}
	
	/**
     * Sets translation rate to 0.5.
     */
	public void slow() {
		this.translation.setRate(0.5);
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