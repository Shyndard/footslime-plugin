package shyndard.spigot.util.footslime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class API {

	private static final String CHARSET = "UTF-8";
	private static String host;
	private static API API;

	private String matchId;
	private Pattern pattern;

	public API() {
		pattern = Pattern.compile("(?:\"id\":\")(.*?)(?:\")");
		host = FootSlimePlugin.getPlugin().getConfig().getString("api-host");
		if (!"/".equals(host.substring(host.length() -2, host.length() -1))) {
			host=host+"/";
		}
	}

	public static API getInstance() {
		if (API == null) {
			API = new API();
		}
		return API;
	}

	public boolean start() {
		SimpleEntry<Integer, String> resultRequest = sendRequest("matchs", "POST", "{}");
		Matcher matcher = pattern.matcher(resultRequest.getValue());
		if(matcher.find()) {
			this.matchId = matcher.group().split(":")[1].replaceAll("\"", "");
		}
		return resultRequest.getKey() == 200;
	}

	public boolean end() {
		return sendRequest("matchs/" + matchId, "PUT", "{}").getKey() == 200;
	}

	public boolean score(String teamName) {
		return sendRequest("matchs/" + matchId + "/score", "PUT", "{\"team\":\"" + teamName + "\"}").getKey() == 200;
	}

	private SimpleEntry<Integer, String> sendRequest(String path, String method, String payload) {
		try {
			URL url = new URL(host + path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			connection.setConnectTimeout(5000);
			connection.setRequestProperty("Accept-Charset", CHARSET);
			connection.setRequestProperty("Content-Type", "application/json;charset=" + CHARSET);
			try (OutputStream output = connection.getOutputStream()) {
				output.write(payload.getBytes(CHARSET));
			}
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				return new AbstractMap.SimpleEntry<>(connection.getResponseCode(), response.toString());
			}
			return new AbstractMap.SimpleEntry<>(connection.getResponseCode(), "Unknown error");
		} catch (Exception ex) {
			ex.printStackTrace();
			return new AbstractMap.SimpleEntry<>(0, ex.getMessage());
		}
	}
}
