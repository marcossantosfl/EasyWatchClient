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
	Label labelUpdate;

	/*
	 * Simple animation in thread.
	 */
	public class ImageAnimation extends Thread {

		boolean executed = false;

		public void run() {
			while (true) {
				if (executed == true) {
					this.interrupt();
					break;
				}
				try {
					Thread.sleep(2000L);

					if (SystemThread.isBeingDownloaded == 1) {
						Platform.runLater(() -> {
							labelUpdate.setText("Image download failed!");
						});
					} else if (SystemThread.isBeingDownloaded == -1) {
						Platform.runLater(() -> {
							labelUpdate.setText("Data download failed!");
						});
					} else if (SystemThread.isBeingDownloaded == 2) {

						Platform.runLater(() -> {
							labelUpdate.setText("Files: " + SystemThread.totalDownload + ": Downloading: "
									+ String.valueOf(SystemThread.downloaded) + "%");
						});

					}
					else if (SystemThread.isBeingDownloaded == 3) {
						SystemThread.isBeingDownloaded = 0;
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
							executed = true;
						});
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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

		labelUpdate.setFont(font.getFontOpenSansRegular(size));
	}
}
