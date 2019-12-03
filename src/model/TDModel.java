package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

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
		setChanged();
		notifyObservers(new PlacementInfo(entity, row, col));
		
		// Return successful placement
		return true;
	}
	

}
