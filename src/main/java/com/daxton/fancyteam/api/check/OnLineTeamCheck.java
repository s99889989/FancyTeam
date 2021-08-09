package com.daxton.fancyteam.api.check;

import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.api.get.OnLineTeamGet;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.entity.Player;

import java.util.UUID;

//玩家在線時判斷
public class OnLineTeamCheck {


	//判斷玩家是否有隊伍
	public static boolean isHaveTeam(Player player){
		UUID playerUUID = player.getUniqueId();
		return AllManager.playerUUID_team_Map.get(playerUUID) != null;
	}

	//檢查palyer1是否有隊伍，並檢查player2是否跟player1同隊
	public static boolean isSameTeam(Player player1, Player player2){
		if(player1 == null || player2 == null){
			return false;
		}
		if(isHaveTeam(player1)){
			FTeam fTeam = OnLineTeamGet.playerFTeam(player1);
			UUID uuid2 = player2.getUniqueId();
			return fTeam.getOnLinePlayers().stream().anyMatch(uuid -> uuid.equals(uuid2));
		}
		return false;
	}

	//判斷隊伍是否都沒人在線上
	public static boolean isNoPlayers(String teamName){
		FTeam fTeam = AllManager.teamName_FTeam_Map.get(teamName);
		return fTeam.getOnLinePlayers().isEmpty();
	}


}
