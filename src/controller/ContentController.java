package controller;

import com.jfoenix.controls.JFXButton;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ContentController {

	@FXML
	AnchorPane anchorPane;

	@FXML
	Label label;

	@FXML
	Label movie01_grid_1;

	@FXML
	Label movie02_grid_1;

	@FXML
	Label movie03_grid_1;

	@FXML
	Label movie04_grid_1;

	@FXML
	Label movie05_grid_1;

	@FXML
	Label movie06_grid_1;

	@FXML
	Label movie07_grid_1;

	@FXML
	Label movie01_grid_2;

	@FXML
	Label movie02_grid_2;

	@FXML
	Label movie03_grid_2;

	@FXML
	Label movie04_grid_2;

	@FXML
	Label movie05_grid_2;

	@FXML
	Label movie06_grid_2;

	@FXML
	Label movie07_grid_2;

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

	ScaleTransition st = null;

	private void scaleEffect(Node node, double scale) {
		ScaleTransition st = new ScaleTransition(Duration.millis(300), node);
		st.setByX(scale);
		st.setByY(scale);
		st.setAutoReverse(true);
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
					scaleEffect(node,scale);
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

		title_grid_1.setFont(font.getFontOpenSansRegular(big));
		title_grid_2.setFont(font.getFontOpenSansRegular(big));

		movie01_grid_1.setFont(font.getFontOpenSansRegular(small));
		movie02_grid_1.setFont(font.getFontOpenSansRegular(small));
		movie03_grid_1.setFont(font.getFontOpenSansRegular(small));
		movie04_grid_1.setFont(font.getFontOpenSansRegular(small));
		movie05_grid_1.setFont(font.getFontOpenSansRegular(small));
		movie06_grid_1.setFont(font.getFontOpenSansRegular(small));
		movie07_grid_1.setFont(font.getFontOpenSansRegular(small));

		movie01_grid_2.setFont(font.getFontOpenSansRegular(small));
		movie02_grid_2.setFont(font.getFontOpenSansRegular(small));
		movie03_grid_2.setFont(font.getFontOpenSansRegular(small));
		movie04_grid_2.setFont(font.getFontOpenSansRegular(small));
		movie05_grid_2.setFont(font.getFontOpenSansRegular(small));
		movie06_grid_2.setFont(font.getFontOpenSansRegular(small));
		movie07_grid_2.setFont(font.getFontOpenSansRegular(small));

		price01_grid_1.setFont(font.getFontOpenSansRegular(small));
		price02_grid_1.setFont(font.getFontOpenSansRegular(small));
		price03_grid_1.setFont(font.getFontOpenSansRegular(small));
		price04_grid_1.setFont(font.getFontOpenSansRegular(small));
		price05_grid_1.setFont(font.getFontOpenSansRegular(small));
		price06_grid_1.setFont(font.getFontOpenSansRegular(small));
		price07_grid_1.setFont(font.getFontOpenSansRegular(small));

		price01_grid_2.setFont(font.getFontOpenSansRegular(small));
		price02_grid_2.setFont(font.getFontOpenSansRegular(small));
		price03_grid_2.setFont(font.getFontOpenSansRegular(small));
		price04_grid_2.setFont(font.getFontOpenSansRegular(small));
		price05_grid_2.setFont(font.getFontOpenSansRegular(small));
		price06_grid_2.setFont(font.getFontOpenSansRegular(small));
		price07_grid_2.setFont(font.getFontOpenSansRegular(small));

		this.scaleEffectApply((Node)imageView01, 0.1f,true);
		this.scaleEffectApply((Node)imageView01, -0.1f,false);
		
		this.scaleEffectApply((Node)imageView02, 0.1f,true);
		this.scaleEffectApply((Node)imageView02, -0.1f,false);
		
		this.scaleEffectApply((Node)imageView03, 0.1f,true);
		this.scaleEffectApply((Node)imageView03, -0.1f,false);
		
		this.scaleEffectApply((Node)imageView04, 0.1f,true);
		this.scaleEffectApply((Node)imageView04, -0.1f,false);
		
		this.scaleEffectApply((Node)imageView05, 0.1f,true);
		this.scaleEffectApply((Node)imageView05, -0.1f,false);
		
		this.scaleEffectApply((Node)imageView06, 0.1f,true);
		this.scaleEffectApply((Node)imageView06, -0.1f,false);
		
		this.scaleEffectApply((Node)imageView07, 0.1f,true);
		this.scaleEffectApply((Node)imageView07, -0.1f,false);
		
		this.scaleEffectApply((Node)imageView01_2, 0.1f,true);
		this.scaleEffectApply((Node)imageView01_2, -0.1f,false);
		
		this.scaleEffectApply((Node)imageView02_2, 0.1f,true);
		this.scaleEffectApply((Node)imageView02_2, -0.1f,false);
		
		this.scaleEffectApply((Node)imageView03_2, 0.1f,true);
		this.scaleEffectApply((Node)imageView03_2, -0.1f,false);
		
		this.scaleEffectApply((Node)imageView04_2, 0.1f,true);
		this.scaleEffectApply((Node)imageView04_2, -0.1f,false);
		
		this.scaleEffectApply((Node)imageView05_2, 0.1f,true);
		this.scaleEffectApply((Node)imageView05_2, -0.1f,false);
		
		this.scaleEffectApply((Node)imageView06_2, 0.1f,true);
		this.scaleEffectApply((Node)imageView06_2, -0.1f,false);
		
		this.scaleEffectApply((Node)imageView07_2, 0.1f,true);
		this.scaleEffectApply((Node)imageView07_2, -0.1f,false);

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