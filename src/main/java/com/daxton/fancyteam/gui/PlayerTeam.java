package com.daxton.fancyteam.gui;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.GuiButtom;
import com.daxton.fancycore.api.item.ItemSet;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.config.FileConfig;
import com.daxton.fancyteam.gui.base.LeaveTeam;
import com.daxton.fancyteam.gui.setting.ChatChange;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import static com.daxton.fancyteam.config.FileConfig.languageConfig;
import java.util.ArrayList;
import java.util.List;

public class PlayerTeam {


	//隊員
	public static void teamPlayers(GUI gui, Player player, FTeam team){
		//切換隊伍聊天
		boolean teamChat = team.isTeam_Chat(player);
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.SettingTeam.TeamChat."+teamChat), false,1, 1);
		gui.setAction(new ChatChange(gui, player, team), 1, 1);
		//經驗
		String experience = team.getExperience().toString();
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.SettingTeam.Experience."+experience), false,1, 2);
		//物品
		String item = team.getItem().toString();
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.SettingTeam.Item."+item), false,1, 3);
		//金錢
		String money = team.getMoney().toString();
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.SettingTeam.Money."+money), false,1, 4);
		//攻擊隊友
		String damage = String.valueOf(team.isDamageTeamPlayer());
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.SettingTeam.AttackTeammate."+damage), false,1, 5);
		//離開隊伍
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.Main.LeaveTeam"), false,1, 6);
		gui.setAction(new LeaveTeam(player, team), 1, 6);
		//隊友列表
		playerList(gui, team);
	}

	//隊員用隊友列表
	public static void playerList(GUI gui, FTeam fTeam){
		FileConfiguration languageConfig = FileConfig.languageConfig;
		ItemStack teamItem = new ItemStack(Material.PLAYER_HEAD);
		List<Integer> ignore = new ArrayList<>();
		fTeam.getOnLinePlayers().forEach(uuid -> {
			Player p = Bukkit.getPlayer(uuid);
			ItemSet.setDisplayName(teamItem, languageConfig.getString("Message.OnLine").replace("%player_name%", p.getName()));
			ItemSet.setHeadValue(teamItem, p.getName());
			gui.addItem(teamItem, false, 10, 54, ignore);
		});
		fTeam.getOffLinePlayers().forEach(uuid -> {
			OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
			ItemSet.setDisplayName(teamItem, languageConfig.getString("Message.OffLine").replace("%player_name%", p.getName()));
			ItemSet.setHeadValue(teamItem, p.getName());
			gui.addItem(teamItem, false, 10, 54, ignore);
		});
	}

}
