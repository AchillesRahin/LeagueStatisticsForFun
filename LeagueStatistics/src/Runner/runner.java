package Runner;

import DTO.LeagueItemDTO;
import PlayerDataUtils.PlayerData;
import StatisticsAPI.ChallengerLeaguePlayerCollector;

public class runner {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		ChallengerLeaguePlayerCollector clpc = new ChallengerLeaguePlayerCollector();
		LeagueItemDTO[] lid = clpc.getChallengerList();
		
		PlayerData pd = new PlayerData();
		int[] roles = getChallengerRoleDistribution(lid, pd);
		System.out.println("Top: " + roles[0]);
		System.out.println("Jungle: " + roles[1]);
		System.out.println("Mid: " + roles[2]);
		System.out.println("ADC: " + roles[3]);
		System.out.println("Support:" + roles[4]);

	}
	
	public static int[] getChallengerRoleDistribution(LeagueItemDTO[] lid, PlayerData pd) throws Exception{
		int[] roles = new int[5];
		for (int i = 0; i < lid.length; i++){
			System.out.println("starting player " + i);
			String playerID = lid[i].getPlayerOrTeamId();
			String accountID = String.valueOf(pd.convertplayerIDtoAccountID(playerID).getAccountId());
			long playerIDInteger = Long.parseLong(accountID);
			String role = pd.getMostPlayedRole(playerIDInteger);
			System.out.println("Most player role for player " + i + " is " + role);
			switch(role){
				case "TOP":
					roles[0]++;
					break;
				case "JUNGLE":
					roles[1]++;
					break;
				case "MID":
					roles[2]++;
					break;
				case "BOTTOM":
					roles[3]++;
					break;
				case "SUPPORT":
					roles[4]++;
					break;
			}
			
		}
		return roles;
	}

}
