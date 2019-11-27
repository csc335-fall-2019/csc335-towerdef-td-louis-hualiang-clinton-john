/**
 * Purpose: Object for holding placement info of a new Entity into TDModel.
 * 
 * <pre>
 * Stores informaton of an Entity placement into TDModel.
 * 
 * Public Methods:
 *   PlacementInfo(Entity entity, int row, int col) - Information of the Entity created and where it was placed.
 *   Getters and Setters
 * </pre>
 * 
 * @author Hualiang Qin
 * @author Louis Galluzzi
 * @author Clinton Kral
 * @author John Stockey
 */
public class PlacementInfo {
	private Entity entity;
	private int row;
	private int col;
	
	/**
	 * Purpose: Information of the Entity created and where it was placed.
	 * 
	 * <pre>
	 * Creates a new PlacementInfo instance holding information of the Entity 
	 * recently placed, and the row column positioning of where it was placed.
	 * Used in order to update a view.
	 * </pre>
	 * 
	 * @param entity An Entity of the entity placed.
	 * @param row An int of row placed at.
	 * @param col An int of the column placed at.
	 */
	public PlacementInfo(Entity entity, int row, int col) {
		this.entity = entity;
		this.row = row;
		this.col = col;
	}
	
	
	/************************ Getters and Setters Block ************************/
	
	/**
	 * Getter for entity.
	 * 
	 * @return Entity
	 */
	public Entity getEntity() {
		return this.entity;
	}
	
	/**
	 * Getter for row.
	 * 
	 * @return int
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * Getter for col.
	 * 
	 * @return int
	 */
	public int getCol() {
		return this.col;
	}
}
