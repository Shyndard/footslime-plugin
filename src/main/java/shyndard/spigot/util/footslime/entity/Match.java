package shyndard.spigot.util.footslime.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Match {

	private int id;
	private Date startAt;
	private Date endAt;
	private UUID redTeamId;
	private UUID blueTeamId;
	private String blueTeamName;
	private String redTeamName;
	private int redTeamPoint;
	private int blueTeamPoint;
	private List<Player> playersRedTeam;
	private List<Player> playersBlueTeam;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public UUID getRedTeamId() {
		return redTeamId;
	}

	public void setRedTeamId(UUID redTeamId) {
		this.redTeamId = redTeamId;
	}

	public UUID getBlueTeamId() {
		return blueTeamId;
	}

	public void setBlueTeamId(UUID blueTeamId) {
		this.blueTeamId = blueTeamId;
	}

	public String getBlueTeamName() {
		return blueTeamName;
	}

	public void setBlueTeamName(String blueTeamName) {
		this.blueTeamName = blueTeamName;
	}

	public String getRedTeamName() {
		return redTeamName;
	}

	public void setRedTeamName(String redTeamName) {
		this.redTeamName = redTeamName;
	}

	public int getRedTeamPoint() {
		return redTeamPoint;
	}

	public void setRedTeamPoint(int redTeamPoint) {
		this.redTeamPoint = redTeamPoint;
	}

	public int getBlueTeamPoint() {
		return blueTeamPoint;
	}

	public void setBlueTeamPoint(int blueTeamPoint) {
		this.blueTeamPoint = blueTeamPoint;
	}

	public List<Player> getPlayersRedTeam() {
		return playersRedTeam;
	}

	public Match setPlayersRedTeam(List<Player> playersRedTeam) {
		this.playersRedTeam = playersRedTeam;
		return this;
	}

	public List<Player> getPlayersBlueTeam() {
		return playersBlueTeam;
	}

	public Match setPlayersBlueTeam(List<Player> playersBlueTeam) {
		this.playersBlueTeam = playersBlueTeam;
		return this;
	}

}