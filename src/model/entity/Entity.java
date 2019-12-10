package model.entity;
import java.util.List;
import java.util.Observable;
import animation.*;
import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import model.TDModel;

/**
 * Purpose: Entity for shared features between Tower and Enemy classes.
 * 
 * <pre>
 * Stores state and provides methods to 
 * 
 * Public Methods:
 *   TDController(TDModel model) - New controller for updating a model of TD.
 *   Getters and Setters
 * </pre>
 * 
 * @author Hualiang Qin
 * @author Louis Galluzzi
 * @author Clinton Kral
 * @author John Stockey
 */
public class Entity {
	private String type;
	private String base;
	private boolean isValid;
	private Image image;
	// More variables for entity specific elementsprivate int health;
	private int health;
	private int attack;
	private double speed;
	private int price;
	private TowerAnimation animation;
	private EntityAnimation enemyAnimation;
	private int frames;
	private int deathFrames;
	private int attackFrames;
	private int walkFrames;
	private int rate;
	private TDModel model;
	private int weaponFrames;
	// More variables for entity specific elements
	
	/**
	 * Purpose: New entity type.
	 * 
	 * <pre>
	 * Takes in the name of an entity type and constructs valid names into method-defined state and behaviors.
	 * </pre>
	 * 
	 * @param type A String of the entity type.
	 */
	public Entity(String type, TDModel model) {
		this.type = type;
		this.isValid = buildEntity();
		this.model = model;
	}
	
	
	/************************** Private Fields Block ***************************/
	
	/**
	 * Purpose: Determines if the entity is an implemented type.
	 */
	private boolean buildEntity() {
		/****************** Tower Creation ******************/
		if (this.type.contains("tower")) {
			if (this.type.equals("tower0")) {
				// Basic starting tower
				this.base = "tower";
				this.image = new Image("images/tower0.png");
				this.speed = 2;
				this.health = 500;
				this.attack = 0;
				this.price = 110;
				this.frames = 1;
			}
			else if (this.type.equals("tower1")) {
				this.base = "tower";
				this.image = new Image("images/tower1.png");
				this.speed = 2;
				this.health = 90;
				this.attack = 30;
				this.price = 120;
				this.frames = 5;
				this.weaponFrames = 1;
			}
			else if (this.type.equals("tower2")) {
				this.base = "tower";
				this.image = new Image("images/tower2.png");
				this.health = 160;
				this.attack = 50;
				this.price = 210;
				this.price = 210;
				this.frames = 6;
				this.speed = 2;
				this.weaponFrames = 8;
			}
			else if (this.type.equals("tower3")) {
				this.base = "tower";
				this.image = new Image("images/tower3.png");
				this.health = 180;
				this.attack = 65;
				this.price = 245;
				this.speed = 2;
				this.frames = 9;
				this.weaponFrames =1;
				

			}
			else if (this.type.equals("tower4")) {
				this.base = "tower";
				this.image = new Image("images/tower4.png");
				this.speed = 2;
				this.health = 200;
				this.attack = 3000;
				this.price = 275;
				this.frames = 9;
				this.weaponFrames = 8;

			}
			else if (this.type.equals("tower5")) {
				this.base = "tower";
				this.image = new Image("images/tower5.png");
				this.weaponFrames = 3;
				this.health = 100;
				this.attack = 25;
				this.price = 90;
				this.speed = 2;
				this.frames = 7;

			}
		}
		
		/****************** Enemy Creation ******************/
		if (this.type.contains("zombie")) {
			if (this.type.equals("zombie0")) {
				this.base = "zombie";
				//this.image = new Image("images/zombie0.png");
				this.health = 300;
				this.attack = 5;
				this.speed = 75;
				this.deathFrames = 9;
				this.walkFrames = 9;
				this.attackFrames =7;
			}
			else if (this.type.equals("zombie1")) {
				this.base = "zombie";
				//this.image = new Image("images/zombie1.png");
				this.health = 200;
				this.attack = 5;
				this.speed = 50;
				this.deathFrames = 5;
				this.walkFrames = 6;
				this.attackFrames =8;
			}
			else if (this.type.equals("zombie2")) {
				this.base = "zombie";
				//this.image = new Image("images/enemy0.png");
				this.health = 500;
				this.attack = 5;
				this.speed = 25;
				this.deathFrames = 5;
				this.walkFrames = 6;
				this.attackFrames =7;
			}
			else if (this.type.equals("zombie3")) {
				this.base = "zombie";
				//this.image = new Image("images/enemy0.png");
				this.health = 100;
				this.attack = 5;
				this.speed = 25;
				this.deathFrames = 5;
				this.walkFrames = 8;
				this.attackFrames =7;
				
			}
		}
		
		/****************** Object Creation ******************/
		if (this.type.contains("object")) {
			if (this.type.equals("object0")) {
				this.base = "object";
				this.image = new Image("images/object0.png");
			}
			else if (this.type.equals("object1")) {
				this.base = "object";
				this.image = new Image("images/object1.png");
			}
			else if (this.type.equals("object2")) {
				this.base = "object";
				this.image = new Image("images/object2.png");
			}
			else if (this.type.equals("object3")) {
				this.base = "object";
				this.image = new Image("images/object3.png");
			}
			else if (this.type.equals("object4")) {
				// Jail building
				this.base = "object";
				this.image = new Image("images/object4.png");
			}
			else if (this.type.equals("object5")) {
				// Inn building
				this.base = "object";
				this.image = new Image("images/object5.png");
			}
			else if (this.type.equals("object6")) {
				// Bar building
				this.base = "object";
				this.image = new Image("images/object6.png");
			}
			else if (this.type.equals("object7")) {
				// Watchtower building
				this.base = "object";
				this.image = new Image("images/object7.png");
			}
			else if (this.type.equals("object8")) {
				// Plus path
				this.base = "object";
				this.image = new Image("images/object8.png");
			}
			else if (this.type.equals("object9")) {
				// NS Path
				this.base = "object";
				this.image = new Image("images/object9.png");
			}
			else if (this.type.equals("object10")) {
				// EW Path
				this.base = "object";
				this.image = new Image("images/object10.png");
			}
			else if (this.type.equals("object11")) {
				// Cobble Ground
				this.base = "object";
				this.image = new Image("images/object11.png");
			}
			else if (this.type.equals("object12")) {
				// Tombstone with dirt
				this.base = "object";
				this.image = new Image("images/object12.png");
			}
			else if (this.type.equals("object13")) {
				// Tombstone
				this.base = "object";
				this.image = new Image("images/object13.png");
			}
			else if (this.type.equals("object14")) {
				// Wood grave marker
				this.base = "object";
				this.image = new Image("images/object14.png");
			}
		}
		
		
		/****************** Out of Creation ******************/
		// Check if a type was created and return results
		if (this.base != null) {
			// Successful creation
			return true;
		} else {
			// Unsuccessful creation
			return false;
		}
	}
	

