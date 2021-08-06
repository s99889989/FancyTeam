package com.daxton.fancyteam.api.check;


import com.daxton.fancyteam.api.get.OffLineTeamGet;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.api.get.OnLineTeamGet;
import com.daxton.fancyteam.config.FileConfig;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

//玩家不在線時判斷
public class OffLineTeamCheck {

	//判斷玩家是否有隊伍
	public static boolean isHaveTeam(Player player){
		UUID uuid = player.getUniqueId();
		String uuidString = uuid.toString();
		FileConfiguration dataConfig = FileConfig.config_Map.get("playerdata.yml");
		return dataConfig.contains(uuidString);
	}

	//判斷隊伍是否上線
	public static boolean isTeamOnLine(String teamName){
		return AllManager.teamName_FTeam_Map.get(teamName) != null;
	}

//	//檢查palyer1是否有隊伍，並檢查player2是否跟player1同隊
//	public static boolean checkIsSameTeam(Player player1, Player player2){
//		if(checkHaveTeam(player1)){
//			FTeam fTeam = OnLineTeamGet.playerFTeam(player1);
//			UUID uuid2 = player2.getUniqueId();
//			return fTeam.getOnLinePlayers().stream().anyMatch(uuid -> uuid.equals(uuid2));
//		}
//		return false;
//	}

}
