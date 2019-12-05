import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import animation.EntityAnimation;
import javafx.scene.layout.StackPane;
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
	private int turn;
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
		boolean cost = canAfford(entity);
		
		// Successful creations are added to the model
		System.out.println("Checking entity");
		if (status && cost) {
			System.out.println("Adding entity");
			model.addEntity(entity, row, col);
		} else {
			System.out.println("Can't afford that!");
		}
		
		// Return the status
		return status;
	}
	
	public boolean removeEntity(String name, int row, int col) {
		Entity entity = new Entity(name);
		model.removeEntity(entity, row, col);
		
		return true;
	}
	
	public boolean canAfford(Entity entity) {
		return ((model.getMoney() - entity.getPrice()) >= 0);
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
					queue.add(new Entity("zombie"+rand_enemy));
					rand_num--;
				}else {
					queue.add(null);
				}
			}
			
			troops.add(queue);
		}
		
		return troops;
	}
	
	/**
	 * Purpose: Runs a round of tower defense.
	 * 
	 * @param root A StackPane to place enemy visuals.
	 * @param rows An int indicating the number of rows.
	 * 
	 * @return boolean indicating the success of the round.
	 */
	public boolean runRound(StackPane root, int rows) {
		// List<Queue<Entity>> enemyQueue = buildEnemyQueue(rows);
		// Will need to randomly build zombie queue, for now just 1 zombie.
		System.out.println("Testing round");
		Entity tower = new Entity("tower0");
		model.addEntity(tower, 0, 7);
		
		Entity zom1 = new Entity("zombie0");
		EntityAnimation entityAnimation = zom1.enemyAnimation(root, 0);
		entityAnimation.translate();
		
		
		model.addEntity(zom1, 0, 8);
		//model.addEntity(new Entity("zombie0"), 0, 8);
		
		for (int i = 0; i < 21; i++) {
			model.nextStep();
		}
		
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
	 * return the current turn
	 * @return
	 */
	public int getTurn() {
		return turn;
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
	 * return the amount of money the player currently has
	 * @return
	 */
	public int getMoney() {
		return model.getMoney();
	}
}
