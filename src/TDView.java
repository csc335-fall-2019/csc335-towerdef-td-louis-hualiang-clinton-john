import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Purpose: GUI window visual for tower defense.
 * 
 * <pre>
 * Uses JavaFX to run a visual game of tower defense.
 * 
 * Public Methods:
 *   start(Stage primaryStage) - Main window view.
 *   update(Observable model, Object target) - Part of the MVC setup to update according to model.
 *   Getters and Setters
 * </pre>
 * 
 * 
 * @author Hualiang Qin
 * @author Louis Galluzzi
 * @author Clinton Kral
 * @author John Stockey
 */
public class TDView extends Application implements Observer {
	/**
	 * Purpose: Main window view.
	 * 
	 * @param primaryStage Stage of the main window to add scenes to.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO 
	}
	
	/**
	 * Purpose: Part of the MVC setup to update according to model.
	 * 
	 * @param model Observable being observed.
	 * @param target Object depicting the change to update in response to.
	 */
	@Override
	public void update(Observable model, Object target) {
		// TODO
	}
	
	
	/************************** Private Fields Block ***************************/
	
	
	/************************ Getters and Setters Block ************************/
	
}
