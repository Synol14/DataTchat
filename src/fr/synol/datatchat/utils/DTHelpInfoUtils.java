package fr.synol.datatchat.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.synol.datatchat.MainPlugin;

import static fr.synol.datatchat.utils.DTGlobalSetFileUtils.saveFile;
import static fr.synol.datatchat.utils.DTUtils.*;

import java.io.File;

public final class DTHelpInfoUtils {
	
	/****************
	 * Help Methods *
	 ****************/
	public static void changeLangage(Player player, String langage, YamlConfiguration yamlConfiguration, File file) {
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("players."+player.getUniqueId());
		if (configurationSection != null) {
			boolean found = false;
			for (String str : MainPlugin.getInstance().getConfig().getStringList("setting.langageList")) {
				if (str.equalsIgnoreCase(langage.toLowerCase())) {
					found = true;
				}
			}
			if (found) {
				yamlConfiguration.set("players."+player.getUniqueId()+".setting.langage", langage.toLowerCase());
				saveFile(yamlConfiguration, file);
				try{
					player.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%langage%", langage.toLowerCase(), MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("setting.langage")+".langageChanged"))));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					player.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%langage%", langage.toLowerCase(), MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".langageChanged"))));
				}
			}else {
				try{
					player.sendMessage(noColor(MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("setting.langage")+".noLangage")));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					player.sendMessage(noColor(MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".noLangage")));
				}
			}
		}
	}
	//==================================================//
	public static void showHelpPage(String page, CommandSender commandSender) {
		StringBuilder sb = new StringBuilder();
		sb.append("§a===========§bpage"+page+"§a===========§r\n");
		boolean found = false;
		for (String string : MainPlugin.getInstance().getConfig().getConfigurationSection("helpPage."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")).getKeys(false)) {
			if (string.equals(page)) found = true;
		}
		if (found) {
			for (String str : MainPlugin.getInstance().getConfig().getStringList("helpPage."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+"."+page)) {
				sb.append(noColor(str)+"\n");
			}
			sb.append(showLabelDown+"§a=");
			commandSender.sendMessage(sb.toString());
		}else {
			commandSender.sendMessage(noColor(splitConfigMessage("%page%", page, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".noPage"))));
		}
	}
	public static void showHelpPage(String page, Player player, YamlConfiguration yamlConfiguration) {
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("players."+player.getUniqueId());
		StringBuilder sb = new StringBuilder();
		sb.append("§a===========§bpage"+page+"§a===========§r\n");
		if (configurationSection != null) {
			boolean found = false;
			for (String string : MainPlugin.getInstance().getConfig().getConfigurationSection("helpPage."+configurationSection.getString("setting.langage")).getKeys(false)) {
				if (string.equals(page)) found = true;
			}
			if (found) {
				for (String str : MainPlugin.getInstance().getConfig().getStringList("helpPage."+configurationSection.getString("setting.langage")+"."+page)) {
					sb.append(noColor(str)+"\n");
				}
				sb.append(showLabelDown+"§a=");
				player.sendMessage(sb.toString());
			}else {
				try {
					player.sendMessage(noColor(splitConfigMessage("%page%", page, MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("setting.langage")+".noPage"))));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					player.sendMessage(noColor(splitConfigMessage("%page%", page, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".noPage"))));
				}
			}
		}else {
			boolean found = false;
			for (String string : MainPlugin.getInstance().getConfig().getConfigurationSection("helpPage."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")).getKeys(false)) {
				if (string.equals(page)) found = true;
			}
			if (found) {
				for (String str : MainPlugin.getInstance().getConfig().getStringList("helpPage."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+"."+page)) {
					sb.append(noColor(str)+"\n");
				}
				sb.append(showLabelDown+"§a=");
				player.sendMessage(sb.toString());
			}else {
				player.sendMessage(noColor(splitConfigMessage("%page%", page, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".noPage"))));
			}
			
		}
	}
	//==================================================//
	public static void showInfoCommand(String command, CommandSender commandSender) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n§a==========§b"+command+"§a==========§r\n");
		boolean found = false;
		for (String string : MainPlugin.getInstance().getConfig().getConfigurationSection("infoPage."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")).getKeys(false)) {
			if (string.equalsIgnoreCase(command)) found = true;
		}
		if (found) {
			for (String str : MainPlugin.getInstance().getConfig().getStringList("infoPage."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+"."+command.toLowerCase())) {
				sb.append(noColor(str)+"\n");
			}
			sb.append(getCommandLabelDown(command));
			commandSender.sendMessage(sb.toString());
		}else {
			commandSender.sendMessage(noColor(splitConfigMessage("%page%", command, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".noPage"))));
		}
	}
	public static void showInfoCommand(String command, Player player, YamlConfiguration yamlConfiguration) {
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("players."+player.getUniqueId());
		StringBuilder sb = new StringBuilder();
		sb.append("§a==========§b"+command+"§a==========§r\n");
		if (configurationSection != null) {
			boolean found = false;
			for (String string : MainPlugin.getInstance().getConfig().getConfigurationSection("infoPage."+configurationSection.getString("setting.langage")).getKeys(false)) {
				if (string.equalsIgnoreCase(command)) found = true;
			}
			if (found) {
				for (String str : MainPlugin.getInstance().getConfig().getStringList("infoPage."+configurationSection.getString("setting.langage")+"."+command.toLowerCase())) {
					sb.append(noColor(str)+"\n");
				}
				sb.append(getCommandLabelDown(command.toLowerCase()));
				player.sendMessage(sb.toString());
			}else {
				try {
					player.sendMessage(noColor(splitConfigMessage("%page%", command, MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("setting.langage")+".noPage"))));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					player.sendMessage(noColor(splitConfigMessage("%page%", command, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".noPage"))));
				}
			}
		}else {
			boolean found = false;
			for (String string : MainPlugin.getInstance().getConfig().getConfigurationSection("infoPage."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")).getKeys(false)) {
				if (string.equalsIgnoreCase(command)) found = true;
			}
			if (found) {
				for (String str : MainPlugin.getInstance().getConfig().getStringList("infoPage."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+"."+command.toLowerCase())) {
					sb.append(noColor(str)+"\n");
				}
				sb.append(getCommandLabelDown(command));
				player.sendMessage(sb.toString());
			}else {
				player.sendMessage(noColor(splitConfigMessage("%page%", command, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".noPage"))));
			}
		}
	}
	
}
