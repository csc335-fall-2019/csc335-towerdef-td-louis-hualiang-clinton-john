package animation;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.entity.Entity;

/**
 * Purpose: Projectile for usage with tower Entity types.
 * 
 * <pre>
 * Allows for constructing various projectiles, unique to the various tower types.
 * Provides the methods for their animations, transitioning, and detecting when they 
 * hit their target, and how to update said information.
 * </pre>
 * 
 * @author Hualiang Qin
 * @author Louis Galluzzi
 * @author Clinton Kral
 * @author John Stockey
 */
public class Projectile {
	private String action = "weapon4";
    private StackPane root1;
    private int y_cor;
    private TranslateTransition walking;
    public GridPane pane;
    private double speed;
    private String mode;
    private int attack;
    private Animation animation;
    private Timeline translation;
    private int x;
    private double difference = 150;
    private double myrate;
    private Entity target;
    private double duration = 5;
    public int row;
    public int col;
    private int gameSpeed = 1;
    public boolean lethal = false;
    public int hits;
    
    private int COLUMNS  =   9;
    private int COUNT    =  6;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 45;
    private static final int HEIGHT   = 45;

    /**
     * Purpose: Contructor for creating a new projectile.
     * 
     * @param stage A StackPane for showing the animation.
     * @param y An int of the row position.
     * @param speed A double for the speed of the projectile.
     * @param mode A String of the mode to animate in.
     * @param action A String of the animation to build and show.
     * @param count An int of the number of frames for the animation.
     * @param attack An int of the attack amount.
     * @param x An int of the x position.
     * @param target An Entity of the target.
     * @param hits An int of the number of hits to make.
     */
    public Projectile(StackPane stage, int y, double speed, String mode, String action, int count, int attack, int x, Entity target, int hits) {
    	this.root1 = stage;
    	this.y_cor = 80 + (150 * y);
    	this.row = y;
    	this.speed = speed * this.gameSpeed;
    	this.mode = mode;
    	this.action = action;
    	this.COUNT = count;
    	this.x = 350 + (x * 150);
    	this.col = x;
    	this.attack = attack;
    	this.myrate = (1.0/150.0)/this.gameSpeed;
    	this.pane = new GridPane();
    	pane.setMouseTransparent(true);
        this.target = target;
        pane.setTranslateX(this.x);
        pane.setTranslateY(this.y_cor);
        this.root1.getChildren().add(pane);
        this.target = target;
        if(this.action.equals("weapon5")) {
        	this.hits = 1;
        }else {
        	this.hits = 0;
        }
    }
    
    /**
     * Purpose: Begins the starting animation.
     */
    public void start() {
    	if(mode.equals("_attack")) {
    		attack();
    	}
    }
    
    /**
     * Purpose: Initialized the animation.
     */
    public void translate() {
    	this.translation = new Timeline();
        this.translation.setCycleCount(Timeline.INDEFINITE);
        
        // KeyFrame for the Animation
        KeyFrame moveBall = new KeyFrame(Duration.seconds(this.myrate),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                    	Timeline transition = getTranslation();
                    	int y_cor = getY();
                    	int hits = getHits();
                    	pane.setTranslateX(pane.getTranslateX() + 1);
                    	pane.setTranslateY(y_cor);
                    	
                    	//System.out.printf("ProjX = %f, EnemyX = %f\n", pane.getTranslateX(), target.getEnemyAnimation().getPane().getTranslateX());
                    	if(((pane.getTranslateX() > target.getEnemyAnimation().getPane().getTranslateX() - 10) && (pane.getTranslateX() < target.getEnemyAnimation().getPane().getTranslateX()+100)) || (pane.getTranslateX() == target.getEnemyAnimation().getPane().getTranslateX()) ) {
                    		//System.out.printf("ProjX = %f, EnemyX = %f\n", pane.getTranslateX(), target.getEnemyAnimation().getTranslateX());
                    		if(hits == 0) {
                    			transition.pause();
                        		Delete();
                        		if(lethal == true) {
                        			target.getEnemyAnimation().Death();
                        			target.getEnemyAnimation().setDeath();
                        		}
                    		}else {
                    			//System.out.println("slowed");
                    			transition.pause();
                        		Delete();
                    			target.getEnemyAnimation().slow();
                    			if(lethal == true) {
                        			target.getEnemyAnimation().Death();
                        			target.getEnemyAnimation().setDeath();
                        		}
                    		}
                    	}
                    }
        		});
        
        // Start the animation
        this.translation.getKeyFrames().add(moveBall);
        this.translation.play();
    }
  
    /**
     * Purpose: Remove the animation from the visual.
     */
    public void Delete() {
    	this.root1.getChildren().remove(this.pane);
    }
    
    /**
     * Purpose: Runs an attack animation.
     */
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
        
        // Add the animation to the pane for visual display
        this.pane.add(imageView, 0, 0);
        
        // A Group object has no layout of children easier to use here
        pane.setMouseTransparent(true);
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
     * Setter for mode.
     * 
     * @param mode A String
     */
    public void setMode(String mode) {
    	this.mode = mode;
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
     * Getter for start.
     * 
     * @return int
     */
    public int getStart() {
    	return this.x;
    }
   
    /**
     * Getter for rate.
     * 
     * @return double
     */
   public double getRate() {
	   return this.myrate;
   }
   
   /**
    * Setter for difference.
    * 
    * @param dif A double
    */
   public void setDifference(double dif) {
	   this.difference = dif;
   }
   
   /**
    * Getter for difference.
    * 
    * @return double
    */
   public double getDifference() {
	   return this.difference;
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
    * Getter for y.
    * 
    * @return int
    */
   public int getY() {
	   return this.y_cor;
   }
   
   /**
    * Sets lethal to be true.
    */
   public void setLethal() {
	   this.lethal = true;
   }
   
   /**
    * Getter for hits.
    * 
    * @return int
    */
   public int getHits() {
	   return this.hits;
   }
 
}