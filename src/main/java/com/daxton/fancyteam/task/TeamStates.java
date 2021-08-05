package com.daxton.fancyteam.task;

import com.daxton.fancyteam.api.FTeam;
import com.daxton.fancyteam.config.FileConfig;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;


public class TeamStates {
	//將隊伍設為上線
	public static void onLine(Player player){
		UUID uuid = player.getUniqueId();
		String uuidString = uuid.toString();
		FileConfiguration dataConfig = FileConfig.config_Map.get("playerdata.yml");
		if(dataConfig.contains(uuidString)){
			String teamName = dataConfig.getString(uuidString);
			AllManager.playerUUID_team_Map.put(uuid, teamName);
			if(AllManager.teamName_FTeam_Map.get(teamName) == null){
				FileConfiguration teamConfig = FileConfig.config_Map.get("team.yml");

				String leader = teamConfig.getString(teamName+".leader");
				List<String> offLinePlayer = teamConfig.getStringList(teamName+".players");
				String experience = teamConfig.getString(teamName+".experience");
				String item = teamConfig.getString(teamName+".item");
				String money = teamConfig.getString(teamName+".money");
				boolean autoJoin = teamConfig.getBoolean(teamName+".autoJoin");
				boolean damageTeamPlayer = teamConfig.getBoolean(teamName+".damageTeamPlayer");

				FTeam team = new FTeam(UUID.fromString(uuidString), teamName);
				team.setLeader(UUID.fromString(leader));
				team.setOfflinePlayers(offLinePlayer);
				team.playerOnLine(player);
				team.setExperience(experience);
				team.setItem(item);
				team.setMoney(money);
				team.setAutoJoin(autoJoin);
				team.setDamageTeamPlayer(damageTeamPlayer);

				AllManager.teamName_FTeam_Map.put(teamName, team);

			}else {
				FTeam team = AllManager.teamName_FTeam_Map.get(teamName);
				team.playerOnLine(player);
			}
		}

	}

	//檢查是否將隊伍設為離線
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
				if(team.getOnLinePlayers().size() == 0){
					AllManager.teamName_FTeam_Map.remove(teamName);
				}
			}
		}
	}

}
