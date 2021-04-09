package http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

import thread.SystemThread;

//http class
public class Http {

	// http client builder
	private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

	//post http client
	public HttpResponse<String> post(String url, JSONObject json) {

		//send a post to an api in with json object
		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json.toString()))
				.uri(URI.create(url)).header("Content-Type", "application/json").build();

		//get response and data
		return retHttpResponse(request);
	}


	//get http client
	public HttpResponse<String> get(String url) {
		
		//send a get to an api
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();

		return retHttpResponse(request);
	}
	
	private HttpResponse<String> retHttpResponse(HttpRequest request) {
		HttpResponse<String> response = null;

		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return response;
	}

	//download data from the api host
	public boolean fileDownloaded() throws IOException {

		//url of the json
		URL url = new URL("https://marcossan.dev/api_easywatch/data/list/results.json");
		URLConnection con = url.openConnection();

		InputStream is = url.openStream();
		OutputStream os = null;

		//create a new file and delete it if exists (for auto update)
		File fi = new File(SystemThread.folder.getAbsolutePath() + "/list/data.json");

		//delete the file if exists
		if (fi.exists()) {
			fi.delete();
		}

		//streams
		os = new FileOutputStream(SystemThread.folder.getAbsolutePath() + "/list/data.json");

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			//write json
			os.write(b, 0, length);
		}

		//close pointers
		is.close();
		os.close();

		return true;

	}
}
