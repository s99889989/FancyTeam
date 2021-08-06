package com.daxton.fancyteam.api.team;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NTeam {
	//玩家
	private final Player player;
	//玩家的UUID
	private final UUID playerUUID;
	//已邀請的隊伍列表
	private Set<String> invite_Team_List = new HashSet<>();
	//已申請的隊伍列表
	private Set<String> apply_Team_List = new HashSet<>();

	public NTeam(Player player){
		this.player = player;
		this.playerUUID = player.getUniqueId();
	}
	//判斷是否已經邀請
	public boolean isInvite(String inTeam){
		return invite_Team_List.contains(inTeam);
	}
	//判斷是否已經申請
	public boolean isApply(String inTeam){
		return apply_Team_List.contains(inTeam);
	}

}
