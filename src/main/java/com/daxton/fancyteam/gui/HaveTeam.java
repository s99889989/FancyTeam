package com.daxton.fancyteam.gui;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HaveTeam {

	//ζιδΌζ
	public static void haveTeam(GUI gui, Player player){
		gui.clearButtonFrom(1, 54);
		UUID uuid = player.getUniqueId();
		String teamName = AllManager.playerUUID_team_Map.get(uuid);
		FTeam team = AllManager.teamName_FTeam_Map.get(teamName);
		if(team.isLeader(player)){
			LeaderTeam.teamLeader(gui, player, team);
		}else {
			PlayerTeam.teamPlayers(gui, player, team);
		}

	}


}
