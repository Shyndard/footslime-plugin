package shyndard.spigot.util.footslime.command;

import java.io.IOException;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import shyndard.spigot.util.footslime.API;
import shyndard.spigot.util.footslime.entity.Match;

public class FootslimeCommand implements CommandExecutor {

	private static final String permissionDenied = "Only an operator or a command block can do that.";

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof BlockCommandSender || sender.isOp())) {
			sender.sendMessage(ChatColor.RED + permissionDenied);
			return true;
		}
		if (args.length >= 3 && "score".equalsIgnoreCase(args[0])) {
			if ("blue".equalsIgnoreCase(args[1]) || "red".equalsIgnoreCase(args[1])) {
				Integer value = 0;
				try {
					value = Integer.parseInt(args[2]);
				} catch (Exception ex) {
					sender.sendMessage(ChatColor.RED + "Bad value format (arg 3). Excepting numeric value.");
					return true;
				}
				try {
					Match match = API.getInstance().score(args[1], value);
					if (match == null) {
						sender.sendMessage(ChatColor.RED + "No match found. Is a match selected ?");
					} else {
						sender.sendMessage(ChatColor.GREEN + "Adding point success");
					}
				} catch (IOException ex) {
					ex.printStackTrace();
					sender.sendMessage(ChatColor.RED + "Error when try to add point to a team. See console logs.");
				}
				return true;
			}
		} else if (args.length == 1 && "end".equalsIgnoreCase(args[0])) {
			try {
				Match match = API.getInstance().end();
				if (match == null) {
					sender.sendMessage(ChatColor.RED + "No match found. Is a match selected ?");
				} else {
					sender.sendMessage(ChatColor.GREEN + "Ending match success");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				sender.sendMessage(ChatColor.RED + "Error when try to end a match. See console logs.");
			}
			return true;
		} else if (args.length == 1 && "start".equalsIgnoreCase(args[0])) {
			try {
				Match match = API.getInstance().start();
				if (match == null) {
					sender.sendMessage(ChatColor.RED + "No match found. Is a match selected ?");
				} else {
					sender.sendMessage(ChatColor.GREEN + "Starting match success");
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				sender.sendMessage(ChatColor.RED + "Error when try to start a match. See console logs.");
			}
			return true;
		} else if (args.length == 1 && "list".equalsIgnoreCase(args[0])) {
			sender.sendMessage("Match to do :");
			try {
				for (Match match : API.getInstance().getToDo()) {
					sender.sendMessage("id: " + match.getId() + "\t" + match.getRedTeamName() + " vs " + match.getBlueTeamName());
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				sender.sendMessage(ChatColor.RED + "Error when try to get match. See console logs.");
			}
			return true;
		} else if(args.length == 2 && "select".equalsIgnoreCase(args[0])) {
			Integer value = 0;
			try {
				value = Integer.parseInt(args[1]);
			} catch(Exception ex) {
				sender.sendMessage(ChatColor.RED + "Bad value format (arg 2). Excepting numeric value.");
				return true;
			}
			API.getInstance().setMatchId(value);
			sender.sendMessage(ChatColor.GREEN + "Select match success.");
			return true;
		}
		return false;
	}
}