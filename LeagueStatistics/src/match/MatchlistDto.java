package match;

public class MatchlistDto {
	
	private MatchReferenceDto[] matches;
	private int totalGames;
	private int startIndex;
	private int endIndex;
	
	public MatchReferenceDto[] getMatches() {
		return matches;
	}
	public void setMatches(MatchReferenceDto[] matches) {
		this.matches = matches;
	}
	public int getTotalGames() {
		return totalGames;
	}
	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	
	
}
