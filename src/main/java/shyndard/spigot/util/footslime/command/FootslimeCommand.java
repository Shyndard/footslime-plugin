package shyndard.spigot.util.footslime.command;

import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import shyndard.spigot.util.footslime.API;

public class FootslimeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		System.out.println(sender.getClass());
		if((sender instanceof CommandBlock)) return false;
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