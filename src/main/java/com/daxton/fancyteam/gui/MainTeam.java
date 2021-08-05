package com.daxton.fancyteam.gui;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.GuiButtom;
import com.daxton.fancycore.api.item.ItemSet;
import com.daxton.fancyteam.api.FTeam;
import com.daxton.fancyteam.api.TeamListType;
import com.daxton.fancyteam.config.FileConfig;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MainTeam {

	//打開Team介面
	public static void open(Player player){
		UUID uuid = player.getUniqueId();
		if(AllManager.playerUUID_GUI_Map.get(uuid) == null){
			GUI gui = GUI.createGui(player, 54, "組隊介面");
			AllManager.playerUUID_GUI_Map.put(uuid, gui);
		}

		GUI gui = AllManager.playerUUID_GUI_Map.get(uuid);
		gui.clearFrom(1, 54);
		gui.setMoveAll(false);
		if(AllManager.playerUUID_team_Map.get(uuid) == null){
			//FancyTeam.fancyTeam.getLogger().info("沒有隊伍");
			noTeam(gui, player);
		}else {
			//FancyTeam.fancyTeam.getLogger().info("有隊伍");
			haveTeam(gui, player);
		}
		gui.open(gui);
	}
	//有隊伍時
	public static void haveTeam(GUI gui, Player player){
		UUID uuid = player.getUniqueId();
		String teamName = AllManager.playerUUID_team_Map.get(uuid);
		FTeam team = AllManager.teamName_FTeam_Map.get(teamName);
		if(team.isLeader(player)){
			teamLeader(gui, player, team);
		}else {
			teamPlayers(gui, player, team);
		}

	}
	//隊員
	public static void teamPlayers(GUI gui, Player player, FTeam team){
		FileConfiguration languageConfig = FileConfig.languageConfig;
		//離開隊伍
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.Main.LeaveTeam"), false,1, 1);
		gui.setAction(new LeaveTeam(gui, player, team), 1, 1);
		//經驗
		String experience = team.getExperience().toString();
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.SettingTeam.Experience."+experience), false,1, 2);
		//物品
		String item = team.getItem().toString();
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.SettingTeam.Item."+item), false,1, 3);
		//金錢
		String money = team.getMoney().toString();
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.SettingTeam.Money."+money), false,1, 4);
		//攻擊隊友
		String damage = String.valueOf(team.isDamageTeamPlayer());
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.SettingTeam.AttackTeammate."+damage), false,1, 5);

		//隊友列表
		playerList(gui, team);
	}
	//隊長
	public static void teamLeader(GUI gui, Player player, FTeam team){
		FileConfiguration languageConfig = FileConfig.languageConfig;
		//顯示列表
		String teamListType = team.getTeamListType().toString();
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.Main.TeamList."+teamListType), false,1, 1);
		gui.setAction(new ChangeTeamType(gui, player, team), 1, 1);
		//經驗
		String experience = team.getExperience().toString();
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.SettingTeam.Experience."+experience), false,1, 2);
		gui.setAction(new ChangeExperience(gui, player, team), 1, 2);
		//物品
		String item = team.getItem().toString();
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.SettingTeam.Item."+item), false,1, 3);
		gui.setAction(new ChangeItem(gui, player, team), 1, 3);
		//金錢
		String money = team.getMoney().toString();
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.SettingTeam.Money."+money), false,1, 4);
		gui.setAction(new ChangeMoney(gui, player, team), 1, 4);
		//攻擊隊友
		String damage = String.valueOf(team.isDamageTeamPlayer());
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.SettingTeam.AttackTeammate."+damage), false,1, 5);
		gui.setAction(new ChangeDamage(gui, player, team), 1, 5);
		//自動加入隊伍
		String autoJon = String.valueOf(team.isAutoJoin());
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.SettingTeam.AgreeJoin."+autoJon), false,1, 6);
		gui.setAction(new ChangeDamage(gui, player, team), 1, 6);
		//離開隊伍
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.Main.LeaveTeam"), false,1, 7);
		gui.setAction(new LeaveTeam(gui, player, team), 1, 7);
		//解散隊伍
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.Main.DisbandTeam"), false,1, 8);
		gui.setAction(new DisbandTeam(gui, player, team), 1, 8);
		//關閉介面
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.Main.Close"), false,1, 9);
		gui.setAction(new Close(gui, player), 1, 9);
		//隊友列表
		playerListLeader(gui, team);

	}
	//隊長用隊友列表
	public static void playerList(GUI gui, FTeam fTeam){
		FileConfiguration languageConfig = FileConfig.languageConfig;
		ItemStack teamItem = new ItemStack(Material.PLAYER_HEAD);
		List<Integer> ignore = new ArrayList<>();
		fTeam.getOnLinePlayers().forEach(uuid -> {
			Player p = Bukkit.getPlayer(uuid);
			ItemSet.setDisplayName(teamItem, p.getName()+"[在線]");
			ItemSet.setHeadValue(teamItem, p.getName());
			gui.addItem(teamItem, false, 10, 54, ignore);
		});
		fTeam.getOffLinePlayers().forEach(uuid -> {
			OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
			ItemSet.setDisplayName(teamItem, p.getName()+"[離線]");
			ItemSet.setHeadValue(teamItem, p.getName());
			gui.addItem(teamItem, false, 10, 54, ignore);
		});
	}
	//隊長用隊友列表
	public static void playerListLeader(GUI gui, FTeam fTeam){
		FileConfiguration languageConfig = FileConfig.languageConfig;
		ItemStack teamItem = new ItemStack(Material.PLAYER_HEAD);
		List<Integer> ignore = new ArrayList<>();
		TeamListType teamListType = fTeam.getTeamListType();
		//隊友
		if(teamListType == TeamListType.TeamPlayers){
			fTeam.getOnLinePlayers().forEach(uuid -> {
				Player p = Bukkit.getPlayer(uuid);
				ItemSet.setDisplayName(teamItem, p.getName()+"[在線]");
				ItemSet.setHeadValue(teamItem, p.getName());
				gui.addItem(teamItem, false, 10, 54, ignore);
			});
			fTeam.getOffLinePlayers().forEach(uuid -> {
				OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
				ItemSet.setDisplayName(teamItem, p.getName()+"[離線]");
				ItemSet.setHeadValue(teamItem, p.getName());
				gui.addItem(teamItem, false, 10, 54, ignore);
			});
		}
		//線上可以邀請的玩家
		if(teamListType == TeamListType.InvitePlayer){
			Bukkit.getOnlinePlayers().forEach(player -> {
				if(!fTeam.isTeamPlayer(player) || !fTeam.isApplyPlayer(player)){
					ItemSet.setDisplayName(teamItem, player.getName());
					gui.addItem(teamItem, false, 10, 54, ignore);
				}
			});
		}
		//申請加入的玩家
		if(teamListType == TeamListType.ApplyInvitePlayer){
			fTeam.getApplyPlayers().forEach(uuid -> {
				OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
				String playerName = offlinePlayer.getName();
				ItemSet.setDisplayName(teamItem, playerName);
				gui.addItem(teamItem, false, 10, 54, ignore);
			});
		}

	}

	//沒有隊伍時
	public static void noTeam(GUI gui, Player player){
		FileConfiguration languageConfig = FileConfig.languageConfig;
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.Main.CreateTeam"), false,1, 1);
		gui.setAction(new CreateTeam(gui, player), 1, 1);

		ItemStack teamItem = GuiButtom.valueOf(languageConfig,"Language.Gui.Main.JoinTeam");

		List<Integer> ignore = new ArrayList<>();

		AllManager.teamName_FTeam_Map.forEach((teamName, fTeam) -> {

			ItemSet.setDisplayName(teamItem, fTeam.getTeamName());
			ItemSet.setHeadValue(teamItem, Bukkit.getPlayer(fTeam.getLeader()).getName());

			gui.addItem(teamItem, false, 10, 54, ignore);
			gui.addAction(new JoinTeam(gui, player, fTeam), 10, 54, ignore);
		});


	}

}
