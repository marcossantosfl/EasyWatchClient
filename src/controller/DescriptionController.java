package controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import thread.SystemThread;

public class DescriptionController { 
	
	@FXML
	AnchorPane anchorPane;
	@FXML
	Label titleLabel;
	@FXML
	ImageView  imagePopUp;
	@FXML
	JFXTextArea textPopUp;
	@FXML
	JFXButton buttonClose;
	
	public class Update extends Thread {

		//break point
		boolean executed = false;

		public void run() {
			while (true) {
				
					try {
						Thread.sleep(1L);
						Platform.runLater(() -> {
						titleLabel.setText(SystemThread.titleDescription);
						textPopUp.setText(SystemThread.resume);	
						imagePopUp.setImage(SystemThread.imagePopup);
						});
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}
	
	//initialize
	@FXML
	protected void initialize() {
		
		new Update().start();
		
		//load new font
		FontController font = new FontController();
		
		//font size
		int big = 24;
		int medium = 19;

		titleLabel.setText("");
		titleLabel.setFont(font.getFontOpenSansBold(big));
		textPopUp.setFont(font.getFontOpenSansBold(medium));
		buttonClose.setFont(font.getFontOpenSansBold(big));
		buttonClose.setFocusTraversable(false);
  }
	
	//close button
	@FXML
	protected void handleCloseAction(ActionEvent event) {
		SystemThread.fourthStage.hide();
		SystemThread.anchorPaneBlur.setEffect(null);
	}
}
