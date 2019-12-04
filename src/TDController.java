import java.util.Observable;

import model.*;
import model.entity.*;
/**
 * Purpose: Controller for tower defense TD.
 * 
 * <pre>
 * Provides simple methods for communicating with TDModel.
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
public class TDController { 
	private TDModel model;   
	private int gameSpeed;
	                            
	/**
	 * Purpose: New controller for updating a model of TD.
	 * 
	 * <pre>
	 * Takes in a TDModel to store and update as the medium for a view.
	 * </pre>
	 * 
	 * @param model A TDModel to operate on.
	 */
	public TDController(TDModel model) {
		this.model = model;
		this.gameSpeed = 1;
	}
	
	/**
	 * Purpose: Place a new Entity into the model at a given row column.
	 * 
	 * <pre>
	 * Takes in the name of an entity to create and passes successfully 
	 * built entities to the model.
	 * </pre>
	 * 
	 * @param name A String of the entity to create.
	 * @param row An int of the row to place it at.
	 * @param col An int of the column to place it at.
	 * 
	 * @return boolean indicating the success status of creating said entity.
	 */
	public boolean placeEntity(String name, int row, int col) {
		// Attempt to create the entity
		Entity entity = new Entity(name);
		
		// Check creation status
		boolean status = entity.getIsValid();
		
		// Successful creations are added to the model
		System.out.println("Checking entity");
		if (status) {
			System.out.println("Adding entity");
			model.addEntity(entity, row, col);
		}
		
		// Return the status
		return status;
	}
	
	/**
	 * Purpose: Runs a round of tower defense.
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @return boolean indicating the success of the round.
	 */
	public boolean runRound(int rows) {
		// List<Queue<Entity>> enemyQueue = buildEnemyQueue(rows);
		// Will need to randomly build zombie queue, for now just 1 zombie.
		model.addEntity(new Entity("enemy0"), 0, 8);
		
		model.nextStep();
		
		return true;
	}
	
	
	/************************** Private Fields Block ***************************/
	
	
	/************************ Getters and Setters Block ************************/
	
	/**
	 * Getter for model.
	 * 
	 * @return TDModel
	 */
	public TDModel getModel() {
		return this.model;
	}
	
	/**
	 * Setter for model.
	 * 
	 * @param model A TDModel representing the state of a tower defense game.
	 */
	public void setModel(TDModel model) {
		this.model = model;
	}
	
	/**
	 * Setter for game speed.
	 * 
	 * @param gameSpeed An int indicating the speed of the rounds.
	 */
	public void setGameSpeed(int gameSpeed) {
		this.gameSpeed = gameSpeed;
	}
}
