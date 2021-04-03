package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thread.SystemThread;

//controller
public class SplashController {
	
	//variables to control fxml
	@FXML
	AnchorPane anchorPane;

	//simple splash screen in thread
	public class Loading extends Thread {

		//run method
		public void run() {
			
			//variable to execute once and interrupt the thread.
			boolean executed = false;
			
			while (true) {

				if (!executed) {
					//set as true
					executed = true;
					try {
						//sleep for few second
						SystemThread.sleep(3000L);

						//it has to be Platform otherwise will generate an error (javafx)
						Platform.runLater(() -> {
							Parent root = null;
							//select screen, movies and series
							try {
								root = FXMLLoader.load(getClass().getResource("/view/select.fxml"));
							} catch (IOException e) {
								e.printStackTrace();
							}

							//load select screen
							Scene scene1 = new Scene(root);

							Stage secondStage = new Stage();

							secondStage.initStyle(StageStyle.TRANSPARENT);
							scene1.setFill(Color.TRANSPARENT);
							secondStage.setTitle("Select");
							secondStage.setResizable(false);
							secondStage.setScene(scene1);
							//close the first screen
							SystemThread.primaryStage.close(); 
							//show the second screen
							secondStage.show();
							//save the stage to close after
							SystemThread.secondStage = secondStage;

						});

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				this.interrupt();
			}
		}
	}

	//initialize everything before
	@FXML
	protected void initialize() {
		new Loading().start();
	}
}
