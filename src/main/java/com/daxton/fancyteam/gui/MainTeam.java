package com.daxton.fancyteam.gui;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.GuiButtom;
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
			GUI gui = GUI.createGui(player, 54, languageConfig.getString("Language.Title"));
			AllManager.playerUUID_GUI_Map.put(uuid, gui);
		}

		GUI gui = AllManager.playerUUID_GUI_Map.get(uuid);
		gui.clearFrom(1, 54);
		gui.setMoveAll(false);
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
		//建立隊伍
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.Main.CreateTeam"), false,1, 1);
		gui.setAction(new CreateTeam(gui, player), 1, 1);
		//列表
		AllManager.playerUUID_List_Map.putIfAbsent(uuid, "JoinTeamList");
		String noTeamList = AllManager.playerUUID_List_Map.get(uuid);
		gui.setItem(GuiButtom.valueOf(languageConfig,"Language.Gui.Main.NoTeamList."+noTeamList), false,1, 2);
		gui.setAction(new NoTeamListChange(player), 1, 2);

		if(noTeamList.equals("JoinTeamList")){
			ItemStack teamItem = GuiButtom.valueOf(languageConfig,"Language.Gui.Main.JoinTeam");
			List<Integer> ignore = new ArrayList<>();
			AllManager.teamName_FTeam_Map.forEach((teamName, fTeam) -> {

				ItemSet.setDisplayName(teamItem, fTeam.getTeamName());
				ItemSet.setHeadValue(teamItem, Bukkit.getPlayer(fTeam.getLeader()).getName());
				gui.addItem(teamItem, false, 10, 54, ignore);
				gui.addAction(new JoinTeam(gui, player, fTeam), 10, 54, ignore);
			});
		}
		if(noTeamList.equals("InviteTimeList")){
			ItemStack teamItem = new ItemStack(Material.PLAYER_HEAD);
			List<Integer> ignore = new ArrayList<>();
			AllManager.playerUUID_inviteList_Map.putIfAbsent(uuid, new HashSet<>());
			AllManager.playerUUID_inviteList_Map.get(uuid).forEach(teamName->{
				FTeam fTeam = AllManager.teamName_FTeam_Map.get(teamName);
				if(fTeam != null){
					ItemSet.setDisplayName(teamItem, fTeam.getTeamName());
					ItemSet.setHeadValue(teamItem, Bukkit.getPlayer(fTeam.getLeader()).getName());
					gui.addItem(teamItem, false, 10, 54, ignore);
					gui.addAction(new DirectlyJoinTeam(gui, player, fTeam), 10, 54, ignore);
				}
			});
		}


	}

	public static boolean deBug(){
		return false;
	}

}
