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

public class SplashController {
	
	@FXML
	AnchorPane anchorPane;


	/*
	 * Simple animation in thread.
	 */
	public class Loading extends Thread {

		public void run() {
			boolean executed = false;
			while (true) {

				if (!executed) {
					executed = true;
					try {
						SystemThread.sleep(3000L);

						Platform.runLater(() -> {
							Parent root = null;
							try {
								root = FXMLLoader.load(getClass().getResource("/view/select.fxml"));
							} catch (IOException e) {
								e.printStackTrace();
							}

							Scene scene1 = new Scene(root);

							Stage secondStage = new Stage();

							secondStage.initStyle(StageStyle.TRANSPARENT);
							scene1.setFill(Color.TRANSPARENT);
							secondStage.setTitle("Select");
							secondStage.setResizable(false);
							secondStage.setScene(scene1);
							SystemThread.primaryStage.close();
							secondStage.show();
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

	/*
	 * Initialize everything before the screen itself.
	 */
	@FXML
	protected void initialize() {
		new Loading().start();
	}
}
