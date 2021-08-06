package com.daxton.fancyteam.gui.base;

import com.daxton.fancycore.api.gui.GuiAction;
import com.daxton.fancyteam.api.team.FTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class LeaveTeam implements GuiAction {

	private final Player player;
	private final FTeam fTeam;

	public LeaveTeam(Player player, FTeam fTeam){
		this.fTeam = fTeam;
		this.player = player;
	}

	//離開隊伍
	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			fTeam.playerLeave(player.getUniqueId());
		}
	}

}
