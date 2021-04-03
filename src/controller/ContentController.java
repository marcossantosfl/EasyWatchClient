package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Category;
import model.DisplayMovie;
import thread.SystemThread;

//controller
public class ContentController {

	// variables to control fxml
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

	// list of nodes as its many movies or series
	@FXML
	private List<Label> labelCategory = new ArrayList<Label>();

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

	// arrow left and right clicks
	private int clickArrowSide1 = 0;
	private int clickArrowSide2 = 0;

	// start with those categories
	private int category1 = 1;
	private int category2 = 2;

	// grid click
	private int gridClick = 0;

	// jump if click on the arrow
	private int jump = 2;

	@FXML
	JFXButton buttonClicked = null;

	private int buttonClickedPrice = -1;

	// scale effect if passes the mouse on the images
	private void scaleEffect(Node node, double scale) {
		ScaleTransition st = new ScaleTransition(Duration.millis(1), node);
		st.setByX(scale);
		st.setByY(scale);
		st.play();
	}

	// apply effect on the nodes
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

	// initialize
	@FXML
	protected void initialize() {

		// load information screen but does not show it.
		Platform.runLater(() -> {
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("/view/description.fxml"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			Scene scene1 = new Scene(root);

			Stage fifthStage = new Stage();

			fifthStage.initStyle(StageStyle.TRANSPARENT);
			scene1.setFill(Color.TRANSPARENT);
			fifthStage.setTitle("Content");
			fifthStage.setResizable(false);
			fifthStage.setScene(scene1);
			SystemThread.fourthStage = fifthStage;
		});

		// load new font
		FontController font = new FontController();

		int big = 16;
		int medium = 14;
		int small = 12;

		// arrow up set invisible
		arrowUpImg.setVisible(false);

		// check if has next category to show the arrow down
		boolean hasCategory = false;

		for (int r = 0; r < 10; r++) {
			if (categoryTotal(category2 + r) > 0) {
				hasCategory = true;
			}
		}

		if (hasCategory != true) {
			arrowDownImg.setVisible(false);
		}

		// type of movies = 0
		if (SystemThread.displayType == 0) {

			// count how many and display only seven on the grid, if there is more, show
			// arrows.
			int categoryCount1 = 0;
			int categoryCount2 = 0;

			// check all data per category
			for (int i = 0; i < SystemThread.movieList.size(); i++) {
				DisplayMovie dMovie = SystemThread.movieList.get(i);

				if (dMovie.getIdCategory() == 1) {
					category1 = 1;
					if (categoryCount1 < 7) {
						createGrid(1, gridPane1, labelList1, imageList1, buttonList1, font, big, small, categoryCount1,
								dMovie);
					}
					categoryCount1++;
				} else if (dMovie.getIdCategory() == 2) {
					category2 = 2;
					if (categoryCount2 < 7) {
						createGrid(2, gridPane2, labelList2, imageList2, buttonList2, font, big, small, categoryCount2,
								dMovie);
					}
					categoryCount2++;
				}
			}

			// label of kind of movie or serie (grid 1)
			Label t = new Label(Category.getCategory(1));
			t.setAlignment(Pos.TOP_LEFT);
			t.setTextFill(Color.WHITE);
			t.setPrefWidth(350.0);
			t.setFont(font.getFontOpenSansRegular(10));
			labelCategory.add(t);
			gridPane1.add(t, 0, 0);

			// label of kind of movie or serie (grid 2)
			Label c = new Label(Category.getCategory(2));
			c.setAlignment(Pos.TOP_LEFT);
			c.setTextFill(Color.WHITE);
			c.setPrefWidth(350.0);
			c.setFont(font.getFontOpenSansRegular(10));
			labelCategory.add(c);
			gridPane2.add(c, 0, 0);

			// create arrows
			arrowCategory(gridPane1, labelList1, imageList1, buttonList1, 1);
			arrowCategory(gridPane2, labelList2, imageList2, buttonList2, 2);
		}

		// arrow down event clicked
		arrowDownImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {

				// reset arrows left in case of user changed it
				clickArrowSide1 = 0;
				clickArrowSide2 = 0;

				// create a list to get all the next ids
				List<Integer> intList1 = new ArrayList<Integer>();
				List<Integer> intList2 = new ArrayList<Integer>();

				// total
				int total1 = 0;
				int total2 = 0;

				// check if there is another category
				boolean found = false;

				int a = 0;

				jump = category2;

				// just 11 categories and jump if does not find modernly
				while (a != 11) {
					if (categoryTotal(jump + a) > 0) {
						if (found == false) {
							category1 = jump + a;
							found = true;
						} else {
							category2 = jump + a;
							break;
						}
					}

					a++;
				}

				// add ids to the list
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

				// save the category if clicks on the arrow up and get back to the previous
				jump = category2;

				// block arrow if there is no category down
				boolean block = false;

				for (int f = 1; f < 7; f++) {
					if (categoryTotal(jump + f) != 0) {
						block = true;
					}
				}

				if (!block) {
					arrowDownImg.setVisible(false);
				}

				// change labels according to the new category
				labelCategory.get(0).setText(Category.getCategory(category1));
				labelCategory.get(1).setText(Category.getCategory(category2));

				int i = 0;

				// change all the content to the new category (grid 1)
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

				// change all the content to the new category (grid 2)
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

				// check arrows according to next category
				if (!arrowUpImg.isVisible()) {
					arrowUpImg.setVisible(true);
				}

				// hide elements if is less then 7 or more than 7 will show the arrow (grid 1)
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

				// hide elements if is less then 7 or more than 7 will show the arrow (grid 2)
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

				// set arrow visible if the content is more than 7 (grid 1)
				if (total2 > 7) {
					arrowList1.get(1).setVisible(true);
				} else {
					arrowList1.get(1).setVisible(false);
				}

				// set arrow visible if the content is more than 7 (grid 2)
				if (total1 > 7) {
					arrowList1.get(0).setVisible(true);
				} else {
					arrowList1.get(0).setVisible(false);
				}

				// arrows invisible
				arrowList2.get(0).setVisible(false);
				arrowList2.get(1).setVisible(false);

			}
		});
		// arrow up event clicked
		arrowUpImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {

				// reset arrows left in case of user changed it
				clickArrowSide1 = 0;
				clickArrowSide2 = 0;

				// set arrow down as visible if it is not
				if (!arrowDownImg.isVisible()) {
					arrowDownImg.setVisible(true);
				}

				// create a list to get all the next ids
				List<Integer> intList1 = new ArrayList<Integer>();
				List<Integer> intList2 = new ArrayList<Integer>();

				// total
				int total1 = 0;
				int total2 = 0;

				boolean found = false;

				category2 = category1;

				// check if there is another category
				int a = category2;

				while (a != 0) {
					if (categoryTotal(a - 1) > 0) {
						category1 = a - 1;
						found = true;
						break;
					}
					a--;
				}

				// add ids to the list
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

				jump = category2;

				// block arrow if there is no category down
				boolean block = false;

				for (int f = 1; f < 7; f++) {
					if (categoryTotal(category1 - f) != 0) {
						block = true;
					}
				}

				if (!block) {
					arrowUpImg.setVisible(false);
				}

				// change labels according to the new category
				labelCategory.get(0).setText(Category.getCategory(category1));
				labelCategory.get(1).setText(Category.getCategory(category2));

				int i = 0;

				// change all the content to the new category (grid 1)
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

				// change all the content to the new category (grid 2)
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

				// hide elements if is less then 7 or more than 7 will show the arrow (grid 1)
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

				// hide elements if is less then 7 or more than 7 will show the arrow (grid 2)
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

				// set arrow visible if the content is more than 7 (grid 1)
				if (total2 > 7) {
					arrowList1.get(1).setVisible(true);
				} else {
					arrowList1.get(1).setVisible(false);
				}

				// set arrow visible if the content is more than 7 (grid 2)
				if (total1 > 7) {
					arrowList1.get(0).setVisible(true);
				} else {
					arrowList1.get(0).setVisible(false);
				}

				// arrows invisible
				arrowList2.get(0).setVisible(false);
				arrowList2.get(1).setVisible(false);
			}
		});

		// focus disabled
		buttonHome.setFocusTraversable(false);
		buttonSearch.setFocusTraversable(false);
		buttonPay.setFocusTraversable(false);
		buttonDown.setFocusTraversable(false);
		buttonClose.setFocusTraversable(false);

		// set font on the buttons
		buttonHome.setFont(font.getFontOpenSansRegular(medium));
		buttonSearch.setFont(font.getFontOpenSansRegular(medium));
		buttonPay.setFont(font.getFontOpenSansRegular(medium));
		buttonDown.setFont(font.getFontOpenSansRegular(medium));
		buttonClose.setFont(font.getFontOpenSansRegular(medium));

	}

	// arrows left/right
	private void arrowCategory(GridPane gridPane, List<Label> labelList, List<ImageView> imageList,
			List<JFXButton> buttonList, int category) {

		// set arrow visible if the content is more than 7 (grids)
		if (categoryTotal(category) > 7) {
			// load arrow image
			ImageView left = new ImageView(new Image("/image/icons/left.png"));
			left.setPickOnBounds(true);
			left.setPreserveRatio(true);
			left.setFitHeight(60.0);
			left.setFitWidth(60.0);
			left.setVisible(false);
			// add to the list
			arrowList2.add(left);

			// load arrow image
			ImageView right = new ImageView(new Image("/image/icons/right.png"));
			right.setPickOnBounds(true);
			right.setPreserveRatio(true);
			right.setFitHeight(60.0);
			right.setFitWidth(60.0);
			right.setVisible(true);
			// add to the list
			arrowList1.add(right);

			// apply scale effect 0.1%
			this.scaleEffectApply(right, 0.1f, true);
			this.scaleEffectApply(right, -0.1f, false);

			this.scaleEffectApply(left, 0.1f, true);
			this.scaleEffectApply(left, -0.1f, false);

			// arrow mouse entered
			right.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					// check which arrow was clicked
					if (arrowList1.get(0).equals(right)) {
						gridClick = 1;
					} else if (arrowList1.get(1).equals(right)) {
						gridClick = 2;
					}
				}
			});
			// arrow mouse entered
			left.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {
					// check which arrow was clicked
					if (arrowList2.get(0).equals(left)) {
						gridClick = 1;
					} else if (arrowList2.get(1).equals(left)) {
						gridClick = 2;
					}
				}
			});
			// right arrow
			right.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {

					// set arrow left visible
					left.setVisible(true);

					// list of integer -> jump to the next array element
					List<Integer> intList1 = new ArrayList<Integer>();

					// check which arrow as clicked
					int c = gridClick == 1 ? category1 : category2;

					// add id's to the list
					for (int k = 0; k < SystemThread.movieList.size(); k++) {
						DisplayMovie dMovie = SystemThread.movieList.get(k);
						if (dMovie.getIdCategory() == c) {
							intList1.add(k);
						}
					}

					// check clicks
					int clickArrowSide = 0;

					// check which arrow was clicked
					if (gridClick == 1) {
						clickArrowSide1++;
						clickArrowSide = clickArrowSide1;
					} else if (gridClick == 2) {
						clickArrowSide2++;
						clickArrowSide = clickArrowSide2;
					}

					// check if there is another element in the array according to the clicks
					// set arrow as visible or invisible
					if (categoryTotal(c) - clickArrowSide == 7) {
						right.setVisible(false);
						left.setVisible(true);
					}

					// change elements of the grid
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
			// left arrow
			left.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {

					// set arrow right visible
					right.setVisible(true);

					// list of integer -> jump to the next array element
					List<Integer> intList1 = new ArrayList<Integer>();

					// check which arrow as clicked
					int c = gridClick == 1 ? category1 : category2;

					// add id's to the list
					for (int k = 0; k < SystemThread.movieList.size(); k++) {
						DisplayMovie dMovie = SystemThread.movieList.get(k);

						if (dMovie.getIdCategory() == c) {
							intList1.add(k);
						}
					}

					// check clicks
					int clickArrowSide = 0;

					// check which arrow was clicked
					if (gridClick == 1) {
						clickArrowSide1--;
						clickArrowSide = clickArrowSide1;
					} else if (gridClick == 2) {
						clickArrowSide2--;
						clickArrowSide = clickArrowSide2;
					}

					// check if there is another element in the array according to the clicks
					if (clickArrowSide == 0) {
						right.setVisible(true);
						left.setVisible(false);
					}

					// change elements of the grid
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

			// add arrow to the pane
			gridPane.add(left, 0, 1);
			gridPane.add(right, 8, 1);
		}
	}

	// design the grid and set all the elements from the data
	@SuppressWarnings("static-access")
	private void createGrid(int category, GridPane gridPane, List<Label> labelList, List<ImageView> imageList,
			List<JFXButton> buttonList, FontController font, int big, int small, int i, DisplayMovie dMovie) {

		// content name label
		Label l = new Label(dMovie.getNameContent());
		l.setTextFill(Color.WHITE);
		l.setFont(font.getFontOpenSansRegular(small));
		l.setAlignment(Pos.CENTER);
		AnchorPane p = new AnchorPane();
		p.setLeftAnchor(l, 0.0);
		p.setRightAnchor(l, 0.0);
		p.setPrefWidth(270.0);
		p.setPrefHeight(140.0);
		// images
		ImageView v = new ImageView(
				new Image("file:///" + SystemThread.folder.getAbsolutePath() + "/image/" + dMovie.getImage() + ".jpg"));
		v.setPickOnBounds(true);
		v.setPreserveRatio(true);
		v.setFitHeight(150.0);
		v.setFitWidth(100.0);
		v.setLayoutY(30);
		p.setLeftAnchor(v, 0.0);
		p.setRightAnchor(v, 0.0);
		// buttons
		JFXButton b = new JFXButton(String.valueOf(dMovie.getPrice()));
		b.setFocusTraversable(false);
		b.setPrefWidth(50.0);
		b.setPrefHeight(20.0);
		b.setTextFill(Color.WHITE);
		p.setLeftAnchor(b, 0.0);
		p.setRightAnchor(b, 0.0);
		b.setAlignment(Pos.CENTER);
		b.setLayoutY(180);
		b.setFont(font.getFontOpenSansRegular(small));
		p.getChildren().setAll(v, l, b);

		// scale effect if the mouse entered
		this.scaleEffectApply(v, 0.1f, true);
		this.scaleEffectApply(v, -0.1f, false);

		b.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				if (buttonClickedPrice != dMovie.getIdDisplay()) {
					b.setStyle("-fx-background-color: #f77f00");
					buttonPay.setStyle("-fx-background-color: #f77f00");

					if (buttonClicked == null) {

						buttonClicked = b;
					} else {
						buttonClicked.setStyle(null);

						buttonClicked = b;
					}

					buttonClickedPrice = dMovie.getIdDisplay();

					buttonPay.setText(String.valueOf(dMovie.getPrice()) + "€");
				}
				else
				{
					buttonClickedPrice = -1;
					b.setStyle(null);
					buttonClicked = null;
					buttonPay.setText("0.00€");
					buttonPay.setStyle("-fx-background-color: linear-gradient(to bottom,#000000, #03045e); -fx-background-radius: 10;");
				}
			}
		});

		v.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				ColorAdjust cAjust = new ColorAdjust(0, -0.9, -0.5, 0);
				GaussianBlur blur = new GaussianBlur(15);
				cAjust.setInput(blur);
				anchorPane.setEffect(cAjust);
				SystemThread.titleDescription = l.getText();
				SystemThread.resume = dMovie.getDescription();
				SystemThread.fourthStage.show();
				SystemThread.anchorPaneBlur = anchorPane;
				SystemThread.imagePopup = new Image(
						"file:///" + SystemThread.folder.getAbsolutePath() + "/image/" + dMovie.getImage() + ".jpg");
			}
		});

		// add all elements to the list as we need to control them later on
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