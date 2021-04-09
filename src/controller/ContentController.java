package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
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
	JFXButton buttonWatch;

	@FXML
	JFXButton buttonSwitch;

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

	// list of nodes as there is many movies or series
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

	// button clicked
	@FXML
	JFXButton buttonClicked = null;

	// button price clicked
	private int buttonClickedPrice = -1;

	// list of events, to remove after grid changed
	@SuppressWarnings("rawtypes")
	List<EventHandler> eventHandlerList = new ArrayList<EventHandler>();
	@SuppressWarnings("rawtypes")
	List<EventHandler> eventHandlerListImage = new ArrayList<EventHandler>();
	@SuppressWarnings("rawtypes")
	List<EventHandler> eventHandlerArrow = new ArrayList<EventHandler>();

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
			nodeEffect(node, scale, MouseEvent.MOUSE_ENTERED);
		} else {
			nodeEffect(node, scale, MouseEvent.MOUSE_EXITED);
		}
	}

	// apply effect on the nodes
	private void nodeEffect(Node node, double scale, EventType<MouseEvent> mouseEntered) {
		node.addEventHandler(mouseEntered, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				scaleEffect(node, scale);
			}

		});
	}

	// initialize
	@FXML
	protected void initialize() {

		// set arrow down as visible (control with grid)
		arrowDownImg.setVisible(true);

		// reset variables
		reset();

		// load information screen but does not show it.
		SystemThread.fifthStage = loadScreen("/view/description.fxml", "Description", false);

		// load new font
		FontController font = new FontController();

		// font size
		int big = 16;
		int medium = 14;
		int small = 12;

		// arrow up set invisible
		arrowUpImg.setVisible(false);

		// check if has next category to show the arrow down
		boolean hasCategory = false;

		// loop to check if there is another
		for (int r = 0; r < 10; r++) {
			if (categoryTotal(category2 + r) > 0) {
				hasCategory = true;
			}
		}

		// set visible false if did not find
		if (hasCategory != true) {
			arrowDownImg.setVisible(false);
		}

		// count how many and display only seven on the grid, if there is more, show
		// arrows.
		int categoryCount1 = 0;
		int categoryCount2 = 0;

		// check all data by category
		for (int i = 0; i < SystemThread.movieList.size(); i++) {

			DisplayMovie dMovie = SystemThread.movieList.get(i);

			if (dMovie.getType() == SystemThread.displayType) {

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
		}

		// label of kind of movie or serie (grid 1)
		Label t = new Label(Category.getCategory(1));
		createCategoryLabel(font, t);
		gridPane1.add(t, 0, 0);

		// label of kind of movie or serie (grid 2)
		Label c = new Label(Category.getCategory(2));
		createCategoryLabel(font, c);
		gridPane2.add(c, 0, 0);

		// create arrows
		arrowCategory(gridPane1, labelList1, imageList1, buttonList1, 1);
		arrowCategory(gridPane2, labelList2, imageList2, buttonList2, 2);

		// create event handle list to delete when changes the grid (arrow down)
		EventHandler<MouseEvent> eventHandler = arrowDownUp(true);

		// mouse clicked
		arrowDownImg.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

		// save event
		eventHandlerArrow.add(eventHandler);

		// create event handle list to delete when changes the grid (arrow up)
		eventHandler = arrowDownUp(false);

		// mouse clicked
		arrowUpImg.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

		// save event
		eventHandlerArrow.add(eventHandler);

		// focus disabled
		buttonWatch.setFocusTraversable(false);
		buttonPay.setFocusTraversable(false);
		buttonDown.setFocusTraversable(false);
		buttonClose.setFocusTraversable(false);
		buttonSwitch.setFocusTraversable(false);

		// set font on the buttons
		buttonWatch.setFont(font.getFontOpenSansRegular(medium));
		buttonPay.setFont(font.getFontOpenSansRegular(medium));
		buttonDown.setFont(font.getFontOpenSansRegular(medium));
		buttonClose.setFont(font.getFontOpenSansRegular(medium));
		buttonSwitch.setFont(font.getFontOpenSansRegular(medium));

		// set text according to the display type (movies or series)
		if (SystemThread.displayType == 0) {
			buttonSwitch.setText("SWITCH TO SERIES");
		} else {
			buttonSwitch.setText("SWITCH TO MOVIES");
		}
	}

	// reset when switch to serie or movie
	@SuppressWarnings("unchecked")
	private void reset() {

		// clear all the events
		for (int i = 0; i < eventHandlerArrow.size(); i++) {
			arrowDownImg.removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerArrow.get(i));
			arrowUpImg.removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerArrow.get(i));
		}

		// reset container as if it is starting again
		category1 = 1;
		category2 = 2;

		labelCategory.clear();
		labelList1.clear();

		arrowList1.clear();
		arrowList2.clear();

		gridPane1.getChildren().clear();
		gridPane2.getChildren().clear();

		labelList1.clear();
		labelList2.clear();

		imageList1.clear();
		imageList2.clear();

		buttonList1.clear();
		buttonList2.clear();

		eventHandlerList.clear();
		eventHandlerListImage.clear();

		clickArrowSide1 = 0;
		clickArrowSide2 = 0;
		gridClick = 0;
		jump = 2;
		buttonClicked = null;

		buttonClickedPrice = -1;
	}

	// load new screen
	private Stage loadScreen(String url, String title, boolean show) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource(url));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene1 = new Scene(root);

		Stage stage = new Stage();

		stage.initStyle(StageStyle.TRANSPARENT);
		scene1.setFill(Color.TRANSPARENT);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setResizable(false);
		stage.setScene(scene1);

		if (show) {
			stage.show();
		}

		return stage;
	}

	// create label (category)
	private void createCategoryLabel(FontController font, Label label) {
		label.setAlignment(Pos.TOP_LEFT);
		label.setTextFill(Color.WHITE);
		label.setPrefWidth(450.0);
		label.setFont(font.getFontOpenSansRegular(10));
		labelCategory.add(label);
	}

	// arrows down and up mouse click event
	private EventHandler<MouseEvent> arrowDownUp(boolean down) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@SuppressWarnings("unchecked")
			public void handle(MouseEvent event) {
				// reset arrows left in case of user changed it
				clickArrowSide1 = 0;
				clickArrowSide2 = 0;

				// set arrow down as visible if it is not
				if (down == false) {
					if (!arrowDownImg.isVisible()) {
						arrowDownImg.setVisible(true);
					}
				}

				// create a list to get all the next ids
				List<Integer> intList1 = new ArrayList<Integer>();
				List<Integer> intList2 = new ArrayList<Integer>();

				// total
				int total1 = 0;
				int total2 = 0;

				// check if there is another category
				boolean found = false;

				int a = 0;

				// check if it is down or up
				if (down == true) {

					// arrow down
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
				} else {

					// arrow up
					category2 = category1;

					a = category2;

					while (a != 0) {
						if (categoryTotal(a - 1) > 0) {
							category1 = a - 1;
							found = true;
							break;
						}
						a--;
					}
				}

				// add ids to the list
				for (int k = 0; k < SystemThread.movieList.size(); k++) {
					DisplayMovie dMovie = SystemThread.movieList.get(k);
					if (dMovie.getType() == SystemThread.displayType) {
						if (dMovie.getIdCategory() == category1) {
							intList1.add(k);
							total1++;
						} else if (dMovie.getIdCategory() == category2) {
							intList2.add(k);
							total2++;
						}
					}
				}

				// save the category if clicks on the arrow up and get back to the previous
				jump = category2;

				// block arrow if there is no category down
				boolean block = false;

				// check if its arrow up or down
				if (down == true) {
					for (int f = 1; f < 7; f++) {
						if (categoryTotal(jump + f) != 0) {
							block = true;
						}
					}
				} else {
					for (int f = 1; f < 7; f++) {
						if (categoryTotal(category1 - f) != 0) {
							block = true;
						}
					}
				}

				// check if its arrow up or down
				if (!block) {
					if (down == true) {
						arrowDownImg.setVisible(false);
					} else {
						arrowUpImg.setVisible(false);
					}
				}

				// change labels according to the new category
				labelCategory.get(0).setText(Category.getCategory(category1));
				labelCategory.get(1).setText(Category.getCategory(category2));

				// the list must be clear to not duplicate clicks

				// clear list
				clearButtonList(buttonList1);
				clearButtonList(buttonList2);
				clearImageList(imageList1);
				clearImageList(imageList2);

				// change all the content to the new category (grid 1)
				int i = 0;
				i = changeGrid(intList1, total1, i, labelList1, imageList1, buttonList1);

				// change all the content to the new category (grid 2)
				int y = 0;
				y = changeGrid(intList2, total2, y, labelList2, imageList2, buttonList2);

				// check arrows according to next category
				if (down == true) {
					if (!arrowUpImg.isVisible()) {
						arrowUpImg.setVisible(true);
					}
				}

				// hide elements if is less then 7 or more than 7 will show the arrow (grid 1)
				hideElement(y, labelList2, imageList2, buttonList2);
				hideElement(i, labelList1, imageList1, buttonList1);

				// set arrow visible if the content is more than 7 (grid 1)
				setArrowList(total2, arrowList1, 1);
				setArrowList(total1, arrowList1, 0);

				// arrows invisible
				for (int q = 0; q < arrowList2.size(); q++) {
					arrowList2.get(q).setVisible(false);
				}
			}

			// clear button event list
			@SuppressWarnings("unchecked")
			private void clearButtonList(List<JFXButton> buttonList) {
				for (int j = 0; j < buttonList.size(); j++) {

					for (int h = 0; h < eventHandlerList.size(); h++) {
						buttonList.get(j).removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerList.get(h));
					}
				}
			}

			// clear image event list
			@SuppressWarnings("unchecked")
			private void clearImageList(List<ImageView> imageList) {
				for (int j = 0; j < imageList.size(); j++) {

					for (int h = 0; h < eventHandlerList.size(); h++) {
						imageList.get(j).removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerList.get(h));
					}
				}
			}

			// set arrows visible or not
			private void setArrowList(int total, List<ImageView> arrowList, int pos) {
				if (total > 7) {
					arrowList.get(pos).setVisible(true);
				} else {
					for (int n = 0; n < arrowList.size(); n++) {
						arrowList.get(n).setVisible(false);
					}
				}
			}

			// hide elements
			private void hideElement(int y, List<Label> labelList, List<ImageView> imageList,
					List<JFXButton> buttonList) {
				for (int z = 0; z < y; z++) {
					buttonList.get(z).setVisible(true);
					labelList.get(z).setVisible(true);
					imageList.get(z).setVisible(true);
				}

				for (int d = y; d < buttonList.size(); d++) {
					buttonList.get(d).setVisible(false);
					labelList.get(d).setVisible(false);
					imageList.get(d).setVisible(false);
				}
			}

			// change grid
			private int changeGrid(List<Integer> intList, int total, int i, List<Label> labelList,
					List<ImageView> imageList, List<JFXButton> buttonList) {
				for (int j = 0; j < total; j++) {
					DisplayMovie dMovie = SystemThread.movieList.get(intList.get(j));
					if (dMovie.getType() == SystemThread.displayType) {
						if (i < 7) {
							EventHandler<MouseEvent> eventHandler = addEventHandler(dMovie, buttonList.get(i));
							buttonList.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
							eventHandlerList.add(eventHandler);

							eventHandler = addEventHandler(dMovie, labelList.get(i), new Image("file:///"
									+ SystemThread.folder.getAbsolutePath() + "/image/" + dMovie.getImage() + ".jpg"),
									imageList.get(i), buttonList.get(i));
							imageList.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
							eventHandlerListImage.add(eventHandler);

							if (buttonClickedPrice == dMovie.getIdDisplay()) {
								buttonClicked = buttonList.get(i);
								buttonList.get(i).setStyle("-fx-background-color: #f77f00");
							} else {
								buttonList.get(i).setStyle(null);
							}
							buttonList.get(i).setText(String.valueOf(dMovie.getPrice()));
							labelList.get(i).setText(String.valueOf(dMovie.getNameContent()));
							imageList.get(i).setImage(new Image("file:///" + SystemThread.folder.getAbsolutePath()
									+ "/image/" + dMovie.getImage() + ".jpg"));
							i++;
						}
					}
				}
				return i;
			}
		};
		return eventHandler;
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

			// add events
			EventHandler<MouseEvent> eventHandler = rightOrLeft(true, right);

			right.addEventHandler(MouseEvent.MOUSE_ENTERED, eventHandler);

			eventHandler = rightOrLeft(false, left);

			left.addEventHandler(MouseEvent.MOUSE_ENTERED, eventHandler);

			eventHandler = rightOrLeft(true, left, right, labelList, imageList, buttonList);

			right.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

			eventHandler = rightOrLeft(false, left, right, labelList, imageList, buttonList);

			left.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

			// add arrow to the pane
			gridPane.add(left, 0, 1);
			gridPane.add(right, 8, 1);
		}
	}

	// arrow event (left or right)
	private EventHandler<MouseEvent> rightOrLeft(boolean isRight, ImageView img) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (isRight == true) {
					if (arrowList1.get(0).equals(img)) {
						gridClick = 1;
					} else if (arrowList1.get(1).equals(img)) {
						gridClick = 2;
					}
				} else {
					if (arrowList2.get(0).equals(img)) {
						gridClick = 1;
					} else if (arrowList2.get(1).equals(img)) {
						gridClick = 2;
					}
				}
			}
		};
		return eventHandler;
	}

	// arrow event (left or right)
	private EventHandler<MouseEvent> rightOrLeft(boolean isRight, ImageView left, ImageView right,
			List<Label> labelList, List<ImageView> imageList, List<JFXButton> buttonList) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@SuppressWarnings("unchecked")
			public void handle(MouseEvent event) {
				// set arrow left visible
				if (isRight == true) {
					left.setVisible(true);
				} else {
					right.setVisible(true);
				}

				// list of integer -> jump to the next array element
				List<Integer> intList1 = new ArrayList<Integer>();

				// check which arrow as clicked
				int c = gridClick == 1 ? category1 : category2;

				// add id's to the list
				for (int k = 0; k < SystemThread.movieList.size(); k++) {
					DisplayMovie dMovie = SystemThread.movieList.get(k);
					if (dMovie.getType() == SystemThread.displayType) {
						if (dMovie.getIdCategory() == c) {
							intList1.add(k);
						}
					}
				}

				// check clicks
				int clickArrowSide = 0;

				// check which arrow was clicked
				if (isRight == true) {
					if (gridClick == 1) {
						clickArrowSide1++;
						clickArrowSide = clickArrowSide1;
					} else if (gridClick == 2) {
						clickArrowSide2++;
						clickArrowSide = clickArrowSide2;
					}
				} else {
					if (gridClick == 1) {
						clickArrowSide1--;
						clickArrowSide = clickArrowSide1;
					} else if (gridClick == 2) {
						clickArrowSide2--;
						clickArrowSide = clickArrowSide2;
					}
				}

				// check if there is another element in the array according to the clicks
				// set arrow as visible or invisible
				if (isRight == true) {
					if (categoryTotal(c) - clickArrowSide == 7) {
						right.setVisible(false);
						left.setVisible(true);
					}
				} else {
					if (clickArrowSide == 0) {
						right.setVisible(true);
						left.setVisible(false);
					}
				}

				// change elements of the grid
				int i = 0;

				for (int j = 0; j < buttonList.size(); j++) {
					buttonList.get(j).setStyle(null);
					for (int h = 0; h < eventHandlerList.size(); h++) {
						buttonList.get(j).removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerList.get(h));
					}
				}

				for (int j = 0; j < imageList.size(); j++) {
					for (int h = 0; h < eventHandlerListImage.size(); h++) {
						imageList.get(j).removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerListImage.get(h));
					}
				}

				i = 0;

				for (int j = clickArrowSide; j < intList1.size(); j++) {
					DisplayMovie dMovie = SystemThread.movieList.get(intList1.get(j));
					if (dMovie.getType() == SystemThread.displayType) {
						if (i < 7) {
							// reset handler when change screen
							EventHandler<MouseEvent> eventHandler = addEventHandler(dMovie, buttonList.get(i));
							buttonList.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
							eventHandlerList.add(eventHandler);

							eventHandler = addEventHandler(dMovie, labelList.get(i), new Image("file:///"
									+ SystemThread.folder.getAbsolutePath() + "/image/" + dMovie.getImage() + ".jpg"),
									imageList.get(i), buttonList.get(i));
							imageList.get(i).addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
							eventHandlerListImage.add(eventHandler);

							if (buttonClickedPrice == dMovie.getIdDisplay()) {
								buttonClicked = buttonList.get(i);
								buttonList.get(i).setStyle("-fx-background-color: #f77f00");
							} else {
								buttonList.get(i).setStyle(null);
							}
							buttonList.get(i).setText(String.valueOf(dMovie.getPrice()));
							labelList.get(i).setText(String.valueOf(dMovie.getNameContent()));
							imageList.get(i).setImage(new Image("file:///" + SystemThread.folder.getAbsolutePath()
									+ "/image/" + dMovie.getImage() + ".jpg"));
							i++;
						}
					}

				}
			}
		};
		return eventHandler;
	}

	// design the grid and set all the elements from the data
	@SuppressWarnings("static-access")
	private void createGrid(int category, GridPane gridPane, List<Label> labelList, List<ImageView> imageList,
			List<JFXButton> buttonList, FontController font, int big, int small, int i, DisplayMovie dMovie) {

		// content name label
		Label l = new Label(dMovie.getNameContent());
		l.setTextFill(Color.WHITE);
		l.setFont(font.getFontOpenSansRegular(small));
		BorderPane p = new BorderPane();
		p.setAlignment(l, Pos.TOP_CENTER);
		// images
		Image image = new Image(
				"file:///" + SystemThread.folder.getAbsolutePath() + "/image/" + dMovie.getImage() + ".jpg");
		ImageView v = new ImageView(image);
		v.setPickOnBounds(true);
		v.setPreserveRatio(true);
		v.setFitHeight(150.0);
		v.setFitWidth(100.0);
		// buttons
		JFXButton b = new JFXButton(String.valueOf(dMovie.getPrice()));
		b.setFocusTraversable(false);
		b.setPrefWidth(100.0);
		b.setPrefHeight(20.0);
		b.setTextFill(Color.WHITE);
		b.setAlignment(Pos.CENTER);
		b.setFont(font.getFontOpenSansRegular(small));

		p.setCenter(v);
		p.setTop(l);
		p.setBottom(b);

		// scale effect if the mouse entered
		this.scaleEffectApply(v, 0.1f, true);
		this.scaleEffectApply(v, -0.1f, false);

		// event list
		EventHandler<MouseEvent> eventHandler = addEventHandler(dMovie, b);

		b.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

		eventHandlerList.add(eventHandler);

		eventHandler = addEventHandler(dMovie, l, image, v, b);

		v.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

		eventHandlerListImage.add(eventHandler);

		// add all elements to the list as we need to control them later on
		labelList.add(l);
		imageList.add(v);
		buttonList.add(b);
		gridPane.add(p, i + 1, 1);
	}

	// image popup
	private EventHandler<MouseEvent> addEventHandler(DisplayMovie dMovie, Label l, Image image, ImageView v,
			JFXButton b) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				addBlurEffect();
				SystemThread.titleDescription = l.getText();
				SystemThread.resume = dMovie.getDescription();
				SystemThread.fifthStage.show();
				SystemThread.anchorPaneBlur = anchorPane;
				SystemThread.imagePopup = image;
			}
		};
		return eventHandler;
	}

	// blur effect
	private void addBlurEffect() {
		ColorAdjust cAjust = new ColorAdjust(0, -0.9, -0.5, 0);
		GaussianBlur blur = new GaussianBlur(15);
		cAjust.setInput(blur);
		anchorPane.setEffect(cAjust);
	}

	// style button price
	private EventHandler<MouseEvent> addEventHandler(DisplayMovie dMovie, JFXButton b) {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {

				if (buttonClickedPrice != dMovie.getIdDisplay()) {

					b.setStyle("-fx-background-color: #f77f00");
					buttonPay.setStyle("-fx-background-color: #f77f00; -fx-background-radius: 10;");

					buttonPay.setDisable(false);

					if (buttonClicked != null) {
						buttonClicked.setStyle(null);
					}

					buttonClicked = b;

					SystemThread.buttonClickedPrice = dMovie.getIdDisplay();
					buttonClickedPrice = dMovie.getIdDisplay();

					buttonPay.setText(String.valueOf(dMovie.getPrice()) + "€");
				} else {
					buttonPay.setDisable(true);
					SystemThread.buttonClickedPrice = -1;
					buttonClickedPrice = -1;
					b.setStyle(null);
					buttonClicked = null;
					buttonPay.setText("0.00€");
					buttonPay.setStyle(
							"-fx-background-color: linear-gradient(to bottom,#000000, #03045e); -fx-background-radius: 10;");
				}

			}
		};
		return eventHandler;
	}

	// return all category total
	private int categoryTotal(int category) {

		int total = 0;

		for (int i = 0; i < SystemThread.movieList.size(); i++) {

			DisplayMovie dMovie = SystemThread.movieList.get(i);

			if (dMovie.getType() == SystemThread.displayType) {
				if (dMovie.getIdCategory() == category) {
					total++;
				}
			}
		}

		return total;
	}

	// min button
	@FXML
	protected void handleMinButtonAction(ActionEvent event) {

		Stage stage = (Stage) anchorPane.getScene().getWindow();
		stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

	// x button
	@FXML
	protected void handleCloseButtonAction(ActionEvent event) {
		System.exit(1);
	}

	// pay action
	@FXML
	protected void handlePayAction(ActionEvent event) {
		addBlurEffect();

		SystemThread.anchorPaneBlur = this.anchorPane;

		SystemThread.sixthStage = loadScreen("/view/payment.fxml", "Payment", true);
	}

	// watch action
	@FXML
	protected void handleWatchAction(ActionEvent event) {
		addBlurEffect();

		SystemThread.anchorPaneBlur = this.anchorPane;

		SystemThread.seventhStage = loadScreen("/view/watch.fxml", "Payment", true);
	}

	// switch type action
	@FXML
	protected void handleSwitchAction(ActionEvent event) {
		if (SystemThread.displayType == 0) {
			SystemThread.displayType = 1;
		} else {
			SystemThread.displayType = 0;
		}

		initialize();
	}
}