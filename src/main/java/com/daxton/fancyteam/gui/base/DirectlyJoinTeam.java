package com.daxton.fancyteam.gui.base;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.config.TeamConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.UUID;

public class DirectlyJoinTeam implements GuiAction {

	private final GUI gui;
	private final Player player;
	private final UUID uuid;
	private final FTeam fTeam;

	public DirectlyJoinTeam(GUI gui, Player player, FTeam fTeam){
		this.fTeam = fTeam;
		this.gui = gui;
		this.player = player;
		this.uuid = player.getUniqueId();
	}

	//申請加入隊伍
	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			fTeam.playerJoin(player);
			//設置隊伍設定
			TeamConfig.setTeamConfig(fTeam);
		}
	}

}
