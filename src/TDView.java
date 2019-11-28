import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	
	
	private TDController controller;
	private GridPane mainGrid;
	private List<List<Rectangle>> gridBoard; // Index is row column style
	private GridPane menu;
	private int occupied = 0;
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
		
		TDModel model = new TDModel();
		this.controller = new TDController(model);
		
		
		MenuBar toolbar = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem newGame = new MenuItem("New Game");
		
		buildMainGridPane();
		buildMenu();
		
		// Add items to each other
		fileMenu.getItems().add(newGame);
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
		primaryStage.setTitle("Knights vs Zombies");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
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
	
	private void buildMainGridPane() {
		int alternate = 0;
		mainGrid = new GridPane();
		this.gridBoard = new ArrayList<List<Rectangle>>();
		int occupied = 0;
		
		// Set the properties of the grid
		mainGrid.setHgap(0);
		mainGrid.setVgap(0);
		
		
		mainGrid.setPadding(new Insets(0));
		
		// Fill the grid with Circles
		for (int rowIndex = 0; rowIndex < ROWMAX; rowIndex++) {
			// New gridBoard row
			gridBoard.add(new ArrayList<Rectangle>());
			
			for (int colIndex = 0; colIndex < COLMAX; colIndex++) {
				// New circle representing a token slot
			
				Rectangle slot = new Rectangle();
				StackPane stack = new StackPane();
				
				Rectangle slot1 = new Rectangle();
				slot1.setFill(Color.BLUE);
				slot1.setOpacity(0.0);
				slot1.setHeight(80);
				slot1.setWidth(80);
				
//				Rectangle slot2 = new Rectangle();
//				slot2.setFill(new ImagePattern(new Image("zombie.png")));
//				slot2.setOpacity(0.0);
//				slot2.setHeight(80);
//				slot2.setWidth(80);
				
				stack.setOnMouseEntered(new EventHandler<MouseEvent>(){
		            @Override
		            public void handle(MouseEvent Event) {
		            	
		                //slot.setOpacity(0.7);
		                
		                if(occupied == 0) {
		                	slot1.setOpacity(0.5);
		                	slot1.setFill(Color.STEELBLUE);
		                	
		                }else {
		                	slot1.setFill(Color.RED);
		                	slot1.setOpacity(0.5);
		                }
//		                ColorAdjust adjust = new ColorAdjust();
//		                adjust.setBrightness(0.5);
//		                adjust.setHue(0.9);
//		                adjust.setSaturation(0);
		            	
//		            	DropShadow shadow = new DropShadow();
//		            	shadow.setColor(Color.RED);
//		            	shadow.setOffsetX(0);
//		            	shadow.setOffsetY(0);
//		            	shadow.setRadius(5.0);
//		            	Glow adjust = new Glow();
//		                adjust.setLevel(0.8);
		               
		                //slot.setEffect(shadow);
		                
		            }
				});
				Image image;
				if(alternate == 0) {
					image = new Image("Dark grass.png");
					alternate = 1;
				}else {
					image = new Image("light grass.png");
					alternate = 0;
				}
				
				stack.setOnMouseExited(new EventHandler<MouseEvent>(){
		            @Override
		            public void handle(MouseEvent Event) {
		            	slot1.setOpacity(0);
		            	//slot.setStroke(Color.BLACK);
		            	//slot.setEffect(null);
		            	
		            	
		            }
				});
				
				slot.setFill(new ImagePattern(image));
				//slot.setStroke(Color.BLACK);
				slot.setHeight(80);
				slot.setWidth(80);
				
				
				// Add the slot to the grid and to the gridBoard
				
				
			    stack.getChildren().addAll(slot, slot1);
				mainGrid.add(stack, colIndex, rowIndex);
				gridBoard.get(rowIndex).add(slot);
			}
		}
		
		// Disable the grid for initial launches
		mainGrid.setDisable(false);
	}
	
	
	private void buildMenu() {
		
		menu = new GridPane();
		
		
		// Set the properties of the grid
		menu.setHgap(0);
		menu.setVgap(0);
		
		
		menu.setPadding(new Insets(0));
		
		// Fill the grid with Circles
		for (int rowIndex = 0; rowIndex < 5; rowIndex++) {
			// New gridBoard row
			gridBoard.add(new ArrayList<Rectangle>());
			
			for (int colIndex = 0; colIndex < 2; colIndex++) {
				// New circle representing a token slot
				Rectangle slot = new Rectangle();
				slot.setOnMouseClicked(new EventHandler<MouseEvent>(){
			            @Override
			            public void handle(MouseEvent Event) {
			                slot.setFill(Color.PURPLE);
			            }
			    });
				slot.setFill(Color.GREENYELLOW);
				slot.setStroke(Color.BLACK);
				slot.setHeight(80);
				slot.setWidth(80);
				
				// Add the slot to the grid and to the gridBoard
				menu.add(slot, colIndex, rowIndex);
				gridBoard.get(rowIndex).add(slot);
			}
		}
		
		// Disable the grid for initial launches
		menu.setDisable(false);
		
		
	}
	
	/************************** Private Fields Block ***************************/
	
	
	/************************ Getters and Setters Block ************************/
	
}
