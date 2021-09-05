package com.daxton.fancyteam.gui.base;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiChatAction;
import com.daxton.fancyteam.FancyTeam;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.config.FileConfig;
import com.daxton.fancyteam.config.TeamConfig;
import com.daxton.fancyteam.gui.MainTeam;
import com.daxton.fancyteam.listener.PlayerListener;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.scheduler.BukkitRunnable;

import static com.daxton.fancyteam.config.FileConfig.languageConfig;
import java.util.UUID;

public class CreateTeam implements GuiAction , GuiChatAction {

	private final GUI gui;
	private final Player player;
	private final UUID uuid;

	public CreateTeam(GUI gui, Player player){
		this.gui = gui;
		this.player = player;
		this.uuid = player.getUniqueId();
	}

	public void execute(Player player, String teamName){
		FileConfiguration teamConfig = FileConfig.config_Map.get("team.yml");
		if(teamConfig.contains(teamName)){
			player.sendTitle(" ",languageConfig.getString("Message.CreateSameName"),10,40,40);
			new BukkitRunnable() {
				@Override
				public void run() {
					MainTeam.open(player);
				}
			}.runTaskLater(FancyTeam.fancyTeam, 10);
		}else {
			String successString = languageConfig.getString("Message.CreateSuccess")+"";
			player.sendTitle(" ", successString.replace("%team_name%", teamName),10,40,40);
			FTeam team = new FTeam(player, teamName);
			AllManager.teamName_FTeam_Map.put(teamName, team);
			//設置隊伍
			TeamConfig.setTeamConfig(team);
			PlayerListener.onTeamChange();
			new BukkitRunnable() {
				@Override
				public void run() {
					MainTeam.open(player);
				}
			}.runTaskLater(FancyTeam.fancyTeam, 10);
		}
	}

	//建立隊伍
	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			//AllManager.playerUUID_chat_Map.put(uuid, true);
			gui.setGuiChatAction(this);
			gui.setChat(true);
			gui.close();
			player.sendTitle(" ",languageConfig.getString("Message.CreateTeam"),10,40,40);
		}
	}


}
