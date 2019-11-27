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
			}
			else if (this.type.equals("tower1")) {
				this.base = "tower";
				this.image = new Image("images/tower1.png");
			}
			else if (this.type.equals("tower2")) {
				this.base = "tower";
				this.image = new Image("images/tower2.png");
			}
			else if (this.type.equals("tower3")) {
				this.base = "tower";
				this.image = new Image("images/tower3.png");
			}
			else if (this.type.equals("tower4")) {
				this.base = "tower";
				this.image = new Image("images/tower4.png");
			}
			else if (this.type.equals("tower5")) {
				this.base = "tower";
				this.image = new Image("images/tower5.png");
			}
		}
		
		/****************** Enemy Creation ******************/
		if (this.type.contains("enemy")) {
			if (this.type.equals("enemy0")) {
				this.base = "enemy";
				this.image = new Image("images/enemy0.png");
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
}
