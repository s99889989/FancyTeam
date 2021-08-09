package com.daxton.fancyteam.manager;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.api.team.NTeam;

import java.util.*;

public class AllManager {

	//玩家GUI PlayerUUID Gui
	public static Map<UUID, GUI> playerUUID_GUI_Map = new HashMap<>();
	//隊伍名稱 -> 隊伍物件
	public static Map<String, FTeam> teamName_FTeam_Map = new HashMap<>();
	//玩家隊伍 PlayerUUID Team
	public static Map<UUID, String> playerUUID_team_Map = new HashMap<>();
	//PlayerUUID boolean，玩家對話輸入
	public static Map<UUID, Boolean> playerUUID_chat_Map = new HashMap<>();
	//玩家無隊伍列表
	public static Map<UUID, String> playerUUID_List_Map = new HashMap<>();
	//隊伍邀請列表
	public static Map<UUID, Set<String>> playerUUID_inviteList_Map = new HashMap<>();
	//沒隊伍的隊伍
	public static Map<UUID, NTeam> playerUUID_NTeam_Map = new HashMap<>();
}
