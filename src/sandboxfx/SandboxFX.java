package sandboxfx;

import javafx.animation.Animation;
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
        
//        final ImageView imageView2 = new ImageView(IMAGE);
//        imageView2.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        
//        final Animation animation2 = new SpriteAnimation(
//                imageView2,
//                Duration.millis(1000),
//                COUNT, COLUMNS,
//                OFFSET_X, OFFSET_Y,
//                WIDTH, HEIGHT
//        );
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        
//        animation2.setCycleCount(Animation.INDEFINITE);
//        animation2.play();
        
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.add(imageView, 0, 0);
//        pane.add(imageView2, 0, 1);

        primaryStage.setScene(new Scene(new Group(pane)));
        primaryStage.show();
    }
}
