package com.daxton.fancyteam.manager;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancyteam.api.FTeam;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import org.bukkit.entity.Player;

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
	//獲得MythicMob的活動實體
	public static Map<UUID, ActiveMob> mobUUID_ActiveMob_Map = new HashMap<>();


}
