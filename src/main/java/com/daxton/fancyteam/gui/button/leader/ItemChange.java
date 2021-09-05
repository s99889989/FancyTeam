package com.daxton.fancyteam.gui.button.leader;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyteam.api.team.FTeam;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import static com.daxton.fancyteam.config.FileConfig.languageConfig;

public class ItemChange implements GuiAction {

	private final GUI gui;
	private final Player player;
	private final String uuidString;
	private final FTeam fTeam;

	public ItemChange(GUI gui, Player player, FTeam fTeam){
		this.fTeam = fTeam;
		this.gui = gui;
		this.player = player;
		this.uuidString = player.getUniqueId().toString();
	}

	//改變物品設定
	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			fTeam.setItemNext();

			String item = fTeam.getItem().toString();
			GuiButton itemChangeButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(languageConfig,"Gui.SettingTeam.Item."+item)).
				setGuiAction(new ItemChange(gui, player, fTeam)).
				build();
			gui.setButton(itemChangeButton, 1, 4);

		}
	}

}
