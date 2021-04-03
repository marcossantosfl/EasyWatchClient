package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import thread.SystemThread;

public class PaymentController {

	@FXML
	AnchorPane anchorPane;
	@FXML
	Label checkOut;
	@FXML
	Label cardLabel;
	@FXML
	JFXTextField cardNumber;
	@FXML
	JFXTextField emailAddress;
	@FXML
	JFXButton buttonClose;
	@FXML
	JFXButton  payButton;
	@FXML
	JFXToggleButton emailButton;
	
	//initialize
	@FXML
	protected void initialize() {
		
		
		//load new font
		FontController font = new FontController();
		
		//font size
		int big = 24;
		int medium = 19;
		
		checkOut.setFont(font.getFontOpenSansBold(big));
		cardNumber.setFont(font.getFontOpenSansBold(big));
		cardLabel.setFont(font.getFontOpenSansBold(big));
		emailButton.setFont(font.getFontOpenSansBold(medium));
		payButton.setFont(font.getFontOpenSansBold(big));
		cardNumber.setFont(font.getFontOpenSansBold(big));
		emailAddress.setFont(font.getFontOpenSansBold(medium));
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
