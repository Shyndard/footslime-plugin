package shyndard.spigot.util.footslime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;

import com.fasterxml.jackson.databind.ObjectMapper;

import shyndard.spigot.util.footslime.entity.Match;

public class API {

	private static final String CHARSET = "UTF-8";
	private static String host;
	private static String bearerToken;
	private static API API;
	private ObjectMapper mapper;

	private Match match;

	public API() {
		mapper = new ObjectMapper();
		host = FootSlimePlugin.getPlugin().getConfig().getString("api-host");
		bearerToken = "Bearer " + FootSlimePlugin.getPlugin().getConfig().getString("api-token");
		if (!"/".equals(host.substring(host.length() - 2, host.length() - 1))) {
			host = host + "/";
		}
	}

	public static API getInstance() {
		if (API == null) {
			API = new API();
		}
		return API;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Match getMatch() {
		return this.match;
	}

	public Match[] getToDo() throws IOException {
		SimpleEntry<Integer, String> result = sendRequest("matchs?started=false", "GET", null);
		if (result.getKey() == 200) {
			return mapper.readValue(result.getValue(), Match[].class);
		}
		throw new IOException("API call http code " + result.getKey());
	}

	public Match getById(int id) throws IOException {
		SimpleEntry<Integer, String> result = sendRequest("matchs/" + id, "GET", null);
		if (result.getKey() == 200) {
			return mapper.readValue(result.getValue(), Match.class);
		}
		throw new IOException("API call http code " + result.getKey());
	}

	public Match start() throws IOException {
		SimpleEntry<Integer, String> result = sendRequest("matchs/" + match.getId() + "/start", "PUT", "{}");
		if (result.getKey() == 200) {
			return mapper.readValue(result.getValue(), Match.class);
		}
		throw new IOException("API call http code " + result.getKey());
	}

	public Match end() throws IOException {
		SimpleEntry<Integer, String> result = sendRequest("matchs/" + match.getId() + "/end", "PUT", "{}");
		if (result.getKey() == 200) {
			return mapper.readValue(result.getValue(), Match.class);
		}
		throw new IOException("API call http code " + result.getKey());
	}

	public Match score(String teamName, int value) throws IOException {
		SimpleEntry<Integer, String> result = sendRequest(
				"matchs/" + match.getId() + "/score/" + teamName + "/" + value, "PUT", "{}");
		if (result.getKey() == 200) {
			return mapper.readValue(result.getValue(), Match.class);
		}
		throw new IOException("API call http code " + result.getKey());
	}

	private SimpleEntry<Integer, String> sendRequest(String path, String method, String payload) throws IOException {
		URL url = new URL(host + path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		connection.setDoOutput(true);
		connection.setConnectTimeout(5000);
		connection.setRequestProperty("Accept-Charset", CHARSET);
		connection.setRequestProperty("Content-Type", "application/json;charset=" + CHARSET);
		connection.setRequestProperty("Authorization", bearerToken);
		if (payload != null) {
			try (OutputStream output = connection.getOutputStream()) {
				output.write(payload.getBytes(CHARSET));
			}
		}
		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return new AbstractMap.SimpleEntry<>(responseCode, response.toString());
		}
		return new AbstractMap.SimpleEntry<>(responseCode, "Unknown error, code " + responseCode);
	}
}
