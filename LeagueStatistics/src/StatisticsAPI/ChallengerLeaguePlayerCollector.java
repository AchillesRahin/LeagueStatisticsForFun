package StatisticsAPI;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.*;

import javax.net.ssl.HttpsURLConnection;

import DTO.LeagueItemDTO;
import DTO.LeagueListDTO;
import constants.RiotAPIKey;
import converter.ConvertJsonToLID;

public class ChallengerLeaguePlayerCollector {
	
	
	public LeagueItemDTO[] getChallengerList() throws Exception{
		ChallengerLeaguePlayerCollector clc = new ChallengerLeaguePlayerCollector();
		LeagueItemDTO[] lolDTO = clc.sendGet();
		return lolDTO;
	}
	
	
	private LeagueItemDTO[] sendGet() throws Exception {
		
		String url = "https://na1.api.riotgames.com/lol/league/v3/challengerleagues/by-queue/RANKED_SOLO_5x5?api_key=";
		url += RiotAPIKey.code;
		final String USER_AGENT = "Mozilla/5.0";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		
		if (responseCode == 403){
			return null;
		}
		if (responseCode == 429){
			System.out.println("429 returned waiting...");
			Thread.sleep(10000);
			return sendGet();
		}

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		
		
		String inputLine;
		StringBuffer response = new StringBuffer();
		StringBuilder sb = new StringBuilder();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
			sb.append(inputLine);
		}
		in.close();
		
		JSONObject json = new JSONObject(sb.toString());
		JSONArray arr =  (JSONArray) json.get("entries");
		
		LeagueItemDTO[] lolDTO = ConvertJsonToLID.convertJsonArrToDTOArr(arr);
		
		return lolDTO;

		//print result
		
		
	}
	
	

}
