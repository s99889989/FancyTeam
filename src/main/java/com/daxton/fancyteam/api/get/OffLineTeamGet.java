package com.daxton.fancyteam.api.get;

import com.daxton.fancyteam.config.FileConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

//離線玩家獲取
public class OffLineTeamGet {

	//隊伍名稱
	public static String teamName(Player player){
		UUID uuid = player.getUniqueId();
		String uuidString = uuid.toString();
		FileConfiguration dataConfig = FileConfig.config_Map.get("playerdata.yml");
		return dataConfig.getString(uuidString);
	}
	//隊長
	public static String leader(Player player){
		String teamName = teamName(player);
		FileConfiguration teamConfig = FileConfig.config_Map.get("team.yml");
		return  teamConfig.getString(teamName+".leader");
	}
	//隊伍隊員列表
	public static List<String> players(Player player){
		String teamName = teamName(player);
		FileConfiguration teamConfig = FileConfig.config_Map.get("team.yml");
		return teamConfig.getStringList(teamName+".players");
	}
	//經驗分配類型
	public static String experience(Player player){
		String teamName = teamName(player);
		FileConfiguration teamConfig = FileConfig.config_Map.get("team.yml");
		return teamConfig.getString(teamName+".experience");
	}
	//物品分配類型
	public static String item(Player player){
		String teamName = teamName(player);
		FileConfiguration teamConfig = FileConfig.config_Map.get("team.yml");
		return teamConfig.getString(teamName+".item");
	}
	//金錢分配類型
	public static String money(Player player){
		String teamName = teamName(player);
		FileConfiguration teamConfig = FileConfig.config_Map.get("team.yml");
		return teamConfig.getString(teamName+".money");
	}
	//是否自動入隊
	public static boolean autoJoin(Player player){
		String teamName = teamName(player);
		FileConfiguration teamConfig = FileConfig.config_Map.get("team.yml");
		return teamConfig.getBoolean(teamName+".autoJoin");
	}
	//是否攻擊隊友
	public static boolean damageTeamPlayer(Player player){
		String teamName = teamName(player);
		FileConfiguration teamConfig = FileConfig.config_Map.get("team.yml");
		return teamConfig.getBoolean(teamName+".damageTeamPlayer");
	}

}
