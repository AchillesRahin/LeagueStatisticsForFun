package Runner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DTO.MatchDto;
import DTO.ParticipantDto;
import DTO.Statistics;
import DTO.SummonerDTO;
import PlayerDataUtils.MatchCalls;
import PlayerDataUtils.PlayerData;
import constants.ChampionIDs;
import match.MatchReferenceDto;

public class RunTFMatchHistory {
	static Map<String, Statistics> fateOpponents;
	static ChampionIDs champs;

	public static void main(String[] args) throws Exception {
		
		
		champs = new ChampionIDs();
		fateOpponents = new HashMap<String, Statistics>();
		List<MatchReferenceDto> matchList;
		
//		runPlayer("nyf achilles", 210811585,4);
//		runPlayer("lolz2244", 48123814,4);
//		runPlayer("trinitosss", 205392717,4);
		//runPlayer("BluCard", 223836438,4);
//		runPlayer("biscuit crusader", 207602038,4);
		runPlayer("unlucky red card", 33716243, 4);
		
//		//nyf achilles
//		System.out.println("Starting achilles..");
//		System.out.println("getting ranked history data");
//		matchList = PlayerData.getFullRankedHistoryByChampionID(210811585, 4);
//		System.out.println("filling opponent match data");
//		fillData(fateOpponents, matchList, champs, 4);
		
		
		displayData(fateOpponents);
		
	}
	
	static void runPlayer(String player, int playerId, int champId) throws Exception{
		//nyf achilles
		System.out.println("Starting" + player + "...");
		System.out.println("getting ranked history data");
		List<MatchReferenceDto> matchList = PlayerData.getFullRankedHistoryByChampionID(playerId, champId, 420);
		System.out.println("filling opponent match data");
		fillData(fateOpponents, matchList, champs, champId);
	}
	
	
	static void displayData(Map<String, Statistics> opponents){
		int sum = 0;
		for (String champ: opponents.keySet()){
			Statistics opponent = opponents.get(champ);
			System.out.println(champ + "," + opponent.getWins() + "," + opponent.getLosses());
			sum += opponent.getLosses();
			sum += opponent.getWins();
		}
		System.out.println(sum);
	}
	
	static void fillData(Map<String, Statistics> opponents, List<MatchReferenceDto> matchList, ChampionIDs champs, int myChamp) throws Exception{
		
		for (int i = 0; i < matchList.size(); i++){
			MatchReferenceDto current = matchList.get(i);
			long gameId = current.getGameId();
			MatchDto match = MatchCalls.getMatch(gameId);
			List<ParticipantDto> participants = match.getParticipants();
			List<Integer> winners = new ArrayList<Integer>();
			List<Integer> losers = new ArrayList<Integer>();
			for (int j = 0; j < participants.size();j++){
				ParticipantDto currentParticipant = participants.get(j);
				int champId = currentParticipant.getChampionID();
				boolean win = currentParticipant.getStats().isWin();
				if (win){
					winners.add(champId);
				}else {
					losers.add(champId);
				}
			}
			if (winners.contains(myChamp)){
				for (int k = 0; k < losers.size(); k++){
					int currentChampId = losers.get(k);
					String champName = champs.getChampNameById(currentChampId);
					if (opponents.containsKey(champName)){
						Statistics stats = opponents.get(champName);
						stats.setWins(stats.getWins()+1);
					}
					else {
						Statistics stats = new Statistics();
						stats.setWins(1);
						opponents.put(champName, stats);
					}
				}
			}else {
				for (int k = 0; k < winners.size(); k++){
					int currentChampId = winners.get(k);
					String champName = champs.getChampNameById(currentChampId);
					if (opponents.containsKey(champName)){
						Statistics stats = opponents.get(champName);
						stats.setLosses(stats.getLosses() + 1);
					} else {
						Statistics stats = new Statistics();
						stats.setLosses(1);
						opponents.put(champName, stats);
					}
				}
			}
		}
		
		
	}

}
