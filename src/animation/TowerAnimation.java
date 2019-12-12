package animation;

import java.util.ArrayList;
import java.util.List;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.entity.Entity;

/**
 * Purpose: TowerAnimation for usage with tower Entity types.
 * 
 * <pre>
 * Visual animation and Projectile creation for tower Entities.
 * </pre>
 * 
 * @author Hualiang Qin
 * @author Louis Galluzzi
 * @author Clinton Kral
 * @author John Stockey
 */
public class TowerAnimation extends Node{
	private String action = "zombie1";
    private Image IMAGE = new Image("images/" + action + "_walk.png");
    private StackPane root1;
    private int y_cor;
    private BorderPane towerPane;
    private double speed;
    private String mode;
    private Animation animation;
    private int attack = 1;
    private int x;
    private int y;
    private int dif;
    private Projectile projectile = null;
    private List<Projectile> listOfPjtile = new ArrayList<Projectile>();
    private int weaponFrames;
    private int COUNT    =  6;
    
    private static final int COLUMNS  =   9;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 90;
    private static final int HEIGHT   = 86;
    
    /**
     * Purpose: Constructs a new tower animation based on the passed in parameters.
     * 
     * @param stage A StackPane for showing the animation.
     * @param y An int of the row position.
     * @param speed A double for the speed of the projectile.
     * @param mode A String of the mode to animate in.
     * @param action A String of the animation to build and show.
     * @param count An int of the number of frames for the general animation.
     * @param x An int of the column position.
     * @param weaponFrames An int of the number of frames for the projectile animation.
     */
    public TowerAnimation(StackPane stage, int y, double speed, String mode, String action, int count, int x, int weaponFrames) {
    	this.root1 = stage;
    	this.y_cor = 60 + (150 * y);
    	this.speed = speed;
    	this.mode = mode;
    	this.action = action;
    	this.COUNT = count;
    	this.x = x;
    	this.y = y;
    	this.dif = 600;
    	this.weaponFrames = weaponFrames;
    }

    /**
     * Purpose: Starts the animation depending on mode.
     */
    public void start() {
    	if(mode.equals("_attack")) {
    		attack();
    		this.attack = 1;
    	}else {
    		this.attack = 0;
    	}
    }
    
    /**
     * Purpose: Removes the visual.
     */
    public void Delete() {
        	this.towerPane.setCenter(null);
    }
    
    /**
     * Purpose: Defines a new attacking animation.
     */
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
        
         // Run the animation
        this.animation.setCycleCount(Animation.INDEFINITE);
        this.animation.play();
        
        // Set the visual
        this.towerPane = new BorderPane();
        this.towerPane.setCenter(imageView);
        
        // A Group object has no layout of children easier to use here
        this.towerPane.setMouseTransparent(true);
        //this.root1.getChildren().add(pane);
    }
    
    /**
     * Purpose: Spawns a projectile and pairs it to the target.
     * 
     * @param target An Entity that the projectile will hit.
     * @param hitsLeft An int of the number of hits the projectile has left.
     * 
     * @return Projectile of the projectile object.
     */
    public Projectile spawnProjectile(Entity target, int hitsLeft) {
    	String action = "weapon" + this.action.charAt(this.action.length()-1);
    	//System.out.print(this.y);
       	projectile = new Projectile(this.root1, this.y, this.speed, this.mode,action, this.weaponFrames, 1, this.x, target, hitsLeft);
   		projectile.translate();
   		projectile.getTranslation().setRate(this.speed);
   		projectile.start();
   		listOfPjtile.add(projectile);
   		return projectile;
    }

    /**
     * Purpose: Adjusts the difference used for animation calculation.
     * 
     * @param dif An int of the difference to translate.
     */
    public void setDif(int dif) {
    	// Set this state's dif
    	this.dif = dif;
    	
    	// Set the Projectile's difference
    	this.projectile.setDifference(dif);
    }
    
    /************************ Getters and Setters Block ************************/

    /**
	 * Purpose: Getter for towerpane.
	 * 
	 * @return BorderPane
	 */
    public BorderPane getPane() {
    	
    	return this.towerPane;
    }
    
    /**
	 * Purpose: Getter for animation.
	 * 
	 * @return Animation
	 */
    public Animation getAnimation() {
    	return this.animation;
    }
    
    /**
	 * Purpose: Getter for projectile.
	 * 
	 * @return Projectile
	 */
    public Projectile getProjectile() {
    	return this.projectile;
    }
    
    /**
	 * Purpose: Getter for list of projectiles.
	 * 
	 * @return List&lt;Projectile&gt;
	 */
    public List<Projectile> getPjList(){
    	return listOfPjtile;
    }
    
    /**
	 * Purpose: Setter for speed.
	 * 
	 * @param speed An int to set animation speed.
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
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
