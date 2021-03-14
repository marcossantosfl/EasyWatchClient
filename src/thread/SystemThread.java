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
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.github.underscore.lodash.U;

import http.Http;
import javafx.stage.Stage;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.DisplayMovie;

public class SystemThread extends Thread {

	public static File folder = new File("data");

	/*
	 * Object to hide after loading.
	 */
	public static Stage primaryStage;
	public static Stage secondStage;
	public static Stage thirdStage;

	public static boolean typeSelected = false;
	public static int displayType = -1;
	public static List<DisplayMovie> movieList = new ArrayList<DisplayMovie>();

	public static int isBeingDownloaded = 0;
	public static int downloaded = 0;
	public static int totalDownload = 0;

	public void run() {
		while (true) {
			try {
				Thread.sleep(100);

				if (typeSelected == true) {

					Http http = new Http();

					try {
						if (http.fileDownloaded() == true) {

							org.json.simple.JSONObject jsonObject = jsonFileArray();

							JSONArray data_array = new JSONObject(jsonObject).getJSONArray("display");

							downloadTotal(data_array);

							for (int i = 0; i < data_array.length(); i++) {

								JSONObject json_row = data_array.getJSONObject(i);

								if (json_row.getInt("type") == 0) {
									DisplayMovie dMovie = new DisplayMovie();
									dMovie.setIdDisplay(json_row.getInt("idDisplay"));
									dMovie.setNameContent(json_row.getString("nameContent"));
									dMovie.setIdCategory(json_row.getInt("idCategory"));
									dMovie.setImage(json_row.getString("image"));
									dMovie.setPrice(json_row.getDouble("price"));
									dMovie.setAvailable(json_row.getInt("available"));
									movieList.add(dMovie);
									displayType = 0;
									try {
										downloadImage(json_row);
									} catch (IOException e) {
										isBeingDownloaded = 1;
										System.out.println("Download failed: "+json_row.getString("image"));
									}
								}
							}
							
							isBeingDownloaded = 3;
						}
					} catch (IOException e) {
						isBeingDownloaded = -1;
						System.out.println("Download JSON failed!");

					}
					typeSelected = false;
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ParseException e) {

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void downloadImage(JSONObject json_row) throws MalformedURLException, IOException, FileNotFoundException {
		if (new File(
				folder.getAbsolutePath() + "/image/" + json_row.getString("image") + ".jpg")
						.isFile() == false) {

			URL url = new URL("https://marcossan.dev/test/images/" + json_row.getString("image")
					+ ".jpg");
			URLConnection con = url.openConnection();
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(folder.getAbsolutePath() + "/image/"
					+ json_row.getString("image") + ".jpg");

			byte[] b = new byte[2048];
			int length;

			int downloaded_data = 0;
			while ((length = is.read(b)) != -1) {
				isBeingDownloaded = 2;
				os.write(b, 0, length);
				downloaded_data += length;
				downloaded = (int) ((downloaded_data * 100) / (con.getContentLength() * 1.0));
			}

			totalDownload--;
			is.close();
			os.close();
	
		}
	}

	private void downloadTotal(JSONArray data_array) {
		for (int i = 0; i < data_array.length(); i++) {

			JSONObject json_row = data_array.getJSONObject(i);
			if (new File(folder.getAbsolutePath() + "/image/" + json_row.getString("image") + ".jpg")
					.isFile() == false) {
				totalDownload++;
			}
		}
	}


	private org.json.simple.JSONObject jsonFileArray() throws IOException, ParseException, FileNotFoundException {
		JSONParser parser = new JSONParser();

		Object obj = parser.parse(new FileReader(folder.getAbsolutePath() + "/list/data.json"));

		org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) obj;
		return jsonObject;
	}
}
