import javafx.application.Platform;
import javafx.application.Application;
import javafx.stage.Stage;
import world.Controller;


public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    public void start(final Stage primaryStage) throws Exception {
        Platform.runLater(new Runnable() {

            public void run() {
                try {
                    new Controller(primaryStage).startGame();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}

