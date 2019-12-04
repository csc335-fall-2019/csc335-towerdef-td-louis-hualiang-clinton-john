import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

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
<<<<<<< HEAD
	private TDModel model;   
	private int gameSpeed;
=======
	private int turn;
	private TDModel model;                    
>>>>>>> branch 'master' of https://github.com/csc335-fall-2019/csc335-towerdef-td-louis-hualiang-clinton-john.git
	                            
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
	
	/**
	 * Purpose: add enemies to the five queues and each queue represents one row in
	 * the map. It take the turn as parameter and each queue generate enemies from 
	 * 3 to 5 for first turn, 6 to 8 for second turn, 9 to 11 for third turn
	 * The enemy type is also various from turn to turn. For the first turn, only enemy0
	 * will show up, for second turn, enemy0 and enemy1 are possible. After third turn, all
	 * types of enemy are possible to show up
	 * @param turn indicate the different turn (form 1 to infinite)
	 * @return
	 */
	public List<List<Entity>> queueUpEnemy(int turn){
		List<List<Entity>> troops = new ArrayList<List<Entity>>();
		
		for (int i=0; i<5; i++) {
			List<Entity> queue = new ArrayList<Entity>();
			Random rand = new Random();
			int rand_num = (int)Math.round(rand.nextDouble()*2 + 3*turn); 
			
			while(rand_num>0) {
				int coin_flip = (int)Math.round(rand.nextDouble());
				if (coin_flip == 1) {
					int enemy_turn;
					if (turn < 4) {
						enemy_turn = turn -1;
					}else {
						enemy_turn = 3;
					}
					int rand_enemy = (int)Math.round(rand.nextDouble()*enemy_turn);
					queue.add(new Entity("enemy"+rand_enemy));
				}else {
					queue.add(null);
				}
				rand_num--;
			}
			
			troops.add(queue);
		}
		
		return troops;
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
	
	/**
	 * return the current turn
	 * @return
	 */
	public int getTurn() {
		return turn;
	}

}
