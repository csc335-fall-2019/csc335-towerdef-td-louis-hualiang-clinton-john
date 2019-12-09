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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.TDModel;
import model.entity.Entity;

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
        //System.out.printf("Projectile info\nthis.x = %d\ntarget.getStart() = %d\nthis.rate = %d\ntarget.getRate() = %f\n", this.x, target.getStart(), this.rate, target.getRate());

    	
    	
    }
    
    public void setNew() {
    	
    }
    
    public void start() {
        
    	if(mode.equals("_attack")) {
    		attack();
    	}else {
    		
    	}
        
        
        
    }
    
    public void translate() {
    	
    	this.translation = new Timeline();

        this.translation.setCycleCount(Timeline.INDEFINITE);
        
        KeyFrame moveBall = new KeyFrame(Duration.seconds(this.myrate),
                new EventHandler<ActionEvent>() {

                    public void handle(ActionEvent event) {
                    	
                    	Timeline transition = getTranslation();
                    	int y_cor = getY();
                    	int hits = getHits();
                    	pane.setTranslateX(pane.getTranslateX() + 1);
                    	pane.setTranslateY(y_cor);
                    	
                    	//System.out.printf("ProjX = %f, EnemyX = %f\n", pane.getTranslateX(), target.getEnemyAnimation().getPane().getTranslateX());
                    	if( ((pane.getTranslateX() > target.getEnemyAnimation().getPane().getTranslateX() - 2) && (pane.getTranslateX() < target.getEnemyAnimation().getPane().getTranslateX()+2)) || (pane.getTranslateX() == target.getEnemyAnimation().getPane().getTranslateX()) ) {
                    		//System.out.printf("ProjX = %f, EnemyX = %f\n", pane.getTranslateX(), target.getEnemyAnimation().getTranslateX());
                    		if(hits == 0) {
                    			transition.pause();
                        		Delete();
                    		}else {
                    			System.out.println("slowed");
                    			transition.pause();
                        		Delete();
                    			target.getEnemyAnimation().slow();
                    		}
                    		
                    		if(lethal == true) {
                    			target.getEnemyAnimation().Death();
                    		}
                    	}
  
                    }
                    
        		});
        this.translation.getKeyFrames().add(moveBall);
        this.translation.play();
        
    }
  
    

    
    public void Delete() {
    	this.root1.getChildren().remove(this.pane);
    	
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
    
    public Timeline getTranslation() {
    	return this.translation;
    }
    
    public int getStart() {
    	return this.x;
    }
   
   public double getRate() {
	   return this.myrate;
   }
   
   public void setDifference(double dif) {
	   this.difference = dif;
   }
   
   public double getDifference() {
	   return this.difference;
   }
   
   public void setSpeed(int speed) {
	   this.gameSpeed = speed;
   }
   
   public int getY() {
	   return this.y_cor;
   }
   
   public void setLethal() {
	   this.lethal = true;
   }
   
   public int getHits() {
	   return this.hits;
   }
 
}