package controller;

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
	GridPane gridPane1;

	@FXML
	GridPane gridPane2;

	@FXML
	ImageView arrowDownImg;

	@FXML
	ImageView arrowUpImg;

	@FXML
	private List<Label> labelList1 = new ArrayList<Label>();

	@FXML
	private List<ImageView> imageList1 = new ArrayList<ImageView>();

	@FXML
	private List<JFXButton> buttonList1 = new ArrayList<JFXButton>();

	@FXML
	private List<Label> labelList2 = new ArrayList<Label>();

	@FXML
	private List<ImageView> imageList2 = new ArrayList<ImageView>();

	@FXML
	private List<JFXButton> buttonList2 = new ArrayList<JFXButton>();

	@FXML
	private List<ImageView> arrowList1 = new ArrayList<ImageView>();

	@FXML
	private List<ImageView> arrowList2 = new ArrayList<ImageView>();

	private int clickArrowSide1 = 0;
	private int clickArrowSide2 = 0;

	private int category1 = 1;
	private int category2 = 2;

	private int gridClick = 0;

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

		arrowUpImg.setVisible(false);

		boolean hasCategory = false;

		for (int r = 0; r < 10; r++) {
			if (categoryTotal(category2 + r) > 0) {
				hasCategory = true;
			}
		}

		if (hasCategory != true) {
			arrowDownImg.setVisible(false);
		}

		int big = 24;
		int medium = 20;
		int small = 16;

		if (SystemThread.displayType == 0) {
			int categoryCount1 = 0;
			int categoryCount2 = 0;

			for (int i = 0; i < SystemThread.movieList.size(); i++) {
				DisplayMovie dMovie = SystemThread.movieList.get(i);

				if (dMovie.getIdCategory() == 1) {
					if (categoryCount1 < 7) {
						extracted(1, "Adventure", gridPane1, labelList1, imageList1, buttonList1, font, big, small,
								categoryCount1, dMovie);
					}
					categoryCount1++;
				} else if (dMovie.getIdCategory() == 2) {
					if (categoryCount2 < 7) {
						extracted(2, "Action", gridPane2, labelList2, imageList2, buttonList2, font, big, small,
								categoryCount2, dMovie);
					}
					categoryCount2++;
				}
			}

			arrowCategory(gridPane1, labelList1, imageList1, buttonList1, 1);
			arrowCategory(gridPane2, labelList2, imageList2, buttonList2, 2);
		}

		arrowDownImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				
				clickArrowSide1 = 0;
				clickArrowSide2 = 0;

				List<Integer> intList1 = new ArrayList<Integer>();
				List<Integer> intList2 = new ArrayList<Integer>();

				category1++;
				category2++;

				int total1 = 0;
				int total2 = 0;

				for (int k = 0; k < SystemThread.movieList.size(); k++) {
					DisplayMovie dMovie = SystemThread.movieList.get(k);
					if (dMovie.getIdCategory() == category1) {
						intList1.add(k);
						total1++;
					} else if (dMovie.getIdCategory() == category2) {
						intList2.add(k);
						total2++;
					}
				}

				int i = 0;

				for (int j = 0; j < total1; j++) {
					DisplayMovie dMovie = SystemThread.movieList.get(intList1.get(j));
					if (i < 7) {
						buttonList1.get(i).setText(String.valueOf(dMovie.getPrice()));
						labelList1.get(i).setText(String.valueOf(dMovie.getNameContent()));
						imageList1.get(i).setImage(new Image("file:///" + SystemThread.folder.getAbsolutePath()
								+ "/image/" + dMovie.getImage() + ".jpg"));
						i++;
					}
				}

				int y = 0;
				for (int j = 0; j < total2; j++) {
					DisplayMovie dMovie = SystemThread.movieList.get(intList2.get(j));
					if (y < 7) {
						buttonList2.get(y).setText(String.valueOf(dMovie.getPrice()));
						labelList2.get(y).setText(String.valueOf(dMovie.getNameContent()));
						imageList2.get(y).setImage(new Image("file:///" + SystemThread.folder.getAbsolutePath()
								+ "/image/" + dMovie.getImage() + ".jpg"));
						y++;
					}
				}

				boolean hasCategory = false;

				for (int r = 0; r < 10; r++) {
					if (categoryTotal(category2 + r) > 0) {
						hasCategory = true;
					}
				}

				if (hasCategory == true) {
					arrowDownImg.setVisible(false);
					arrowUpImg.setVisible(true);
				}

				for (int z = 0; z < y; z++) {
					buttonList2.get(z).setVisible(true);
					labelList2.get(z).setVisible(true);
					imageList2.get(z).setVisible(true);
					if ((z + 1) == y) {
						int l = (z + 1);
						for (int w = l; w < 7; w++) {
							buttonList2.get(w).setVisible(false);
							labelList2.get(w).setVisible(false);
							imageList2.get(w).setVisible(false);
						}
					}
				}
				
				for (int z = 0; z < i; z++) {
					buttonList1.get(z).setVisible(true);
					labelList1.get(z).setVisible(true);
					imageList1.get(z).setVisible(true);
					if ((z + 1) == i) {
						int l = (z + 1);
						for (int w = l; w < 7; w++) {
							buttonList1.get(w).setVisible(false);
							labelList1.get(w).setVisible(false);
							imageList1.get(w).setVisible(false);
						}
					}
				}
				
				if(total1 > 7)
				{
					arrowList1.get(1).setVisible(false);
				}
				else
				{
					arrowList1.get(1).setVisible(true);
				}
				
				if(total2 > 7)
				{
					arrowList1.get(0).setVisible(false);
				}
				else
				{
					arrowList1.get(0).setVisible(true);
				}
				

				arrowList2.get(0).setVisible(false);
				arrowList2.get(1).setVisible(false);

			}
		});
		arrowUpImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {

				clickArrowSide1 = 0;
				clickArrowSide2 = 0;
				
				if (!arrowDownImg.isVisible()) {
					arrowDownImg.setVisible(true);
				}

				if (categoryTotal(category1 - 2) == 0) {
					arrowUpImg.setVisible(false);
				}

				List<Integer> intList1 = new ArrayList<Integer>();
				List<Integer> intList2 = new ArrayList<Integer>();

				category1--;
				category2--;

				int total1 = 0;
				int total2 = 0;

				for (int k = 0; k < SystemThread.movieList.size(); k++) {
					DisplayMovie dMovie = SystemThread.movieList.get(k);
					if (dMovie.getIdCategory() == category1) {
						intList1.add(k);
						total1++;
					} else if (dMovie.getIdCategory() == category2) {
						intList2.add(k);
						total2++;
					}
				}

				int i = 0;

				for (int j = 0; j < total1; j++) {
					DisplayMovie dMovie = SystemThread.movieList.get(intList1.get(j));
					if (i < 7) {
						buttonList1.get(i).setText(String.valueOf(dMovie.getPrice()));
						labelList1.get(i).setText(String.valueOf(dMovie.getNameContent()));
						imageList1.get(i).setImage(new Image("file:///" + SystemThread.folder.getAbsolutePath()
								+ "/image/" + dMovie.getImage() + ".jpg"));
						i++;
					}
				}

				int y = 0;
				for (int j = 0; j < total2; j++) {
					DisplayMovie dMovie = SystemThread.movieList.get(intList2.get(j));
					if (y < 7) {
						buttonList2.get(y).setText(String.valueOf(dMovie.getPrice()));
						labelList2.get(y).setText(String.valueOf(dMovie.getNameContent()));
						imageList2.get(y).setImage(new Image("file:///" + SystemThread.folder.getAbsolutePath()
								+ "/image/" + dMovie.getImage() + ".jpg"));
						y++;
					}
				}

				for (int z = 0; z < y; z++) {
					buttonList2.get(z).setVisible(true);
					labelList2.get(z).setVisible(true);
					imageList2.get(z).setVisible(true);
					if ((z + 1) == y) {
						int l = (z + 1);
						for (int w = l; w < 7; w++) {
							buttonList2.get(w).setVisible(false);
							labelList2.get(w).setVisible(false);
							imageList2.get(w).setVisible(false);
						}
					}
				}
				
				for (int z = 0; z < i; z++) {
					buttonList1.get(z).setVisible(true);
					labelList1.get(z).setVisible(true);
					imageList1.get(z).setVisible(true);
					if ((z + 1) == i) {
						int l = (z + 1);
						for (int w = l; w < 7; w++) {
							buttonList1.get(w).setVisible(false);
							labelList1.get(w).setVisible(false);
							imageList1.get(w).setVisible(false);
						}
					}
				}
				
				if(total1 > 7)
				{
					arrowList1.get(1).setVisible(true);
				}
				else
				{
					arrowList1.get(1).setVisible(false);
				}
				
				if(total2 > 7)
				{
					arrowList1.get(0).setVisible(true);
				}
				else
				{
					arrowList1.get(0).setVisible(false);
				}
				

				arrowList2.get(0).setVisible(false);
				arrowList2.get(1).setVisible(false);
			}
		});

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

	private void arrowCategory(GridPane gridPane, List<Label> labelList, List<ImageView> imageList,
			List<JFXButton> buttonList, int category) {

		if (categoryTotal(category) > 7) {
			ImageView left = new ImageView(new Image("/image/icons/left.png"));
			left.setPickOnBounds(true);
			left.setPreserveRatio(true);
			left.setFitHeight(80.0);
			left.setFitWidth(80.0);
			left.setVisible(false);
			arrowList2.add(left);

			ImageView right = new ImageView(new Image("/image/icons/right.png"));
			right.setPickOnBounds(true);
			right.setPreserveRatio(true);
			right.setFitHeight(80.0);
			right.setFitWidth(80.0);
			right.setVisible(true);
			arrowList1.add(right);

			this.scaleEffectApply(right, 0.1f, true);
			this.scaleEffectApply(right, -0.1f, false);

			this.scaleEffectApply(left, 0.1f, true);
			this.scaleEffectApply(left, -0.1f, false);

			right.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					if (arrowList1.get(0).equals(right)) {
						gridClick = 1;
					} else if (arrowList1.get(1).equals(right)) {
						gridClick = 2;
					}
				}
			});

			left.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					if (arrowList2.get(0).equals(left)) {
						gridClick = 1;
					} else if (arrowList2.get(1).equals(left)) {
						gridClick = 2;
					}
				}
			});

			right.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {

					left.setVisible(true);

					List<Integer> intList1 = new ArrayList<Integer>();

					int c = gridClick == 1 ? category1 : category2;

					for (int k = 0; k < SystemThread.movieList.size(); k++) {
						DisplayMovie dMovie = SystemThread.movieList.get(k);
						if (dMovie.getIdCategory() == c) {
							intList1.add(k);
						}
					}

					int clickArrowSide = 0;

					if (gridClick == 1) {
						clickArrowSide1++;
						clickArrowSide = clickArrowSide1;
					} else if (gridClick == 2) {
						clickArrowSide2++;
						clickArrowSide = clickArrowSide2;
					}

					if (categoryTotal(c) - clickArrowSide == 7) {
						right.setVisible(false);
						left.setVisible(true);
					}

					int i = 0;

					for (int j = clickArrowSide; j < intList1.size(); j++) {
						DisplayMovie dMovie = SystemThread.movieList.get(intList1.get(j));
						if (i < 7) {
							buttonList.get(i).setText(String.valueOf(dMovie.getPrice()));
							labelList.get(i).setText(String.valueOf(dMovie.getNameContent()));
							imageList.get(i).setImage(new Image("file:///" + SystemThread.folder.getAbsolutePath()
									+ "/image/" + dMovie.getImage() + ".jpg"));
							i++;
						}

					}

				}
			});

			left.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {

					right.setVisible(true);

					List<Integer> intList1 = new ArrayList<Integer>();

					int c = gridClick == 1 ? category1 : category2;

					for (int k = 0; k < SystemThread.movieList.size(); k++) {
						DisplayMovie dMovie = SystemThread.movieList.get(k);

						if (dMovie.getIdCategory() == c) {
							intList1.add(k);
						}
					}

					int clickArrowSide = 0;

					if (gridClick == 1) {
						clickArrowSide1--;
						clickArrowSide = clickArrowSide1;
					} else if (gridClick == 2) {
						clickArrowSide2--;
						clickArrowSide = clickArrowSide2;
					}

					if (clickArrowSide == 0) {
						right.setVisible(true);
						left.setVisible(false);
					}

					int i = 0;

					for (int j = clickArrowSide; j < intList1.size(); j++) {
						DisplayMovie dMovie = SystemThread.movieList.get(intList1.get(j));
						if (i < 7) {
							buttonList.get(i).setText(String.valueOf(dMovie.getPrice()));
							labelList.get(i).setText(String.valueOf(dMovie.getNameContent()));
							imageList.get(i).setImage(new Image("file:///" + SystemThread.folder.getAbsolutePath()
									+ "/image/" + dMovie.getImage() + ".jpg"));
							i++;
						}

					}
				}
			});

			gridPane.add(left, 0, 1);
			gridPane.add(right, 8, 1);
		}
	}

	private void extracted(int category, String categoryName, GridPane gridPane, List<Label> labelList,
			List<ImageView> imageList, List<JFXButton> buttonList, FontController font, int big, int small, int i,
			DisplayMovie dMovie) {
		Label t = new Label(categoryName);
		t.setAlignment(Pos.TOP_LEFT);
		t.setTextFill(Color.WHITE);
		t.setPrefWidth(280.0);
		t.setFont(font.getFontOpenSansRegular(big));
		Label l = new Label(dMovie.getNameContent());
		l.setTextFill(Color.WHITE);
		l.setFont(font.getFontOpenSansRegular(small));
		l.setAlignment(Pos.CENTER);
		AnchorPane p = new AnchorPane();
		p.setLeftAnchor(l, 0.0);
		p.setRightAnchor(l, 0.0);
		p.setPrefWidth(270.0);
		p.setPrefHeight(140.0);
		ImageView v = new ImageView(
				new Image("file:///" + SystemThread.folder.getAbsolutePath() + "/image/" + dMovie.getImage() + ".jpg"));
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

		this.scaleEffectApply(v, 0.1f, true);
		this.scaleEffectApply(v, -0.1f, false);

		gridPane.add(t, 0, 0);
		labelList.add(l);
		imageList.add(v);
		buttonList.add(b);
		gridPane.add(p, i + 1, 1);
	}

	private int categoryTotal(int category) {

		int total = 0;

		for (int i = 0; i < SystemThread.movieList.size(); i++) {

			DisplayMovie dMovie = SystemThread.movieList.get(i);

			if (dMovie.getIdCategory() == category) {
				total++;
			}
		}

		return total;
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