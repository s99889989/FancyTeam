package com.daxton.fancyteam.gui.noTeam;

import com.daxton.fancycore.api.gui.GuiAction;
import com.daxton.fancyteam.gui.MainTeam;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.UUID;

public class NoTeamListChange implements GuiAction {

	private final Player player;
	private final UUID uuid;

	public NoTeamListChange(Player player){
		this.player = player;
		this.uuid= player.getUniqueId();
	}

	//改變無隊伍列表
	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			String noTeamList = AllManager.playerUUID_List_Map.get(uuid);
			if(noTeamList.equals("InviteTimeList")){
				AllManager.playerUUID_List_Map.put(uuid, "JoinTeamList");
			}else if(noTeamList.equals("JoinTeamList")){
				AllManager.playerUUID_List_Map.put(uuid, "InviteTimeList");
			}
			MainTeam.open(player);
		}
	}

}
