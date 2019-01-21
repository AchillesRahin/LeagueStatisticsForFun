package Runner;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;

import DTO.SummonerDTO;
import PlayerDataUtils.PlayerData;
import match.MatchReferenceDto;

public class HomePage {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		//getFullRankedHistory(long playerID, int champID, int queueID, int season)
		String ign = "achillesgreat";
		SummonerDTO playerID = PlayerData.convertPlayerIGNToSummoner(ign);
		List<MatchReferenceDto> matchList = PlayerData.getFullRankedHistory(playerID.getAccountId(), 267, 400, 7);
		System.out.println(matchList.size());
	}

}

