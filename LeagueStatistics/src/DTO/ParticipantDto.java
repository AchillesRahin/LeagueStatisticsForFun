package DTO;

public class ParticipantDto {

	
	private int championID;
	private int teamId;
	private ParticipantStatsDto stats;
	private int participantId;

	public int getChampionID() {
		return championID;
	}

	public void setChampionID(int championID) {
		this.championID = championID;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public ParticipantStatsDto getStats() {
		return stats;
	}

	public void setStats(ParticipantStatsDto stats) {
		this.stats = stats;
	}

	public int getParticipantId() {
		return participantId;
	}

	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}
	
	
}