	/**
	 * Purpose: Decrease the health of the entity.
	 * 
	 * @param damage An int of the damage to be applied.
	 */
	public void beAttacked(int damage) {
		if (health > damage) {
			health -= damage;
		}else {
			health = 0;
		}
	}
	
	/**
	 * Purpose: Checks if entity health is 0 or less.
	 * 
	 * @return boolean indicating if the entity has died.
	 */
	public boolean isDead() {
		return health <= 0;
	}
	
	
	/**
	 * Purpose: Constructs a new enemy animation for visual display.
	 * 
	 * @param root A StackPane of the root to place visuals onto.
	 * @param row An int of the row to place the Animation onto.
	 * 
	 * @return EntityAnimation the animation class for the enemy visual.
	 */
	public EntityAnimation enemyAnimation(StackPane root, int row, int col, Entity zom) {
		int y = 60 + (150 * row);
		String mode = "_walk";
		this.enemyAnimation = new EntityAnimation(root, y, this.speed, mode, this.type, this.frames, this.deathFrames, this.walkFrames, this.attackFrames, this.model, col, zom, row);
		this.enemyAnimation.start();
		return this.enemyAnimation;
	}
	
	
	/**
	 * Purpose: Constructs a new tower animation for visual display.
	 * 
	 * @param root A StackPane of the root to place visuals onto.
	 * @param row An int of the row to place the Animation onto.
	 * @param col An int of the column to place the Animation onto.
	 * 
	 * @return TowerAnimation the animation class for the tower visual.
	 */
	public TowerAnimation buildAnimation(StackPane root, int row, int col) {
		int y = 60 + (150 * row);
		String mode = "_attack";
		int x = col;
		this.animation = new TowerAnimation(root, row, this.speed, mode, this.type, this.frames, col, this.weaponFrames);
		this.animation.start();
		return this.animation;
	}
	
