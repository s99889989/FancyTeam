package com.daxton.fancyteam.gui.button.leader;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.gui.LeaderTeam;
import com.daxton.fancyteam.gui.MainTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import static com.daxton.fancyteam.config.FileConfig.languageConfig;

public class TeamTypeChange implements GuiAction {

	private final GUI gui;
	private final Player player;
	private final String uuidString;
	private final FTeam fTeam;

	public TeamTypeChange(GUI gui, Player player, FTeam fTeam){
		this.fTeam = fTeam;
		this.gui = gui;
		this.player = player;
		this.uuidString = player.getUniqueId().toString();
	}

	//改變列表顯示
	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			fTeam.setTeamListTypeNext();
			String teamListType = fTeam.getTeamListType().toString();
			GuiButton teamTypeChangeButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(languageConfig,"Gui.Main.TeamList."+teamListType)).
				setGuiAction(this).
				build();
			gui.setButton(teamTypeChangeButton, 1, 1);
			//重整列表顯示
			LeaderTeam.playerListLeader(player, gui, fTeam);
		}
	}

}
