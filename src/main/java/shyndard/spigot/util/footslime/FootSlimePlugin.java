package shyndard.spigot.util.footslime;

import org.bukkit.plugin.java.JavaPlugin;

import shyndard.spigot.util.footslime.command.FootslimeCommand;

public class FootSlimePlugin extends JavaPlugin {
	
	private static FootSlimePlugin instance;

	@Override
	public void onEnable() {
		instance = this;
		this.getCommand("footslime").setExecutor(new FootslimeCommand());
		this.saveDefaultConfig();
	}

	public static FootSlimePlugin getPlugin() {
		return instance;
	}
	
}