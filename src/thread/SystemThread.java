package thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import http.Http;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.DisplayMovie;

//systemthread (general thread, global)
public class SystemThread extends Thread {

	// folder data
	public static File folder = new File("data");

	// objects of all screens
	public static Stage primaryStage;
	public static Stage secondStage;
	public static Stage thirdStage;
	public static Stage fourthStage;
	public static Stage fifthStage;
	public static Stage sixthStage;
	public static Stage seventhStage;
	public static Stage eighthStage;

	// if selected movie or serie
	public static boolean typeSelected = false;
	public static int displayType = -1;

	// create a list with all the movies from the data
	public static List<DisplayMovie> movieList = new ArrayList<DisplayMovie>();

	// download status and %
	public static int isBeingDownloaded = 0;
	public static int downloaded = 0;
	public static int totalDownload = 0;

	// description
	public static String titleDescription;
	@FXML
	public static Image imagePopup;
	public static String resume;

	@FXML
	public static AnchorPane anchorPaneBlur = null;

	// id content selected
	public static int buttonClickedPrice = -1;

	// id content selected
	public static boolean resultPayment = false;

	// public static

	// thread
	public void run() {
		while (true) {
			try {
				// sleep for 100ms
				Thread.sleep(100);

				// if select movie or serie, it will download the data and imagens.
				if (typeSelected == true) {

					// http class object
					Http http = new Http();

					try {
						// check if the json was downloaded
						if (http.fileDownloaded() == true) {

							// there has to be this library or otherwise will generate an error
							org.json.simple.JSONObject jsonObject = jsonFileArray();

							// get the json array
							JSONArray data_array = new JSONObject(jsonObject).getJSONArray("display");

							// check how many imagens have to be downloaded and download them
							downloadTotal(data_array);

							// check all json data
							setJsonObject(data_array);

							// success download
							isBeingDownloaded = 3;
						}
					} catch (IOException e) {
						// error if does not find any json
						isBeingDownloaded = -1;
						System.out.println("Download JSON failed!");

					}
					// select as false to not download again or open mutiplies screen
					typeSelected = false;
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ParseException e) {

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private void setJsonObject(JSONArray data_array) {
		for (int i = 0; i < data_array.length(); i++) {

			JSONObject json_row = data_array.getJSONObject(i);

			// get only the type 0, movies.
			DisplayMovie dMovie = new DisplayMovie();
			dMovie.setType(json_row.getInt("type"));
			dMovie.setIdDisplay(json_row.getInt("idDisplay"));
			dMovie.setNameContent(json_row.getString("nameContent"));
			dMovie.setIdCategory(json_row.getInt("idCategory"));
			dMovie.setImage(json_row.getString("image"));
			dMovie.setPrice(json_row.getDouble("price"));
			dMovie.setAvailable(json_row.getInt("available"));
			dMovie.setDescription(json_row.getString("description"));
			movieList.add(dMovie);
			try {
				downloadImage(json_row);
			} catch (IOException e) {
				// in case of an error, display on the screen by changing the variable
				isBeingDownloaded = 1;
				System.out.println("Download failed: " + json_row.getString("image"));
			}

		}
	}

	// method to download image from the host
	private void downloadImage(JSONObject json_row) throws MalformedURLException, IOException, FileNotFoundException {

		// check if the file exists according to json_row (just one specific)
		if (new File(folder.getAbsolutePath() + "/image/" + json_row.getString("image") + ".jpg").isFile() == false) {

			// open url
			URL url = new URL("https://marcossan.dev/test/images/" + json_row.getString("image") + ".jpg");
			URLConnection con = url.openConnection();

			// get stream and download
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(
					folder.getAbsolutePath() + "/image/" + json_row.getString("image") + ".jpg");

			byte[] b = new byte[2048];
			int length;

			int downloaded_data = 0;

			while ((length = is.read(b)) != -1) {
				// download status
				isBeingDownloaded = 2;
				os.write(b, 0, length);
				downloaded_data += length;
				// download %
				downloaded = (int) ((downloaded_data * 100) / (con.getContentLength() * 1.0));
			}

			// total of images
			totalDownload--;
			is.close();
			os.close();

		}
	}

	// check total of images to be downloaded
	private void downloadTotal(JSONArray data_array) {
		for (int i = 0; i < data_array.length(); i++) {

			JSONObject json_row = data_array.getJSONObject(i);
			if (new File(folder.getAbsolutePath() + "/image/" + json_row.getString("image") + ".jpg")
					.isFile() == false) {
				totalDownload++;
			}
		}
	}

	// json in array
	private org.json.simple.JSONObject jsonFileArray() throws IOException, ParseException, FileNotFoundException {
		JSONParser parser = new JSONParser();

		Object obj = parser.parse(new FileReader(folder.getAbsolutePath() + "/list/data.json"));

		org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
		return jsonObject;
	}
}
