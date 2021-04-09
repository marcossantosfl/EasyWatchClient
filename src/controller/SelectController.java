package controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import thread.SystemThread;

//controller
public class SelectController {
	
	//variables to control fxml
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

	
	//method to go the another screen
	private void goToScreen(String url, String title)
	{
		//it has to be Platform otherwise will generate an error (javafx)
		Platform.runLater(() -> {
			Parent root = null;
			
			//load new screen
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
			//close the second screen
			SystemThread.secondStage.close();
			//save the third screen
			SystemThread.thirdStage = thirdStage;
		});
	}

	//initialize everything before
	@FXML
	protected void initialize() {
		
		//load new font (OpenSans)
		FontController font = new FontController();
		
		//font size
		int size = 24;
		
		setFontAndFocus(font, size);
		
		addEventClick();
		addEventEnter();
		addEventExit();
	}

	private void addEventExit() {
		//add event if mouse exit, just change the background to the original
		anchorPaneSerie.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				anchorPaneSerie.setStyle("-fx-background-color: linear-gradient(to bottom,#000000, #03045e); -fx-background-radius: 100 100 100 100;");
			}
		});
		//add event if mouse exit, just change the background to the original
		anchorPaneMovie.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				anchorPaneMovie.setStyle("-fx-background-color: linear-gradient(to bottom,#000000, #03045e); -fx-background-radius: 100 100 100 100;");
			}
		});
	}

	private void addEventEnter() {
		//add event if mouse entered, just change the background
		anchorPaneMovie.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				anchorPaneMovie.setStyle("-fx-background-color: #000000; -fx-background-radius: 100 100 100 100;");
			}
		});
		//add event if mouse entered, just change the background
		anchorPaneSerie.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				anchorPaneSerie.setStyle("-fx-background-color: #000000; -fx-background-radius: 100 100 100 100;");
			}
		});
	}

	private void addEventClick() {
		//add event if click on movies, it goes to movie screen
		anchorPaneMovie.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				//control the thread to get the data
				SystemThread.typeSelected = true;
				goToScreen("/view/splash_data.fxml", "Movies");
				SystemThread.displayType = 0;
			}
		});
		//add event if click on series, it goes to serie screen
		anchorPaneSerie.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				//control the thread to get the data
				SystemThread.typeSelected = true;
				goToScreen("/view/splash_data.fxml", "Serie");
				SystemThread.displayType = 1;
			}
		});
	}

	private void setFontAndFocus(FontController font, int size) {
		//set font to the Node
		buttonWindowDown.setFont(font.getFontOpenSansBold(size));
		buttonWindowClose.setFont(font.getFontOpenSansBold(size));
		
		//set focus off
		buttonWindowDown.setFocusTraversable(false);
		buttonWindowClose.setFocusTraversable(false);
	}
	
	//minimize button
	@FXML
	protected void handleMinButtonAction(ActionEvent event) {
		Stage stage = (Stage) anchorPane.getScene().getWindow();
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	//close button
	@FXML
	protected void handleCloseButtonAction(ActionEvent event) {
		System.exit(1);
	}

}
