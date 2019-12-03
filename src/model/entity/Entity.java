package model.entity;
import java.util.Observable;

import javafx.scene.image.Image;

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
	private int speed;
	private int price;
	
	
	/**
	 * Purpose: New entity type.
	 * 
	 * <pre>
	 * Takes in the name of an entity type and constructs valid names into method-defined state and behaviors.
	 * </pre>
	 * 
	 * @param type A String of the entity type.
	 */
	public Entity(String type) {
		this.type = type;
		this.isValid = buildEntity();
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
				this.health = 100;
				this.attack = 10;
				this.price = 110;
			}
			else if (this.type.equals("tower1")) {
				this.base = "tower";
				this.image = new Image("images/tower1.png");
				this.health = 90;
				this.attack = 30;
				this.price = 120;
			}
			else if (this.type.equals("tower2")) {
				this.base = "tower";
				this.image = new Image("images/tower2.png");
				this.health = 160;
				this.attack = 50;
				this.price = 210;
			}
			else if (this.type.equals("tower3")) {
				this.base = "tower";
				this.image = new Image("images/tower3.png");
				this.health = 180;
				this.attack = 65;
				this.price = 245;
			}
			else if (this.type.equals("tower4")) {
				this.base = "tower";
				this.image = new Image("images/tower4.png");
				this.health = 200;
				this.attack = 135;
				this.price = 335;
			}
			else if (this.type.equals("tower5")) {
				this.base = "tower";
				this.image = new Image("images/tower5.png");
				this.health = 352;
				this.attack = 0;
				this.price = 90;
			}
		}
		
		/****************** Enemy Creation ******************/
		if (this.type.contains("enemy")) {
			if (this.type.equals("enemy0")) {
				this.base = "enemy";
				this.image = new Image("images/enemy0.png");
				this.health = 300;
				this.attack = 5;
				this.speed = 50;
			}else if (this.type.equals("enemy1")) {
				this.base = "enemy";
				this.image = new Image("images/enemy0.png");
				this.health = 200;
				this.attack = 50;
				this.speed = 80;
			}else if (this.type.equals("enemy2")) {
				this.base = "enemy";
				this.image = new Image("images/enemy0.png");
				this.health = 500;
				this.attack = 50;
				this.speed = 20;
			}else if (this.type.equals("enemy3")) {
				this.base = "enemy";
				this.image = new Image("images/enemy0.png");
				this.health = 100;
				this.attack = 10;
				this.speed = 110;
			}
		}
		
		/****************** Object Creation ******************/
		if (this.type.contains("object")) {
			if (this.type.equals("object0")) {
				this.base = "enemy";
				this.image = new Image("images/object0.png");
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
	 * Purpose: decrease the health of the entity when it is attacked
	 * 
	 * @param damage
	 */
	public void beAttacked(int damage) {
		if (health > damage) {
			health -= damage;
		}else {
			health = 0;
		}
	}
	
	/**
	 * return true when the health is zero
	 * 
	 * @return
	 */
	public boolean isDead() {
		return health == 0;
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
	 * getter of the health
	 * @return
	 */
	public int getHealth() {
		return health;
	}
	
	/**
	 * getter of the attack
	 * @return
	 */
	public int getAttack() {
		return attack;
	}
	
	/**
	 * getter of the price, only tower have the price
	 * @return
	 */
	public int getPrice() {
		if (this.base.equals("tower")) {
			return price;
		}
		return 0;
	}
	
	/**
	 * getter of the speed, only enemy have the speed
	 * @return
	 */
	public int getSpeed() {
		if (this.base.equals("enemy")) {
			return speed;
		}
		return 0;
	}
}
