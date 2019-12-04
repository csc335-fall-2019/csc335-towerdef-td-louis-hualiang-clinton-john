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
		setChanged();
		notifyObservers(new PlacementInfo(entity, row, col));
		
		// Return successful placement
		return true;
	}
	
	/**
	 * Purpose: Checks each Entity for their round actions and notifies observers.
	 * 
	 * @return boolean indicating successful checking.
	 */
	public boolean nextStep() {
		// Iterate over row by row
		for (int row = 0; row < grid.size(); row++) {
			List<List<Entity>> rows = grid.get(row);
			// Iterate over column by column
			for (int col = 0; col < rows.size(); col++) {
				List<Entity> column = rows.get(col);
				// Iterate over each Entity
				for (Entity entity : column) {
					// Check entity base type
					if (entity.getBase().equals("enemy")) {
						// entity is an enemy, perform actions
						enemyAction(row, col, entity);
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
	 * @param row An int of the row being checked.
	 * @param col An int of the column being checked.
	 * @param entity An Entity of enemy type.
	 */
	private void enemyAction(int row, int col, Entity entity) {
		// Check the space to the left
		if (col > 0) {
			System.out.printf("row %d, col %d\n", row, col);
			Entity check = grid.get(row).get(col-1).get(0);
			if (check == null) {
				// Walk to the left
				System.out.println("Empty");
				
			}
			else if (check.getBase().equals("tower")) {
				// Do damage to tower
				System.out.print("Attack");
			}
		}
	}

}
