package constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;

import DTO.SummonerDTO;
import converter.ConvertJsonToLID;

public class ChampionIDs {

	Map<Integer, String> championData;

	public ChampionIDs() throws Exception{
		fillChampionData();
	}

	private void fillChampionData() throws Exception {

		String url = "https://na1.api.riotgames.com/lol/static-data/v3/champions?locale=en_US&dataById=true";
		url += "&api_key=";
		url += RiotAPIKey.code;

		final String USER_AGENT = "Mozilla/5.0";
		URL obj = new URL(url);

		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();

		if (responseCode == 400) {
			System.out.println("Mistakes were made");
			return;
		}

		if (responseCode == 403) {
			return;
		}
		if (responseCode == 429) {
			System.out.println("429 returned waiting...");
			Thread.sleep(10000);
			fillChampionData();
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		String inputLine;
		StringBuffer response = new StringBuffer();
		StringBuilder sb = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
			sb.append(inputLine);
		}
		in.close();

		JSONObject json = new JSONObject(sb.toString());
		championData = ConvertJsonToLID.convertChampionDataToIDMap(json);

	}
	
	public String getChampNameById(int id){
		return championData.get(id);
	}

}
