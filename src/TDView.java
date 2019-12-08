import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sandboxfx.SandboxFX;
import model.entity.*;
import model.*;
import animation.*;
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
	private boolean occupied = false;
	private String towerChoice;
	private Thread newRound;
	
	
	public static int COLMAX = 9;
	public static int ROWMAX = 5;
	public static int gridSize = 150;
	public Scene scene;
	public Stage primaryStage;
	public BorderPane root;
	public StackPane root1;
	public boolean pause = false;
	public boolean doubleSpeed = false;
	 
	/**
	 * Purpose: Main window view.
	 * 
	 * @param primaryStage Stage of the main window to add scenes to.
	 *
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		TDModel model = new TDModel(ROWMAX, COLMAX);
		model.addObserver(this);
		this.controller = new TDController(model);
		
		MenuBar toolbar = new MenuBar();
		Menu stageMenu = new Menu("Stages");
		
		// Add items to each other
		toolbar.getMenus().add(stageMenu);
		
		// Build visuals
		buildMainGridPane();
		buildMenu();
		
		// Add events to stageMenu
		newStageEvents(stageMenu);
		
		// VBox to hold the toolbar and mainGrid
		//HBox root = new HBox(3);
		this.root1 = new StackPane();
		this.root = new BorderPane();
		this.root.setTop(toolbar);
		this.root.setCenter(mainGrid);
		this.root.setLeft(menu);
		this.root1.getChildren().add(root);
		
		// Create the scene
		this.scene = new Scene(root1);
		
		// Setup and show the window
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Zombies Defense");
		
		
		// This code is very simple setup of testing zombie walk animation

//		int y = 60;
//		double speed = 1;
//		String mode = "_walk";
//		String action = "zombie0";
//		int frames = 6;
//		int death = 6;
//		int walk = 8;
//		int attack =7;
//		ArrayList<EntityAnimation> anime = new ArrayList<EntityAnimation>();
//		for(int i = 0; i<10; i++) {
//			if(i%2!=0) {
//				speed = 0.0200;
//
//				y+=150;
//			}else {
//				speed = 0.0200;
//
//			}
//			EntityAnimation tower = new EntityAnimation(this.root1, y, speed, mode, action, frames, death, walk, attack);
//			tower.start();
//			tower.translate();
//			
//			anime.add(tower);
//		}


//		String a = "weapon4";
//		int dif = 600;
//    	Projectile projectile = new Projectile(this.root1, 1, 2, "_attack",a, 8, 1, 500, dif);
//    	projectile.start();
//    	projectile.translate();
		

		this.primaryStage.setScene(this.scene);
		this.primaryStage.show();
		
		//Testing out animation
		
		// Run the game test
		runGame(root1);
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
		
		//ImageView imgView = new ImageView(entity.getImage());
		
		// Add a tower
		if (entity.getBase().equals("tower")) {
			if (((PlacementInfo) target).getDel() == 0) {
				System.out.println("Making image view for entity");
				System.out.println(entity.getType());
					
				// Create a new Node with the Image and place it into the appropriate grid point
				TowerAnimation animation = entity.buildAnimation(this.root1, row, col);
				gridBoard.get(row).get(col).getChildren().add(animation.getPane());
			}
			
			//deletion
			else {
				System.out.println(gridBoard.get(row).get(col).getChildren());
				gridBoard.get(row).get(col).getChildren().remove(2);
				System.out.println(gridBoard.get(row).get(col).getChildren());
			}
			
			// refresh the menu showing how much money is left
			addMenuInfo();
		}
		
		// Add an obstable
		else if (entity.getBase().equals("object")) {
			// Add object
			if (((PlacementInfo) target).getDel() == 0) {
				// Create a new Node with the Image and place it into the appropriate grid point
				ImageView objView = new ImageView(entity.getImage());
				gridBoard.get(row).get(col).getChildren().add(objView);
			}
			
			// Delete object
			else {
				gridBoard.get(row).get(col).getChildren().remove(2);
			}
		}
	}

	
	/************************** Private Fields Block ***************************/
	/**
	 * Purpose: Runs the current game of tower defense.
	 * 
	 * <pre>
	 * 
	 * </pre>
	 * 
	 * @param root A StackPane for placing enemy visuals onto
	 */
	private void runGame(StackPane root) {
		newRound = new Thread(() -> {
			controller.runRound(root, ROWMAX);
		});
		newRound.start();
	}
	
	/**
	 * Purpose: Builds the main grid visual display.
	 * 
	 * <pre>
	 * Creates the List of Lists of StackPanes which hold the ground visual, a 
	 * highlightable tile, and the Entity visual. Events added to the StackPane 
	 * determine tower placeability and define the action for tower placement and 
	 * selling.
	 * </pre>
	 */
	private void buildMainGridPane() {
		int alternate = 0;
		mainGrid = new GridPane();
		gridBoard = new ArrayList<List<StackPane>>();
		int occupied = 1;
		
		// Set the properties of the grid
		mainGrid.setHgap(0);
		mainGrid.setVgap(0);
		mainGrid.setPadding(new Insets(0));
		
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
				highlight.setHeight(gridSize);
				highlight.setWidth(gridSize);
				
				/*
				Rectangle slot2 = new Rectangle();
				slot2.setFill(new ImagePattern(new Image("images/zambie.png")));
				slot2.setOpacity(0);
				slot2.setHeight(30);
				slot2.setWidth(30);
				*/
				
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
		            	} else if (stack.getChildren().size() >= 3 && Event.getButton() == MouseButton.SECONDARY) {
		            		System.out.printf("tower: %s, row: %d, col: %d has been Removed\n", towerChoice, row, col);
		            		controller.removeEntity(towerChoice, row, col);
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
				ground.setHeight(gridSize);
				ground.setWidth(gridSize);
				
				// Stack event to remove placement highlighting
				stack.setOnMouseExited(new EventHandler<MouseEvent>(){
		            @Override
		            public void handle(MouseEvent Event) {
		            	highlight.setOpacity(0);
		            }
				});
				
				// Add the slot to the grid and to the gridBoard
			    //stack.getChildren().addAll(ground, highlight, slot2);
				
				// Add the ground and highlight to the stack
				stack.getChildren().addAll(ground, highlight);
				
				// Add the stack to the mainGrid and the gridBoard
				mainGrid.add(stack, colIndex, rowIndex);
				gridBoard.get(rowIndex).add(stack);
			}
		}
		
		// Disable the grid for initial launches
		mainGrid.setDisable(true);
		
		// Fill in column 0 with the randomized town
		controller.randomizeTownCol0(ROWMAX);
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
				
				// New Rectangle representing a tower choice
				Rectangle choice = new Rectangle();
				
				// Customize the choice
				String towerName = "tower"+tower;
				choice.setFill(new ImagePattern(new Image("images/"+towerName+".png")));
				choice.setStroke(Color.BLACK);
				choice.setHeight(gridSize);
				choice.setWidth(gridSize);
				
				// New Rectangle representing the cover
				Rectangle cover = new Rectangle();
				cover.setFill(Color.DIMGREY);
				cover.setOpacity(0);
				cover.setHeight(gridSize);
				cover.setWidth(gridSize);
				coverList.add(cover);
				
				Rectangle hover = new Rectangle();
				hover.setFill(Color.GREENYELLOW);
				hover.setOpacity(0);
				hover.setHeight(gridSize);
				hover.setWidth(gridSize);
				
				stack.setOnMouseEntered(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent Event) {
						if (mainGrid.isDisable()) {
							hover.setOpacity(0.1);
							Tooltip t = new Tooltip();
							if (towerName.equals("tower0")) {
								t.setText("Meow!\n Cost: 110");
							} else if (towerName.equals("tower1")) {
								t.setText("Sharpshooter!\n Cost: 120");
							} else if (towerName.equals("tower2")) {
								t.setText("Sneaky, Deadly, no Escaping!\n Cost: 210");
							} else if (towerName.equals("tower3")) {
								t.setText("Noble Knight!\n Cost: 245");
							} else if (towerName.equals("tower4")) {
								t.setText("Barbarian!\n Cost: 335");
							} else if (towerName.equals("tower5")) {
								t.setText("YOU SHALL NOT PASS!\n Cost: 90");
							}
							Tooltip.install(hover, t);
						}
						
					}
				});
				
				stack.setOnMouseExited(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent Event) {
						hover.setOpacity(0);
					}
				});
				
				// Menu Choice Event
				stack.setOnMouseClicked(new EventHandler<MouseEvent>(){
		            @Override
		            public void handle(MouseEvent Event) {
		            	if (mainGrid.isDisabled()) {
		            		// Select a new tower
		            		towerChoice = towerName;
		            		
		            		// Show which is selected and allow for placement checks
		            		cover.setFill(Color.DIMGREY);
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
				stack.getChildren().addAll(choice, cover, hover);
				
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
		// Shared background
		Rectangle infoBackground = new Rectangle();
		infoBackground.setFill(Color.LIGHTBLUE);
		infoBackground.setStroke(Color.BLACK);
		infoBackground.setHeight(gridSize);
		infoBackground.setWidth(gridSize);
		
		// Box for currency info
		StackPane currencyBox = new StackPane();
		VBox currencyInfo = new VBox(2);
		Label currency = new Label("Money");
		Label amount = new Label("" + controller.getMoney());
		currencyInfo.setAlignment(Pos.TOP_CENTER);
		
		// Add the currency info together
		currencyInfo.getChildren().addAll(currency, amount);
		
		// Add the Stacks to have backgrounds and then auxiliary information
		currencyBox.getChildren().addAll(infoBackground, currencyInfo);
		
		// Add the box to the menu
		menu.add(currencyBox, 0, 3);
		
		
		// Double speed button
		Circle dsBackground = new Circle();
		dsBackground.setFill(Color.BLUEVIOLET);
		dsBackground.setStroke(Color.BLACK);
		dsBackground.setRadius(gridSize/2-5);
		
		StackPane dsBox = new StackPane();
		VBox dsVbox = new VBox(1);
		Label dsTag = new Label("Double");
		dsTag.setFont(Font.font(32));
		dsVbox.setAlignment(Pos.CENTER);
		dsVbox.getChildren().add(dsTag);
		
		dsBox.getChildren().addAll(dsBackground, dsVbox);
		
		dsBox.setOnMouseClicked(event->{
			if (doubleSpeed) {   // normal speed now
				dsTag.setText("Double");
				dsBackground.setFill(Color.BLUEVIOLET);
				System.out.println("back to normal speed");
//				controller.setSpeed(0.5);  //assume there is setSpeed() method takes in an multiplier
			}else {      // double speed now
				dsTag.setText("Normal");
				dsBackground.setFill(Color.CORNFLOWERBLUE);
				System.out.println("double speed");
//				controller.setSpeed(2);  //assume there is setSpeed() method takes in an multiplier
			}
			doubleSpeed = !doubleSpeed;
		});
		
		menu.add(dsBox, 0, 4);
		
		// Pause button
		Circle pauseBackground = new Circle();
		pauseBackground.setFill(Color.RED);
		pauseBackground.setStroke(Color.BLACK);
		pauseBackground.setRadius(gridSize/2-5);
		
		StackPane pauseBox = new StackPane();
		VBox pVbox = new VBox(1);
		Label tag = new Label("Pause");
		tag.setFont(Font.font(32));
		pVbox.setAlignment(Pos.CENTER);
		pVbox.getChildren().add(tag);
		
		pauseBox.getChildren().addAll(pauseBackground, pVbox);
		
		pauseBox.setOnMouseClicked(event->{
			if (pause) {  // resume now
				tag.setText("Pause");
				pauseBackground.setFill(Color.RED);
				List<Node> towers = menu.getChildren();
				for (int i=0; i<towers.size()-1; i++) {
					Node tower = towers.get(i);
					tower.setDisable(false);
				}
//				controller.resume();   //assume there is resume() method takes in an multiplier
			}else {     // pause now
				tag.setText("Resume");
				pauseBackground.setFill(Color.LIMEGREEN);
				List<Node> towers = menu.getChildren();
				for (int i=0; i<towers.size()-1; i++) {
					Node tower = towers.get(i);
					tower.setDisable(true);
				}
				controller.pause();
			}
			pause = !pause;
			
			
		});
		
		menu.add(pauseBox, 1, 4);
		
	}
	
	/**
	 * Purpose: Add stage creation events to a Stage selector.
	 * 
	 * @param stageMenu A Menu to hold the stage selections.
	 */
	private void newStageEvents(Menu stageMenu) {
		// Create MenuItems for the different stages
		MenuItem stage1 = new MenuItem("Stage 1");
		MenuItem stage2 = new MenuItem("Stage 2");
		MenuItem randomStage = new MenuItem("Randomized Obstacles");
		MenuItem surpriseMode = new MenuItem("Surprise Mode");
		
		// Create Stage 1 on action
		stage1.setOnAction((e) -> {
			if (newRound != null && newRound.isAlive()) {
				// Wait for rounds to finish
				newRoundPrevention();
			} else {
				// Reset the model
				this.controller.reset();
				
				// Fill in column 0 with the randomized town
				controller.randomizeTownCol0(ROWMAX);
			}
		});
		
		// Create Stage 2 on action
		stage2.setOnAction((e) -> {
			if (newRound != null && newRound.isAlive()) {
				// Wait for rounds to finish
				newRoundPrevention();
			} else {
				// Reset the model
				this.controller.reset();
				
				// Fill in column 0 with the randomized town
				controller.randomizeTownCol0(ROWMAX);
				
				// Build stage 2
				System.out.println("Stage 2");
				this.controller.buildStage2();
			}
		});
		
		// Create Random Stage on action
		randomStage.setOnAction((e) -> {
			if (newRound != null && newRound.isAlive()) {
				// Wait for rounds to finish
				newRoundPrevention();
			} else {
				// Reset the model
				this.controller.reset();
				
				// Fill in column 0 with the randomized town
				controller.randomizeTownCol0(ROWMAX);
				
				// Build random stage
				this.controller.buildRandomStage(ROWMAX, COLMAX);
			}
		});
		
		// Create Surprise Stage on action
		surpriseMode.setOnAction((e) -> {
			if (newRound != null && newRound.isAlive()) {
				// Wait for rounds to finish
				newRoundPrevention();
			} else {
				// Reset the model
				this.controller.reset();
				
				// Fill in column 0 with the randomized town
				controller.randomizeTownCol0(ROWMAX);
				
				// Set surprise mode
				//this.controller.setSurpriseMode();
			}
		});
		
		// Add the MenuItem's to the Menu passed in
		stageMenu.getItems().addAll(stage1, stage2, randomStage, surpriseMode);
	}
	
	/**
	 * Purpose: Displays an alert to the user about starting a new round.
	 */
	private void newRoundPrevention() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Hold up");
		alert.setContentText("Wait for the round to end before choosing a new stage");
		alert.showAndWait();
	}
	
}

