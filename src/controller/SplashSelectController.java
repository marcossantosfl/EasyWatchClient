package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thread.SystemThread;

//controller
public class SplashSelectController {

	//variables to control fxml
	@FXML
	Label labelUpdate;

	//thread to check the download
	public class Download extends Thread {

		//break point
		boolean executed = false;

		public void run() {
			while (true) {
				//break point
				if (executed == true) {
					this.interrupt();
					break;
				}
				try {
					//update
					Thread.sleep(500L);

					//label changed according to the status of download (imagens and json data)
					if (SystemThread.isBeingDownloaded == 1) {
						//change label text
						Platform.runLater(() -> {
							labelUpdate.setText("Image download failed!");
						});
					} else if (SystemThread.isBeingDownloaded == -1) {
						//change label text
						Platform.runLater(() -> {
							labelUpdate.setText("Data download failed!");
						});
					} else if (SystemThread.isBeingDownloaded == 2) {

						//change label text
						Platform.runLater(() -> {
							labelUpdate.setText("Files: " + SystemThread.totalDownload + ": Downloading: "
									+ String.valueOf(SystemThread.downloaded) + "%");
						});

					}
					//final status = concluded
					else if (SystemThread.isBeingDownloaded == 3) {
						//set as default value to not load more than once time the new screen
						SystemThread.isBeingDownloaded = 0;
						Platform.runLater(() -> {
							Parent root = null;
							try {
								root = FXMLLoader.load(getClass().getResource("/view/content.fxml"));
							} catch (IOException e) {
								e.printStackTrace();
							}

							Scene scene1 = new Scene(root);

							Stage fourthStage = new Stage();

							//initialize content screen
							fourthStage.initStyle(StageStyle.TRANSPARENT);
							scene1.setFill(Color.TRANSPARENT);
							fourthStage.setTitle("Content");
							fourthStage.setResizable(false);
							fourthStage.setScene(scene1);
							//close the third screen
							SystemThread.thirdStage.close();
							//show the fourth
							fourthStage.show();
							//break point
							executed = true;
						});
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//initialize
	@FXML
	protected void initialize() {

		//start thread
		new Download().start();

		//load new font
		FontController font = new FontController();

		//font size
		int size = 18;

		//set font to the label
		labelUpdate.setFont(font.getFontOpenSansRegular(size));
	}
}
