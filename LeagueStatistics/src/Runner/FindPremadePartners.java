package Runner;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DTO.MatchDto;
import DTO.SummonerDTO;
import PlayerDataUtils.MatchCalls;
import PlayerDataUtils.PlayerData;
import match.MatchReferenceDto;

public class FindPremadePartners {
	
	String currentPlayer;
	long girlAccountID;
	Map<Long, DuoPartner> partners;
	List<DuoPartner> partnerList;
	
	public FindPremadePartners(String ign) throws Exception{
		//SummonerDTO playerID = PlayerData.convertPlayerIGNToSummoner(ign);
		SummonerDTO playerID = new SummonerDTO();
		playerID.setAccountId(217968423);
		this.currentPlayer = ign;
		this.girlAccountID = playerID.getAccountId();
		partners = new HashMap<Long, DuoPartner>();
		partnerList = new ArrayList<DuoPartner>();
		
		//getFullRankedHistory(long playerID, int champID, int queueID, int season)
		List<MatchReferenceDto> matchList = PlayerData.getFullRankedHistory(playerID.getAccountId(), -1, 440, 11);
		MatchCalls.checkForDuplicateData(matchList);
		
		System.out.println("total matches is " + matchList.size());

		countPartners(matchList);
		addAllPartners();
		sortPartnersByDuoCount();
	}

	
	public void countPartners(List<MatchReferenceDto> history) throws Exception{
		for (int i = 0; i < history.size(); i++){
			System.out.println("starting game " + (i + 1));
			MatchReferenceDto currentMatch = history.get(i);
			long gameId = currentMatch.getGameId();
			lookThroughGame(gameId);
			System.out.println("finished game " + (i+ 1));
		}
	}
	
	public void lookThroughGame(long gameId) throws Exception{
		try {
			MatchDto currentGame = MatchCalls.getMatch(gameId);
			if (currentGame.getGameDuration() < 360) return;
			currentGame.getParticipants();
			List<Long> winningTeam = MatchCalls.getParticipantsAccountsByResult(currentGame, true);
			List<Long> losingTeam = MatchCalls.getParticipantsAccountsByResult(currentGame, false);
			if (winningTeam.contains(girlAccountID)){
				addAllPartners(winningTeam, true);
			}
			else {
				addAllPartners(losingTeam, false);
			}
		} catch (Exception e){
			System.out.println(e + " " + gameId);
		}
		
	}
	
	public void addAllPartners(List<Long> team, boolean win) throws Exception{
		for (int i = 0; i < team.size(); i++){
			long accountID = team.get(i);
			if (accountID != girlAccountID){
				
				if (!partners.containsKey(accountID)){
					DuoPartner partner = new DuoPartner(team.get(i), PlayerData.convertAccountIDToSummoner(accountID).getName());
					partners.put(accountID, partner);
				}

				if (win){
					partners.get(accountID).wins = partners.get(accountID).wins + 1;
				} else {
					partners.get(accountID).losses = partners.get(accountID).losses + 1;
				}
				partners.get(accountID).duoCount = partners.get(accountID).duoCount + 1;

			}
		}
	}
	
	public void addAllPartners(){
		for (long accountID : partners.keySet()){
			partnerList.add(partners.get(accountID));
		}
	}
	
	public void sortPartnersByDuoCount(){
		@SuppressWarnings({ "rawtypes", "unchecked" })
		Comparator<DuoPartner> comparePartner = new Comparator(){

			@Override
			public int compare(Object o1, Object o2) {
				// TODO Auto-generated method stub
				DuoPartner first = (DuoPartner) o1;
				DuoPartner second = (DuoPartner) o2;
				
				if (first.duoCount > second.duoCount){
					return 1;
				}
				else if (first.duoCount < second.duoCount){
					return -1;
				}
				return 0;
			}
		};
		Collections.sort(partnerList, comparePartner);
	}
	
	public void printPartnerByConfidence(int confidence){
		for (int i = partnerList.size() - 1; i >= 0; i--){
			if (partnerList.get(i).duoCount > confidence){
				System.out.println(partnerList.get(i).ign + " wins:" + partnerList.get(i).wins + " losses:" + partnerList.get(i).losses);
			}
		}
	}
	
	class DuoPartner {
		int duoCount;
		int wins;
		int losses;
		String ign;
		long accountID;
		public DuoPartner(long accountID, String ign){
			duoCount = 0;
			wins = 0;
			losses = 0;
			this.accountID = accountID;
			this.ign = ign;
		}
	}


}
