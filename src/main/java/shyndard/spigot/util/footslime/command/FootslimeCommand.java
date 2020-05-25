package shyndard.spigot.util.footslime.command;

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
				if(API.getInstance().score(args[1])) {
					sender.sendMessage(ChatColor.GREEN + "Adding point success");
				} else {
					sender.sendMessage(ChatColor.RED + "Error when try add point to a team");
				}
				return true;
			}
		} else if(args.length == 1 && "end".equalsIgnoreCase(args[0])) {
			if(API.getInstance().end()) {
				sender.sendMessage(ChatColor.GREEN + "Ending match success");
			} else {
				sender.sendMessage(ChatColor.RED + "Error when try end match");
			}
			return true;
		} else if(args.length == 1 && "start".equalsIgnoreCase(args[0])) {
			if(API.getInstance().start()) {
				sender.sendMessage(ChatColor.GREEN + "Starting match success");
			} else {
				sender.sendMessage(ChatColor.RED + "Error when try stop start match");
			}
			return true;
		}
		return false;
	}
}