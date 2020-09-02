package fr.synol.datatchat;

import static fr.synol.datatchat.utils.DTUtils.*;
import static fr.synol.datatchat.utils.DTGlobalSetFileUtils.*;
import static fr.synol.datatchat.utils.DTUserSetFileUtils.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fr.synol.datatchat.commands.CommandsAdmin;
import fr.synol.datatchat.commands.CommandsHelp;
import fr.synol.datatchat.commands.CommandsUser;

public class MainPlugin extends JavaPlugin implements Listener{
	
	private static MainPlugin instance;
	
	public static MainPlugin getInstance() {
		return instance;
	}
	
	public void onEnable() {
		
		final File file = new File(getDataFolder()+"/data.yml");
		final YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
		if (!file.exists()) {
			setDefaultSection(data, file);
			saveFile(data, file);
		}else {
			refresh(data, file);
		}//*/
		
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		
		getServer().getConsoleSender().sendMessage(dataTchatLabel+"§e-> Loading config file ...");
		saveDefaultConfig();
		
		//help commands 
		getServer().getConsoleSender().sendMessage(dataTchatLabel+"§e-> Loading help commands ...");
		getCommand("DTHelp").setExecutor(new CommandsHelp(this));
		getCommand("DTInfo").setExecutor(new CommandsHelp(this));
		getCommand("setLangage").setExecutor(new CommandsHelp(this));
		//user commands 
		getServer().getConsoleSender().sendMessage(dataTchatLabel+"§e-> Loading user commands ...");
		getCommand("addData").setExecutor(new CommandsUser(this));
		getCommand("removeData").setExecutor(new CommandsUser(this));
		//getCommand("setData").setExecutor(new CommandsUser(this));
		getCommand("showDataInTchat").setExecutor(new CommandsUser(this));
		getCommand("showDataWhen").setExecutor(new CommandsUser(this));
		getCommand("showMyData").setExecutor(new CommandsUser(this));
		getCommand("restorAdminData").setExecutor(new CommandsUser(this));
		//admin commands
		getServer().getConsoleSender().sendMessage(dataTchatLabel+"§e-> Loading admin commands ...");
		getCommand("addDataForAll").setExecutor(new CommandsAdmin(this));
		getCommand("removeDataForAll").setExecutor(new CommandsAdmin(this));
		//getCommand("setDataForAll").setExecutor(new CommandsAdmin(this));
		getCommand("addDataFor").setExecutor(new CommandsAdmin(this));
		getCommand("removeDataFor").setExecutor(new CommandsAdmin(this));
		//getCommand("setDataFor").setExecutor(new CommandsAdmin(this));
		getCommand("allowRemoveAdminData").setExecutor(new CommandsAdmin(this));
		getCommand("showData").setExecutor(new CommandsAdmin(this));
		getCommand("setDefaultLangage").setExecutor(new CommandsAdmin(this)); 
		getCommand("saveConfig").setExecutor(new CommandsAdmin(this)); 
		getCommand("setMaxData").setExecutor(new CommandsAdmin(this)); //*/
		
		getServer().getConsoleSender().sendMessage(dataTchatLabel+"§a---> Enabled !");
	}
	
