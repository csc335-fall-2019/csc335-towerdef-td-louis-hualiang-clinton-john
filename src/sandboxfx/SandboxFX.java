package sandboxfx;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SandboxFX extends Application {

    private static final Image IMAGE = new Image("images/zombie_walk.jpg");
    
    private static final int COLUMNS  =   4;
    private static final int COUNT    =  16;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 55;
    private static final int HEIGHT   = 58;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Horse in Motion");

        final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        

        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        
        
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.add(imageView, 0, 0);
        
        // move the zombie from right to left
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(2000));
        translateTransition.setNode(pane);
        translateTransition.setFromX(540);
        translateTransition.setToX(0);
        translateTransition.setDuration(Duration.seconds(25));
        translateTransition.play();
        
        // A Group object has no layout of children easier to use here
        Group root = new Group(pane);
        Scene scene = new Scene(root, 600, 70);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
