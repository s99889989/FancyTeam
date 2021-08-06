package com.daxton.fancyteam.task;

import com.daxton.fancyteam.api.check.OffLineTeamCheck;
import com.daxton.fancyteam.api.check.OnLineTeamCheck;
import com.daxton.fancyteam.api.get.OffLineTeamGet;
import com.daxton.fancyteam.api.get.OnLineTeamGet;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.api.team.NTeam;
import com.daxton.fancyteam.config.FileConfig;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;


public class TeamStates {

	//玩家上線有隊伍
	public static void onLineHaveTeam(Player player){
		UUID uuid = player.getUniqueId();
		String uuidString = uuid.toString();

		String teamName = OffLineTeamGet.teamName(player);

		AllManager.playerUUID_team_Map.put(uuid, teamName);
		if(!OffLineTeamCheck.isTeamOnLine(teamName)){

			FTeam team = new FTeam(UUID.fromString(uuidString), teamName);
			team.setLeader(UUID.fromString(OffLineTeamGet.leader(player)));
			team.setOfflinePlayers(OffLineTeamGet.players(player));
			team.playerOnLine(player);
			team.setExperience(OffLineTeamGet.experience(player));
			team.setItem(OffLineTeamGet.item(player));
			team.setMoney(OffLineTeamGet.money(player));
			team.setAutoJoin(OffLineTeamGet.autoJoin(player));
			team.setDamageTeamPlayer(OffLineTeamGet.damageTeamPlayer(player));

			AllManager.teamName_FTeam_Map.put(teamName, team);

		}else {
			FTeam team = AllManager.teamName_FTeam_Map.get(teamName);
			team.playerOnLine(player);
		}

	}
	//玩家上線沒隊伍
	public static void onLineNoHaveTeam(Player player){
		UUID uuid = player.getUniqueId();
		AllManager.playerUUID_NTeam_Map.putIfAbsent(uuid, new NTeam(player));
	}

	//玩家上線
	public static void onLine(Player player){

		if(OffLineTeamCheck.isHaveTeam(player)){
			onLineHaveTeam(player);
		}else {
			//onLineNoHaveTeam(player);
		}

	}

	//玩家下線
	public static void offLine(Player player){
		UUID uuid = player.getUniqueId();
		String uuidString = uuid.toString();
		FileConfiguration dataConfig = FileConfig.config_Map.get("playerdata.yml");
		if(dataConfig.contains(uuidString)){
			AllManager.playerUUID_team_Map.remove(uuid);
			String teamName = dataConfig.getString(uuidString);
			if(AllManager.teamName_FTeam_Map.get(teamName) != null){
				FTeam team = AllManager.teamName_FTeam_Map.get(teamName);
				team.playerOffLine(player);
				//如果隊伍沒人在線上，把隊伍從線上移除
				if(OnLineTeamCheck.isNoPlayers(teamName)){
					teamEmpty(player);
				}
			}
		}
	}

	//如果隊伍沒人在線上，把隊伍從線上移除
	public static void teamEmpty(Player player){
		String teamName = OffLineTeamGet.teamName(player);
		AllManager.teamName_FTeam_Map.remove(teamName);
	}


}
