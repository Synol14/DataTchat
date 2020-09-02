package fr.synol.datatchat.commands;

import static fr.synol.datatchat.utils.DTUtils.*;
import static fr.synol.datatchat.utils.DTAdminSetFileUtils.*;
import static fr.synol.datatchat.utils.DTGlobalSetFileUtils.*;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.synol.datatchat.MainPlugin;

public class CommandsAdmin implements CommandExecutor {
	
	private MainPlugin main;
	
	//Constructor
	public CommandsAdmin(MainPlugin mainPlugin) {
		this.main = mainPlugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		final File file = new File(main.getDataFolder() + "/data.yml");
		final YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
		setDefaultAdminSection(data, file, sender);
		
		//command 1
		if (cmd.getName().equalsIgnoreCase("addDataForAll") && args.length != 0) {
			addData(data, file, sender, args);
			return true;
		}
		//command 2
		if (cmd.getName().equalsIgnoreCase("removeDataForAll")) {
			if (args.length == 0) {
				showData(data, sender);
				return true;
			}else if (args.length != 0) {
				if (args[0].equals("all")) {
					removeAllData(data, file, sender);
					return true;
				}else {
					removeData(data, file, sender, args);
					return true;
				}
			}
		}
		/*//command 3
		if (cmd.getName().equalsIgnoreCase("setDataForAll") && args.length > 1) {
			
		}// */
		//command 4
		if (cmd.getName().equalsIgnoreCase("addDataFor") && args.length != 0) {
			addData(sender, data, file, getUUIDPlayer(data, args[0]), getString(args).replace(args[0]+" ", ""), "personalAdminData");
			return true;
		}
		//command 5
		if (cmd.getName().equalsIgnoreCase("removeDataFor") && args.length != 0) {
			if (args[1].equals("all")) {
				removeAllData(sender, data, file, getUUIDPlayer(data, args[0]), "personalAdminData");
				return true;
			}else {
				removeData(sender, data, file, getUUIDPlayer(data, args[0]), args, "personalAdminData");
				return true;
			}
		}
		/*//command 6
		if (cmd.getName().equalsIgnoreCase("setDataFor") && args.length > 1) {
			
		}// */
		//command 7
		if (cmd.getName().equalsIgnoreCase("showData") && args.length == 1) {
			showData(data, sender, getUUIDPlayer(data, args[0]));
			return true;
		}
		//command 8
		if (cmd.getName().equalsIgnoreCase("allowRemoveAdminData") && args.length >= 1) {
			if(args.length == 1) {
				if (args[0].equals("all")) {
					changeSetting("defaultAllowRemoveAdminData", 2, sender);
					changeAllowRemoveAdminDataForAll(2, data, file);
					return true;
				}else {
					changeSetting(sender, "allowRemoveAdminData", 2, getUUIDPlayer(data, args[0]), data, file);
					return true;
				}
			}else if(args.length == 2) {
				if (args[0].equals("all")) {
					if (args[1].equals("toggle")) {
						changeSetting("defaultAllowRemoveAdminData", 2, sender);
						changeAllowRemoveAdminDataForAll(2, data, file);
						return true;
					}else if (args[1].equals("true")) {
						changeSetting("defaultAllowRemoveAdminData", 1, sender);
						changeAllowRemoveAdminDataForAll(1, data, file);
						return true;
					}else if (args[1].equals("false")) {
						changeSetting("defaultAllowRemoveAdminData", 0, sender);
						changeAllowRemoveAdminDataForAll(0, data, file);
						return true;
					}
				}else {
					if (args[1].equals("toggle")) {
						changeSetting(sender, "allowRemoveAdminData", 2, getUUIDPlayer(data, args[0]), data, file);
						return true;
					}else if (args[1].equals("true")) {
						changeSetting(sender, "allowRemoveAdminData", 1, getUUIDPlayer(data, args[0]), data, file);
						return true;
					}else if (args[1].equals("false")) {
						changeSetting(sender, "allowRemoveAdminData", 0, getUUIDPlayer(data, args[0]), data, file);
						return true;
					}
				}
			}
		}
		//command 9
		if (cmd.getName().equalsIgnoreCase("setDefaultLangage") && args.length == 1) {
			String lastLangage = main.getConfig().getString("setting.defaultLangage");
			main.getConfig().set("setting.defaultLangage", args[0].toLowerCase());
			try{
				sender.sendMessage(dataTchatLabel+AdminLabel+"\n   "+noColor(main.getConfig().getString("message."+main.getConfig().getString("setting.defaultLangage")+".settingChanged").replace("%settingTitle%", "defaultLangage").replace("%value%", args[0])));
			}catch (NullPointerException e) {
				MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
				sender.sendMessage(dataTchatLabel+AdminLabel+"\n   "+noColor(main.getConfig().getString("message."+lastLangage+".settingChanged").replace("%settingTitle%", "defaultLangage").replace("%value%", args[0])));
			}
			saveConfig();
			return true;
		}
		//command 10
		if (cmd.getName().equalsIgnoreCase("saveConfig") && args.length==0) {
			main.saveConfig();
			main.saveDefaultConfig();
			main.reloadConfig();
			sender.sendMessage(dataTchatLabel+AdminLabel+"§e-> config file saved !");
			return true;
		}
		//command 11
		if (cmd.getName().equalsIgnoreCase("setMaxData") && args.length == 1) {
			String lastLangage = main.getConfig().getString("setting.defaultLangage");
			main.getConfig().set("setting.limitDataPerPlayer", getInteger(args[0]));
			try{
				sender.sendMessage(dataTchatLabel+AdminLabel+"\n   "+noColor(main.getConfig().getString("message."+main.getConfig().getString("setting.defaultLangage")+".settingChanged").replace("%settingTitle%", "limitDataPerPlayer").replace("%value%", args[0])));
			}catch (NullPointerException e) {
				MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
				sender.sendMessage(dataTchatLabel+AdminLabel+"\n   "+noColor(main.getConfig().getString("message."+lastLangage+".settingChanged").replace("%settingTitle%", "limitDataPerPlayer").replace("%value%", args[0])));
			}
			saveConfig();
			return true;
		}
		
		return false;
	}

}
