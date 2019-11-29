package sandboxfx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

// I am trying to make the bullet of tower works, but it seems like
// there are two problems: 1. I used canvas while the TDView don't
// 2. the animation can go infinitely.
public class BulletAnimation extends Application{
	private static final int BULLET_SIZE = 5;
	private static final int MILLISECONDS = 5;
	private static final int BULLET_VELOCITY = 5;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		
		Group root = new Group();
		Canvas canvas = new Canvas(400, 400);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		
		
		
		canvas.setOnMouseClicked((event)->{
			gc.setFill(Color.BLACK);
			KeyFrame key = new KeyFrame(Duration.millis(MILLISECONDS), new Animation(event.getY(), gc));
			Timeline t = new Timeline(key);
			t.setCycleCount(Timeline.INDEFINITE);
			t.play();
				
		});
		
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		
	}
	
	private class Animation implements EventHandler<ActionEvent> {
		private double xVal;
		private double yVal;
		private GraphicsContext gc;
		
		public Animation(double startingY, GraphicsContext context) {
			this.yVal = startingY;
			this.xVal = 0 + BULLET_SIZE ;
			this.gc = context;
			System.out.println(yVal);
		}
		
		public void handle(ActionEvent e) {
			this.gc.setFill(Color.BLACK);
			if (xVal < 400) {
				gc.clearRect(this.xVal - BULLET_SIZE, this.yVal, BULLET_SIZE, BULLET_SIZE);
				xVal += BULLET_VELOCITY;
				gc.fillRect(this.xVal - BULLET_SIZE, this.yVal, BULLET_SIZE, BULLET_SIZE);
			} else {
				gc.clearRect(this.xVal - BULLET_SIZE, this.yVal, BULLET_SIZE, BULLET_SIZE);
			}
		}

	}


}
