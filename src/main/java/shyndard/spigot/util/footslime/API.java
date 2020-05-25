package shyndard.spigot.util.footslime;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {

	private static final String HOST = "somehost";
	private static final String CHARSET = "UTF-8";
	private static API API;

	public static API getInstance() {
		if (API == null) {
			API = new API();
		}
		return API;
	}

	public boolean score(String teamName) {
		return sendRequest("score", "PUT", "{\"team\":\"" + teamName + "\"}");
	}

	public boolean reset() {
		return sendRequest("reset", "POST", "{}");
	}

	private boolean sendRequest(String path, String method, String payload) {
		System.out.println("try to send : path=" + path + ", method =" + method + ", payload=" + payload);
		int responseCode = 400;
		try {
			URL url = new URL(HOST + path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			connection.setConnectTimeout(5000);
			connection.setRequestProperty("Accept-Charset", CHARSET);
			connection.setRequestProperty("Content-Type", "application/json;charset=" + CHARSET);
			try (OutputStream output = connection.getOutputStream()) {
				output.write(payload.getBytes(CHARSET));
			}
			responseCode = connection.getResponseCode();
		} catch (Exception ex) {
			return false;
		}
		return responseCode == 200;
	}
}
