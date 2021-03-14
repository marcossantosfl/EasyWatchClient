package http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;

import org.json.JSONObject;

import thread.SystemThread;

public class Http {

	// http client builder
	private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

	// post
	public HttpResponse<String> post(String url, JSONObject json) {

		HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(json.toString()))
				.uri(URI.create(url)).header("Content-Type", "application/json").build();

		HttpResponse<String> response = null;

		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return response;
	}

	// get
	public HttpResponse<String> get(String url) {
		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.header("Content-Type", "application/json").build();

		HttpResponse<String> response = null;

		try {
			response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		return response;
	}

	public boolean fileDownloaded() throws IOException {

		URL url = new URL("http://localhost/test/data/list/results.json");
		URLConnection con = url.openConnection();

		InputStream is = url.openStream();
		OutputStream os = null;

		File fi = new File(SystemThread.folder.getAbsolutePath() + "/list/data.json");

		if (fi.exists()) {
			fi.delete();
		}

		os = new FileOutputStream(SystemThread.folder.getAbsolutePath() + "/list/data.json");

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();

		return true;

	}
}
