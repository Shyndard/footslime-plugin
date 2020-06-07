package shyndard.spigot.util.footslime.entity;

import java.util.UUID;

public class Player {

	private String name;
	private UUID teamId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getTeamId() {
		return teamId;
	}

	public void setTeamId(UUID teamId) {
		this.teamId = teamId;
	}
}