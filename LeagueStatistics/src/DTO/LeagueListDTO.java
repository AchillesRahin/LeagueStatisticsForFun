package DTO;

import java.util.List;

public class LeagueListDTO {
	
	private String leagueId;
	private String tier;
	private List<LeagueItemDTO> entries;
	private String queue;
	private String name;
	public String getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	public List<LeagueItemDTO> getEntries() {
		return entries;
	}
	public void setEntries(List<LeagueItemDTO> entries) {
		this.entries = entries;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
