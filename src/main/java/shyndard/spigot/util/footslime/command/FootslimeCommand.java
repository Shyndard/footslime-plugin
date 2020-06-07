package shyndard.spigot.util.footslime.command;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
						sender.sendMessage(
								ChatColor.GREEN + "Adding point " + value + " to " + args[1].toLowerCase() + " team");
					}
				} catch (IOException ex) {
					ex.printStackTrace();
					sender.sendMessage(ChatColor.RED + "Error when try to add point to a team : " + ex.getMessage());
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
				sender.sendMessage(ChatColor.RED + "Error when try to end a match : " + ex.getMessage());
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
				sender.sendMessage(ChatColor.RED + "Error when try to start a match : " + ex.getMessage());
			}
			return true;
		} else if (args.length == 1 && "list".equalsIgnoreCase(args[0])) {
			sender.sendMessage("Match to do :");
			try {
				for (Match match : API.getInstance().getToDo()) {
					sender.sendMessage(
							"id: " + match.getId() + " - " + match.getRedTeamName() + " vs " + match.getBlueTeamName());
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				sender.sendMessage(ChatColor.RED + "Error when try to get match : " + ex.getMessage());
			}
			return true;
		} else if (args.length == 2 && "select".equalsIgnoreCase(args[0])) {
			Integer matchId = 0;
			try {
				matchId = Integer.parseInt(args[1]);
			} catch (Exception ex) {
				sender.sendMessage(ChatColor.RED + "Bad value format (arg 2). Excepting numeric value.");
				return true;
			}
			Match match;
			try {
				match = API.getInstance().getById(matchId);
				API.getInstance().setMatch(match);
			} catch (IOException e) {
				sender.sendMessage(ChatColor.RED + "No matching id found. Please check /footslime list");
				return true;
			}
			sender.sendMessage(
					ChatColor.GREEN + "Select match " + ChatColor.GRAY + match.getId() + ChatColor.GREEN + " success.");
			sender.sendMessage(ChatColor.BLUE + "BLUE team: " + match.getBlueTeamName() + ChatColor.GRAY
					+ " (tp members via /footslime tp blue)");
			sender.sendMessage(ChatColor.RED + "RED team: " + match.getRedTeamName() + ChatColor.GRAY
					+ " (tp members via /footslime tp red)");
			return true;
		} else if (args.length >= 2 && "tp".equalsIgnoreCase(args[0]) && sender instanceof Player) {
			if ("blue".equalsIgnoreCase(args[1])) {
				API.getInstance().getMatch().getPlayersBlueTeam().forEach(member -> {
					Player player = Bukkit.getPlayer(member.getName());
					if (player == null) {
						sender.sendMessage(ChatColor.RED + "Player " + ChatColor.BLUE + member.getName() + ChatColor.RED
								+ " not found.");
					} else {
						player.teleport((Player) sender);
					}
				});
			} else if ("red".equalsIgnoreCase(args[1])) {
				System.out.println(API.getInstance().getMatch());
				API.getInstance().getMatch().getPlayersRedTeam().forEach(member -> {
					Player player = Bukkit.getPlayer(member.getName());
					if (player == null) {
						sender.sendMessage(ChatColor.RED + "Player " + member.getName() + " not found.");
					} else {
						player.teleport((Player) sender);
					}
				});
			} else {
				sender.sendMessage(ChatColor.RED + "Tp blue or red team.");
			}
			return true;
		}
		return false;
	}
}