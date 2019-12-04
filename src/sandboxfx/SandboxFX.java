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
	static String action = "assasin";
    private static final Image IMAGE = new Image("images/" + action + "_attack.png");
    
    
    private static final int COLUMNS  =   9;
    private static final int COUNT    =  6;
    private static final int OFFSET_X =  0;
    private static final int OFFSET_Y =  0;
    private static final int WIDTH    = 90;
    private static final int HEIGHT   = 86;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("The Horse in Motion");

        final ImageView imageView = new ImageView(IMAGE);
        imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));
        
        final ImageView imageView2 = new ImageView(IMAGE);
        imageView2.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
        
<<<<<<< HEAD
        
=======
        final Animation animation2 = new SpriteAnimation(
                imageView2,
                Duration.millis(1000),
                COUNT, COLUMNS,
                OFFSET_X, OFFSET_Y,
                WIDTH, HEIGHT
        );
>>>>>>> parent of 65f8eee... Merge branch 'master' of https://github.com/csc335-fall-2019/csc335-towerdef-td-louis-hualiang-clinton-john.git into jbranch
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
        
        animation2.setCycleCount(Animation.INDEFINITE);
        animation2.play();
        
        GridPane pane = new GridPane();
        pane.setVgap(10);
        pane.setHgap(10);
        pane.add(imageView, 0, 0);
<<<<<<< HEAD
        
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
        Scene scene = new Scene(root, 600, 600);
=======
        pane.add(imageView2, 0, 1);
>>>>>>> parent of 65f8eee... Merge branch 'master' of https://github.com/csc335-fall-2019/csc335-towerdef-td-louis-hualiang-clinton-john.git into jbranch

        primaryStage.setScene(new Scene(new Group(pane)));
        primaryStage.show();
    }
}
