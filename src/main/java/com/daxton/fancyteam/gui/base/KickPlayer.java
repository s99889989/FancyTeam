package com.daxton.fancyteam.gui.base;

import com.daxton.fancycore.api.gui.GuiAction;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.config.TeamConfig;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.UUID;

public class KickPlayer implements GuiAction {

	private final UUID uuid;
	private final FTeam fTeam;

	public KickPlayer(FTeam fTeam, UUID uuid){
		this.fTeam = fTeam;
		this.uuid = uuid;

	}

	//離開隊伍
	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			if(!fTeam.getLeader().equals(uuid)){
				fTeam.playerLeave(uuid);
				//設置隊伍設定
				TeamConfig.setTeamConfig(fTeam);
			}

		}
	}

}
