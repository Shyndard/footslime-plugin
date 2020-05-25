package shyndard.spigot.util.footslime;

import org.bukkit.plugin.java.JavaPlugin;

import shyndard.spigot.util.footslime.command.FootslimeCommand;

public class FootSlimePlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		this.getCommand("footslime").setExecutor(new FootslimeCommand());
	}
	
}