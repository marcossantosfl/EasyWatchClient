package controller;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import http.Http;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import thread.SystemThread;

public class WatchController {

	@FXML
	AnchorPane anchorPane;
	@FXML
	Label labelWatch;
	@FXML
	Label labelCard;
	@FXML
	JFXTextField cardNumber;
	@FXML
	JFXButton buttonClose;
	@FXML
	JFXButton buttonCard;
	@FXML
	JFXButton buttonWatch;
	@FXML
	JFXComboBox<String> comboBox;

	private ObservableList<String> comboList = FXCollections.observableArrayList();

	// card type (8 max)
	int maxNumber = 8;

	private static class WatchList {

		public static List<WatchList> list = new ArrayList<WatchList>();

		private String name;
		private String date;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}
	}

	// initialize
	@FXML
	protected void initialize() {

		// load new font
		FontController font = new FontController();

		// font size
		int big = 20;
		int medium = 16;

		setFontAndFocus(font, big, medium);

		addComboListener();

		addEventCard();
	}

	private void addComboListener() {
		comboBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue ob, String s1, String s2) {
				comboBox.setStyle(" -fx-background-color: #FFFFFF");
			}
		});
	}

	private void addEventCard() {
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
					buttonCard.setVisible(true);
				} else {
					buttonCard.setVisible(false);
				}
			}
		});
	}

	private void setFontAndFocus(FontController font, int big, int medium) {
		labelWatch.setFont(font.getFontOpenSansBold(big));
		labelCard.setFont(font.getFontOpenSansBold(medium));
		cardNumber.setFont(font.getFontOpenSansBold(medium));
		buttonCard.setFont(font.getFontOpenSansBold(big));
		buttonWatch.setFont(font.getFontOpenSansBold(big));
		buttonClose.setFont(font.getFontOpenSansBold(big));
		buttonClose.setFocusTraversable(false);
		buttonCard.setFocusTraversable(false);
		buttonWatch.setFocusTraversable(false);
	}

	@FXML
	protected void handleCloseAction(ActionEvent event) {
		SystemThread.seventhStage.close();
		SystemThread.anchorPaneBlur.setEffect(null);
	}

	@FXML
	protected void handleCheckCardAction(ActionEvent event) throws IOException {
		JSONObject jsonSend = new JSONObject();

		jsonSend.put("number", this.cardNumber.getText());

		Http http = new Http();

		HttpResponse<String> httpResponse = http.post("https://marcossan.dev/api_easywatch/pages/getWatchList.php",
				jsonSend);

		// re-use the same screen node (original screen)
		AnchorPane pResult = new AnchorPane();
		pResult.getChildren().addAll(anchorPane.getChildren());

		AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/processing.fxml"));
		anchorPane.getChildren().clear();
		anchorPane.getChildren().setAll(pane);

		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {

				anchorPane.getChildren().clear();
				anchorPane.getChildren().setAll(pResult);

				comboBoxSet(httpResponse);
			}

		});
		pause.play();

	}

	private void comboBoxSet(HttpResponse<String> httpResponse) {
		if (httpResponse.statusCode() == 200) {
			WatchList.list.clear();

			JSONObject jsonReceive = new JSONObject(httpResponse.body());

			JSONArray data = jsonReceive.getJSONArray("watchList");

			createListCombo(data);

			if (WatchList.list.size() > 0) {
				for (int i = 0; i < WatchList.list.size(); i++) {
					WatchList watch = WatchList.list.get(i);

					LocalDate localDate = LocalDate.parse(watch.getDate());

					int date = (int) ChronoUnit.DAYS.between(localDate,
							java.sql.Date.valueOf(LocalDate.now()).toLocalDate());

					String complete = date > 7 ? " - EXPIRED" : " - " + (7 - date) + " DAYS(S) LEFT";

					comboList.add(watch.getName() + complete);
				}

				comboBox.valueProperty().set(null);

				comboBox.setItems(comboList);

				comboBox.setVisible(true);
			} else {
				comboBox.setVisible(false);
			}
		} else {
			comboBox.setVisible(false);
		}
	}

	private void createListCombo(JSONArray data) {
		for (int i = 0; i < data.length(); i++) {

			WatchList watchList = new WatchList();
			JSONObject json = data.getJSONObject(i);

			watchList.setName((json.getString("name")));
			watchList.setDate(json.getString("date"));

			WatchList.list.add(watchList);
		}
	}

	@FXML
	protected void handleComboBoxAction(ActionEvent event) {
		// check if content if expired
		if (comboBox.getValue() != null) {

			buttonWatch.setVisible(true);

			for (int i = 0; i < WatchList.list.size(); i++) {
				WatchList watch = WatchList.list.get(i);

				if (comboBox.getValue().equalsIgnoreCase(watch.getName() + " - EXPIRED")) {
					buttonWatch.setVisible(false);
				}
			}

		}
	}

	@FXML
	protected void handleWatchAction(ActionEvent event) {
		Platform.runLater(() -> {

			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("/view/play.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			Scene scene1 = new Scene(root);

			Stage stage = new Stage();

			stage.initStyle(StageStyle.TRANSPARENT);
			scene1.setFill(Color.TRANSPARENT);
			stage.setTitle("Watching");
			stage.setResizable(false);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene1);
			stage.show();
			SystemThread.eighthStage = stage;
		});
	}
}
