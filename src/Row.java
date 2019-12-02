import java.util.ArrayList;
import java.util.List;
import model.entity.Entity;
/**
 * Purpose: Rows for a Plants vs Zombies style tower defense.
 * 
 * <pre>
 * Provides methods for maintaining a row in tower defense.
 * 
 * Public Methods:
 *   Row(int cols) - New Row for maintaining row events.
 *   Getters and Setters
 * </pre>
 * 
 * @author Hualiang Qin
 * @author Louis Galluzzi
 * @author Clinton Kral
 * @author John Stockey
 */
public class Row {
	private int cols;
	private List<List<Entity>> entities;
	
	/**
	 * Purpose: New Row for maintaining row events.
	 * 
	 * <pre>
	 * Maintains state on a row in a tower defense game.
	 * </pre>
	 * 
	 * @param cols An int indicating the length of the row.
	 */
	public Row(int cols) {
		this.cols = cols;
		this.entities = new ArrayList<List<Entity>>();
		
		// Build the entities using cols as the length of the inner list
		for (int i = 0; i < cols; i++) {
			this.entities.add(new ArrayList<Entity>());
		}
	}
	
	
	/************************** Private Fields Block ***************************/
	
	
	/************************ Getters and Setters Block ************************/
	
	/**
	 * Getter for cols.
	 * 
	 * @return cols
	 */
	public int getCols() {
		return this.cols;
	}
}
