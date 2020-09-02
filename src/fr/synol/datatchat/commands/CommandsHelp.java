package fr.synol.datatchat.commands;

import static fr.synol.datatchat.utils.DTHelpInfoUtils.*;
import static fr.synol.datatchat.utils.DTGlobalSetFileUtils.*;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.synol.datatchat.MainPlugin;

public class CommandsHelp implements CommandExecutor{
	
	private MainPlugin main;
	
	public CommandsHelp(MainPlugin mainPlugin) {
		this.main = mainPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		final File file = new File(main.getDataFolder() + "/data.yml");
		final YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
		
		//command 1
		if (cmd.getName().equalsIgnoreCase("DTHelp")) {
			
			if (sender instanceof Player) {
				Player player = (Player)sender;
				if (args.length == 0) {
					setDefaultPlayerSection(data, file, player);
					showHelpPage("1", player, data);
				}else {
					setDefaultPlayerSection(data, file, player);
					showHelpPage(args[0], player, data);
				}
				
			}else {
				if (args.length == 0) {
					showHelpPage("1", sender);
				}else {
					showHelpPage(args[0], sender);
				}
			}
			return true;
		}
		//command 2
		if (cmd.getName().equalsIgnoreCase("DTInfo")&& args.length == 1) {
			if (sender instanceof Player) {
				Player player = (Player)sender;
				setDefaultPlayerSection(data, file, player);
				showInfoCommand(args[0], player, data);
			}else {
				showInfoCommand(args[0], sender);
			}
			return true;
		}
		//command 3
		if (cmd.getName().equalsIgnoreCase("setLangage") && args.length == 1 && sender instanceof Player) {
			Player player = (Player)sender;
			setDefaultPlayerSection(data, file, player);
			changeLangage(player, args[0], data, file);
			return true;
		}
		return false;
	}
}
