import java.util.ArrayList;
import java.util.List;

import com.sun.rowset.internal.Row;

/**
 * Purpose: Model for tower defense TD.
 * 
 * <pre>
 * Stores data representing the state of a tower defense game.
 * 
 * Public Methods:
 *   TDModel(int rows, int cols) - New model for a tower defense game state.
 *   Getters and Setters
 * </pre>
 * 
 * @author Hualiang Qin
 * @author Louis Galluzzi
 * @author Clinton Kral
 * @author John Stockey
 */
public class TDModel {
	private List<Row> rowArray = new ArrayList<Row>();
	/**
	 * Purpose: New model for a tower defense game state.
	 * 
	 * <pre>
	 * Description
	 * </pre>
	 */
	public TDModel() {
		for (int i=0; i<5; i++) {
			Row row = new Row();
			rowArray.add(row);
		}
	}
	
	public TDModel(int n) {
		for (int i=0; i<n; i++) {
			Row row = new Row();
			rowArray.add(row);
		}
	}
	
	public Row getRow(int i) {
		return rowArray.get(i);
	}
}
