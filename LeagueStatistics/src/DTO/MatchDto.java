package DTO;

import java.util.List;

public class MatchDto {
	
	private List<ParticipantDto> participants;
	private List<ParticipantIdentityDto> participantIdentities;
	private long gameDuration;

	public List<ParticipantDto> getParticipants() {
		return participants;
	}

	public void setParticipants(List<ParticipantDto> participants) {
		this.participants = participants;
	}

	public List<ParticipantIdentityDto> getParticipantIdentities() {
		return participantIdentities;
	}

	public void setParticipantIdentities(List<ParticipantIdentityDto> participantIdentities) {
		this.participantIdentities = participantIdentities;
	}

	public long getGameDuration() {
		return gameDuration;
	}

	public void setGameDuration(long gameDuration) {
		this.gameDuration = gameDuration;
	}
	
	

}
