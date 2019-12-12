import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import animation.EntityAnimation;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import model.*;
import model.entity.*;
/**
 * Purpose: Controller for tower defense TD.
 * 
 * <pre>
 * Provides methods for communicating with TDModel.
 * 
 * Public Methods:
 *   TDController(TDModel model) - New controller for updating a model of TD.
 *   placeEntity(String name, int row, int col) - Place a new Entity into the model at a given row column.
 *   removeEntity(String name, int row, int col) - Remove an Entity from the model at a given row column.
 *   buildStage2 - Builds a preset stage 2 object layout.
 *   buildStage3(int row) - Builds a preset stage 3 object layout.
 *   buildRandomStage(int row, int col) - Builds a random stage object layout.
 *   runRound(StackPane root, int rows) - Runs a round of tower defense.
 *   pause(boolean isPause) - Pauses and resumes round progression.
 *   changeSpeed(double t) - Adjusts game speed.
 *   randomizeTownCol0(int rows) - Places town objects on column 0 randomly.
 *   randomizeGravesColEnd(int rows, int cols) - Places grave objects at the ending column.
 *   reset() - Resets the model's state.
 *   enemiesInQueue(List<List<Entity>> queue) - Determines if there are still enemeies in the queue.
 *   queueUpEnemy(int turn) - Builds the enemies to send down each row over a round.
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
	private int gameSpeed = 1;
	private boolean pause = false;
	private int currentStep;

	                            
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
		this.pause = false;
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
		Entity entity = new Entity(name, this.model);
		
		// Check creation status
		boolean status = entity.getIsValid();
		boolean cost = canAfford(entity);
		
		// Successful creations are added to the model
		if (status && cost) {
			model.addEntity(entity, row, col);
		} else {
			System.out.println("Can't afford that!");
		}
		
		// Return the status
		return status;
	}
	
	/**
	 * Purpose: Remove an Entity from the model at a given row column.
	 * 
	 * @param name A String of the entity to remove.
	 * @param row An int of the row to remove it from.
	 * @param col An int of the column to remove it from.
	 * 
	 * @return boolean indicating the success status of removing said entity.
	 */
	public boolean removeEntity(String name, int row, int col) {
		Entity entity = new Entity(name, this.model);
		model.removeEntity(entity, row, col, true);
		
		return true;
	}
	
	/**
	 * Purpose: Builds a preset stage 2 object layout.
	 * 
	 * @return boolean indicating successful stage creation.
	 */
	public boolean buildStage2() {
		model.addEntity(new Entity("object0", this.model), 0, 7);
		model.addEntity(new Entity("object0", this.model), 1, 7);
		model.addEntity(new Entity("object0", this.model), 1, 6);
		model.addEntity(new Entity("object2", this.model), 2, 3);
		model.addEntity(new Entity("object2", this.model), 1, 3);
		model.addEntity(new Entity("object2", this.model), 4, 3);
		model.addEntity(new Entity("object1", this.model), 3, 7);
		model.addEntity(new Entity("object1", this.model), 2, 7);
		model.addEntity(new Entity("object3", this.model), 2, 4);

		// Reached when successful layout creation.
		return true;
	}
	
	/**
	 * Purpose: Builds a preset stage 3 object layout.
	 * 
	 * @param row An int of the number of rows.
	 * 
	 * @return boolean indicating successful stage creation.
	 */
	public boolean buildStage3(int row) {
		// Layout broken barricades and path
		for (int i = 0; i < row; i++) {
			model.addEntity(new Entity("object9", this.model), i, 3);
			model.addEntity(new Entity("object2", this.model), i, 3);
		}
		
		// Layout buildings
		model.addEntity(new Entity("object5", this.model), 4, 1);
		model.addEntity(new Entity("object6", this.model), 0, 1);
		model.addEntity(new Entity("object7", this.model), 3, 2);
		model.addEntity(new Entity("object7", this.model), 1, 2);

		// Reached when successful layout creation.
		return true;
	}
	
	/**
	 * Purpose: Builds a random stage object layout.
	 * 
	 * @param row An int of the number of rows.
	 * @param col An int of the number of cols.
	 * 
	 * @return boolean indicating successful stage creation.
	 */
	public boolean buildRandomStage(int row, int col) {
		Random rand = new Random();
		
		//15% chance of spawning an object in between (0,1) - (8,8)
		for (int i = 0; i < row; i++) {
			for (int j = 1; j < col-1; j++) {
				if (rand.nextInt(100) <= 15) {
					//selects random object
					model.addEntity(new Entity("object"+rand.nextInt(2), this.model), i, j);
				}
				else if (rand.nextInt(100) > 50 && rand.nextInt(15) >= 14) {
					model.addEntity(new Entity("object3", this.model), i, j);
				}
			}
		}
		
		// Reached when successful layout creation.
		return true;
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
		pause = false;
		pause(pause);
		// Build a randomized queue
		List<List<Entity>> enemyQueue = queueUpEnemy(model.getTurn());
		// Set model's round status
		model.setRoundStatus(0);
		this.currentStep = 0;
		model.enemyCount=0;
		
		// Loop over placing from the queue and progressing round, until round ends
		boolean roundOver = false;
		while (!roundOver) {
			if (!this.pause) {
				Platform.runLater(() -> {
					// Progress through the queue every n steps
					if (currentStep % 3 == 0) {
						// Progressing through the queue, place entities when they appear in queue
						for (int currRow = 0; currRow < rows; currRow++) {
							if (pause) { return;}   //pause the loop if it is already in the loop
							// Guard against empty queues
							if (enemyQueue.get(currRow).size() > 0) {
								Entity zom = enemyQueue.get(currRow).remove(0);
								
								// Check if anything to place
								if (zom != null) {
									EntityAnimation entityAnimation = zom.enemyAnimation(root, currRow, 8, zom);
									entityAnimation.translate();
									
									// Place zombie at end of current row
									model.addEntity(zom, currRow, 8);
								}
							}
						}
					}
					
					// Perform the model progression		
					model.nextStep();
					changeSpeed(gameSpeed);   // Adjust its speed to match the current speed
					currentStep++;
				});
				
				// Check if round was lost
				if (model.getRoundStatus() == -1) {
					roundOver = true;
					Platform.runLater(()-> {
						pause(true);
						model.roundOver("zombies");
						model.clearUp();
					});
				}
				
				// Check if round was won
				else if (model.getRoundStatus() == 1 && !enemiesInQueue(enemyQueue)) {
					roundOver = true;
					Platform.runLater(()->{
						pause(true);
						model.roundOver("player");
					});
				}
			}	
			// Sleep the thread, interrupts return false
			try {
				Thread.sleep(1000/this.gameSpeed);
			} catch (InterruptedException e) {
				// Interrupt indicates stop thread
				return false;
			}
		}
		// Reached when the round finishes
		model.incrTurn();
		return true;
	}
	
	/**
	 * Purpose: Pauses and resumes round progression.
	 * 
	 * @param isPause A boolean indicating if pausing.
	 */
	public void pause(boolean isPause) {
		// Iterate over row by row
		for (int row = 0; row < model.getRows(); row++) {
			// Iterate over column by column starting from leftmost
			for (int col = 0; col < model.getCols(); col++) {
				// Iterate over each Entity
				if(isPause) {
					model.pause(col, row, true);
				}else {
					model.pause(col, row, false);
				}
			}
		}
		pause = isPause;
	}
	
	/**
	 * Purpose: Adjusts game speed.
	 * 
	 * @param t A double indicating the speed to set to.
	 */
	public void changeSpeed(double t) {
		// Iterate over row by row
		for (int row = 0; row < model.getRows(); row++) {
			// Iterate over column by column starting from leftmost
			for (int col = 0; col < model.getCols(); col++) {
				// Iterate over each Entity
				model.changeSpeed(col, row, t);
			}
		}
		this.gameSpeed = (int) t;
	}

	/**
	 * Purpose: Places town objects on column 0 randomly.
	 * 
	 * <pre>
	 * Column 0 defines losing the game, so the visual incentive is a 
	 * town which is being defended by the knights during the rounds.
	 * Thus when a zombie reaches the town, the game is lost.
	 * </pre>
	 * 
	 * @param rows An int of the number of rows.
	 * 
	 * @return boolean indicating the successful placement of the objects.
	 */
	public boolean randomizeTownCol0(int rows) {
		// Random generator
		Random rand = new Random();
		
		// Loop over the rows at column 0
		for (int i = 0; i < rows; i++) {
			model.addEntity(new Entity("object"+(rand.nextInt(4)+4), this.model), i, 0);
		}
		
		// Returns true if all objects were successfully placed.
		return true;
	}
	
	/**
	 * Purpose: Places grave objects at the ending column.
	 * 
	 * <pre>
	 * Rightmost column is for spawning enemies, so place grave markers 
	 * to indicate their spawn point visually.
	 * </pre>
	 * 
	 * @param rows An int of the number of rows.
	 * 
	 * @return boolean indicating the successful placement of the objects.
	 */
	public boolean randomizeGravesColEnd(int rows, int cols) {
		// Random generator
		Random rand = new Random();
		
		// Loop over the rows at rightmost column
		for (int i = 0; i < rows; i++) {
			model.addEntity(new Entity("object"+(rand.nextInt(3)+12), this.model), i, cols-1);
		}
		
		// Returns true if all objects were successfully placed.
		return true;
	}
	
	/**
	 * Purpose: Resets the model's state.
	 * 
	 * @return boolean indicating successful reset of model's state.
	 */
	public boolean reset() {
		// Have model reset itself
		return model.reset();
	}
	
	/**
	 * Purpose: Checks if there's enough money to buy a specific tower.
	 * 
	 * @param entity An Entity of the tower attempting to be bought.
	 * 
	 * @return boolean indicating if the state can afford the tower.
	 */
	private boolean canAfford(Entity entity) {
		return ((model.getMoney() - entity.getPrice()) >= 0);
	}
	
	/**
	 * Purpose: Determines if there are still enemeies in the queue.
	 * 
	 * @param queue A List&ltList&ltEntity&gt&gt holding the enemies to queue.
	 * 
	 * @return boolean indicating if enemeies are still in queue.
	 */
	public boolean enemiesInQueue(List<List<Entity>> queue) {
		// Iterate over the Lists in the queue
		for (List<Entity> lineUp : queue) {
			// If even one line contains items, then the queue is not empty
			if (!lineUp.isEmpty()) {
				return true;
			}
		}
		
		// Reached if all lists in queue were empty
		return false;
	}
	
	/**
	 * Purpose: Builds the enemies to send down each row over a round.
	 * 
	 * <pre>
	 * Add enemies to the five queues and each queue represents one row in
	 * the map. It take the turn as parameter and each queue generate enemies from 
	 * 3 to 5 for first turn, 6 to 8 for second turn, 9 to 11 for third turn
	 * The enemy type is also various from turn to turn. For the first turn, only enemy0
	 * will show up, for second turn, enemy0 and enemy1 are possible. After third turn, all
	 * types of enemy are possible to show up
	 * </pre>
	 * 
	 * @param turn An int indicating the different turn (from 1 to infinite).
	 * 
	 * @return List&ltList&ltEntity&gt&gt the row and entity round queue.
	 */
	public List<List<Entity>> queueUpEnemy(int turn) {
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
					queue.add(new Entity("zombie"+rand_enemy, this.model));
					rand_num--;
				}else {
					queue.add(null);
				}
			}
			
			troops.add(queue);
		}
		
		return troops;
	}
	
	/************************ Getters and Setters Block ************************/
	/**
	 * Getter for model.
	 * 
	 * @return TDModel of the paired model.
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
	 * Getter for money.
	 * 
	 * @return int indicating the amount of money the player has.
	 */
	public int getMoney() {
		return model.getMoney();
	}

}
