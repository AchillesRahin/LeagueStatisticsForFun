package converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONML;
import org.json.JSONObject;

import DTO.LeagueItemDTO;
import DTO.MatchDto;
import DTO.ParticipantDto;
import DTO.ParticipantIdentityDto;
import DTO.ParticipantStatsDto;
import DTO.PlayerDto;
import DTO.SummonerDTO;
import match.MatchReferenceDto;

public class ConvertJsonToLID {
	
	public static Map<Integer, String> convertChampionDataToIDMap(JSONObject json) throws JSONException {
		
		Map<Integer, String> championMap = new HashMap<Integer, String>();
		
		JSONObject data = json.getJSONObject("data"); 
		Iterator it = data.keys();
		while (it.hasNext()){
			String current = (String) it.next();
			String champName = (String) data.getJSONObject(current).get("name");
			int val = Integer.valueOf(current);
			championMap.put(val, champName);
		}
		return championMap;
		
	}
	
	
	public static LeagueItemDTO[] convertJsonArrToDTOArr(JSONArray arr) throws JSONException{
		
		LeagueItemDTO[] lid = new LeagueItemDTO[arr.length()];
		
		for (int i = 0; i < arr.length(); i++){
			
			lid[i] = convertJSONObjectToLeagueItemDTO(arr.getJSONObject(i));
		}
		
		return lid;
		
	}
	
	public static LeagueItemDTO convertJSONObjectToLeagueItemDTO(JSONObject json) throws JSONException{
		
		LeagueItemDTO leagueItem = new LeagueItemDTO();
		leagueItem.setFreshBlood(json.getBoolean("freshBlood"));
		leagueItem.setRank(json.getString("rank"));
		leagueItem.setHotStreak(json.getBoolean("hotStreak"));
		leagueItem.setWins(json.getInt("wins"));
		leagueItem.setVeteran(json.getBoolean("veteran"));
		leagueItem.setLosses(json.getInt("losses"));
		leagueItem.setPlayerOrTeamName(json.getString("playerOrTeamName"));
		leagueItem.setInactive(json.getBoolean("inactive"));
		leagueItem.setPlayerOrTeamId(json.getString("playerOrTeamId"));
		leagueItem.setLeaguePoints(json.getInt("leaguePoints"));
		
		
		
		return leagueItem;
	}

	public static MatchReferenceDto[] convertJsonArrToMatchReferenceDto(JSONArray arr) throws JSONException {
		// TODO Auto-generated method stub
		MatchReferenceDto[] mrDto = new MatchReferenceDto[arr.length()];
		for (int i = 0; i < arr.length(); i++){
			mrDto[i] = convertJsonObjectToMatchReference(arr.getJSONObject(i));
		}
		
		
		return mrDto;
	}
	
	public static MatchReferenceDto convertJsonObjectToMatchReference(JSONObject json) throws JSONException{
		MatchReferenceDto mrDto = new MatchReferenceDto();
		mrDto.setChampion(json.getInt("champion"));
		mrDto.setGameId(json.getLong("gameId"));
		mrDto.setLane(json.getString("lane"));
		mrDto.setPlatformId(json.getString("platformId"));
		mrDto.setQueue(json.getInt("queue"));
		mrDto.setRole(json.getString("role"));
		mrDto.setSeason(json.getInt("season"));
		mrDto.setTimestamp(json.getLong("timestamp"));
		return mrDto;
	}

	public static SummonerDTO convertJsonObjectToSummonerDto(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		SummonerDTO sdto = new SummonerDTO();
		
		sdto.setAccountId(json.getLong("accountId"));
		sdto.setId(json.getLong("id"));
		sdto.setName(json.getString("name"));
		sdto.setProfileIconId(json.getInt("profileIconId"));
		sdto.setRevisionDate(json.getLong("revisionDate"));
		sdto.setSummonerLevel(json.getLong("summonerLevel"));
		return sdto;
	}


	public static MatchDto convertJsonObjectToMatchDto(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		MatchDto match = new MatchDto();
		JSONArray participants = json.getJSONArray("participants");
		List<ParticipantDto> parts = new ArrayList<ParticipantDto>();
		for (int i = 0; i < participants.length(); i++){
			parts.add(convertJsonObjectToParticipantDto(participants.getJSONObject(i)));
		}
		match.setParticipants(parts);
		JSONArray participantIdentities = json.getJSONArray("participantIdentities");
		List<ParticipantIdentityDto> participantIdentitiesList = new ArrayList<ParticipantIdentityDto>();
		for (int i = 0; i < participantIdentities.length(); i++){
			participantIdentitiesList.add(convertJSonObjectToParticipantIdentitoDto(participantIdentities.getJSONObject(i)));
		}
		match.setParticipantIdentities(participantIdentitiesList);
		match.setGameDuration(json.getLong("gameDuration"));
		return match;
	}


	private static ParticipantIdentityDto convertJSonObjectToParticipantIdentitoDto(JSONObject json) throws JSONException {
		ParticipantIdentityDto pid = new ParticipantIdentityDto();
		pid.setParticipantId(json.getInt("participantId"));
		
		JSONObject playerObject = json.getJSONObject("player");
		PlayerDto player = convertJsonObjectToPlayerDto(playerObject);
		pid.setPlayer(player);
		
		return pid;
	}
	
	private static PlayerDto convertJsonObjectToPlayerDto(JSONObject json) throws JSONException {
		
		PlayerDto player = new PlayerDto();
		
		player.setAccountId(json.getLong("accountId"));
		player.setCurrentAccountId(json.getLong("currentAccountId"));
		player.setSummonerName(json.getString("summonerName"));
		return player;
	}


	private static ParticipantDto convertJsonObjectToParticipantDto(JSONObject json) throws JSONException {
		
		ParticipantDto participants = new ParticipantDto();
		participants.setChampionID(json.getInt("championId"));
		participants.setTeamId(json.getInt("teamId"));
		participants.setStats(convertJsonObjectToParticipantStatsDto(json.getJSONObject("stats")));
		participants.setParticipantId(json.getInt("participantId"));
		return participants;
	}


	private static ParticipantStatsDto convertJsonObjectToParticipantStatsDto(JSONObject json) throws JSONException {
		// TODO Auto-generated method stub
		
		ParticipantStatsDto stats = new ParticipantStatsDto();
		stats.setWin(json.getBoolean("win"));
		return stats;
	}

}
