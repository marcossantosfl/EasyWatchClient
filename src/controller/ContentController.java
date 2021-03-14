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

	private int clickArrowSide1 = 1;
	private int clickArrowSide2 = 1;

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

			ImageView right = new ImageView(new Image("/image/icons/right.png"));
			right.setPickOnBounds(true);
			right.setPreserveRatio(true);
			right.setFitHeight(80.0);
			right.setFitWidth(80.0);
			right.setVisible(true);

			this.scaleEffectApply(right, 0.1f, true);
			this.scaleEffectApply(right, -0.1f, false);

			this.scaleEffectApply(left, 0.1f, true);
			this.scaleEffectApply(left, -0.1f, false);

			right.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent e) {

					left.setVisible(true);

					List<Integer> intList1 = new ArrayList<Integer>();
					List<Integer> intList2 = new ArrayList<Integer>();

					for (int k = 0; k < SystemThread.movieList.size(); k++) {
						DisplayMovie dMovie = SystemThread.movieList.get(k);
						if (dMovie.getIdCategory() == 1) {
							intList1.add(k);
						} else if (dMovie.getIdCategory() == 2) {
							intList2.add(k);
						}
					}

					int i = 0;

					int size = category == 1 ? intList1.size() : intList2.size();

					int clickArrowSide = category == 1 ? clickArrowSide1 : clickArrowSide2;

					int max = (clickArrowSide + 6) >= size ? 0 : size;

					if (max != 0) {
						for (int j = clickArrowSide; j < max; j++) {
							DisplayMovie dMovie = SystemThread.movieList
									.get(category == 1 ? intList1.get(j) : intList2.get(j));
							if (i < 7) {
								buttonList.get(i).setText(String.valueOf(dMovie.getPrice()));
								labelList.get(i).setText(String.valueOf(dMovie.getNameContent()));
								imageList.get(i).setImage(new Image("file:///" + SystemThread.folder.getAbsolutePath()
										+ "/image/" + dMovie.getImage() + ".jpg"));
								i++;
							}
						}

						if (category == 1) {
							clickArrowSide1++;
						}
						if (category == 2) {
							clickArrowSide2++;
						}

						if ((category == 1 ? clickArrowSide1 + 6 : clickArrowSide2 + 6) >= max) {
							right.setVisible(false);
						}
					}

				}
			});

			left.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {

					right.setVisible(true);

					List<Integer> intList1 = new ArrayList<Integer>();
					List<Integer> intList2 = new ArrayList<Integer>();

					for (int k = 0; k < SystemThread.movieList.size(); k++) {
						DisplayMovie dMovie = SystemThread.movieList.get(k);
						if (dMovie.getIdCategory() == 1) {
							intList1.add(k);
						} else if (dMovie.getIdCategory() == 2) {
							intList2.add(k);
						}
					}

					int i = 0;

					int size = category == 1 ? intList1.size() : intList2.size();

					int clickArrowSide = category == 1 ? clickArrowSide1 : clickArrowSide2;

					int max = (clickArrowSide - 6) >= size ? 0 : size;

					if (max != 0) {
						for (int j = clickArrowSide - 2; max > j; j++) {
							if (j != -1) {
								if (j == 0) {
									left.setVisible(false);
								}
								DisplayMovie dMovie = SystemThread.movieList
										.get(category == 1 ? intList1.get(j) : intList2.get(j));
								if (i < 7) {
									buttonList.get(i).setText(String.valueOf(dMovie.getPrice()));
									labelList.get(i).setText(String.valueOf(dMovie.getNameContent()));
									imageList.get(i)
											.setImage(new Image("file:///" + SystemThread.folder.getAbsolutePath()
													+ "/image/" + dMovie.getImage() + ".jpg"));
									i++;
								}

							} else {

							}
						}
						if (category == 1) {
							clickArrowSide1--;
						}
						if (category == 2) {
							clickArrowSide2--;
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