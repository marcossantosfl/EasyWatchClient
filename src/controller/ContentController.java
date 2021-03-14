package controller;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.DisplayMovie;
import thread.SystemThread;

public class ContentController {

	@FXML
	AnchorPane anchorPane;

	@FXML
	AnchorPane originalAnchor;

	@FXML
	Label label01_grid_1;

	@FXML
	Label label02_grid_1;

	@FXML
	Label label03_grid_1;

	@FXML
	Label label04_grid_1;

	@FXML
	Label label05_grid_1;

	@FXML
	Label label06_grid_1;

	@FXML
	Label label07_grid_1;

	@FXML
	Label label01_grid_2;

	@FXML
	Label label02_grid_2;

	@FXML
	Label label03_grid_2;

	@FXML
	Label label04_grid_2;

	@FXML
	Label label05_grid_2;

	@FXML
	Label label06_grid_2;

	@FXML
	Label label07_grid_2;

	@FXML
	Label title_grid_1;

	@FXML
	Label title_grid_2;

	@FXML
	JFXButton price01_grid_1;

	@FXML
	JFXButton price02_grid_1;

	@FXML
	JFXButton price03_grid_1;

	@FXML
	JFXButton price04_grid_1;

	@FXML
	JFXButton price05_grid_1;

	@FXML
	JFXButton price06_grid_1;

	@FXML
	JFXButton price07_grid_1;

	@FXML
	JFXButton price01_grid_2;

	@FXML
	JFXButton price02_grid_2;

	@FXML
	JFXButton price03_grid_2;

	@FXML
	JFXButton price04_grid_2;

	@FXML
	JFXButton price05_grid_2;

	@FXML
	JFXButton price06_grid_2;

	@FXML
	JFXButton price07_grid_2;

	@FXML
	JFXButton buttonHome;

	@FXML
	JFXButton buttonSearch;

	@FXML
	JFXButton buttonPay;

	@FXML
	JFXButton buttonDown;

	@FXML
	JFXButton buttonClose;

	@FXML
	ImageView imageView01;

	@FXML
	ImageView imageView02;

	@FXML
	ImageView imageView03;

	@FXML
	ImageView imageView04;

	@FXML
	ImageView imageView05;

	@FXML
	ImageView imageView06;

	@FXML
	ImageView imageView07;

	@FXML
	ImageView imageView01_2;

	@FXML
	ImageView imageView02_2;

	@FXML
	ImageView imageView03_2;

	@FXML
	ImageView imageView04_2;

	@FXML
	ImageView imageView05_2;

	@FXML
	ImageView imageView06_2;

	@FXML
	ImageView imageView07_2;

	@FXML
	GridPane gridPane1;

	private void scaleEffect(Node node, double scale) {
		ScaleTransition st = new ScaleTransition(Duration.millis(01), node);
		st.setByX(scale);
		st.setByY(scale);
		st.play();
	}

	private void scaleEffectApply(Node node, double scale, boolean type) {
		if (type == true) {
			node.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					scaleEffect(node, scale);
				}

			});
		} else {
			node.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					scaleEffect(node, scale);
				}

			});
		}
	}

	@FXML
	protected void initialize() {

		FontController font = new FontController();

		int big = 24;
		int medium = 20;
		int small = 16;

		if (SystemThread.displayType == 0) {
			for (int i = 0; i < SystemThread.movieList.size(); i++) {
				DisplayMovie dMovie = SystemThread.movieList.get(i);

				if (dMovie.getIdCategory() == 1 || dMovie.getIdCategory() == 2) {
					Label t = new Label("Adventure");
					t.setAlignment(Pos.TOP_LEFT);
					t.setTextFill(Color.WHITE);
					t.setFont(font.getFontOpenSansRegular(big));
					gridPane1.add(t,0,0);
					Label l = new Label(dMovie.getNameContent());
					l.setTextFill(Color.WHITE);
					l.setFont(font.getFontOpenSansRegular(small));
					AnchorPane p = new AnchorPane();
					p.setLeftAnchor(l, 0.0);
					p.setRightAnchor(l, 0.0);
					l.setAlignment(Pos.CENTER);
					p.setPrefWidth(270.0);
					p.setPrefHeight(140.0);
					ImageView v = new ImageView(new Image(
							"file:///" + SystemThread.folder.getAbsolutePath() + "/image/" + dMovie.getImage() + ".jpg"));
					v.setPickOnBounds(true);
					v.setPreserveRatio(true);
					v.setFitHeight(220.0);
					v.setFitWidth(180.0);
					// v.setLayoutX(15);
					v.setLayoutY(30);
					p.setLeftAnchor(v, 0.0);
					p.setRightAnchor(v, 0.0);
					JFXButton b = new JFXButton(String.valueOf(dMovie.getPrice()));
					b.setFocusTraversable(false);
					b.setPrefWidth(90.0);
					b.setPrefHeight(30.0);
					b.setTextFill(Color.WHITE);
					p.setLeftAnchor(b, 0.0);
					p.setRightAnchor(b, 0.0);
					b.setAlignment(Pos.CENTER);
					b.setLayoutY(270);
					b.setFont(font.getFontOpenSansRegular(small));
					p.getChildren().setAll(v, l, b);
					gridPane1.add(p, 1 + i, 1);

					this.scaleEffectApply(v, 0.1f, true);
					this.scaleEffectApply(v, -0.1f, false);
				}
			}
		}

		buttonHome.setFocusTraversable(false);
		buttonSearch.setFocusTraversable(false);
		buttonPay.setFocusTraversable(false);
		buttonDown.setFocusTraversable(false);
		buttonClose.setFocusTraversable(false);

		buttonHome.setFont(font.getFontOpenSansRegular(medium));
		buttonSearch.setFont(font.getFontOpenSansRegular(medium));
		buttonPay.setFont(font.getFontOpenSansRegular(medium));
		buttonDown.setFont(font.getFontOpenSansRegular(medium));
		buttonClose.setFont(font.getFontOpenSansRegular(medium));

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