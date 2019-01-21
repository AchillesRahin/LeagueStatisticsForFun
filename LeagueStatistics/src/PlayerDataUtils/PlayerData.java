package PlayerDataUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import DTO.LeagueItemDTO;
import DTO.SummonerDTO;
import constants.RiotAPIKey;
import constants.SeasonDates;
import converter.ConvertJsonToLID;
import match.MatchReferenceDto;

public class PlayerData {
	
	public static List<MatchReferenceDto> getFullRankedHistory(long playerID, int champID, int queueID, int season) throws Exception{
		if (season == 11 || season == -1) return getFullRankedHistoryByChampionID(playerID, champID, queueID);
		else if (season == 8) return getFullRankedHistoryByDate(playerID, champID, queueID, SeasonDates.season8Start, SeasonDates.season8End);
		else if (season == 7) return getFullRankedHistoryByDate(playerID, champID, queueID, SeasonDates.season7Start, SeasonDates.season7End);
		else if (season == 6) return getFullRankedHistoryByDate(playerID, champID, queueID, SeasonDates.season6Start, SeasonDates.season6End);
		else if (season == 5) return getFullRankedHistoryByDate(playerID, champID, queueID, SeasonDates.season5Start, SeasonDates.season5End);
		return null;
	}
	
	public static List<MatchReferenceDto> getFullRankedHistoryByChampionID(long playerID, int champID, int queueID) throws Exception{
		List<MatchReferenceDto> matchList = new ArrayList<MatchReferenceDto>();
		int index = 0;
		MatchReferenceDto[] mrd = getFullRankedHistoryByChampionIDHelper(playerID, index, queueID, champID);
		matchList.addAll(Arrays.asList(mrd));
		while (mrd.length == 100){
			mrd = getFullRankedHistoryByChampionIDHelper(playerID, index+= 100, queueID, champID);
			matchList.addAll(Arrays.asList(mrd));
		}
		return matchList;
	}
	
	private static List<MatchReferenceDto> getFullRankedHistoryByDate(long playerID, int champID, int queueID, long beginDate, long endDate) throws Exception{
		List<MatchReferenceDto> matchList = new ArrayList<MatchReferenceDto>();
		while (beginDate < endDate){
			MatchReferenceDto[] mrd = getFullRankedHistoryByDateHelper(playerID, champID, queueID, beginDate, beginDate + 432000000);
			matchList.addAll(Arrays.asList(mrd));
			beginDate += 432000000;
		}
		return matchList;
	}
	
