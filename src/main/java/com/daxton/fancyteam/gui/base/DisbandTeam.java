package com.daxton.fancyteam.gui.base;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyteam.api.team.FTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class DisbandTeam implements GuiAction {

	private final GUI gui;
	private final Player player;
	private final String uuidString;
	private final FTeam fTeam;

	public DisbandTeam(GUI gui, Player player, FTeam fTeam){
		this.fTeam = fTeam;
		this.gui = gui;
		this.player = player;
		this.uuidString = player.getUniqueId().toString();
	}

	//解散隊伍
	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){

			fTeam.disbandTeam();

		}
	}

}
