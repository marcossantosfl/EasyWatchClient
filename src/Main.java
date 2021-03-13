import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thread.SystemThread;

public class Main extends Application {
	
	@Override
	public void stop() throws Exception {
	    super.stop();
	    System.exit(0);
	}

    @Override
    public void start(Stage primaryStage) throws Exception
    {
		/*
		 * Splash screen loading.
		 */
    	
		Parent root = FXMLLoader.load(getClass().getResource("/view/splash.fxml"));
		Scene scene = new Scene(root);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setTitle("Loading...");
		primaryStage.setResizable(false);
		scene.setFill(Color.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		/*
		 * Get the object to hide after loading.
		 */
		SystemThread.primaryStage = primaryStage;
		
		new SystemThread().start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}