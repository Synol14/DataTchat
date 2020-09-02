package fr.synol.datatchat.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.synol.datatchat.MainPlugin;
import static fr.synol.datatchat.utils.DTUtils.*;
import static fr.synol.datatchat.utils.DTGlobalSetFileUtils.*;

public class DTUserSetFileUtils {
	
	public static void changeSetting(String labelSection, int state, Player player, YamlConfiguration yamlConfiguration, File file, String settingTitle) {
		String key = "players."+player.getUniqueId()+".setting";
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		if (configurationSection != null) {
			switch(state) {
			case 0:
				yamlConfiguration.set(key+"."+labelSection, false);
				saveFile(yamlConfiguration, file );
				try{
					player.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%value%", settingTitle, false, MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("langage")+".settingChanged"))));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					player.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%value%", settingTitle, false, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
				}
				break;
			case 1:
				yamlConfiguration.set(key+"."+labelSection, true);
				saveFile(yamlConfiguration, file );
				try{
					player.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%valuev", settingTitle, true, MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("langage")+".settingChanged"))));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					player.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%value%", settingTitle, true, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
				}
				break;
			case 2:
				boolean b = configurationSection.getBoolean(labelSection);
				b = !b;
				yamlConfiguration.set(key+"."+labelSection, b);
				saveFile(yamlConfiguration, file);
				try{
					player.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%value%", settingTitle, b, MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("langage")+".settingChanged"))));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					player.sendMessage(dataTchatLabel+noColor(splitConfigMessage("%settingTitle%", "%value%", settingTitle, b, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".settingChanged"))));
				}
				break;
			}
		}else {
			player.sendMessage(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")));
		}
	}
	//==================================================//
	public static void addData(YamlConfiguration yamlConfiguration, File file, Player player, String msg, String labelSection) {
		String key = "players."+player.getUniqueId();
		List<String> data = new ArrayList<>();
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		if(configurationSection == null) {
			setDefaultPlayerSection(yamlConfiguration, file, player);
			saveFile(yamlConfiguration, file);
			data.add(msg);
			if (labelSection.equals("data")) yamlConfiguration.set(key+".msgnumber", 1);
			yamlConfiguration.set(key+"."+labelSection, data);
			if (labelSection.equals("noAdminData")) {
				saveFile(yamlConfiguration, file);
			}else {
				saveFile(yamlConfiguration, file, player);
			}
		}else {
			if (labelSection.equals("data")) {
				if (MainPlugin.getInstance().getConfig().getInt("setting.limitDataPerPlayer")==0 || configurationSection.getInt("msgnumber")<MainPlugin.getInstance().getConfig().getInt("setting.limitDataPerPlayer")) {
					for (String str : configurationSection.getStringList(labelSection)) {
					data.add(str);
					}
					data.add(msg);
					int msgNumber = configurationSection.getInt("msgnumber");
					yamlConfiguration.set(key+".msgnumber", msgNumber+1);
					yamlConfiguration.set(key+"."+labelSection, data);
					saveFile(yamlConfiguration, file, player);
				}else {
					try{
					player.sendMessage(dataTchatLabel+noColor(MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("setting.langage")+".maxData")));
					}catch (NullPointerException e) {
						MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
						player.sendMessage(dataTchatLabel+noColor(MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".maxData")));
					}
				}
			}else {
				for (String str : configurationSection.getStringList(labelSection)) {
					data.add(str);
					}
					data.add(msg);
					yamlConfiguration.set(key+"."+labelSection, data);
					if (labelSection.equals("noAdminData")) {
						saveFile(yamlConfiguration, file);
					}else {
						saveFile(yamlConfiguration, file, player);
					}
			}
		}
	}
	//==================================================//
	public static void removeData(YamlConfiguration yamlConfiguration, File file, Player player, String[] args, String labelSection) {
		String lastData = getString(args);
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
				try{
					player.sendMessage(noColor(splitConfigMessage("%deletedData%", lastData, MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("setting.langage")+".deletedData"))));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					player.sendMessage(noColor(splitConfigMessage("%deletedData%", lastData, MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".deletedData"))));
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
			player.sendMessage(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")));
		}
	}
	//==================================================//
	public static void removeAllData(YamlConfiguration yamlConfiguration, File file, Player player, String labelSection, boolean msg) {
		String key = "players."+player.getUniqueId();
		List<String> data = new ArrayList<>();
		data.clear();
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		if (configurationSection != null && configurationSection.getStringList(labelSection).size() != 0) {
			yamlConfiguration.set(key+"."+labelSection, data);
			saveFile(yamlConfiguration, file);
			if (msg) {
				try {
					player.sendMessage(noColor(MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("setting.langage")+".deletedAllData")));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					player.sendMessage(noColor(MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".deletedAllData")));
				}
			}else {
				try{
					player.sendMessage(noColor(MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("setting.langage")+".allRestored")));
				}catch (NullPointerException e) {
					MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
					player.sendMessage(noColor(MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".allRestored")));
				}
			}
		}else {
			try{
				player.sendMessage(getMessageNoData(configurationSection.getString("setting.langage")));
			}catch (NullPointerException e) {
				MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
				player.sendMessage(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")));
			}
		}
	}
	//==================================================//
	public static void showData(YamlConfiguration yamlConfiguration, Player player) {
		String key = "players."+player.getUniqueId();
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		ConfigurationSection adminSection = yamlConfiguration.getConfigurationSection("admin");
		StringBuilder sb = new StringBuilder();
		sb.append(showLabelUp);
		boolean noRemove = false;
		if (configurationSection != null) {
			//append personal admin data
			if (configurationSection.getBoolean("setting.allowRemoveAdminData")) {
				if (configurationSection.getStringList("personalAdminData").size()!=0) {
					for (String personalAdminData : configurationSection.getStringList("personalAdminData")) {
						boolean found = false;
						for (String noAdminData : configurationSection.getStringList("noAdminData")) {
							if (personalAdminData.equals(noAdminData)) {
								found = true;
							}
						}
						if (!found) {
							noRemove = true;
							sb.append(" §4-> §r"+AdminLabel+personalAdminData+"\n");
						}
					}
				}
			}else {
				if (configurationSection.getStringList("personalAdminData").size()!=0) {
					for (String personalAdminData : configurationSection.getStringList("personalAdminData")) {
						sb.append(" §4-> §r"+AdminLabel+personalAdminData+"\n");
					}
					noRemove = true;
				}
			}
			if (adminSection != null) {
				// append global admin data
				if (configurationSection.getBoolean("setting.allowRemoveAdminData")) {
					if (adminSection.getStringList("data").size()!=0) {
						for (String adminData : adminSection.getStringList("data")) {
							boolean found = false;
							for (String noAdminData : configurationSection.getStringList("noAdminData")) {
								if (adminData.equals(noAdminData)) {
									found = true;
								}
							}
							if (!found) {
								noRemove = true;
								sb.append(" §b- §r"+AdminLabel+adminData+"\n");
							}
						}
					}
				}else {
					if (adminSection.getStringList("data").size()!=0) {
						for (String adminData : adminSection.getStringList("data")) {
							sb.append(" §b- §r"+AdminLabel+adminData+"\n");
						}
						noRemove = true;
					}
				}
			}
			if (noRemove==false && configurationSection.getStringList("data").size()==0) {
				try {
					player.sendMessage(getMessageNoData(configurationSection.getString("setting.langage")));
				}catch (NullPointerException e) {
					player.sendMessage(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")));
				}
			}else {
				// append personal data
				for (String data : configurationSection.getStringList("data")) {
					sb.append(" §b- §r"+data+"\n");
				}
				sb.append(showLabelDown);
				player.sendMessage(sb.toString());
			}
		}else if (adminSection != null && adminSection.getStringList("data").size()!=0) {
			for (String adminData : adminSection.getStringList("data")) {
				sb.append(" §b- §r"+AdminLabel+adminData+"\n");
			}
			sb.append(showLabelDown);
			player.sendMessage(sb.toString());
		}else {
			player.sendMessage(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")));
		}
	}
	//==================================================//
		public static void showNoAdminData(YamlConfiguration yamlConfiguration, Player player) {
			ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("players."+player.getUniqueId());
			if (configurationSection != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(showLabelDown+"\n");
				if (configurationSection.getStringList("noAdminData").size() != 0) {
					for (String string : configurationSection.getStringList("noAdminData")) {
						sb.append(" §b- §r"+AdminLabel+string+"\n");
					}
					sb.append(showLabelDown);
					player.sendMessage(sb.toString());
				}else {
					try {
						player.sendMessage(getMessageNoData(configurationSection.getString("setting.langage")));
					}catch (NullPointerException e) {
						player.sendMessage(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")));
					}
				}
				
			}else {
				player.sendMessage(getMessageNoData(MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")));
			}
		}
	
}
