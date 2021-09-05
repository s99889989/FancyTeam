package com.daxton.fancyteam.config;

import com.daxton.fancyteam.FancyTeam;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TeamConfig {
	//把玩家入設定檔
	public static void setPlayerData(Player player, String teamName){
		UUID uuid = player.getUniqueId();
		FileConfiguration dataConfig = FileConfig.config_Map.get("playerdata.yml");
		dataConfig.set(uuid.toString(), teamName);

		File file = new File(FancyTeam.fancyTeam.getDataFolder(), "playerdata.yml");
		try {
			dataConfig.save(file);
		}catch (IOException exception){
			exception.printStackTrace();
		}
	}

	//把玩家入設定檔
	public static void setPlayerData(UUID uuid, String teamName){

		FileConfiguration dataConfig = FileConfig.config_Map.get("playerdata.yml");
		dataConfig.set(uuid.toString(), teamName);

		File file = new File(FancyTeam.fancyTeam.getDataFolder(), "playerdata.yml");
		try {
			dataConfig.save(file);
		}catch (IOException exception){
			exception.printStackTrace();
		}
	}

	//把玩家移除設定檔
	public static void removePlayerData(Player player){
		UUID uuid = player.getUniqueId();
		FileConfiguration dataConfig = FileConfig.config_Map.get("playerdata.yml");
		dataConfig.set(uuid.toString(), null);
		File file = new File(FancyTeam.fancyTeam.getDataFolder(), "playerdata.yml");
		try {
			dataConfig.save(file);
		}catch (IOException exception){
			exception.printStackTrace();
		}
	}
	//把玩家移除設定檔
	public static void removePlayerData(UUID uuid){
		FileConfiguration dataConfig = FileConfig.config_Map.get("playerdata.yml");
		dataConfig.set(uuid.toString(), null);
		File file = new File(FancyTeam.fancyTeam.getDataFolder(), "playerdata.yml");
		try {
			dataConfig.save(file);
		}catch (IOException exception){
			exception.printStackTrace();
		}
	}
	//移除組隊設定
	public static void removeTeam(String teamName){
		AllManager.teamName_FTeam_Map.remove(teamName);
		FileConfiguration teamConfig = FileConfig.config_Map.get("team.yml");
		if(teamConfig.contains(teamName)){

			List<String> playerList = teamConfig.getStringList(teamName+".players");
			FileConfiguration dataConfig = FileConfig.config_Map.get("playerdata.yml");
			playerList.forEach(playerUUIDString -> {
				if(dataConfig.contains(playerUUIDString)){
					dataConfig.set(playerUUIDString, null);
				}
			});
			teamConfig.set(teamName, null);
			File teamFile = new File(FancyTeam.fancyTeam.getDataFolder(), "team.yml");
			File dataFile = new File(FancyTeam.fancyTeam.getDataFolder(), "playerdata.yml");
			try {
				teamConfig.save(teamFile);
				dataConfig.save(dataFile);
			}catch (IOException exception){
				exception.printStackTrace();
			}

		}


	}

	//設置隊伍設定檔
	public static void setTeamConfig(FTeam team){
		FileConfiguration teamConfig = FileConfig.config_Map.get("team.yml");

		String teamName = team.getTeamName();
		String leader = team.getLeader().toString();
		List<String> onLinePlayer = team.getOnLinePlayers().stream().map(UUID::toString).collect(Collectors.toList());
		List<String> offLinePlayer = team.getOffLinePlayers().stream().map(UUID::toString).collect(Collectors.toList());
		onLinePlayer.addAll(offLinePlayer);
		String experience = team.getExperience().toString();
		String item = team.getItem().toString();
		String money = team.getMoney().toString();
		boolean autoJoin = team.isAutoJoin();
		boolean damageTeamPlayer = team.isDamageTeamPlayer();

		teamConfig.set(teamName+".teamName", teamName);
		teamConfig.set(teamName+".leader", leader);
		teamConfig.set(teamName+".players", onLinePlayer);
		teamConfig.set(teamName+".experience", experience);
		teamConfig.set(teamName+".item", item);
		teamConfig.set(teamName+".money", money);
		teamConfig.set(teamName+".autoJoin", autoJoin);
		teamConfig.set(teamName+".damageTeamPlayer", damageTeamPlayer);

		File file = new File(FancyTeam.fancyTeam.getDataFolder(), "team.yml");

		try {
			teamConfig.save(file);
		}catch (IOException exception){
			exception.printStackTrace();
		}




	}

}
