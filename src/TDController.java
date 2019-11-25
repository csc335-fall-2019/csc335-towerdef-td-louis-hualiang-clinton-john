
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
	private TDModel model;
	
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
}
