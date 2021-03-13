package thread;

import java.net.http.HttpResponse;
import java.util.Map;

import com.github.underscore.lodash.U;

import http.Http;
import javafx.stage.Stage;

public class SystemThread extends Thread {

	/*
	 * Object to hide after loading.
	 */
	public static Stage primaryStage;
	public static Stage secondStage;
	public static Stage thirdStage;

	public void run() {
		while (true) {
			try {

				Thread.sleep(3000);

				Http http = new Http();

				HttpResponse<String> httpResponse = http.get("http://localhost/test/pages/user/read.php");

				if (httpResponse.statusCode() == 200) {

					Map<String, Object> jsonObject = null;

					try {
						jsonObject = U.fromJsonMap(httpResponse.body());
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					
					System.out.println(jsonObject);

				} 

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
