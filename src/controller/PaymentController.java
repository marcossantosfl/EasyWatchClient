package controller;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import http.Http;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
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
	JFXButton payButton;
	@FXML
	Label labelEmail;
	@FXML
	JFXToggleButton emailToggle;

	// card type (8 max)
	int maxNumber = 8;
	
	Pattern pattern = null;
	Matcher matcher = null;

	// initialize
	@FXML
	protected void initialize() {

		// load new font
		FontController font = new FontController();

		// font size
		int big = 20;
		int medium = 16;

		checkOut.setFont(font.getFontOpenSansBold(big));
		cardNumber.setFont(font.getFontOpenSansBold(medium));
		cardLabel.setFont(font.getFontOpenSansBold(medium));
		emailToggle.setFont(font.getFontOpenSansBold(medium));
		payButton.setFont(font.getFontOpenSansBold(big));
		emailAddress.setFont(font.getFontOpenSansBold(medium));
		labelEmail.setFont(font.getFontOpenSansBold(medium));
		buttonClose.setFont(font.getFontOpenSansBold(big));
		buttonClose.setFocusTraversable(false);

		cardNumber.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				if (cardNumber.getLength() >= maxNumber) {
					k.consume();
				}
			}
		});
		cardNumber.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					cardNumber.setText(newValue.replaceAll("[^\\d]", ""));
				}

				if (cardNumber.getLength() == maxNumber) {
					payButton.setVisible(true);
				} else {
					payButton.setVisible(false);
				}
			}
		});
		emailAddress.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				pattern = Pattern.compile("^(.+)@(.+)$");
				matcher = pattern.matcher(emailAddress.getText());
				if ((emailAddress.getLength() <= 5) || !matcher.matches()) {
					if (emailToggle.isSelected()) {
						if (emailAddress.getLength() >= maxNumber) {
							payButton.setVisible(false);
						}
					} else {
						payButton.setVisible(true);
					}
				} else {
					if (matcher.matches()) {
						if (emailToggle.isSelected()) {
							if (emailAddress.getLength() >= maxNumber) {
								payButton.setVisible(true);
							}
						} else {
							payButton.setVisible(false);
						}
					}
				}

			}
		});
		emailAddress.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent k) {
				if (emailAddress.getLength() >= 40) {
					k.consume();
				}
			}
		});
	}

	@FXML
	protected void handleCloseAction(ActionEvent event) {
		SystemThread.sixthStage.close();
		SystemThread.anchorPaneBlur.setEffect(null);
	}

	@FXML
	protected void handleToggleAction(ActionEvent event) {
		if (emailToggle.isSelected()) {
			labelEmail.setVisible(true);
			emailAddress.setVisible(true);
		} else {
			labelEmail.setVisible(false);
			emailAddress.setVisible(false);
		}
		
		if (emailToggle.isSelected()) {
			emailAddress.setVisible(true);
			labelEmail.setVisible(true);
			payButton.setVisible(false);

			pattern = Pattern.compile("^(.+)@(.+)$");
			matcher = pattern.matcher(emailAddress.getText());

			if (matcher.matches()) {
				payButton.setVisible(true);
			}
		} else {
			if (cardNumber.getLength() >= maxNumber) {
				payButton.setVisible(true);
			}
			emailAddress.setVisible(false);
			labelEmail.setVisible(false);
		}
	}
	
	@FXML
	protected void handlePayAction(ActionEvent event) throws IOException {
		JSONObject jsonSend = new JSONObject();

		jsonSend.put("number", this.cardNumber.getText());
		jsonSend.put("idDisplay", SystemThread.buttonClickedPrice);
		if(emailToggle.isSelected())
		{
			jsonSend.put("email", this.emailAddress.getText());
		}

		Http http = new Http();
		
		HttpResponse<String> httpResponse = http.post("https://marcossan.dev/api_easywatch/pages/getCard.php", jsonSend);
		
		if (httpResponse.statusCode() == 200)
		{
			SystemThread.resultPayment = true;
		}
		else
		{
			SystemThread.resultPayment = false;
		}
		
		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/processing.fxml"));
		anchorPane.getChildren().clear();
		anchorPane.getChildren().setAll(pane);

		AnchorPane pResult = FXMLLoader.load(getClass().getResource("/view/payment_processing_result.fxml"));

		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				anchorPane.getChildren().clear();
				anchorPane.getChildren().setAll(pResult);
			}
		});
		pause.play();

	}
}
