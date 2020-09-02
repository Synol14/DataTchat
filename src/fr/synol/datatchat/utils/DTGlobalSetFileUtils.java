package fr.synol.datatchat.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.synol.datatchat.MainPlugin;
import static fr.synol.datatchat.utils.DTUtils.*;

public final class DTGlobalSetFileUtils {
	
	/************************
	 * Basic/global Methods *
	 ************************/
	public static void saveFile(YamlConfiguration yamlConfiguration, File file, CommandSender commandSender) {
		//essayer d'enregistrer le fichier
		try {
			yamlConfiguration.save(file);
			commandSender.sendMessage(MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".savedData").replace("&", "§"));
		} catch (IOException e) {
			//e.printStackTrace();
			commandSender.sendMessage(MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".errorSavedData").replace("&", "§"));
		}
	}
	public static void saveFile(YamlConfiguration yamlConfiguration, File file, Player player) {
		//essayer d'enregistrer le fichier
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("players."+player.getUniqueId());
		try {
			yamlConfiguration.save(file);
			try{
				player.sendMessage(MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("setting.langage")+".savedData").replace("&", "§"));
			}catch (NullPointerException e) {
				MainPlugin.getInstance().getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4 Une section de message n'est pas défini pour une langue !");
				player.sendMessage(MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".savedData").replace("&", "§"));
			}
		} catch (IOException e) {
			//e.printStackTrace();
			player.sendMessage(MainPlugin.getInstance().getConfig().getString("message."+configurationSection.getString("setting.langage")+".errorSavedData").replace("&", "§"));
		}
	}
	public static void saveFile(YamlConfiguration yamlConfiguration, File file) {
		//essayer d'enregistrer le fichier
		try {
			yamlConfiguration.save(file);
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	//==================================================//
	public static void saveConfig() {
		MainPlugin.getInstance().saveDefaultConfig();
		MainPlugin.getInstance().saveConfig();
	}
	//==================================================//
	public static void setDefaultPlayerSection(YamlConfiguration yamlConfiguration, File file, Player player) {
		String key = "players." + player.getUniqueId();
		String keyp = "players." + player.getUniqueId() + ".setting";
		String keypw = "players." + player.getUniqueId() + ".setting.enableWhen";
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		// si le joueur n'a pas pas de dossier on lui créer ses section pour les utiliser apres
		if(configurationSection == null) {
			player.sendMessage(dataTchatLabel+MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".creationFile").replace("&", "§"));
			yamlConfiguration.createSection(key+".name");
			yamlConfiguration.set(key+".name", player.getName());
			yamlConfiguration.createSection(key+".msgnumber");
			yamlConfiguration.set(key+".msgnumber", 0);
			yamlConfiguration.createSection(key+".data");
			yamlConfiguration.createSection(key+".personalAdminData");
			yamlConfiguration.createSection(key+".noAdminData");
			yamlConfiguration.createSection(keyp+".allowRemoveAdminData");
			yamlConfiguration.set(keyp+".allowRemoveAdminData", MainPlugin.getInstance().getConfig().getBoolean("setting.defaultAllowRemoveAdminData"));
			yamlConfiguration.createSection(keyp+".enableInTchat");
			yamlConfiguration.set(keyp+".enableInTchat", true);
			yamlConfiguration.createSection(keypw+".join");
			yamlConfiguration.set(keypw+".join", true);
			yamlConfiguration.createSection(keypw+".worldChanged");
			yamlConfiguration.set(keypw+".worldChanged", true);
			yamlConfiguration.createSection(keypw+".leaveBed");
			yamlConfiguration.set(keypw+".leaveBed", true);
			yamlConfiguration.createSection(keypw+".respawn");
			yamlConfiguration.set(keypw+".respawn", true);
			yamlConfiguration.createSection(keyp+".langage");
			yamlConfiguration.set(keyp+".langage", MainPlugin.getInstance().getConfig().getString("setting.defaultLangage"));
			player.sendMessage(dataTchatLabel+MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".createdFile").replace("&", "§"));
		}
		saveFile(yamlConfiguration, file);
	}
	public static void setDefaultPlayerSection(YamlConfiguration yamlConfiguration, File file, Player player, CommandSender commandSender) {
		String key = "players." + player.getUniqueId();
		String keyp = "players." + player.getUniqueId() + ".setting";
		String keypw = "players." + player.getUniqueId() + ".setting.enableWhen";
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		// si le joueur n'a pas pas de dossier on lui créer ses section pour les utiliser apres
		if(configurationSection == null) {
			commandSender.sendMessage(dataTchatLabel+MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".creationFile").replace("&", "§"));
			yamlConfiguration.createSection(key+".name");
			yamlConfiguration.set(key+".name", player.getName());
			yamlConfiguration.createSection(key+".msgnumber");
			yamlConfiguration.set(key+".msgnumber", 0);
			yamlConfiguration.createSection(key+".data");
			yamlConfiguration.createSection(key+".personalAdminData");
			yamlConfiguration.createSection(key+".noAdminData");
			yamlConfiguration.createSection(keyp+".allowRemoveAdminData");
			yamlConfiguration.set(keyp+".allowRemoveAdminData", MainPlugin.getInstance().getConfig().getBoolean("setting.defaultAllowRemoveAdminData"));
			yamlConfiguration.createSection(keyp+".enableInTchat");
			yamlConfiguration.set(keyp+".enableInTchat", true);
			yamlConfiguration.createSection(keypw+".join");
			yamlConfiguration.set(keypw+".join", true);
			yamlConfiguration.createSection(keypw+".worldChanged");
			yamlConfiguration.set(keypw+".worldChanged", true);
			yamlConfiguration.createSection(keypw+".leaveBed");
			yamlConfiguration.set(keypw+".leaveBed", true);
			yamlConfiguration.createSection(keypw+".respawn");
			yamlConfiguration.set(keypw+".respawn", true);
			yamlConfiguration.createSection(keyp+".langage");
			yamlConfiguration.set(keyp+".langage", MainPlugin.getInstance().getConfig().getString("setting.defaultLangage"));
			commandSender.sendMessage(dataTchatLabel+MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".createdFile").replace("&", "§"));
		}
		saveFile(yamlConfiguration, file);
	}
	public static void setDefaultPlayerSection(YamlConfiguration yamlConfiguration, File file, String uuid, CommandSender commandSender) {
		String key = "players." + uuid;
		String keyp = "players." + uuid + ".setting";
		String keypw = "players." + uuid + ".setting.enableWhen";
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		// si le joueur n'a pas pas de dossier on lui créer ses section pour les utiliser apres
		if(configurationSection == null) {
			commandSender.sendMessage(dataTchatLabel+MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".creationFile").replace("&", "§"));
			yamlConfiguration.createSection(key+".name");
			yamlConfiguration.set(key+".name", "");
			yamlConfiguration.createSection(key+".msgnumber");
			yamlConfiguration.set(key+".msgnumber", 0);
			yamlConfiguration.createSection(key+".data");
			yamlConfiguration.createSection(key+".personalAdminData");
			yamlConfiguration.createSection(key+".noAdminData");
			yamlConfiguration.createSection(keyp+".allowRemoveAdminData");
			yamlConfiguration.set(keyp+".allowRemoveAdminData", MainPlugin.getInstance().getConfig().getBoolean("setting.defaultAllowRemoveAdminData"));
			yamlConfiguration.createSection(keyp+".enableInTchat");
			yamlConfiguration.set(keyp+".enableInTchat", true);
			yamlConfiguration.createSection(keypw+".join");
			yamlConfiguration.set(keypw+".join", true);
			yamlConfiguration.createSection(keypw+".worldChanged");
			yamlConfiguration.set(keypw+".worldChanged", true);
			yamlConfiguration.createSection(keypw+".leaveBed");
			yamlConfiguration.set(keypw+".leaveBed", true);
			yamlConfiguration.createSection(keypw+".respawn");
			yamlConfiguration.set(keypw+".respawn", true);
			yamlConfiguration.createSection(keyp+".langage");
			yamlConfiguration.set(keyp+".langage", MainPlugin.getInstance().getConfig().getString("setting.defaultLangage"));
			commandSender.sendMessage(dataTchatLabel+MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".createdFile").replace("&", "§"));
		}
		saveFile(yamlConfiguration, file);
	}
	//==================================================//
	public static void setDefaultAdminSection(YamlConfiguration yamlConfiguration, File file, CommandSender commandSender) {
		String key = "admin";
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection(key);
		if(configurationSection == null) {
			commandSender.sendMessage(dataTchatLabel+AdminLabel+MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".creationFile").replace("&", "§"));
			yamlConfiguration.createSection(key+".data");
			commandSender.sendMessage(dataTchatLabel+AdminLabel+MainPlugin.getInstance().getConfig().getString("message."+MainPlugin.getInstance().getConfig().getString("setting.defaultLangage")+".createdFile").replace("&", "§"));
		}
		saveFile(yamlConfiguration, file);
	}
	//==================================================//
	public static void refresh(YamlConfiguration yamlConfiguration, File file) {
		ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("players");
		ConfigurationSection adminSection = yamlConfiguration.getConfigurationSection("admin");
		List<String> noAdminData = new ArrayList<>();
		for (String uuid : configurationSection.getKeys(false)) {
			yamlConfiguration.set("players."+uuid+".msgnumber", configurationSection.getStringList(uuid+".data").size());
			if (configurationSection.getStringList(uuid+".noAdminData").size() != 0) {
				boolean b = false;
				for (String string : configurationSection.getStringList(uuid+".noAdminData")) {
					for (String str : adminSection.getStringList("data")) {
						if (string.equals(str)) b = true;
					}
					for (String str : configurationSection.getStringList(uuid+".personalAdminData")) {
						if (string.equals(str)) b = true;
					}
					
					if (b) {
						noAdminData.add(string);
					}else if (configurationSection.getStringList(uuid+".noAdminData").size() == 1) {
						noAdminData.clear();
					}
				}
				yamlConfiguration.set("players."+uuid+".noAdminData", noAdminData);
			}
		}
		saveFile(yamlConfiguration, file);
	}
}
