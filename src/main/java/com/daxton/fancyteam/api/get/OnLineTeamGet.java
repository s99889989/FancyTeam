package com.daxton.fancyteam.api.get;

import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.entity.Player;

import java.util.UUID;

//線上玩家獲取
public class OnLineTeamGet {

	//獲取玩家隊伍
	public static FTeam playerFTeam(Player player){
		UUID playerUUID = player.getUniqueId();
		if(AllManager.playerUUID_team_Map.get(playerUUID) != null){
			String teamName = AllManager.playerUUID_team_Map.get(playerUUID);
			return AllManager.teamName_FTeam_Map.get(teamName);
		}
		return null;
	}
	//獲取隊伍名稱
	public static String teamName(Player player){
		FTeam fTeam = playerFTeam(player);
		return fTeam.getTeamName();
	}


}
