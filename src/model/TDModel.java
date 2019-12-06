package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import model.entity.*;

/**
 * Purpose: Model for tower defense TD.
 * 
 * <pre>
 * Stores data representing the state of a tower defense game.
 * 
 * Public Methods:
 *   TDModel(int rows, int cols, int maxOnOne) - New model for a tower defense game state.
 *   Getters and Setters
 * </pre>
 * 
 * @author Hualiang Qin
 * @author Louis Galluzzi
 * @author Clinton Kral
 * @author John Stockey
 */

public class TDModel extends Observable {
	private int rows;
	private int cols;
	private List<List<List<Entity>>> grid; // Index is row column style
	private int money;

	/**
	 * Purpose: New model for a tower defense game state.
	 * 
	 * <pre>
	 * Holds a row column based List of Lists of Lists of Entities 
	 * to store the game state.
	 * </pre>
	 * 
	 * @param rows An int of the number of rows.
	 * @param cols An int of the number of columns.
	 */

	public TDModel(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.grid = new ArrayList<List<List<Entity>>>();
		this.money = 500;
		
		// Setup the Inner list of lists of entities
		for (int i = 0; i < rows; i++) {
			List<List<Entity>> innerList = new ArrayList<List<Entity>>();
			
			// Setup the inner innet list of entities
			for (int j = 0; j < cols; j++) {
				List<Entity> entityList = new ArrayList<Entity>();
				innerList.add(entityList);
			}
			
			// Add the new list
			this.grid.add(innerList);
		}
	}
	
	/**
	 * Purpose: Add a given Entity into the model at a given row column.
	 * 
	 * <pre>
	 * Takes in an entity and places it into the model at the appropriate 
	 * row and column.
	 * </pre>
	 * 
	 * @param entity An entity to place in the model.
	 * @param row An int of the row to place it at.
	 * @param col An int of the column to place it at.
	 * 
	 * @return boolean indicating the successful placement of the entity.
	 */
	public boolean addEntity(Entity entity, int row, int col) {
		// Fails when out of bounds or entity is null
		if (entity == null || row > rows || col > cols) {
			return false;
		}
		
		// Reached when a valid Entity to store
		// Place the entity and pass it to view to update
		grid.get(row).get(col).add(entity);
		this.money = this.money - entity.getPrice();
		setChanged();
		notifyObservers(new PlacementInfo(entity, row, col, 0));
		
		// Return successful placement
		return true;
	}
	
