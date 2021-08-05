package com.daxton.fancyteam.gui;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.GuiAction;
import com.daxton.fancyteam.api.FTeam;
import com.daxton.fancyteam.config.FileConfig;
import com.daxton.fancyteam.config.TeamConfig;
import com.daxton.fancyteam.listener.PlayerListener;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.UUID;

public class CreateTeam implements GuiAction {

	private final GUI gui;
	private final Player player;
	private final UUID uuid;

	public CreateTeam(GUI gui, Player player){
		this.gui = gui;
		this.player = player;
		this.uuid = player.getUniqueId();
	}
	//建立隊伍
	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			AllManager.playerUUID_chat_Map.put(uuid, true);
			gui.close();
			player.sendTitle(" ","請輸入隊伍名稱",10,40,40);
		}
	}

	public void chat(String teamName){
		AllManager.playerUUID_chat_Map.put(uuid, false);
		FileConfiguration teamConfig = FileConfig.config_Map.get("team.yml");
		if(teamConfig.contains(teamName)){
			player.sendTitle(" ","建立失敗，此隊伍名稱已使用",10,40,40);
		}else {
			player.sendMessage("成功建立隊伍: "+teamName);
			FTeam team = new FTeam(player, teamName);
			AllManager.teamName_FTeam_Map.put(teamName, team);
			//設置隊伍設定
			TeamConfig.setTeamConfig(team);
			MainTeam.open(player);
			PlayerListener.onTeamChange();
		}

	}

}
