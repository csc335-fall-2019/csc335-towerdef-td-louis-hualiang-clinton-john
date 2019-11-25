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
		if (this.type.equals("tower1")) {
			// Basic starting tower
			this.base = "tower";
			this.image = new Image("tower1.jpg");
		}
		else if (this.type.equals("tower2")) {
			this.base = "tower";
			this.image = new Image("tower2.jpg");
		}
		
		/****************** Enemy Creation ******************/
		else if (this.type.equals("enemy1")) {
			this.base = "enemy";
			this.image = new Image("enemy1.jpg0");
		}
		
		
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
	 * @return type
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Getter for base.
	 * 
	 * @return base
	 */
	public String getBase() {
		return this.base;
	}
	
	/**
	 * Getter for isValid.
	 * 
	 * @return isValid
	 */
	public boolean getIsValid() {
		return this.isValid;
	}
}