	public boolean removeEntity(Entity entity, int row, int col) {
		if (entity == null || row > rows || col > cols) {
			return false;
		}
		
		grid.get(row).get(col).remove(entity);
		this.money += (entity.getPrice() - 75);
		
		setChanged();
		notifyObservers(new PlacementInfo(entity, row, col, 1));
		
		return true;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	/**
	 * Purpose: Checks each Entity for their round actions and notifies observers.
	 * 
	 * @return boolean indicating successful checking.
	 */
	public boolean nextStep() {
		// Get a copy of the grid for iteration
		List<List<List<Entity>>> gridCopy = grid;
		
		// Iterate over row by row
		for (int row = 0; row < gridCopy.size(); row++) {
			List<List<Entity>> rows = gridCopy.get(row);
			// Iterate over column by column
			for (int col = 0; col < rows.size(); col++) {
				List<Entity> column = rows.get(col);
				// Iterate over each Entity
				for (int position = 0; position < column.size(); position++) {
					Entity entity = column.get(position);
					
					// Check entity base type
					if (entity.getBase().equals("zombie")) {
						// entity is an enemy, perform actions
						enemyAction(row, col, position, gridCopy);
					} else if (entity.getBase().equals("tower")) {
						// entity is a tower
						// Calculate how many check to the right to perform
						int range = this.cols - col;
						int hits = 1;
						
						// tower3 is melee, so limit range to 1
						if (entity.getType().equals("tower3")) {
							range = 1;
						}
						// tower5 Wizard has two hit penetrations
						else if (entity.getType().equals("tower5")) {
							hits = 2;
						}
						
						// perform the actions
						//towerAction(row, col, position, range, hits, gridCopy);
					}
				}
			}
		}
		return true;
	}
	
	/************************** Private Fields Block ***************************/
	
	/**
	 * Purpose: Performs actions on known enemy entity types.
	 * 
	 * <pre>
	 * Using row, col, and position, live state Entities are obtained from the state 
	 * grid. Towers are dealt damage to, but otherwise the enemy Entity at the row, col, 
	 * and position location will progress to the left.
	 * </pre>
	 * 
	 * @param row An int of the row being checked.
	 * @param col An int of the column being checked.
	 * @param position An int of the Entity's position.
	 * @param gridCopy A List&ltList&ltList&ltEntity&gt&gt&gt of the grid for moving entries.
	 */
	private void enemyAction(int row, int col, int position, List<List<List<Entity>>> gridCopy) {
		// Check the space to the left
		if (col > 0) {
			System.out.printf("row %d, col %d, position %d\n", row, col, position);
			// Left entry has elements to grab
			if (!grid.get(row).get(col-1).isEmpty()) {
				// Get check from real grid
				Entity check = grid.get(row).get(col-1).get(0);
				
				// Non-tower means movement
				if (check == null || !check.getBase().contentEquals("tower")) {
					// Move current enemy to the left
					tryMoveLeft(row, col, position, gridCopy);
				}
				// Previous checks failed so this is a tower
				else {
					// Do damage to tower
					damageTower(row, col, position, gridCopy);
				}
			}
			// Left entry didnt have elements to grab, thus open space
			else {
				// Move current enemy to the left
				tryMoveLeft(row, col, position, gridCopy);
			}
		}
		
		// No spaces left implies end of row
		else {
			// End of row actions
			System.out.println("End of row action");
			Entity removed = gridCopy.get(row).get(col).get(position);
			grid.get(row).get(col).remove(removed);
		}
	}
	
	/**
	 * Purpose: Visually moves entities at a specified location left.
	 * 
	 * <pre>
	 * row, col, and position specify the original Entity to move.
	 * The copy of the state grid is required to prevent iteration from being 
	 * performed correctly due to removing and adding elements.
	 * </pre>
	 * 
	 * @param row An int of the row to move on.
	 * @param col An int of the column to move from.
	 * @param position An int of the Entity's position.
	 * @param gridCopy A List&ltList&ltList&ltEntity&gt&gt&gt of the grid for moving entries.
	 */
	private void tryMoveLeft(int row, int col, int position, List<List<List<Entity>>> gridCopy) {
		// Find the enemy in the copy
		Entity moved = gridCopy.get(row).get(col).get(position);
		
		// Check if the entity is visually moved
		if (moved.getEnemyAnimation().getMove() < 150) {
			System.out.println("Translate");
			// Still need to visually move
			moved.getEnemyAnimation().translate();
		} else {
			// Can now physically move
			System.out.println("Moved left");
			moved.getEnemyAnimation().translate();
			moved.getEnemyAnimation().resetMove();
			
			// Add to the state grid and then remove by object
			grid.get(row).get(col-1).add(moved);
			grid.get(row).get(col).remove(moved);
		}
	}
	
	/**
	 * Purpose: Attacks towers to the left of the row, col, position position.
	 * 
	 * <pre>
	 * row, col, and position specify the original Entity location.
	 * The copy of the state grid is required to prevent iteration from being 
	 * performed correctly due to removing and adding elements, in the event of 
	 * defeated towers.
	 * </pre>
	 * 
	 * @param row An int of the row the enemy is on.
	 * @param col An int of the column the enemy is on.
	 * @param position An int of the enemy's position in its queue.
	 * @param gridCopy A List&ltList&ltList&ltEntity&gt&gt&gt of the grid for moving entries.
	 */
	private void damageTower(int row, int col, int position, List<List<List<Entity>>> gridCopy) {
		System.out.println("Attack");
		
		// Grab the attacker and tower for their state
		Entity attacker = gridCopy.get(row).get(col).get(position);
		Entity tower = gridCopy.get(row).get(col-1).get(0);
		
		// Apply damage
		tower.beAttacked(attacker.getAttack());
		
		// Visual
		if (!attacker.getEnemyAnimation().getMode().equals("_attack")) {
			attacker.getEnemyAnimation().setMode("_attack");
			attacker.getEnemyAnimation().start();
		}
		
		// Check if tower is defeated
		if (tower.isDead()) {
			// Tower is defeated, remove from state grid
			System.out.println("Tower defeated");
			grid.get(row).get(col-1).remove(tower);
			attacker.getEnemyAnimation().setMode("_walk");
			attacker.getEnemyAnimation().start();
			//attacker.getEnemyAnimation().getTranslation().play();
		}
	}
	
	/**
	 * Purpose: Performs actions on known tower entity types.
	 * 
	 * <pre>
	 * Uses range to check for enemies to hit in the row. 
	 * </pre>
	 * 
	 * @param row An int of the row being checked.
	 * @param col An int of the column being checked.
	 * @param position An int of the Entity's position.
	 * @param range An int of the tower's attack range.
	 * @param hits An int of the tower's attack penetration.
	 * @param gridCopy A List&ltList&ltList&ltEntity&gt&gt&gt of the grid for handling entries.
	 */
	private void towerAction(int row, int col, int position, int range, int hits, List<List<List<Entity>>> gridCopy) {	
		// Perform checks based on range and hits penetration
		int shift = 1;
		int hitsLeft = hits;

		System.out.printf("row %d, col %d, position %d\n", row, col, position);
		while (shift <= range && hitsLeft > 0) {
			// Check the spaces to the right
			if (col+shift < this.cols) {
				// Check that right entry has elements to grab
				if (!grid.get(row).get(col+shift).isEmpty()) {
					// Get check from real grid
					for (Entity check : grid.get(row).get(col+shift)) {
						// Attack any zombies
						if (check != null && check.getBase().contentEquals("zombie") && hitsLeft > 0) {
							// Apply tower's damage to the enemy
							System.out.println("Attacking zombie");
							Entity tower = grid.get(row).get(col).get(position);
							damageEnemy(row, col+shift, tower, check, gridCopy);
							hitsLeft--;
							System.out.println(hitsLeft);
						}
					}
				}
			}
			
			// Next right column
			shift++;
		}
	}
	
	/**
	 * Purpose: Attacks enemies using the tower
	 * 
	 * <pre>
	 * 
	 * performed correctly due to removing and adding elements, in the event of 
	 * defeated enemies.
	 * </pre>
	 * 
	 * @param row An int of the row the tower and enemy are on.
	 * @param col An int of the column the enemy is on.
	 * @param position An int of the enemy's position in its queue.
	 * @param gridCopy A List&ltList&ltList&ltEntity&gt&gt&gt of the grid for moving entries.
	 */
	private void damageEnemy(int row, int col, Entity tower, Entity attacker, List<List<List<Entity>>> gridCopy) {
		// Apply damage
		tower.beAttacked(attacker.getAttack());
		
		// Visual
		attacker.getEnemyAnimation();
		attacker.getEnemyAnimation().getTranslation();
		attacker.getEnemyAnimation().getTranslation().pause();
		attacker.getEnemyAnimation().setMode("_attack");
		attacker.getEnemyAnimation().start();
		
		// Check if tower is defeated
		if (tower.isDead()) {
			// Tower is defeated, remove from state grid
			System.out.println("Tower defeated");
			grid.get(row).get(col-1).remove(tower);
			attacker.getEnemyAnimation().setMode("_walk");
			attacker.getEnemyAnimation().start();
			attacker.getEnemyAnimation().getTranslation().play();
		}
	}
}