	public void onDisable() {
		final File file = new File(getDataFolder()+"/data.yml");
		final YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
		if (file.exists()) {
			refresh(data, file);
		}
		getServer().getConsoleSender().sendMessage(dataTchatLabel+"§4---> Disabled !");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		final File file = new File(this.getDataFolder() + "/data.yml");
		final YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
		String key = "players." + player.getUniqueId();
		ConfigurationSection configurationSection = data.getConfigurationSection(key);
		if (configurationSection != null) {
			if (configurationSection.getBoolean("setting.enableInTchat") == true) {
				if (configurationSection.getBoolean("setting.enableWhen.join") == true) {
					showData(data, player);
				}
			}
		}else {
			showData(data, player);
		}
		refresh(data, file);
	}
	@EventHandler
	public void onChangedWorld(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		final File file = new File(this.getDataFolder() + "/data.yml");
		final YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
		String key = "players." + player.getUniqueId();
		ConfigurationSection configurationSection = data.getConfigurationSection(key);
		if (configurationSection != null) {
			if (configurationSection.getBoolean("setting.enableInTchat") == true) {
				if (configurationSection.getBoolean("setting.enableWhen.worldChanged") == true) {
					showData(data, player);
				}
			}
		}else {
			showData(data, player);
		}
		refresh(data, file);
	}
	@EventHandler
	public void onBedLeave(PlayerBedLeaveEvent event) {
		Player player = event.getPlayer();
		final File file = new File(this.getDataFolder() + "/data.yml");
		final YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
		String key = "players." + player.getUniqueId();
		ConfigurationSection configurationSection = data.getConfigurationSection(key);
		if (configurationSection != null) {
			if (configurationSection.getBoolean("setting.enableInTchat") == true) {
				if (configurationSection.getBoolean("setting.enableWhen.leaveBed") == true) {
					showData(data, player);
				}
			}
		}else {
			showData(data, player);
		}
		refresh(data, file);
	}
	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		final File file = new File(this.getDataFolder() + "/data.yml");
		final YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
		String key = "players." + player.getUniqueId();
		ConfigurationSection configurationSection = data.getConfigurationSection(key);
		if (configurationSection != null) {
			if (configurationSection.getBoolean("setting.enableInTchat") == true) {
				if (configurationSection.getBoolean("setting.enableWhen.respawn") == true) {
					showData(data, player);
				}
			}
		}else {
			showData(data, player);
		}
		refresh(data, file);
	}
	
	private void setDefaultSection(YamlConfiguration yamlConfiguration, File file){
		String key = "players.00000000-0000-0000-0000-000000000000";
		String keys = "players.00000000-0000-0000-0000-000000000000.setting";
		String keysw = "players.00000000-0000-0000-0000-000000000000.setting.enableWhen";
		List<String> adminData = new ArrayList<>();
		adminData.add("Use commande /addData <NewData> ! ");
		List<String> data = new ArrayList<>();
		data.add("Hello world ! ");data.add("test 1, 2 ! ");
		List<String> personalAdminData = new ArrayList<>();
		personalAdminData.add("Je ne suis pas supprimé ! ");personalAdminData.add("J'ai été supprimé par Test ! ");
		List<String> noAdminData = new ArrayList<>();
		noAdminData.add("J'ai été supprimé par Test ! ");
		yamlConfiguration.createSection("admin.data");
		yamlConfiguration.set("admin.data", adminData);
		yamlConfiguration.createSection(key+".name");
		yamlConfiguration.set(key+".name", "Test");
		yamlConfiguration.createSection(key+".msgnumber");
		yamlConfiguration.set(key+".msgnumber", 2);
		yamlConfiguration.createSection(key+".data");
		yamlConfiguration.set(key+".data", data);
		yamlConfiguration.createSection(key+".personalAdminData");
		yamlConfiguration.set(key+".personalAdminData", personalAdminData);
		yamlConfiguration.createSection(key+".noAdminData");
		yamlConfiguration.set(key+".noAdminData", noAdminData);
		yamlConfiguration.createSection(keys+".allowRemoveAdminData");
		yamlConfiguration.set(keys+".allowRemoveAdminData", true);
		yamlConfiguration.createSection(keys+".enableInTchat");
		yamlConfiguration.set(keys+".enableInTchat", true);
		yamlConfiguration.createSection(keysw+".join");
		yamlConfiguration.set(keysw+".join", true);
		yamlConfiguration.createSection(keysw+".worldChanged");
		yamlConfiguration.set(keysw+".worldChanged", true);
		yamlConfiguration.createSection(keysw+".leaveBed");
		yamlConfiguration.set(keysw+".leaveBed", false);
		yamlConfiguration.createSection(keysw+".respawn");
		yamlConfiguration.set(keysw+".respawn", true);
		yamlConfiguration.createSection(keys+".langage");
		yamlConfiguration.set(keys+".langage", "fr");
		saveFile(yamlConfiguration, file);
	}
	
}
