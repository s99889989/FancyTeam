package com.daxton.fancyteam.gui.base;

import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.config.TeamConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.UUID;

public class AgreeJoin implements GuiAction {

	private final Player player;
	private final UUID uuid;
	private final FTeam fTeam;

	public AgreeJoin(Player player ,FTeam fTeam, UUID uuid){
		this.fTeam = fTeam;
		this.uuid = uuid;
		this.player = player;
	}

	//同意申請
	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			fTeam.playerJoin(uuid);
			//設置隊伍設定
			TeamConfig.setTeamConfig(fTeam);
			//MainTeam.open(player);
		}
	}

}
