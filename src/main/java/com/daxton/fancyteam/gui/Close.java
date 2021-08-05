package com.daxton.fancyteam.gui;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.GuiAction;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.UUID;

public class Close implements GuiAction {

	private final GUI gui;
	private final Player player;
	private final UUID uuid;

	public Close(GUI gui, Player player){
		this.gui = gui;
		this.player = player;
		this.uuid = player.getUniqueId();
	}
	//建立隊伍
	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			gui.close();
		}
	}

}
