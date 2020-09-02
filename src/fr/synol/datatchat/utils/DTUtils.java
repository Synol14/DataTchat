package fr.synol.datatchat.utils;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.synol.datatchat.MainPlugin;

public final class DTUtils {
	
	/****************
	 * Utils Labels *
	 ****************/
	public static String dataTchatLabel = "§r[§3DataTchat§r] ";
	public static String AdminLabel = "§r[§4Admin§r] ";
	public static String showLabelUp = "§a==========MyData==========§r\n";
	public static String showLabelDown = "§a==========================";
	
	
	/*****************
	 * Utils Methods *
	 *****************/
	public static String getString(String[] args) {
		// récupération de tous les mots apres la commande dans une variable de type String
		StringBuilder sb =new StringBuilder();
		for(String str : args) {
			sb.append(str + " ");
		}
		return sb.toString();
	}
	//==================================================//
	public static String splitConfigMessage(String label, Object value, String message) {
		return message.replace(label, ""+value); 
	}
	public static String splitConfigMessage(String label, String label2, Object value, Object value2, String message) {
		return message.replace(label, ""+value).replace(label2, ""+value2); 
	}
	//==================================================//
	public static String getMessageNoData(String langage) {
		String msgNoData = showLabelUp+noColor(MainPlugin.getInstance().getConfig().getString("message."+langage.toLowerCase()+".noData"))+"\n"+showLabelDown;
		return msgNoData;
	}
	//==================================================//
		public static String getCommandLabelDown(String command) {
			StringBuilder sb = new StringBuilder();
			sb.append("§a==========");
			for (int i=1; i<=command.length(); i++) {
				sb.append("=");
			}
			sb.append("==========");
			return sb.toString();
		}
	//==================================================//
	public static String noColor(String msg) {
		String finalMsg;
		if (msg.contains("%noColor%")) {
			finalMsg = msg.replace("%noColor%", "");
		}else {
			finalMsg = msg.replace("&", "§");
		}
		return finalMsg;
	}
	public static String ignoreColor(String msg) {
		return msg.replace("&4", "").replace("&c", "").replace("&6", "").replace("&e", "").replace("&2", "").replace("&a", "").replace("&b", "").replace("&3", "").replace("&1", "").replace("&9", "").replace("&d", "").replace("&5", "").replace("&f", "").replace("&7", "").replace("&8", "").replace("&0", "").replace("&k", "").replace("&l", "").replace("&m", "").replace("&n", "").replace("&o", "").replace("&r", "");
	}
	//==================================================//
	public static String getUUIDPlayer(YamlConfiguration yamlConfiguration, String displayName) {
		ConfigurationSection configSection = yamlConfiguration.getConfigurationSection("players");
		String uuid = null;
		if (configSection != null) {
			for (String id : configSection.getKeys(false)) {
				if (configSection.getString(id+".name").equals(displayName)) {
					uuid = id;
				}
			}
		}else {
			uuid = null;
		}
		return uuid;
	}
	//==================================================//
	public static int getInteger(String arg) {
		int i = 0;
		while (!arg.equals(""+i)) {
			i++;
		}
		return i;
	}
}