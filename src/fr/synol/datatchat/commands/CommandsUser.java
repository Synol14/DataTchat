package fr.synol.datatchat.commands;

import static fr.synol.datatchat.utils.DTUtils.*;
import static fr.synol.datatchat.utils.DTUserSetFileUtils.*;
import static fr.synol.datatchat.utils.DTGlobalSetFileUtils.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.synol.datatchat.MainPlugin;

public class CommandsUser implements CommandExecutor {
	
	private MainPlugin main;
	
	//Constructor
	public CommandsUser(MainPlugin mainPlugin) {
		this.main = mainPlugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			// création d'un fichier pour enregistrer les données
			final File file = new File(main.getDataFolder() + "/data.yml");
			final YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
			String key = "players." + player.getUniqueId();
			ConfigurationSection configurationSection = data.getConfigurationSection(key);
			setDefaultPlayerSection(data, file, player);
			
			// command 1
			if(cmd.getName().equalsIgnoreCase("addData") && args.length != 0) {
				addData(data, file, player, getString(args), "data");
				return true;
			}
			// command 2
			if(cmd.getName().equalsIgnoreCase("removeData") && args.length != 0) {
				if (args[0].equals("all")) {
					removeAllData(data, file, player, "data", true);
					return true;
				}else if (args[0].contains("%")) {
					if (configurationSection.getBoolean("setting.allowRemoveAdminData")) {
						ConfigurationSection adminSection = data.getConfigurationSection("admin");
						List<String> adminData = new ArrayList<>();
						if (adminSection != null) {
							for (String str : adminSection.getStringList("data")) {
								adminData.add(str);
							}
						}
						for (String str : configurationSection.getStringList("personalAdminData")) {
							adminData.add(str);
						}
						if (adminData.contains(getString(args).replace("%", "")) && !configurationSection.getStringList("noAdminData").contains(getString(args).replace("%", ""))) {
							addData(data, file, player, getString(args).replace("%", ""), "noAdminData");
							try{
								player.sendMessage(noColor(splitConfigMessage("%deletedData%", getString(args).replace("%", ""), MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("setting.langage")+".deletedData"))));
							}catch (NullPointerException e) {
								MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
								player.sendMessage(noColor(splitConfigMessage("%deletedData%", getString(args).replace("%", ""), MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".deletedData"))));
							}
						}else {
							try{
								player.sendMessage(noColor(MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("setting.langage")+".noThisData")));
							}catch (NullPointerException e) {
								MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
								player.sendMessage(noColor(MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".noThisData")));
							}
						}
					}else {
						player.sendMessage(dataTchatLabel+noColor(main.getConfig().getString("message."+configurationSection.getString("setting.langage")+".noPermission")));
					}
					return true;
				}else {
					removeData(data, file, player, args, "data");
					return true;
				}
			}
			// command 3
			if(cmd.getName().equalsIgnoreCase("setData") && args.length > 1) {
				
			}
			// command 4
			if(cmd.getName().equalsIgnoreCase("showMyData") && args.length == 0) {
				showData(data, player);
				return true;
			}
			// command 5
			if(cmd.getName().equalsIgnoreCase("showDataInTchat")) {
				if(args.length == 0) {
					changeSetting("enableInTchat", 2, player, data, file, "enableInTchat");
					return true;
				}else if(args.length == 1) {
					if (args[0].equals("toggle")) {
						changeSetting("enableInTchat", 2, player, data, file, "enableInTchat");
						return true;
					}else if (args[0].equals("true")) {
						changeSetting("enableInTchat", 1, player, data, file, "enableInTchat");
						return true;
					}else if (args[0].equals("false")) {
						changeSetting("enableInTchat", 0, player, data, file, "enableInTchat");
						return true;
					}
				}
			}
			// command 6
			if(cmd.getName().equalsIgnoreCase("showDataWhen")) {
				if(args.length == 1) {
					if (args[0].equals("join")) {
						changeSetting("enableWhen.join", 2, player, data, file, "showDataWhen.Join");
						return true;
					}else if (args[0].equalsIgnoreCase("worldChanged")) {
						changeSetting("enableWhen.worldChanged", 2, player, data, file, "showDataWhen.WorldChanged");
						return true;
					}else if (args[0].equalsIgnoreCase("leaveBed")) {
						changeSetting("enableWhen.leaveBed", 2, player, data, file, "showDataWhen.LeaveBed");
						return true;
					}
					else if (args[0].equalsIgnoreCase("respawn")) {
						changeSetting("enableWhen.respawn", 2, player, data, file, "showDataWhen.Respawn");
						return true;
					}
				}else if(args.length == 2) {
					if (args[0].equals("join")) {
						if (args[1].equals("toggle")) {
							changeSetting("enableWhen.join", 2, player, data, file, "showDataWhen.Join");
							return true;
						}else if (args[1].equals("true")) {
							changeSetting("enableWhen.join", 1, player, data, file, "showDataWhen.Join");
							return true;
						}else if (args[1].equals("false")) {
							changeSetting("enableWhen.join", 0, player, data, file, "showDataWhen.Join");
							return true;
						}
					}else if (args[0].equalsIgnoreCase("worldChanged")) {
						if (args[1].equals("toggle")) {
							changeSetting("enableWhen.worldChanged", 2, player, data, file, "showDataWhen.WorldChanged");
							return true;
						}else if (args[1].equals("true")) {
							changeSetting("enableWhen.worldChanged", 1, player, data, file, "showDataWhen.WorldChanged");
							return true;
						}else if (args[1].equals("false")) {
							changeSetting("enableWhen.worldChanged", 0, player, data, file, "showDataWhen.WorldChanged");
							return true;
						}
					}else if (args[0].equalsIgnoreCase("leaveBed")) {
						if (args[1].equals("toggle")) {
							changeSetting("enableWhen.leaveBed", 2, player, data, file, "showDataWhen.LeaveBed");
							return true;
						}else if (args[1].equals("true")) {
							changeSetting("enableWhen.leaveBed", 1, player, data, file, "showDataWhen.LeaveBed");
							return true;
						}else if (args[1].equals("false")) {
							changeSetting("enableWhen.leaveBed", 0, player, data, file, "showDataWhen.LeaveBed");
							return true;
						}
					}else if (args[0].equalsIgnoreCase("respawn")) {
						if (args[1].equals("toggle")) {
							changeSetting("enableWhen.respawn", 2, player, data, file, "showDataWhen.Respawn");
							return true;
						}else if (args[1].equals("true")) {
							changeSetting("enableWhen.respawn", 1, player, data, file, "showDataWhen.Respawn");
							return true;
						}else if (args[1].equals("false")) {
							changeSetting("enableWhen.respawn", 0, player, data, file, "showDataWhen.Respawn");
							return true;
						}
					}
				}
			}
			//command 7
			if (cmd.getName().equalsIgnoreCase("restorAdminData")) {
				if (args.length == 0) {
					showNoAdminData(data, player);
					return true;
				}else if (args.length != 0) {
					if (args[0].equals("all")) {
						removeAllData(data, file, player, "noAdminData", false);
						return true;
					}else {
						removeData(data, file, player, args, "noAdminData");
						return true;
					}
				}
			}
		}
		return false;
	}
}