	/**
	 * Purpose: Builds the Projectile to fire at an enemy and runs it.
	 * 
	 * @param enemy An Entity referencing the enemy to fire at.
	 */
	public void fireProjectile(Entity enemy) {
		/*
		String a = "weapon4";
		int dif = 600;
		Projectile projectile = new Projectile(this.root1, 60, 2, "_attack",a, 8, 1, 500, dif);
		projectile.start();
		projectile.translate();
		*/
		
		// Get the tower and enemy x locations to calculate difference
		//this.animation.makeProjectile(enemy.getEnemyAnimation());
		
		/*
		int towerX = this.animation.getProjectile().getStart();
		int enemyX = enemy.getEnemyAnimation().getStart();
		this.animation.setDif(towerX - enemyX);
		this.animation.start();
		*/
	}
	
	//test pause method
	public void pause(String theBase) {
		if (theBase.equals("zombie")) {
			getEnemyAnimation().getAnimation().pause();
			getEnemyAnimation().getTranslation().pause();
		}else if (theBase.equals("tower")) {
			getAnimation().getAnimation().pause();
			Projectile pjtile = getAnimation().getProjectile();
			if (pjtile != null) {
				List<Projectile> listOfPjtile = getAnimation().getPjList();
				for (Projectile pjt: listOfPjtile) {
					pjt.getAnimation().pause();
					pjt.getTranslation().pause();
				}
				
			}
		}
	}
	
	//test resume method
	public void resume(String theBase) {
		if (theBase.equals("zombie")) {
			getEnemyAnimation().getAnimation().play();
			getEnemyAnimation().getTranslation().play();
		}else if (theBase.equals("tower")) {
			getAnimation().getAnimation().play();
			Projectile pjtile = getAnimation().getProjectile();
			if (pjtile != null) {
				List<Projectile> listOfPjtile = getAnimation().getPjList();
				for (Projectile pjt: listOfPjtile) {
					pjt.getAnimation().play();
					pjt.getTranslation().play();
				}
			}
			
		}
	}
	
	public void changeSpeed(double multiplier, String theBase) {
		if (theBase.equals("zombie")) {
			getEnemyAnimation().getTranslation().setRate(multiplier);
			getEnemyAnimation().getAnimation().setRate(multiplier);
		}else if (theBase.equals("tower")) {
			getAnimation().getAnimation().setRate(multiplier);
			Projectile pjtile = getAnimation().getProjectile();
			if (pjtile != null) {
				List<Projectile> listOfPjtile = getAnimation().getPjList();
				for (Projectile pjt: listOfPjtile) {
					pjt.getAnimation().setRate(multiplier);
					pjt.getTranslation().setRate(multiplier);
				}
			}
		}
	}
	
	/************************ Getters and Setters Block ************************/
	
	/**
	 * Getter for type.
	 * 
	 * @return String referencing the entity type
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Getter for base.
	 * 
	 * @return String referencing the entities' grouping.
	 */
	public String getBase() {
		return this.base;
	}
	
	/**
	 * Getter for isValid.
	 * 
	 * @return boolean indicating a valid entity.
	 */
	public boolean getIsValid() {
		return this.isValid;
	}
	
	/**
	 * Getter for image.
	 * 
	 * @return Image an image representation of the entity.
	 */
	public Image getImage() {
		return this.image;
	}
	
	/**
	 * Getter for health
	 * 
	 * @return int representing the Entity's health.
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * Getter for attack
	 * 
	 * @return int representing the Entity's attack damage.
	 */
	public int getAttack() {
		return attack;
	}
	
	/**
	 * Getter for price (0 if non-tower)
	 * 
	 * @return int indicating cost of Entity.
	 */
	public int getPrice() {
		if (this.base.equals("tower")) {
			return price;
		}
		return 0;
	}
	
	/**
	 * Getter of the speed, only enemy have the speed
	 * 
	 * @return int indicating enemy speed.
	 */
	public double getSpeed() {
		if (this.base.equals("enemy")) {
			return speed;
		}
		return 0;
	}
	
	/**
	 * Getter for frames.
	 * 
	 * @return int indicating number of sprites on sprite sheet.
	 */
	public int getFrames() {
		return frames;

	}
	
	/**
	 * Getter for enemyAnimation.
	 * 
	 * @return EntityAnimation, the animations for an enemy.
	 */
	public EntityAnimation getEnemyAnimation() {
		return enemyAnimation;
	}
	
	
	/**
	 * Getter for animation.
	 * 
	 * @return TowerAnimation, the animations for a tower.
	 */
	public TowerAnimation getAnimation() {
		return animation;
	}

	
}
