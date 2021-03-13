package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thread.SystemThread;

public class SplashSelectController {
	
	@FXML
	Label labelInfo;

	/*
	 * Simple animation in thread.
	 */
	public class ImageAnimation extends Thread {

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
								root = FXMLLoader.load(getClass().getResource("/view/content.fxml"));
							} catch (IOException e) {
								e.printStackTrace();
							}

							Scene scene1 = new Scene(root);

							Stage quarterStage = new Stage();

							quarterStage.initStyle(StageStyle.TRANSPARENT);
							scene1.setFill(Color.TRANSPARENT);
							quarterStage.setTitle("Content");
							quarterStage.setResizable(false);
							quarterStage.setScene(scene1);
							SystemThread.thirdStage.hide();
							quarterStage.show();
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
		new ImageAnimation().start();
		
		FontController font = new FontController();
		
		int size = 18;
		
		labelInfo.setFont(font.getFontOpenSansRegular(size));
	}
}
