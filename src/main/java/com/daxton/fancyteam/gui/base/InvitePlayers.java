package com.daxton.fancyteam.gui.base;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.gui.HaveTeam;
import com.daxton.fancyteam.gui.MainTeam;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import static com.daxton.fancyteam.config.FileConfig.languageConfig;
import java.util.*;

public class InvitePlayers implements GuiAction {

	private final Player player;
	private final Player invitePlayer;
	private final FTeam fTeam;

	public InvitePlayers(Player player, FTeam fTeam, Player invitePlayer){
		this.fTeam = fTeam;
		this.player = player;
		this.invitePlayer = invitePlayer;
	}

	//邀請玩家入隊
	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			UUID inviteUUID = invitePlayer.getUniqueId();
			if(!fTeam.isInvitePlayer(inviteUUID)){
				AllManager.playerUUID_inviteList_Map.putIfAbsent(inviteUUID, new HashSet<>());
				Set<String> inviteList = AllManager.playerUUID_inviteList_Map.get(inviteUUID);
				inviteList.add(fTeam.getTeamName());
				AllManager.playerUUID_inviteList_Map.put(inviteUUID, inviteList);
				fTeam.addInvitePlayer(inviteUUID);

				GUI selfGui = AllManager.playerUUID_GUI_Map.get(player.getUniqueId());
				HaveTeam.haveTeam(selfGui, player);
				GUI inviteGui = AllManager.playerUUID_GUI_Map.get(invitePlayer.getUniqueId());
				if(inviteGui != null){
					MainTeam.noTeam(inviteGui, invitePlayer);
				}
			}else {
				player.sendMessage(languageConfig.getString("Message.AlreadyInvited")+"");
			}
		}
	}

}
