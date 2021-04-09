package controller;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import thread.SystemThread;

public class PaymentProcessingResultController {
	@FXML
	AnchorPane anchorPane;

	@FXML
	JFXButton buttonClose;

	@FXML
	Label labelCheckout;

	@FXML
	Label labePaymentResult;

	@FXML
	protected void initialize() {

		// load new font
		FontController font = new FontController();

		// font size
		int big = 24;

		labelCheckout.setFont(font.getFontOpenSansBold(big));
		labePaymentResult.setFont(font.getFontOpenSansBold(big));
		buttonClose.setFont(font.getFontOpenSansBold(big));

		if (SystemThread.resultPayment == true) {
			labePaymentResult.setText("PAYMENT WAS ACCEPTED.");
		} else {
			labePaymentResult.setText("PAYMENT WAS DECLINED.");
		}
	}

	@FXML
	protected void handleCloseAction(ActionEvent event) {
		SystemThread.sixthStage.close();
		SystemThread.anchorPaneBlur.setEffect(null);
	}
}
