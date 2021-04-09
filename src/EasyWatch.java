import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thread.SystemThread;

//EasyWatch class
public class EasyWatch extends Application {
	
	//if close the app, threads will stop working in background.
	@Override
	public void stop() throws Exception {
	    super.stop();
	    System.exit(0);
	}

	//start method
    @Override
    public void start(Stage primaryStage) throws Exception
    {
		//create a folder called data which will storage imagens and json data.
    	//sub folders -> image and list.
    	SystemThread.folder.mkdir();
    	new File(SystemThread.folder.getAbsolutePath()+"/image/").mkdir();
    	new File(SystemThread.folder.getAbsolutePath()+"/list/").mkdir();
    	
    	//first screen splash
		Parent root = FXMLLoader.load(getClass().getResource("/view/splash.fxml"));
		Scene scene = new Scene(root);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setTitle("Loading...");
		primaryStage.setResizable(false);
		scene.setFill(Color.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//get the stage to close after
		SystemThread.primaryStage = primaryStage;
		
		//start a thread to get the contents
		new SystemThread().start();
    }


    //main method
    public static void main(String[] args) {
        launch(args);
    }
}
