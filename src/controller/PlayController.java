package controller;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebView;
import thread.SystemThread;

public class PlayController {

	@FXML
	AnchorPane anchorPane;
	@FXML
	JFXButton buttonClose;
	@FXML
	WebView webview;

	// initialize
	@FXML
	protected void initialize() {

		// load new font
		FontController font = new FontController();

		// font size
		int big = 24;
		
		webview = new WebView();
	    webview.getEngine().load(
	      "https://vjs.zencdn.net/v/oceans.mp4"
	    );
	    webview.setPrefSize(1000, 600);
	    
	    this.anchorPane.getChildren().add(webview);
	    
		buttonClose = new JFXButton("X");
		buttonClose.setFont(font.getFontOpenSansBold(big));
		buttonClose.setFocusTraversable(false);
		buttonClose.setTextFill(Paint.valueOf("WHITE"));
		buttonClose.setLayoutX(915);
		buttonClose.setLayoutY(15);
		buttonClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	handleCloseAction(event);
            }
        });
		this.anchorPane.getChildren().add(buttonClose);
		
	}

	@FXML
	protected void handleCloseAction(ActionEvent event) {
		webview.getEngine().load(null);
		SystemThread.eighthStage.close();
	}
}
