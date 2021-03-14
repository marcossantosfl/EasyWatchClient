package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import thread.SystemThread;

public class SelectController {
	
	@FXML
	AnchorPane anchorPane;
	
	@FXML
	AnchorPane anchorPaneMovie;
	
	@FXML
	AnchorPane anchorPaneSerie;

	@FXML
	ImageView imageMovie;

	@FXML
	ImageView imageSerie;
	
	@FXML
	JFXButton buttonWindowDown;

	@FXML
	JFXButton buttonWindowClose;

	
	private void goToScreen(String url, String title)
	{
		Platform.runLater(() -> {
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource(url));
			} catch (IOException e) {
				e.printStackTrace();
			}

			Scene scene1 = new Scene(root);

			Stage thirdStage = new Stage();

			thirdStage.initStyle(StageStyle.TRANSPARENT);
			scene1.setFill(Color.TRANSPARENT);
			thirdStage.setTitle(title);
			thirdStage.setResizable(false);
			thirdStage.setScene(scene1);
			thirdStage.show();
			SystemThread.secondStage.hide();
			
			SystemThread.thirdStage = thirdStage;
		});
	}

	@FXML
	protected void initialize() {
		
		FontController font = new FontController();
		
		int size = 24;
		
		buttonWindowDown.setFont(font.getFontOpenSansBold(size));
		buttonWindowClose.setFont(font.getFontOpenSansBold(size));
		
		buttonWindowDown.setFocusTraversable(false);
		buttonWindowClose.setFocusTraversable(false);
		
		anchorPaneMovie.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				SystemThread.typeSelected = true;
				goToScreen("/view/splash_data.fxml", "Movies");
			}
		});
		anchorPaneMovie.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				anchorPaneMovie.setStyle("-fx-background-color: #000000; -fx-background-radius: 100 100 100 100;");
			}
		});
		anchorPaneMovie.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				anchorPaneMovie.setStyle("-fx-background-color: linear-gradient(to bottom,#000000, #03045e); -fx-background-radius: 100 100 100 100;");
			}
		});
		anchorPaneSerie.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				anchorPaneSerie.setStyle("-fx-background-color: #000000; -fx-background-radius: 100 100 100 100;");
			}
		});
		anchorPaneSerie.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				anchorPaneSerie.setStyle("-fx-background-color: linear-gradient(to bottom,#000000, #03045e); -fx-background-radius: 100 100 100 100;");
			}
		});
		anchorPaneSerie.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				SystemThread.typeSelected = true;
				goToScreen("/view/splash_data.fxml", "Serie");
			}
		});
	}
	
	@FXML
	protected void handleMinButtonAction(ActionEvent event) {
		Stage stage = (Stage) anchorPane.getScene().getWindow();
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	@FXML
	protected void handleCloseButtonAction(ActionEvent event) {
		System.exit(1);
	}

}
