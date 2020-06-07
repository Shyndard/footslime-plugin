package shyndard.spigot.util.footslime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.AbstractMap;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import com.fasterxml.jackson.databind.ObjectMapper;

import shyndard.spigot.util.footslime.entity.Match;

public class API {

	private static final String CHARSET = "UTF-8";
	private static String host;
	private static String bearerToken;
	private static API API;
	private ObjectMapper mapper;

	private int matchId;

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

	public Match[] getToDo() throws IOException {
		return mapper.readValue(sendRequest("matchs?started=false", "GET", "{}").getValue(), Match[].class);
	}

	public Match start() throws IOException {
		return mapper.readValue(sendRequest("matchs/" + matchId + "/start", "PUT", "{}").getValue(), Match.class);
	}

	public Match end() throws IOException {
		return mapper.readValue(sendRequest("matchs/" + matchId + "/end", "PUT", "{}").getValue(), Match.class);
	}

	public Match score(String teamName, int value) throws IOException {
		return mapper.readValue(sendRequest("matchs/" + matchId + "/score/" + teamName + "/" + value, "PUT", "{}").getValue(), Match.class);
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
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}
}
