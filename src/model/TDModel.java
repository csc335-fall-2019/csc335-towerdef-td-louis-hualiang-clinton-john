package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import animation.Projectile;
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
	private int turn;
	private int enemyCount;
	private int roundStatus;


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

		this.money = 10000;
		this.turn = 1;
		this.enemyCount = 0;
		this.roundStatus = 0;

		
		// Setup the Inner list of lists of entities
		for (int i = 0; i < rows; i++) {
			List<List<Entity>> innerList = new ArrayList<List<Entity>>();
			
			// Setup the inner inner list of entities
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
		
		// zombie types increment enemy count
		if (entity.getBase().equals("zombie")) {
			this.enemyCount++;
		}
		
		// Return successful placement
		return true;
	}
	
	/**
	 * Purpose: Removes a given Entity from the model at a given row column.
	 * 
	 * <pre>
	 * Takes in an entity and removes it from the model at the appropriate 
	 * row and column.
	 * </pre>
	 * 
	 * @param entity An entity to removed from the model.
	 * @param row An int of the row to remove at.
	 * @param col An int of the column to remove at.
	 * @param isSelling A boolean indicating if selling towers.
	 * 
	 * @return boolean indicating the successful removal of the entity.
	 */
	public boolean removeEntity(Entity entity, int row, int col, boolean isSelling) {
		// Fails when out of bounds or entity is null
		if (entity == null || row > rows || col > cols) {
			return false;
		}
		
		// Remove the entity
		grid.get(row).get(col).remove(entity);
		
		// Give money for selling
		if (isSelling) {
			this.money += (entity.getPrice() - 75);
		}
		
		// Notify observers and return successful
		setChanged();
		notifyObservers(new PlacementInfo(entity, row, col, 1));
		return true;
	}
	
	
	/**
	 * Purpose: Checks each Entity for their round actions and notifies observers.
	 * 
	 * @return int indicating round continuation, loss, or win.
	 */
	public int nextStep() {
		// Boolean for checking if round is loss
		boolean isLoss = false;
		
		// Get a copy of the grid for iteration
		List<List<List<Entity>>> gridCopy = grid;
		// Iterate over row by row
		for (int row = 0; row < gridCopy.size(); row++) {
			List<List<Entity>> rows = gridCopy.get(row);
			// Iterate over column by column starting from leftmost
			for (int col = 0; col < rows.size(); col++) {
				List<Entity> column = rows.get(col);
				// Iterate over each Entity
				for (int position = 0; position < column.size(); position++) {
					Entity entity = column.get(position);
					
					// Check entity base type
					if (entity.getBase().equals("zombie")) {
						// entity is an enemy, perform actions
						boolean roundContinue = enemyAction(row, col, position, gridCopy);
						
						// Update round loss
						if (!roundContinue) {
							isLoss = true;
						}
					} else if (entity.getBase().equals("tower")) {
						// entity is a tower
						// Calculate how many checks to the right to perform (-1 for looking 1 to the right)
						int range = this.cols - col - 1;
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
						towerAction(row, col, position, range, hits, gridCopy);
					}
				}
			}
		}
		
		// Check if the round will continue or is won
		if (isLoss) {
			// Round is lost
			this.roundStatus = -1;
			return -1;
		}
		else if (this.enemyCount == 0) {
			// No more enemies and the round wasn't previously lost
			this.roundStatus = 1;
			return 1;
		} else {
			// Round continues on
			this.roundStatus = 0;
			return 0;
		}
		
		
	}
	
	/**
	 * Purpose: Moves an Entity from one column to the column left of it.
	 * 
	 * <pre>
	 * Updates the state grid by moving the designated entity to the column 1 
	 * space to the left and then removes it from the column it's on.
	 * </pre>
	 * 
	 * @param col An int of the column the entity is on.
	 * @param row An int of the row the entity is on.
	 * @param moved An Entity of the entity to move.
	 */
	public void updateSpot(int col, int row, Entity moved) {
		grid.get(row).get(col-1).add(moved);
		grid.get(row).get(col).remove(moved);
	}
	
	
	public void resume(int col, int row, List<List<List<Entity>>> gridCopy) {
		for(int i = 0; i<gridCopy.get(row).get(col).size()-1; i++ ) {
			if(gridCopy.get(row).get(col).get(i).getBase().equals("zombie") ) {
				gridCopy.get(row).get(col).get(i).getEnemyAnimation().getTranslation().play();
				gridCopy.get(row).get(col).get(i).getEnemyAnimation().setMode("_walk");
				gridCopy.get(row).get(col).get(i).getEnemyAnimation().start();
			}
			
		}
	}
	
	// a test pause method
	public void pause(int col, int row, boolean isPause) {
		for(int i = 0; i<grid.get(row).get(col).size(); i++ ) {
			Entity entity = grid.get(row).get(col).get(i);
			if (isPause) {
				entity.pause(entity.getBase());
			}else {
				entity.resume(entity.getBase());
			}
			
		}
	}
	
	// a test change speed method
	public void changeSpeed(int col, int row, double t) {
		for(int i = 0; i<grid.get(row).get(col).size(); i++ ) {
			Entity entity = grid.get(row).get(col).get(i);
			entity.changeSpeed(t, entity.getBase());
		}
	}
	
	/**
	 * Purpose: clear the last round of grid by removing only enemy and tower entities.
	 * 
	 * @return boolean indicating successful reset.
	 */
	public boolean clearUp() {
		this.enemyCount = 0;
		// Grab a copy of the grid for iteration
		List<List<List<Entity>>> gridCopy = grid;
		// Iterate over the rows
		for (int row = 0; row < gridCopy.size(); row++) {
			List<List<Entity>> rows = gridCopy.get(row);
			
			// Iterate over the columns
			for (int col = 0; col < rows.size(); col++) {
				List<Entity> cols = rows.get(col);
				
				// Iterate over the entities and remove them
				int size = cols.size();
				for (int i = 0; i < size; i++) {
					Entity entity = cols.get(0);
					if (entity.getBase().equals("zombie")) {
						entity.getEnemyAnimation().Delete();
						grid.get(row).get(col).remove(entity);
					}else if (entity.getBase().equals("tower")) {
						Projectile pjtile = entity.getAnimation().getProjectile();
						if (pjtile != null) {
							List<Projectile> listOfPjtile = entity.getAnimation().getPjList();
							for (Projectile pjt: listOfPjtile) {
								pjt.Delete();
							}
						}
						entity.getAnimation().Delete();
						removeEntity(entity, row, col, false);
					}
				}
			}
		} 
		
		// Reached if removal successful
		return true;
	}
	
	
	/**
	 * Purpose: Resets the state of grid by removing entities.
	 * 
	 * @return boolean indicating successful reset.
	 */
	public boolean reset() {
		// Reset status variables
		this.money = 10000;
		this.turn = 1;
		this.enemyCount = 0;
		
		// Grab a copy of the grid for iteration
		List<List<List<Entity>>> gridCopy = grid;
		
		// Iterate over the rows
		for (int row = 0; row < gridCopy.size(); row++) {
			List<List<Entity>> rows = gridCopy.get(row);
			
			// Iterate over the columns
			for (int col = 0; col < rows.size(); col++) {
				List<Entity> cols = rows.get(col);
				
				// Iterate over the entities and remove them
				for (int i = 0; i < cols.size(); i++) {
					Entity entity = cols.get(i);
					this.removeEntity(entity, row, col, false);
				}
			}
		}
		
		// Reached if removal successful
		return true;
	}
	
	/**
	 * Purpose: Increment turn.
	 * 
	 * @return int of the new turn.
	 */
	public int incrTurn() {
		this.turn++;
		return this.turn;
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
	 * 
	 * @return boolean indicating if round continues (was not lost).
	 */
	private boolean enemyAction(int row, int col, int position, List<List<List<Entity>>> gridCopy) {
		// Check the space to the left
		if (col > 0) {
			//System.out.printf("row %d, col %d, position %d\n", row, col, position);
			// Left entry has elements to grab
			if (!grid.get(row).get(col).isEmpty()) {
				// Get check from real grid
				Entity check = grid.get(row).get(col).get(0);
				
				// Non-tower means movement
				if (check == null || !check.getBase().contentEquals("tower")) {
					// Move current enemy to the left
					//tryMoveLeft(row, col, position, gridCopy);
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
				//tryMoveLeft(row, col, position, gridCopy);
				
				resume(row, col, gridCopy);
			}
		}
		
		// No spaces left implies end of row
		else {
			// End of row actions
			System.out.println("End of row action");
			for(int i = 0; i<gridCopy.get(row).get(col).size(); i++ ) {
				if(gridCopy.get(row).get(col).get(i).getBase().equals("zombie") ) {
					Entity removed = gridCopy.get(row).get(col).get(i);
					removed.getEnemyAnimation().getTranslation().pause();
					removed.getEnemyAnimation().Death();
					grid.get(row).get(col).remove(removed);
				}
			}
			
			return false;
		}
		
		// Reached when round was not lost
		return true;

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
		
		if (!moved.getEnemyAnimation().getMode().equals("_walk")) {
			moved.getEnemyAnimation().setMode("_walk");
			moved.getEnemyAnimation().getTranslation().play();
			moved.getEnemyAnimation().start();
		}
		
		// Check if the entity is visually moved
		if (moved.getEnemyAnimation().getMove() < 150) {
			//System.out.println("Translate");
			// Still need to visually move
			//moved.getEnemyAnimation().translate();
		} else {
			// Can now physically move
			//System.out.println("Moved left");
			moved.getEnemyAnimation().minusStart();
			moved.getEnemyAnimation().resetMove();
			//moved.getEnemyAnimation().incrMove();
			//moved.getEnemyAnimation().translate();
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
	 * @param gridCopy A List&ltList&ltList&ltEntity&gt&gt&gt of the grid for getting and moving entries.
	 */
	private void damageTower(int row, int col, int position, List<List<List<Entity>>> gridCopy) {
		//System.out.println("Attack");

		// Grab the attacker and tower for their state
		Entity attacker = gridCopy.get(row).get(col).get(position);
		Entity tower = gridCopy.get(row).get(col).get(0);
		
		// Apply damage
		tower.beAttacked(attacker.getAttack());
		
		// Visual
		if (!attacker.getEnemyAnimation().getMode().equals("_attack")) {
			attacker.getEnemyAnimation().getTranslation().pause();
			attacker.getEnemyAnimation().setMode("_attack");
			attacker.getEnemyAnimation().start();
		}
		
		// Check if tower is defeated
		if (tower.isDead()) {
			// Tower is defeated, remove from state grid
			//System.out.println("Tower defeated");

			grid.get(row).get(col).remove(tower);
			for(int i = 0; i<gridCopy.get(row).get(col).size(); i++ ) {
				if(gridCopy.get(row).get(col).get(i).getBase().equals("zombie") ) {
					//System.out.println(gridCopy.get(row).get(col).get(i).getBase());
					gridCopy.get(row).get(col).get(i).getEnemyAnimation().getTranslation().play();
					gridCopy.get(row).get(col).get(i).getEnemyAnimation().setMode("_walk");
					gridCopy.get(row).get(col).get(i).getEnemyAnimation().start();
				}
				
			}

			removeEntity(tower, row, col, false);

//			attacker.getEnemyAnimation().getTranslation().play();
//
//			attacker.getEnemyAnimation().setMode("_walk");
//			attacker.getEnemyAnimation().start();
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
		int shift = 0;
		int hitsLeft = hits;

		// Loop over the row based on range
		while (shift < range && hitsLeft > 0) {
			// Check the spaces to the right
			if (col+shift < this.cols) {
				// Check that right entry has elements to grab
				if (!grid.get(row).get(col+shift).isEmpty()) {
					// Get check from state grid
					for (int i = 0; i < grid.get(row).get(col+shift).size(); i++) {
						Entity check = grid.get(row).get(col+shift).get(i);
						// Attack any zombies
						if (check != null && check.getBase().equals("zombie") && hitsLeft > 0) {
							// Decrement how many hits one projectile makes
							hitsLeft--;
							
							// Apply tower's damage to the enemy
							Entity tower = gridCopy.get(row).get(col).get(position);
							damageEnemy(row, col+shift, hitsLeft, tower, check);
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
	 * row, col, specify the enemy location for removing on death.
	 * </pre>
	 * 
	 * @param row An int of the row the tower and enemy are on.
	 * @param col An int of the column the enemy is on.
	 * @param hitsLeft An int of how many hits a projectile has left to make.
	 * @param tower An Entity of the tower attacking.
	 * @param enemy An Entity of the enemy being attacked.
	 */
	private void damageEnemy(int row, int col, int hitsLeft, Entity tower, Entity enemy) {
		//System.out.println("Attacking zombie");
		// Apply damage
		enemy.beAttacked(tower.getAttack());

		// Visual
		tower.getAnimation().spawnProjectile(enemy);
		
		// Check if tower is defeated
		if (tower.isDead()) {
			// Tower is defeated, remove from state grid
			System.out.println("Tower defeated");
			grid.get(row).get(col-1).remove(tower);
			tower.getAnimation().Delete();


			// Visual - Projectile spawned when final enemy hit is found
			if (hitsLeft == 0) {
				// Final enemy that the projectile will hit
				tower.fireProjectile(enemy);
				/*
				enemy.getEnemyAnimation();
				enemy.getEnemyAnimation().getTranslation();
				enemy.getEnemyAnimation().getTranslation().pause();
				enemy.getEnemyAnimation().setMode("_attack");
				enemy.getEnemyAnimation().start();
				*/
			}
			
			// Check if enemy is defeated
			if (enemy.isDead()) {
				// Tower is defeated, remove from state grid and set death in animation
				System.out.println("Zombie defeated");
				enemy.getEnemyAnimation().setDeath();
				grid.get(row).get(col).remove(enemy);
				// Visual death will be called in the projectile
				
				// Reward money
				this.money += 50;
				
				// Decrement enemy count
				this.enemyCount--;
			}
		}

	}
	
	public void roundOver(String entity) {
		setChanged();
		notifyObservers(entity);
	}
	
	
	/************************ Getters and Setters Block ************************/
	
	/*
	 * Purpose: Getter for money.
	 * 
	 * @return int indicating current money amount.
	 */
	public int getMoney() {
		return this.money;
	}
	
	/*
	 * Purpose: Getter for rows.
	 * 
	 * @return int indicating number of rows.
	 */
	public int getRows() {
		return rows;
	}
	
	/*
	 * Purpose: Getter for cols.
	 * 
	 * @return int indicating number of columns.
	 */
	public int getCols() {
		return cols;
	}
	
	/*
	 * Purpose: Getter for turn.
	 * 
	 * @return int indicating current turn.
	 */
	public int getTurn() {
		return turn;
	}
	
	/*
	 * Purpose: Getter for roundStatus.
	 * 
	 * @return int indicating a round's status.
	 */
	public int getRoundStatus() {
		return roundStatus;
	}
	
	/**
	 * Purpose: Setter for roundStatus.
	 * 
	 * @param roundStatus An int to set roundStatus.
	 */
	public void setRoundStatus(int roundStatus) {
		this.roundStatus = roundStatus;
	}
	
}