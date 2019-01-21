package DTO;

public class LeagueItemDTO {
	private String rank;
	private boolean hotStreak;
	private MiniSeriesDTO miniSeries;
	private int wins;
	private boolean veteran;
	private int losses;
	private boolean freshBlood;
	private String playerOrTeamName;
	private boolean inactive;
	private String playerOrTeamId;
	private int leaguePoints;
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public boolean isHotStreak() {
		return hotStreak;
	}
	public void setHotStreak(boolean hotStreak) {
		this.hotStreak = hotStreak;
	}
	public MiniSeriesDTO getMiniSeries() {
		return miniSeries;
	}
	public void setMiniSeries(MiniSeriesDTO miniSeries) {
		this.miniSeries = miniSeries;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public boolean isVeteran() {
		return veteran;
	}
	public void setVeteran(boolean veteran) {
		this.veteran = veteran;
	}
	public int getLosses() {
		return losses;
	}
	public void setLosses(int losses) {
		this.losses = losses;
	}
	public boolean isFreshBlood() {
		return freshBlood;
	}
	public void setFreshBlood(boolean freshBlood) {
		this.freshBlood = freshBlood;
	}
	public String getPlayerOrTeamName() {
		return playerOrTeamName;
	}
	public void setPlayerOrTeamName(String playerOrTeamName) {
		this.playerOrTeamName = playerOrTeamName;
	}
	public boolean isInactive() {
		return inactive;
	}
	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}
	public String getPlayerOrTeamId() {
		return playerOrTeamId;
	}
	public void setPlayerOrTeamId(String playerOrTeamId) {
		this.playerOrTeamId = playerOrTeamId;
	}
	public int getLeaguePoints() {
		return leaguePoints;
	}
	public void setLeaguePoints(int leaguePoints) {
		this.leaguePoints = leaguePoints;
	}
	
	
	
}
