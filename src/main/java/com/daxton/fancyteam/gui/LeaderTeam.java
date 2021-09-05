package com.daxton.fancyteam.gui;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancycore.api.item.ItemSet;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.api.teamenum.TeamListType;
import com.daxton.fancyteam.gui.base.*;
import com.daxton.fancyteam.gui.button.leader.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static com.daxton.fancyteam.config.FileConfig.languageConfig;

public class LeaderTeam {

	//隊長
	public static void teamLeader(GUI gui, Player player, FTeam fTeam){
		//顯示列表
		String teamListType = fTeam.getTeamListType().toString();

		GuiButton teamTypeChangeButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.Main.TeamList."+teamListType)).
			setGuiAction(new TeamTypeChange(gui, player, fTeam)).
			build();
		gui.setButton(teamTypeChangeButton, 1, 1);

		//切換隊伍聊天
		boolean teamChat = fTeam.isTeam_Chat(player);
		GuiButton chatChangeButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.SettingTeam.TeamChat."+teamChat)).
			setGuiAction(new ChatChange(gui, player, fTeam)).
			build();
		gui.setButton(chatChangeButton, 1, 2);
		//經驗
		String experience = fTeam.getExperience().toString();
		GuiButton experienceChangeButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.SettingTeam.Experience."+experience)).
			setGuiAction(new ExperienceChange(gui, player, fTeam)).
			build();
		gui.setButton(experienceChangeButton, 1, 3);
		//物品
		String item = fTeam.getItem().toString();
		GuiButton itemChangeButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.SettingTeam.Item."+item)).
			setGuiAction(new ItemChange(gui, player, fTeam)).
			build();
		gui.setButton(itemChangeButton, 1, 4);
		//金錢
		String money = fTeam.getMoney().toString();
		GuiButton moneyChangeButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.SettingTeam.Money."+money)).
			setGuiAction(new MoneyChange(gui, player, fTeam)).
			build();
		gui.setButton(moneyChangeButton, 1, 5);
		//攻擊隊友
		String damage = String.valueOf(fTeam.isDamageTeamPlayer());
		GuiButton damageChangeButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.SettingTeam.AttackTeammate."+damage)).
			setGuiAction(new DamageChange(gui, player, fTeam)).
			build();
		gui.setButton(damageChangeButton, 1, 6);
		//自動加入隊伍
		String autoJon = String.valueOf(fTeam.isAutoJoin());
		GuiButton autoJoinChangeButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.SettingTeam.AgreeJoin."+autoJon)).
			setGuiAction(new AutoJoinChange(gui, player, fTeam)).
			build();
		gui.setButton(autoJoinChangeButton, 1, 7);
		//離開隊伍
		GuiButton leaveTeamButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.Main.LeaveTeam")).
			setGuiAction(new LeaveTeam(player, fTeam)).
			build();
		gui.setButton(leaveTeamButton, 1, 8);
		//解散隊伍
		GuiButton disbandTeamButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.Main.DisbandTeam")).
			setGuiAction(new DisbandTeam(gui, player, fTeam)).
			build();
		gui.setButton(disbandTeamButton, 1, 9);
		//隊友列表
		playerListLeader(player, gui, fTeam);

	}

	//隊長用隊友列表
	public static void playerListLeader(Player inPlayer, GUI gui, FTeam fTeam){
		gui.clearButtonFrom(10, 54);
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
		ItemStack teamItem = GuiItem.valueOf(languageConfig,"Gui.Main.KickPlayer");
		Integer[] ignore = new Integer[]{};
		fTeam.getOnLinePlayers().forEach(uuid -> {
			Player p = Bukkit.getPlayer(uuid);
			ItemSet.setDisplayName(teamItem, languageConfig.getString("Message.OnLine").replace("%player_name%", p.getName()));
			ItemSet.setHeadValue(teamItem, p.getName());

			GuiButton kickPlayerButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(teamItem).
				setGuiAction(new KickPlayer(fTeam, uuid)).
				build();
			gui.addButton(kickPlayerButton, 10, 54, ignore);
		});
		fTeam.getOffLinePlayers().forEach(uuid -> {
			OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
			ItemSet.setDisplayName(teamItem, languageConfig.getString("Message.OffLine").replace("%player_name%", p.getName()));
			ItemSet.setHeadValue(teamItem, p.getName());

			GuiButton kickPlayerButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(teamItem).
				setGuiAction(new KickPlayer(fTeam, uuid)).
				build();
			gui.addButton(kickPlayerButton, 10, 54, ignore);
		});
	}
	//線上可以邀請的玩家
	public static void inviteList(GUI gui, FTeam fTeam, Player inPlayer){
		ItemStack teamItem = new ItemStack(Material.PLAYER_HEAD);
		Integer[] ignore = new Integer[]{};
		Bukkit.getOnlinePlayers().forEach(player -> {
			if(!fTeam.isTeamPlayer(player) && !fTeam.isApplyPlayer(player)){
				ItemSet.setDisplayName(teamItem, player.getName());
				ItemSet.setHeadValue(teamItem, player.getName());

				GuiButton kickPlayerButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(teamItem).
					setGuiAction(new InvitePlayers(inPlayer, fTeam, player)).
					build();
				gui.addButton(kickPlayerButton, 10, 54, ignore);
			}
		});
	}
	//申請加入的玩家
	public static void applyList(GUI gui, FTeam fTeam, Player inPlayer){
		ItemStack teamItem = new ItemStack(Material.PLAYER_HEAD);
		Integer[] ignore = new Integer[]{};
		fTeam.getApplyPlayers().forEach(uuid -> {
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
			String playerName = offlinePlayer.getName();
			ItemSet.setDisplayName(teamItem, playerName);
			ItemSet.setHeadValue(teamItem, playerName);

			GuiButton agreeJoinButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(teamItem).
				setGuiAction(new AgreeJoin(inPlayer, fTeam, offlinePlayer.getUniqueId())).
				build();
			gui.addButton(agreeJoinButton, 10, 54, ignore);
		});
	}

}
