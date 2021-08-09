package com.daxton.fancyteam.gui;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.GuiButtom;
import com.daxton.fancycore.api.item.ItemSet;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.api.teamenum.TeamListType;
import com.daxton.fancyteam.gui.base.*;
import com.daxton.fancyteam.gui.setting.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.daxton.fancyteam.config.FileConfig.languageConfig;

public class LeaderTeam {

	//隊長
	public static void teamLeader(GUI gui, Player player, FTeam team){
		//顯示列表
		String teamListType = team.getTeamListType().toString();
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.Main.TeamList."+teamListType), false,1, 1);
		gui.setAction(new TeamTypeChange(gui, player, team), 1, 1);
		//切換隊伍聊天
		boolean teamChat = team.isTeam_Chat(player);
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.SettingTeam.TeamChat."+teamChat), false,1, 2);
		gui.setAction(new ChatChange(gui, player, team), 1, 2);
		//經驗
		String experience = team.getExperience().toString();
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.SettingTeam.Experience."+experience), false,1, 3);
		gui.setAction(new ExperienceChange(gui, player, team), 1, 3);
		//物品
		String item = team.getItem().toString();
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.SettingTeam.Item."+item), false,1, 4);
		gui.setAction(new ItemChange(gui, player, team), 1, 4);
		//金錢
		String money = team.getMoney().toString();
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.SettingTeam.Money."+money), false,1, 5);
		gui.setAction(new MoneyChange(gui, player, team), 1, 5);
		//攻擊隊友
		String damage = String.valueOf(team.isDamageTeamPlayer());
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.SettingTeam.AttackTeammate."+damage), false,1, 6);
		gui.setAction(new DamageChange(gui, player, team), 1, 6);
		//自動加入隊伍
		String autoJon = String.valueOf(team.isAutoJoin());
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.SettingTeam.AgreeJoin."+autoJon), false,1, 7);
		gui.setAction(new AutoJoinChange(gui, player, team), 1, 7);
		//離開隊伍
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.Main.LeaveTeam"), false,1, 8);
		gui.setAction(new LeaveTeam(player, team), 1, 8);
		//解散隊伍
		gui.setItem(GuiButtom.valueOf(languageConfig,"Gui.Main.DisbandTeam"), false,1, 9);
		gui.setAction(new DisbandTeam(gui, player, team), 1, 9);
		//隊友列表
		playerListLeader(player, gui, team);

	}

	//隊長用隊友列表
	public static void playerListLeader(Player inPlayer, GUI gui, FTeam fTeam){
		TeamListType teamListType = fTeam.getTeamListType();
		//隊友
		if(teamListType == TeamListType.TeamPlayers){
			teamList(gui, fTeam);
		}
		//線上可以邀請的玩家
		if(teamListType == TeamListType.InvitePlayer){
			inviteList(gui, fTeam, inPlayer);
		}
		//申請加入的玩家
		if(teamListType == TeamListType.ApplyInvitePlayer){
			applyList(gui, fTeam, inPlayer);
		}
	}
	//隊友
	public static void teamList(GUI gui, FTeam fTeam){
		ItemStack teamItem = GuiButtom.valueOf(languageConfig,"Gui.Main.KickPlayer");
		List<Integer> ignore = new ArrayList<>();
		fTeam.getOnLinePlayers().forEach(uuid -> {
			Player p = Bukkit.getPlayer(uuid);
			ItemSet.setDisplayName(teamItem, languageConfig.getString("Message.OnLine").replace("%player_name%", p.getName()));
			ItemSet.setHeadValue(teamItem, p.getName());
			gui.addItem(teamItem, false, 10, 54, ignore);
			gui.addAction(new KickPlayer(fTeam, uuid), 10, 54, ignore);
		});
		fTeam.getOffLinePlayers().forEach(uuid -> {
			OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
			ItemSet.setDisplayName(teamItem, languageConfig.getString("Message.OffLine").replace("%player_name%", p.getName()));
			ItemSet.setHeadValue(teamItem, p.getName());
			gui.addItem(teamItem, false, 10, 54, ignore);
			gui.addAction(new KickPlayer(fTeam, uuid), 10, 54, ignore);
		});
	}
	//線上可以邀請的玩家
	public static void inviteList(GUI gui, FTeam fTeam, Player inPlayer){
		ItemStack teamItem = new ItemStack(Material.PLAYER_HEAD);
		List<Integer> ignore = new ArrayList<>();
		Bukkit.getOnlinePlayers().forEach(player -> {
			if(!fTeam.isTeamPlayer(player) && !fTeam.isApplyPlayer(player)){
				ItemSet.setDisplayName(teamItem, player.getName());
				ItemSet.setHeadValue(teamItem, player.getName());
				gui.addItem(teamItem, false, 10, 54, ignore);
				gui.addAction(new InvitePlayers(inPlayer, fTeam, player), 10, 54, ignore);
			}
		});
	}
	//申請加入的玩家
	public static void applyList(GUI gui, FTeam fTeam, Player inPlayer){
		ItemStack teamItem = new ItemStack(Material.PLAYER_HEAD);
		List<Integer> ignore = new ArrayList<>();
		fTeam.getApplyPlayers().forEach(uuid -> {
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
			String playerName = offlinePlayer.getName();
			ItemSet.setDisplayName(teamItem, playerName);
			ItemSet.setHeadValue(teamItem, playerName);
			gui.addItem(teamItem, false, 10, 54, ignore);
			gui.addAction(new AgreeJoin(inPlayer, fTeam, offlinePlayer.getUniqueId()), 10, 54, ignore);
		});
	}

}
