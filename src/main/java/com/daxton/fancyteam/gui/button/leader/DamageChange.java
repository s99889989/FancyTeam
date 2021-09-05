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

public class DamageChange implements GuiAction {

	private final GUI gui;
	private final Player player;
	private final String uuidString;
	private final FTeam fTeam;

	public DamageChange(GUI gui, Player player, FTeam fTeam){
		this.fTeam = fTeam;
		this.gui = gui;
		this.player = player;
		this.uuidString = player.getUniqueId().toString();
	}

	//攻擊隊友設定
	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			fTeam.setDamageTeamPlayer(!fTeam.isDamageTeamPlayer());

			String damage = String.valueOf(fTeam.isDamageTeamPlayer());
			GuiButton damageChangeButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(languageConfig,"Gui.SettingTeam.AttackTeammate."+damage)).
				setGuiAction(new DamageChange(gui, player, fTeam)).
				build();
			gui.setButton(damageChangeButton, 1, 6);

		}
	}

}
