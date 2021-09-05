package com.daxton.fancyteam.gui;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancycore.api.item.ItemSet;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.gui.base.*;
import com.daxton.fancyteam.gui.noTeam.NoTeamListChange;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static com.daxton.fancyteam.config.FileConfig.languageConfig;

public class MainTeam {

	//打開Team介面
	public static void open(Player player){
		UUID uuid = player.getUniqueId();
		if(AllManager.playerUUID_GUI_Map.get(uuid) == null){
			GUI gui = GUI.GUIBuilder.getInstance().setPlayer(player).setSize(54).setTitle(languageConfig.getString("Title")).build();
			gui.setMove(false);
			AllManager.playerUUID_GUI_Map.put(uuid, gui);
		}

		GUI gui = AllManager.playerUUID_GUI_Map.get(uuid);

		if(AllManager.playerUUID_team_Map.get(uuid) == null){
			noTeam(gui, player);
		}else {
			HaveTeam.haveTeam(gui, player);
		}
		gui.open(gui);
	}

	//沒有隊伍時
	public static void noTeam(GUI gui, Player player){
		UUID uuid = player.getUniqueId();
		gui.clearButtonFrom(1, 54);
		//建立隊伍
		GuiButton createTeamButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.Main.CreateTeam")).
			setGuiAction(new CreateTeam(gui, player)).
			build();
		gui.setButton(createTeamButton, 1, 1);
		//列表
		AllManager.playerUUID_List_Map.putIfAbsent(uuid, "JoinTeamList");
		String noTeamList = AllManager.playerUUID_List_Map.get(uuid);

		GuiButton noTeamListChangeButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(languageConfig,"Gui.Main.NoTeamList."+noTeamList)).
			setGuiAction(new NoTeamListChange(player, gui)).
			build();
		gui.setButton(noTeamListChangeButton, 1, 2);

		if(noTeamList.equals("JoinTeamList")){
			ItemStack teamItem = GuiItem.valueOf(languageConfig,"Gui.Main.JoinTeam");
			Integer[] ignore = new Integer[]{};
			AllManager.teamName_FTeam_Map.forEach((teamName, fTeam) -> {

				ItemSet.setDisplayName(teamItem, fTeam.getTeamName());
				ItemSet.setHeadValue(teamItem, Bukkit.getPlayer(fTeam.getLeader()).getName());

				GuiButton joinTeamButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(teamItem).
					setGuiAction(new JoinTeam(gui, player, fTeam)).
					build();
				gui.addButton(joinTeamButton, 10, 54, ignore);
			});
		}
		if(noTeamList.equals("InviteTimeList")){
			ItemStack teamItem = new ItemStack(Material.PLAYER_HEAD);
			Integer[] ignore = new Integer[]{};
			AllManager.playerUUID_inviteList_Map.putIfAbsent(uuid, new HashSet<>());
			AllManager.playerUUID_inviteList_Map.get(uuid).forEach(teamName->{
				FTeam fTeam = AllManager.teamName_FTeam_Map.get(teamName);
				if(fTeam != null){
					ItemSet.setDisplayName(teamItem, fTeam.getTeamName());
					ItemSet.setHeadValue(teamItem, Bukkit.getPlayer(fTeam.getLeader()).getName());

					GuiButton directlyJoinTeamButton = GuiButton.ButtonBuilder.getInstance().
						setItemStack(teamItem).
						setGuiAction(new DirectlyJoinTeam(gui, player, fTeam)).
						build();
					gui.addButton(directlyJoinTeamButton, 10, 54, ignore);
				}
			});
		}


	}

	public static boolean deBug(){
		return false;
	}

}
