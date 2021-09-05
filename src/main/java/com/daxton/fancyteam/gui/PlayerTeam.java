package com.daxton.fancyteam.gui;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancycore.api.item.ItemSet;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.config.FileConfig;
import com.daxton.fancyteam.gui.base.LeaveTeam;
import com.daxton.fancyteam.gui.button.noleader.ChatChange;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static com.daxton.fancyteam.config.FileConfig.languageConfig;

public class PlayerTeam {


	//隊員
	public static void teamPlayers(GUI gui, Player player, FTeam team){
		//切換隊伍聊天
		boolean teamChat = team.isTeam_Chat(player);
		GuiButton chatChangeButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.SettingTeam.TeamChat."+teamChat)).
			setGuiAction(new ChatChange(gui, player, team)).
			build();
		gui.setButton(chatChangeButton, 1, 1);
		//經驗
		String experience = team.getExperience().toString();
		GuiButton experienceChangeButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.SettingTeam.Experience."+experience)).
			build();
		gui.setButton(experienceChangeButton, 1, 2);
		//物品
		String item = team.getItem().toString();
		GuiButton itemChangeButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.SettingTeam.Item."+item)).
			build();
		gui.setButton(itemChangeButton, 1, 3);
		//金錢
		String money = team.getMoney().toString();
		GuiButton moneyChangeButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.SettingTeam.Money."+money)).
			build();
		gui.setButton(moneyChangeButton, 1, 4);
		//攻擊隊友
		String damage = String.valueOf(team.isDamageTeamPlayer());
		GuiButton damageChangeButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.SettingTeam.AttackTeammate."+damage)).
			build();
		gui.setButton(damageChangeButton, 1, 5);
		//離開隊伍
		GuiButton leaveTeamButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.Main.LeaveTeam")).
			setGuiAction(new LeaveTeam(player, team)).
			build();
		gui.setButton(leaveTeamButton, 1, 6);
		//隊友列表
		playerList(gui, team);
	}

	//隊員用隊友列表
	public static void playerList(GUI gui, FTeam fTeam){
		FileConfiguration languageConfig = FileConfig.languageConfig;
		ItemStack teamItem = new ItemStack(Material.PLAYER_HEAD);
		Integer[] ignore = new Integer[]{};
		fTeam.getOnLinePlayers().forEach(uuid -> {
			Player p = Bukkit.getPlayer(uuid);
			ItemSet.setDisplayName(teamItem, languageConfig.getString("Message.OnLine").replace("%player_name%", p.getName()));
			ItemSet.setHeadValue(teamItem, p.getName());

			GuiButton teamButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(teamItem).
				build();
			gui.addButton(teamButton, 10, 54, ignore);

		});
		fTeam.getOffLinePlayers().forEach(uuid -> {
			OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
			ItemSet.setDisplayName(teamItem, languageConfig.getString("Message.OffLine").replace("%player_name%", p.getName()));
			ItemSet.setHeadValue(teamItem, p.getName());
			GuiButton teamButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(teamItem).
				build();
			gui.addButton(teamButton, 10, 54, ignore);
		});
	}

}
