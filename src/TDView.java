import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import sandboxfx.SandboxFX;
import model.entity.*;
import model.*;
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
	
	private  TDController controller;
	private GridPane mainGrid;
	private List<List<StackPane>> gridBoard; // Index is row column style
	private GridPane menu;
	private String towerChoice;
	public static int COLMAX = 9;
	public static int ROWMAX = 5;
	
	/**
	 * Purpose: Main window view.
	 * 
	 * @param primaryStage Stage of the main window to add scenes to.
	 *
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// Create a model and add this as an observer
		TDModel model = new TDModel(ROWMAX, COLMAX);
		model.addObserver(this);
		this.controller = new TDController(model);
		
		// Toolbar for game options
		MenuBar toolbar = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem newGame = new MenuItem("New Game");
		MenuItem supriseMode = new MenuItem("Suprised Mode");
		
		// Setup the grid visuals
		buildMainGridPane();
		buildMenu();
		
		// Add items to each other
		fileMenu.getItems().addAll(newGame, supriseMode);
		toolbar.getMenus().add(fileMenu);
		
		// Add event to newGame
		//newGameEvent(newGame);
		
		// VBox to hold the toolbar and mainGrid
		//HBox root = new HBox(3);
		BorderPane root = new BorderPane();
		root.setTop(toolbar);
		root.setCenter(mainGrid);
		root.setLeft(menu);
		
		// Create the scene
		Scene scene = new Scene(root);
		
		// Setup and show the window
		primaryStage.setTitle("Zombies Defense");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// Run the game
		runGame();

	}
	
	/**
	 * Purpose: Part of the MVC setup to update according to model.
	 * 
	 * @param model Observable being observed.
	 * @param target Object depicting the change to update in response to.
	 */
	@Override
	public void update(Observable model, Object target) {
		// Take out the information passed through target
		Entity entity = ((PlacementInfo) target).getEntity();
		int row = ((PlacementInfo) target).getRow();
		int col = ((PlacementInfo) target).getCol();

		System.out.println("Making image view for entity");
		
		// Create a new Node with the Image and place it into the appropriate grid point
		ImageView imgView = new ImageView(entity.getImage());
		gridBoard.get(row).get(col).getChildren().add(imgView);
	}

	
	/************************** Private Fields Block ***************************/
	/**
	 * Purpose: Runs the current game of tower defense.
	 * 
	 * <pre>
	 * 
	 * </pre>
	 */
	private void runGame() {
		controller.runRound(ROWMAX);
	}
	
	
	// TODO
	private void buildMainGridPane() {
		int alternate = 0;
		mainGrid = new GridPane();
		gridBoard = new ArrayList<List<StackPane>>();
		
		// Fill the grid
		for (int rowIndex = 0; rowIndex < ROWMAX; rowIndex++) {
			// New gridBoard row
			gridBoard.add(new ArrayList<StackPane>());
			
			for (int colIndex = 0; colIndex < COLMAX; colIndex++) {
				// New Rectangle for the main grid
				Rectangle ground = new Rectangle();
				StackPane stack = new StackPane();
				
				// New Rectangle for showing placement validity
				Rectangle highlight = new Rectangle();
				highlight.setFill(Color.BLUE);
				highlight.setOpacity(0.0);
				highlight.setHeight(80);
				highlight.setWidth(80);
				
				SandboxFX slot3 = new SandboxFX();
				
				// Stack event to highlight grid placement validity
				stack.setOnMouseEntered(new EventHandler<MouseEvent>(){
		            @Override
		            public void handle(MouseEvent Event) {

		            	// Valid placement check
		                if(stack.getChildren().size() < 3) {
		                	// Valid placement
		                	highlight.setFill(Color.STEELBLUE);
		                	highlight.setOpacity(0.5);
		                } else {
		                	// Invalid placement
		                	highlight.setFill(Color.RED);
		                	highlight.setOpacity(0.3);

		                }
		            }
				});
				
				// Capture row and column for use in the lambda
				int row = rowIndex;
				int col = colIndex;
				
				// Stack event to place a designated tower on valid grids
				stack.setOnMouseClicked(new EventHandler<MouseEvent>(){
		            @Override
		            public void handle(MouseEvent Event) {
		            	// Valid placement check
		            	if (stack.getChildren().size() < 3) {
		            		System.out.printf("tower: %s, row: %d, col: %d\n", towerChoice, row, col);
		            		controller.placeEntity(towerChoice, row, col);
		            	}
		                //slot2.setOpacity(1);
		                
		            }
				});
				
				// Alternate grid image background
				Image image;
				if (alternate == 0) {
					image = new Image("images/Dark grass.png");
					alternate = 1;
				} else {
					image = new Image("images/light grass.png");
					alternate = 0;
				}
				
				ground.setFill(new ImagePattern(image));
				ground.setHeight(80);
				ground.setWidth(80);
				
				// Stack event to remove placement highlighting
				stack.setOnMouseExited(new EventHandler<MouseEvent>(){
		            @Override
		            public void handle(MouseEvent Event) {
		            	highlight.setOpacity(0);
		            }
				});
				
				// Add the ground and highlight to the stack
				stack.getChildren().addAll(ground, highlight);
				
				// Add the stack to the mainGrid and the gridBoard
				mainGrid.add(stack, colIndex, rowIndex);
				gridBoard.get(rowIndex).add(stack);
			}
		}
		
		// Disable the grid for initial launches
		mainGrid.setDisable(true);
	}
	
	/**
	 * Purpose: Builds the left menu to display tower choices and currency info.
	 */
	private void buildMenu() {
		menu = new GridPane();
		
		// Set the properties of the grid
		menu.setHgap(0);
		menu.setVgap(0);
		menu.setPadding(new Insets(0));
		
		// Fill the towers and their events
		addMenuTowers();
		
		// Fill the bottom two menu info boxes
		addMenuInfo();
		
		// Enable grid if disabled
		menu.setDisable(false);
	}
	
	
	/**
	 * Purpose: Adds in the Towers to the menu slots.
	 */
	private void addMenuTowers() {
		// Counter for tower creation
		int tower = 0;
		
		// List for tower event management
		List<Rectangle> coverList = new ArrayList<>();
		
		// 3 rows of 2 per row, so 6 towers total
		for (int rowIndex = 0; rowIndex < 3; rowIndex++) {
			for (int colIndex = 0; colIndex < 2; colIndex++) {
				// New Stack to hold the image and a cover to highlight
				StackPane stack = new StackPane();
				
				// New ImageView representing a tower choice
				// Customize the choice
				String towerName = "tower"+tower;
				ImageView choice = new ImageView(new Image("images/"+towerName+".png"));
				
				// New Rectangle representing the cover
				Rectangle cover = new Rectangle();
				cover.setFill(Color.DIMGREY);
				cover.setOpacity(0);
				cover.setHeight(80);
				cover.setWidth(80);
				coverList.add(cover);
				
				// Menu Choice Event
				stack.setOnMouseClicked(new EventHandler<MouseEvent>(){
		            @Override
		            public void handle(MouseEvent Event) {
		            	if (mainGrid.isDisabled()) {
		            		// Select a new tower
		            		towerChoice = towerName;
		            		
		            		// Show which is selected and allow for placement checks
		            		cover.setOpacity(0.5);
		            		mainGrid.setDisable(false);
		            		
		            	} else {
		            		// Prevent more than one visible cover
		            		for (Rectangle storedCover : coverList) {
		            			storedCover.setOpacity(0);
		            		}
		            		
		            		// Disable placement
		            		mainGrid.setDisable(true);
		            	}
		            }
				});
				
				// Add the rectangles to the stack
				stack.getChildren().addAll(choice, cover);
				
				// Add the slot to the menu
				menu.add(stack, colIndex, rowIndex);
				
				// Increment the tower tracker
				tower++;
			}
		}
	}
	
	/**
	 * Purpose: Adds in the info boxes to the menu slots
	 */
	private void addMenuInfo() {
		// Box for currency info
		VBox currencyInfo = new VBox(2);
		Label currency = new Label("Money");
		Label amount = new Label("0");
		currencyInfo.setAlignment(Pos.TOP_CENTER);
		
		// Add the currency info together
		currencyInfo.getChildren().addAll(currency, amount);
		
		// Add the currency info to the menu
		menu.add(currencyInfo, 0, 4);
	}
	
}
