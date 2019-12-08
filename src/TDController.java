import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.TimerTask;

import animation.EntityAnimation;
import javafx.application.Platform;
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
		model.removeEntity(entity, row, col, true);
		
		return true;
	}
	
	public boolean canAfford(Entity entity) {
		return ((model.getMoney() - entity.getPrice()) >= 0);
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
	
	public boolean buildStage2() {
		model.addEntity(new Entity("object0"), 0, 7);
		model.addEntity(new Entity("object0"), 1, 7);
		model.addEntity(new Entity("object0"), 1, 6);
		model.addEntity(new Entity("object1"), 3, 7);
		model.addEntity(new Entity("object1"), 4, 8);
		model.addEntity(new Entity("object1"), 2, 8);
		model.addEntity(new Entity("object2"), 3, 8);
		model.addEntity(new Entity("object2"), 2, 7);
		model.addEntity(new Entity("object3"), 0, 6);
		
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
		// List<Queue<Entity>> enemyQueue = buildEnemyQueue(rows);
		// Will need to randomly build zombie queue, for now just 1 zombie.
		Platform.runLater(() -> {
			System.out.println("Testing round");
			Entity tower = new Entity("tower0");
			Entity tower1 = new Entity("tower0");
			Entity tower2 = new Entity("tower0");
			Entity tower3 = new Entity("tower0");
			Entity tower4 = new Entity("tower0");
			Entity tower5 = new Entity("tower0");
			//model.addEntity(tower2, 0, 2);
			//model.addEntity(tower1, 0, 3);
			//model.addEntity(tower4, 0, 4);
			model.addEntity(tower, 0, 5);
			//model.addEntity(tower2, 3, 7);
			model.addEntity(tower3, 3, 3);
			//model.addEntity(tower5, 0, 1);
			
			Entity zom1 = new Entity("zombie0");
			EntityAnimation entityAnimation = zom1.enemyAnimation(root, 0);
			entityAnimation.translate();
			
			
			model.addEntity(zom1, 0, 8);
			
			
			Entity zom2 = new Entity("zombie2");
			EntityAnimation entityAnimation2 = zom2.enemyAnimation(root, 3);
			entityAnimation2.translate();
			//entityAnimation2.incrMove();
			
			model.addEntity(zom2, 3, 8);
			
		});
		
		for (int i = 0; i < 100; i++) {
			Platform.runLater(() -> {
				model.nextStep();
			});
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
