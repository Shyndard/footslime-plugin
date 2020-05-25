package shyndard.spigot.util.footslime.command;

import org.bukkit.block.CommandBlock;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import net.md_5.bungee.api.ChatColor;
import shyndard.spigot.util.footslime.API;

public class FootslimeCommand implements CommandExecutor {

	private static final String permissionDenied = "Only a command block can do that.";
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof BlockCommandSender)) {
			sender.sendMessage(ChatColor.RED + permissionDenied);
			return true;
		}
		if(args.length >= 2 && "score".equalsIgnoreCase(args[0])) {
			if("blue".equalsIgnoreCase(args[1]) || "red".equalsIgnoreCase(args[1])) {
				API.getInstance().score(args[1]);
				return true;
			}
		} else if(args.length == 1 && "reset".equalsIgnoreCase(args[0])) {
			API.getInstance().reset();
			return true;
		}
		return false;
	}
}