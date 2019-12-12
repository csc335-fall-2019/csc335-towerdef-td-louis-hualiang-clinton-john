package model.entity;
import java.util.List;
import animation.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import model.TDModel;

/**
 * Purpose: Entity for shared features between towers, enemies, and objects.
 * 
 * <pre>
 * Allows for constructing various towers, enemies, and objects.
 * Animations, visuals, and behaviours are defined via methods, minor 
 * differences result in shared code, but requires specific instancing for 
 * checks against collision and animations.
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
	private TDModel model;
	private int weaponFrames;
	
	/**
	 * Purpose: New entity type.
	 * 
	 * <pre>
	 * Takes in the name of an entity type and constructs valid names into method-defined state and behaviors.
	 * Model is stored to pass to animations for updating model on collision event.
	 * </pre>
	 * 
	 * @param type A String of the entity type.
	 * @param model A TDModel for use in an animation updating on collision.
	 */
	public Entity(String type, TDModel model) {
		this.type = type;
		this.isValid = buildEntity();
		this.model = model;
	}
	
	
	/**
	 * Purpose: Determines if the entity is an implemented type.
	 * 
	 * <pre>
	 * Defines the values for various towers, enemies, and objects.
	 * Sets their values appropriately.
	 * </pre>
	 */
	private boolean buildEntity() {
		/****************** Tower Creation ******************/
		if (this.type.contains("tower")) {
			if (this.type.equals("tower0")) {
				// Basic starting tower
				this.base = "tower";
				this.speed = 2;
				this.health = 500;
				this.attack = 0;
				this.price = 110;
				this.frames = 1;
			}
			else if (this.type.equals("tower1")) {
				this.base = "tower";
				this.speed = 1;
				this.health = 90;
				this.attack = 30;
				this.price = 50;
				this.frames = 5;
				this.weaponFrames = 1;
			}
			else if (this.type.equals("tower2")) {
				this.base = "tower";
				this.health = 160;
				this.attack = 25;
				this.price = 210;
				this.frames = 6;
				this.speed = 1;
				this.weaponFrames = 8;
			}
			else if (this.type.equals("tower3")) {
				this.base = "tower";
				this.health = 245;
				this.attack = 25;
				this.price = 245;
				this.speed = 1;
				this.frames = 9;
				this.weaponFrames =1;
			}
			else if (this.type.equals("tower4")) {
				this.base = "tower";
				this.speed = 1;
				this.health = 200;
				this.attack = 3000;
				this.price = 500;
				this.frames = 9;
				this.weaponFrames = 8;
			}
			else if (this.type.equals("tower5")) {
				this.base = "tower";
				this.weaponFrames = 3;
				this.health = 100;
				this.attack = 25;
				this.price = 90;
				this.speed = 1;
				this.frames = 7;
			}
		}
		
		/****************** Enemy Creation ******************/
		if (this.type.contains("zombie")) {
			if (this.type.equals("zombie0")) {
				this.base = "zombie";
				this.health = 150;
				this.attack = 5;
				this.speed = 75;
				this.deathFrames = 9;
				this.walkFrames = 9;
				this.attackFrames =7;
			}
			else if (this.type.equals("zombie1")) {
				this.base = "zombie";
				this.health = 500;
				this.attack = 5;
				this.speed = 50;
				this.deathFrames = 5;
				this.walkFrames = 6;
				this.attackFrames =8;
			}
			else if (this.type.equals("zombie2")) {
				this.base = "zombie";
				this.health = 250;
				this.attack = 5;
				this.speed = 50;
				this.deathFrames = 5;
				this.walkFrames = 6;
				this.attackFrames =7;
			}
			else if (this.type.equals("zombie3")) {
				this.base = "zombie";
				this.health = 100;
				this.attack = 25;
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
			}
			else if (this.type.equals("object1")) {
				this.base = "object";
			}
			else if (this.type.equals("object2")) {
				this.base = "object";
			}
			else if (this.type.equals("object3")) {
				this.base = "object";
			}
			else if (this.type.equals("object4")) {
				// Jail building
				this.base = "object";
			}
			else if (this.type.equals("object5")) {
				// Inn building
				this.base = "object";
			}
			else if (this.type.equals("object6")) {
				// Bar building
				this.base = "object";
			}
			else if (this.type.equals("object7")) {
				// Watchtower building
				this.base = "object";
			}
			else if (this.type.equals("object8")) {
				// Plus path
				this.base = "object";
			}
			else if (this.type.equals("object9")) {
				// NS Path
				this.base = "object";
			}
			else if (this.type.equals("object10")) {
				// EW Path
				this.base = "object";
			}
			else if (this.type.equals("object11")) {
				// Cobble Ground
				this.base = "object";
			}
			else if (this.type.equals("object12")) {
				// Tombstone with dirt
				this.base = "object";
			}
			else if (this.type.equals("object13")) {
				// Tombstone
				this.base = "object";
			}
			else if (this.type.equals("object14")) {
				// Wood grave marker
				this.base = "object";
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
		String mode = "_attack";
		this.animation = new TowerAnimation(root, row, this.speed, mode, this.type, this.frames, col, this.weaponFrames);
		this.animation.start();
		this.animation.getAnimation().setRate(this.speed);
		return this.animation;
	}
	
	/**
	 * Purpose: Pauses the animations tied to the entity.
	 * 
	 * @param theBase String of the entity base type.
	 */
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
	
	/**
	 * Purpose: Resumes the animations tied to the entity.
	 * 
	 * @param theBase String of the entity base type.
	 */
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
	
	/**
	 * Purpose: Changes the speed of the animations.
	 * 
	 * @param multiplier
	 * @param theBase String of the entity base type.
	 */
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
		this.image = new Image("images/" + this.type + ".png");
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
		if (this.base.equals("zombie")) {
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