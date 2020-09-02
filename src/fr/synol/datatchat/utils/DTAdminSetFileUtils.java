package fr.synol.datatchat.utils;

import static fr.synol.datatchat.utils.DTUtils.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.synol.datatchat.MainPlugin;

import static fr.synol.datatchat.utils.DTGlobalSetFileUtils.*;

public class DTAdminSetFileUtils {
	
	public static void changeSetting(String labelSection, int state, CommandSender commandSender, String settingTitle) {
		switch(state) {
		case 0:
			MainPlugin.getInstance().getConfig().set("setting."+labelSection, false);
			saveConfig();
			commandSender.sendMessage(dataTchatLabel+AdminLabel+"\n   "+noColor(splitConfigMessage("%settingTitle%", "%value%", settingTitle, false, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
			break;
		case 1:
			MainPlugin.getInstance().getConfig().set("setting."+labelSection, true);
			saveConfig();
			commandSender.sendMessage(dataTchatLabel+AdminLabel+"\n   "+noColor(splitConfigMessage("%settingTitle%", "%value%", settingTitle, true, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
			break;
		case 2:
			boolean b = MainPlugin.getInstance().getConfig().getBoolean("setting."+labelSection);
			b = !b;
			MainPlugin.getInstance().getConfig().set("setting."+labelSection, b);
			saveConfig();
			commandSender.sendMessage(dataTchatLabel+AdminLabel+"\n   "+noColor(splitConfigMessage("%settingTitle%", "%value%", settingTitle, b, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
			break;
		}
	}
	public static void changeSetting(String labelSection, int state, CommandSender commandSender) {
		switch(state) {
		case 0:
			MainPlugin.getInstance().getConfig().set("setting."+labelSection, false);
			saveConfig();
			commandSender.sendMessage(dataTchatLabel+AdminLabel+"\n   "+noColor(splitConfigMessage("%settingTitle%", "%value%", labelSection, false, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
			break;
		case 1:
			MainPlugin.getInstance().getConfig().set("setting."+labelSection, true);
			saveConfig();
			commandSender.sendMessage(dataTchatLabel+AdminLabel+"\n   "+noColor(splitConfigMessage("%settingTitle%", "%value%", labelSection, true, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
			break;
		case 2:
			boolean b = MainPlugin.getInstance().getConfig().getBoolean("setting."+labelSection);
			b = !b;
			MainPlugin.getInstance().getConfig().set("setting."+labelSection, b);
			saveConfig();
			commandSender.sendMessage(dataTchatLabel+AdminLabel+"\n   "+noColor(splitConfigMessage("%settingTitle%", "%value%", labelSection, b, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
			break;
		}
	}
	//==================================================//
	public static void changeSetting(CommandSender commandSender, String labelSection, int state, String uuid, YamlConfiguration yamlConfiguration, File file, String settingTitle) {
		String key = "players."+uuid+".setting";
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		if (configurationSection != null) {
			switch(state) {
			case 0:
				yamlConfiguration.set(key+"."+labelSection, false);
				saveFile(yamlConfiguration, file );
				try{
					commandSender.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%value%", settingTitle, false, MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("langage")+".settingChanged"))));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					commandSender.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%value%", settingTitle, false, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
				}
				break;
			case 1:
				yamlConfiguration.set(key+"."+labelSection, true);
				saveFile(yamlConfiguration, file );
				try{
					commandSender.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%valuev", settingTitle, true, MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("langage")+".settingChanged"))));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					commandSender.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%value%", settingTitle, true, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
				}
				break;
			case 2:
				boolean b = configurationSection.getBoolean(labelSection);
				b = !b;
				yamlConfiguration.set(key+"."+labelSection, b);
				saveFile(yamlConfiguration, file);
				try{
					commandSender.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%value%", settingTitle, b, MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("langage")+".settingChanged"))));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					commandSender.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%value%", settingTitle, b, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
				}
				break;
			}
		}else {
			commandSender.sendMessage(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")));
		}
	}
	public static void changeSetting(CommandSender commandSender, String labelSection, int state, String uuid, YamlConfiguration yamlConfiguration, File file) {
		String key = "players."+uuid+".setting";
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		if (configurationSection != null) {
			switch(state) {
			case 0:
				yamlConfiguration.set(key+"."+labelSection, false);
				saveFile(yamlConfiguration, file );
				commandSender.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%value%", labelSection, false, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
				break;
			case 1:
				yamlConfiguration.set(key+"."+labelSection, true);
				saveFile(yamlConfiguration, file );
				commandSender.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%value%", labelSection, true, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
				break;
			case 2:
				boolean b = configurationSection.getBoolean(labelSection);
				b = !b;
				yamlConfiguration.set(key+"."+labelSection, b);
				saveFile(yamlConfiguration, file);
				commandSender.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%value%", labelSection, b, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
			}
		}else {
			commandSender.sendMessage(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")));
		}
	}
	//==================================================//
	public static void changeAllowRemoveAdminDataForAll(int state, YamlConfiguration yamlConfiguration, File file) {
		String key = "players";
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		if (configurationSection != null) {
			switch(state) {
			case 0:
				for (String uuid : configurationSection.getKeys(false)) {
					yamlConfiguration.set(key+"."+uuid+".setting.allowRemoveAdminData", false);
				}
				saveFile(yamlConfiguration, file );
				break;
			case 1:
				for (String uuid : configurationSection.getKeys(false)) {
					yamlConfiguration.set(key+"."+uuid+".setting.allowRemoveAdminData", true);
				}
				saveFile(yamlConfiguration, file );
				break;
			case 2:
				for (String uuid : configurationSection.getKeys(false)) {
					yamlConfiguration.set(key+"."+uuid+".setting.allowRemoveAdminData", MainPlugin.getInstance().getConfig().getBoolean("setting.defaultAllowRemoveAdminData"));
				}
				saveFile(yamlConfiguration, file);
				break;
			}
		}
	}
	//==================================================//
	public static void addData(CommandSender commandSender, YamlConfiguration yamlConfiguration, File file, Player player, String msg, String labelSection) {
		String key = "players."+player.getUniqueId();
		List<String> data = new ArrayList<>();
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		if(configurationSection == null) {
			setDefaultPlayerSection(yamlConfiguration, file, player, commandSender);
			saveFile(yamlConfiguration, file);
			data.add(msg);
			if (labelSection.equals("data")) yamlConfiguration.set(key+".msgnumber", 1);
			yamlConfiguration.set(key+"."+labelSection, data);
			if (labelSection.equals("noAdminData")) {
				saveFile(yamlConfiguration, file);
			}else {
				saveFile(yamlConfiguration, file, commandSender);
			}
		}else {
			if (labelSection.equals("data")) {
				for (String str : configurationSection.getStringList(labelSection)) {
					data.add(str);
				}
				data.add(msg);
				int msgNumber = configurationSection.getInt("msgnumber");
				yamlConfiguration.set(key+".msgnumber", msgNumber+1);
				yamlConfiguration.set(key+"."+labelSection, data);
				saveFile(yamlConfiguration, file, commandSender);
			}else {
				for (String str : configurationSection.getStringList(labelSection)) {
					data.add(str);
					}
					data.add(msg);
					yamlConfiguration.set(key+"."+labelSection, data);
					if (labelSection.equals("noAdminData")) {
						saveFile(yamlConfiguration, file);
					}else {
						saveFile(yamlConfiguration, file, commandSender);
					}
			}
			
			
		}
	}
	public static void addData(CommandSender commandSender, YamlConfiguration yamlConfiguration, File file, String uuid, String msg, String labelSection) {
		String key = "players."+uuid;
		List<String> data = new ArrayList<>();
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		if(configurationSection == null) {
			setDefaultPlayerSection(yamlConfiguration, file, uuid, commandSender);
			saveFile(yamlConfiguration, file);
			data.add(msg);
			if (labelSection.equals("data")) yamlConfiguration.set(key+".msgnumber", 1);
			yamlConfiguration.set(key+"."+labelSection, data);
			if (labelSection.equals("noAdminData")) {
				saveFile(yamlConfiguration, file);
			}else {
				saveFile(yamlConfiguration, file, commandSender);
			}
		}else {
			if (labelSection.equals("data")) {
				for (String str : configurationSection.getStringList(labelSection)) {
					data.add(str);
				}
				data.add(msg);
				int msgNumber = configurationSection.getInt("msgnumber");
				yamlConfiguration.set(key+".msgnumber", msgNumber+1);
				yamlConfiguration.set(key+"."+labelSection, data);
				saveFile(yamlConfiguration, file, commandSender);
			}else {
				for (String str : configurationSection.getStringList(labelSection)) {
					data.add(str);
					}
					data.add(msg);
					yamlConfiguration.set(key+"."+labelSection, data);
					if (labelSection.equals("noAdminData")) {
						saveFile(yamlConfiguration, file);
					}else {
						saveFile(yamlConfiguration, file, commandSender);
					}
			}
			
			
		}
	}
	public static void addData(YamlConfiguration yamlConfiguration, File file, CommandSender commandSender, String[] args) {
		String msg = getString(args);
		String key = "admin";
		List<String> data = new ArrayList<>();
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		if(configurationSection == null) {
			setDefaultAdminSection(yamlConfiguration, file, commandSender);
			saveFile(yamlConfiguration, file);
			data.add(msg);
		}else {
			for (String str : configurationSection.getStringList("data")) {
				data.add(str);
			}
			data.add(msg);
		}
		yamlConfiguration.set(key+".data", data);
		if (commandSender instanceof Player) {
			Player player = (Player)commandSender;
			saveFile(yamlConfiguration, file, player);
		}else {
			saveFile(yamlConfiguration, file, commandSender);
		}
	}
	//==================================================//
	public static void removeData(CommandSender commandSender, YamlConfiguration yamlConfiguration, File file, Player player, String[] args, String labelSection) {
		String lastData = getString(args).replace(args[0]+" ", "");
		String key = "players."+player.getUniqueId();
		List<String> data = new ArrayList<>();
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		if (configurationSection != null && configurationSection.getStringList(labelSection).size() != 0) {
			for (String str : configurationSection.getStringList(labelSection)) {
				data.add(str);
			}
			if (data.contains(lastData)) {
				data.remove(lastData);
				yamlConfiguration.set(key+"."+labelSection, data);
				if (labelSection.equals("data")) yamlConfiguration.set(key+".msgnumber", configurationSection.getInt("msgnumber")-1);
				saveFile(yamlConfiguration, file);
				commandSender.sendMessage(noColor(splitConfigMessage("%deletedData%", lastData, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".deletedData"))));
			}else {
				commandSender.sendMessage(noColor(MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".noThisData")));
			}
		}else {
			commandSender.sendMessage(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")));
		}
	}
	public static void removeData(CommandSender commandSender, YamlConfiguration yamlConfiguration, File file, String uuid, String[] args, String labelSection) {
		String lastData = getString(args).replace(args[0]+" ", "");
		String key = "players."+uuid;
		List<String> data = new ArrayList<>();
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		if (configurationSection != null && configurationSection.getStringList(labelSection).size() != 0) {
			for (String str : configurationSection.getStringList(labelSection)) {
				data.add(str);
			}
			if (data.contains(lastData)) {
				data.remove(lastData);
				yamlConfiguration.set(key+"."+labelSection, data);
				if (labelSection.equals("data")) yamlConfiguration.set(key+".msgnumber", configurationSection.getInt("msgnumber")-1);
				saveFile(yamlConfiguration, file);
				commandSender.sendMessage(noColor(splitConfigMessage("%deletedData%", lastData, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".deletedData"))));
			}else {
				commandSender.sendMessage(noColor(MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".noThisData")));
			}
		}else {
			commandSender.sendMessage(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")));
		}
	}
	public static void removeData(YamlConfiguration yamlConfiguration, File file, CommandSender commandSender, String[] args) {
		String lastData = getString(args);
		String key = "admin";
		List<String> data = new ArrayList<>();
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		if (configurationSection != null && configurationSection.getStringList("data").size() != 0) {
			for (String str : configurationSection.getStringList("data")) {
				data.add(str);
			}
			if (data.contains(lastData)) {
				data.remove(lastData);
				yamlConfiguration.set(key+".data", data);
				saveFile(yamlConfiguration, file);
				try{
					commandSender.sendMessage(noColor(splitConfigMessage("%deletedData%", lastData, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".deletedData"))));
				}catch (NullPointerException e) {}
			}else {
				try{
					commandSender.sendMessage(noColor(MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".noThisData")));
				}catch (NullPointerException e) {}
			}
		}else {
			commandSender.sendMessage(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")));
		}
	}
	//==================================================//
	public static void removeAllData(YamlConfiguration yamlConfiguration, File file, CommandSender commandSender) {
		String key = "admin";
		List<String> data = new ArrayList<>();
		data.clear();
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		if (configurationSection != null && configurationSection.getStringList("data").size() != 0) {
			yamlConfiguration.set(key+".data", data);
			saveFile(yamlConfiguration, file);
			commandSender.sendMessage(noColor(MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".deletedAllData")));
		}else {
			commandSender.sendMessage(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")));
		}
	}
		public static void removeAllData(CommandSender commandSender, YamlConfiguration yamlConfiguration, File file, String uuid, String labelSection) {
			String key = "players."+uuid;
			List<String> data = new ArrayList<>();
			data.clear();
			ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
			if (configurationSection != null && configurationSection.getStringList(labelSection).size() != 0) {
				yamlConfiguration.set(key+"."+labelSection, data);
				saveFile(yamlConfiguration, file);
				try {
					commandSender.sendMessage(noColor(MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("setting.langage")+".deletedAllData")));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					commandSender.sendMessage(noColor(MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".deletedAllData")));
				}
			}else {
				try{
					commandSender.sendMessage(getMessageNoData(configurationSection.getString("setting.langage")));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					commandSender.sendMessage(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")));
				}
			}
		}
	//==================================================//
	public static void showData(YamlConfiguration yamlConfiguration, CommandSender commandSender) {
		ConfigurationSection adminSection = yamlConfiguration.getConfigurationSection("admin");
		StringBuilder sb = new StringBuilder();
		sb.append("§4=========AdminData=========§r\n");
		if (adminSection.getStringList("data").size() != 0) {
			for (String adminData : adminSection.getStringList("data")) {
				sb.append(" §b- §r"+AdminLabel+adminData+"\n");
			}
		}else {
			sb.append("  §b No Admin Data !\n");
		}
		sb.append("§4===========================");
		commandSender.sendMessage(sb.toString());
	}
	public static void showData(YamlConfiguration yamlConfiguration, CommandSender commandSender, Player player) {
		String key = "players."+player.getUniqueId();
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		StringBuilder sb = new StringBuilder();
		sb.append(showLabelUp);
		if (configurationSection != null) {
			for (String personalAdminData : configurationSection.getStringList("personalAdminData")) {
				boolean found = false;
				for (String noAdminData : configurationSection.getStringList("noAdminData")) {
					if (personalAdminData.equals(noAdminData)) {
						found = true;
					}
				}
				if (found) {
					sb.append(" §4X "+AdminLabel+personalAdminData+"\n");
				}else {
					sb.append(" §4-> §r"+AdminLabel+personalAdminData+"\n");
				}
			}
			for (String data : configurationSection.getStringList("data")) {
				sb.append(" §b- §r"+data+"\n");
			}
		}else {
			sb.append(noColor(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage"))));
		}
		sb.append(showLabelDown);
		commandSender.sendMessage(sb.toString());
	}
	public static void showData(YamlConfiguration yamlConfiguration, CommandSender commandSender, String uuid) {
		String key = "players."+uuid;
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		StringBuilder sb = new StringBuilder();
		if (configurationSection != null) {
			sb.append(showLabelUp);
			for (String personalAdminData : configurationSection.getStringList("personalAdminData")) {
				boolean found = false;
				for (String noAdminData : configurationSection.getStringList("noAdminData")) {
					if (personalAdminData.equals(noAdminData)) {
						found = true;
					}
				}
				if (found) {
					sb.append(" §4X "+AdminLabel+personalAdminData+"\n");
				}else {
					sb.append(" §4-> §r"+AdminLabel+personalAdminData+"\n");
				}
			}
			for (String data : configurationSection.getStringList("data")) {
				sb.append(" §b- §r"+data+"\n");
			}
			sb.append(showLabelDown);
		}else {
			sb.append(noColor(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage"))));
		}
		commandSender.sendMessage(sb.toString());
	}
	
}
