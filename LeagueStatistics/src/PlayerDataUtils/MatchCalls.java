package PlayerDataUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import DTO.MatchDto;
import DTO.ParticipantDto;
import DTO.ParticipantIdentityDto;
import constants.RiotAPIKey;
import converter.ConvertJsonToLID;
import match.MatchReferenceDto;

public class MatchCalls {


	public static MatchDto getMatch(long gameId) throws Exception{
		String url = "https://na1.api.riotgames.com/lol/match/v3/matches/" + gameId;
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
			return getMatch(gameId);
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
		
		MatchDto match = ConvertJsonToLID.convertJsonObjectToMatchDto(json);
		
		return match;
	}
	//true for winner and false for losers
	public static List<Long> getParticipantsAccountsByResult(MatchDto match, boolean winner){
		List<Long> participants = new ArrayList<Long>();
		List<Integer> currPart = new ArrayList<Integer>();
		for (int i = 0; i < match.getParticipants().size();i++){
			ParticipantDto currentParticipant = match.getParticipants().get(i);
			if (currentParticipant.getStats().isWin() == winner){
				currPart.add(currentParticipant.getParticipantId());
			}
		}
		List<ParticipantIdentityDto> identities = match.getParticipantIdentities();
		for (int i = 0; i < identities.size(); i++){
			ParticipantIdentityDto currentIdentity = identities.get(i);
			if (currPart.contains(currentIdentity.getParticipantId())){
				participants.add(currentIdentity.getPlayer().getCurrentAccountId());
			}
		}
		return participants;
	}
	
	
	public static void checkForDuplicateData(List<MatchReferenceDto> matchList){
		Set<Long> gameIDs = new HashSet<Long>();
		int count = 0;
		int countRankedSolo = 0;
		int countTwistedFlex = 0;
		for (int i = 0; i < matchList.size(); i++){
			if (matchList.get(i).getQueue() == 420){
				countRankedSolo++;
			}
			if (matchList.get(i).getQueue() == 470){
				countTwistedFlex++;
			}
			if (gameIDs.contains(matchList.get(i).getGameId())){
				count++;
			}else {
				gameIDs.add(matchList.get(i).getGameId());
			}
			if (matchList.get(i).getSeason() != 11 || matchList.get(i).getQueue() != 420){
				System.out.println(matchList.get(i).getGameId());
			}
			Date date=new Date(matchList.get(i).getTimestamp());
			System.out.println(date.toString());
		}
		System.out.println("number of duplicates is " + count);
		System.out.println("ranked count is " + countRankedSolo);
		System.out.println("current count is " + matchList.size());
		System.out.println("countTwistedFlex count is " + countTwistedFlex);
	}
}