	private static MatchReferenceDto[] getFullRankedHistoryByDateHelper(long playerID, int champID, int queueID, long beginDate, long endDate) throws Exception{
		String url = "https://na1.api.riotgames.com/lol/match/v3/matchlists/by-account/";
		url += String.valueOf(playerID);
		url += "?endTime=";
		url += endDate;
		url += "&beginTime=";
		url+= beginDate;
		if (champID != -1){
			url += "&champion=";
			url += champID;
		}
		if (queueID != -1){
			url += "&queue=";
			url += queueID;
		}
		url+= "&api_key=";
		url += RiotAPIKey.code;
		final String USER_AGENT = "Mozilla/5.0";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		
		int responseCode = con.getResponseCode();
		
		if (responseCode == 403){
			return null;
		}
		if (responseCode == 429){
			System.out.println("429 returned waiting...");
			Thread.sleep(40000);
			return getFullRankedHistoryByDateHelper(playerID, champID, queueID, beginDate, endDate);
		}
		if (responseCode == 400){
			System.out.println("beginDate: " + beginDate);
			System.out.println("endDate: " + endDate);
		}
		if (responseCode == 404){
			return new MatchReferenceDto[0];
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
		JSONArray arr =  (JSONArray) json.get("matches");
		
		MatchReferenceDto[] mrDto = ConvertJsonToLID.convertJsonArrToMatchReferenceDto(arr);
		
		return mrDto;
	}
	
	
	private static MatchReferenceDto[] getFullRankedHistoryByChampionIDHelper(long playerID, int beginIndex, int queueID, int championID) throws Exception {
		String url = "https://na1.api.riotgames.com/lol/match/v3/matchlists/by-account/";
		url += String.valueOf(playerID);
		url += "?beginIndex=";
		url += beginIndex;
		if (championID != -1){
			url += "&champion=";
			url += championID;
		}
		if (queueID != -1){
			url += "&queue=";
			url += queueID;
		}
		url+= "&season=11&api_key=";
		url += RiotAPIKey.code;
		final String USER_AGENT = "Mozilla/5.0";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		
		int responseCode = con.getResponseCode();
		
		if (responseCode == 403){
			return null;
		}
		if (responseCode == 429){
			System.out.println("429 returned waiting...");
			Thread.sleep(10000);
			return getFullRankedHistoryByChampionIDHelper(playerID, beginIndex, queueID, championID);
		}
		if (responseCode == 503){
			System.out.println("503 server error waiting...");
			Thread.sleep(10000);
			return getFullRankedHistoryByChampionIDHelper(playerID, beginIndex, queueID, championID);
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
		JSONArray arr =  (JSONArray) json.get("matches");
		
		MatchReferenceDto[] mrDto = ConvertJsonToLID.convertJsonArrToMatchReferenceDto(arr);
		
		return mrDto;
		
	}
	
	
	
	//only returns the first 100 matches for a simple analysis
	public MatchReferenceDto[] getSimpleRankedHistory(long playerID) throws Exception{
		PlayerData pd = new PlayerData();
		MatchReferenceDto[] matchHistory = pd.sendGet(playerID);
		return matchHistory;
	}
	
	private MatchReferenceDto[] sendGet(long playerID) throws Exception{
		
		String url = "https://na1.api.riotgames.com/lol/match/v3/matchlists/by-account/";
		url += String.valueOf(playerID);
		url += "?beginIndex=0&queue=420&season=11&api_key=";
		url += RiotAPIKey.code;
		final String USER_AGENT = "Mozilla/5.0";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		
		int responseCode = con.getResponseCode();
		
		if (responseCode == 403){
			return null;
		}
		if (responseCode == 429){
			System.out.println("429 returned waiting...");
			Thread.sleep(10000);
			return sendGet(playerID);
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
		JSONArray arr =  (JSONArray) json.get("matches");
		
		MatchReferenceDto[] mrDto = ConvertJsonToLID.convertJsonArrToMatchReferenceDto(arr);
		
		return mrDto;
	}
	
	public String getMostPlayedRole(long playerID) throws Exception{
		MatchReferenceDto[] mrDto = getSimpleRankedHistory(playerID);
		int[] roleArray = new int[5];
		
		for (int i = 0; i < mrDto.length; i++){
			String role = mrDto[i].getLane();
			switch(role){
				case "TOP": 
					roleArray[0]++;
					break;
				case "JUNGLE":
					roleArray[1]++;
					break;
				case "MID":
					roleArray[2]++;
					break;
				case "BOTTOM":
					if (mrDto[i].getRole().equals("DUO_CARRY")){
						roleArray[3]++;
					}
					else if(mrDto[i].getRole().equals("DUO_SUPPORT")){
						roleArray[4]++;
					}
					break;
			}
		}
		
		int maxIndex = -1;
		int maxVal = -1;
		for (int i = 0; i < roleArray.length; i++){
			if (roleArray[i] > maxVal){
				maxIndex = i;
				maxVal = roleArray[i];
			}
		}
		switch(maxIndex){
		case 0:
			return "TOP";
		case 1:
			return "JUNGLE";
		case 2:
			return "MID";
		case 3:
			return "BOTTOM";
		case 4: 
			return "SUPPORT";
		}
		return null;
		
	}
	
	public static SummonerDTO convertPlayerIGNToSummoner(String ign) throws Exception {
		String url = "https://na1.api.riotgames.com/lol/summoner/v3/summoners/by-name/";
		url += ign;
		url += "?api_key=";
		url += RiotAPIKey.code;
		
		final String USER_AGENT = "Mozilla/5.0";
		URL obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		
		int responseCode = con.getResponseCode();
		
		if (responseCode == 400){
			System.out.println("Mistakes were made");
			return null;
		}
		
		if (responseCode == 403){
			return null;
		}
		if (responseCode == 429){
			System.out.println("429 returned waiting...");
			Thread.sleep(10000);
			return convertPlayerIGNToSummoner(ign);
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
		SummonerDTO sdto = ConvertJsonToLID.convertJsonObjectToSummonerDto(json);
		
		return sdto;
		
	}

	public SummonerDTO convertplayerIDtoAccountID(String playerID) throws Exception {
		String url = "https://na1.api.riotgames.com/lol/summoner/v3/summoners/";
		url += String.valueOf(playerID);
		url += "?api_key=";
		url += RiotAPIKey.code;
		final String USER_AGENT = "Mozilla/5.0";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		
		int responseCode = con.getResponseCode();
		
		if (responseCode == 403){
			return null;
		}
		if (responseCode == 429){
			System.out.println("429 returned waiting...");
			Thread.sleep(10000);
			return convertplayerIDtoAccountID(playerID);
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
		SummonerDTO sdto = ConvertJsonToLID.convertJsonObjectToSummonerDto(json);
		
		return sdto;
	}


	public static SummonerDTO convertAccountIDToSummoner(Long accountID) throws Exception {
		String url = "https://na1.api.riotgames.com/lol/summoner/v3/summoners/by-account/";
		url += accountID;
		url += "?api_key=";
		url += RiotAPIKey.code;
		
		final String USER_AGENT = "Mozilla/5.0";
		URL obj = new URL(url);
		
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		
		int responseCode = con.getResponseCode();
		
		if (responseCode == 400){
			System.out.println("Mistakes were made");
			return null;
		}
		
		if (responseCode == 403){
			return null;
		}
		if (responseCode == 429){
			System.out.println("429 returned waiting...");
			Thread.sleep(10000);
			return convertAccountIDToSummoner(accountID);
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
		SummonerDTO sdto = ConvertJsonToLID.convertJsonObjectToSummonerDto(json);
		
		return sdto;
	}
	
}
