package com.daxton.fancyteam.api;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancyteam.FancyTeam;
import com.daxton.fancyteam.config.TeamConfig;
import com.daxton.fancyteam.gui.MainTeam;
import com.daxton.fancyteam.listener.PlayerListener;
import com.daxton.fancyteam.manager.AllManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class FTeam extends FTeamSetting{

	//隊伍名稱
	private String teamName;
	//隊長
	private UUID leader;
	//線上隊員
	private Set<UUID> onlinePlayers = new HashSet<>();
	//離線隊員
	private Set<UUID> offlinePlayers = new HashSet<>();
	//申請入對玩家
	private Set<UUID> applyPlayers = new HashSet<>();
	//建立隊伍
	public FTeam(Player player, String teamName){
		this.teamName = teamName;
		UUID uuid = player.getUniqueId();
		this.leader = uuid;
		onlinePlayers.add(uuid);
		AllManager.playerUUID_team_Map.put(uuid, teamName);
		//設置玩家設定
		TeamConfig.setPlayerData(player, teamName);
		PlayerListener.onTeamChange();
	}
	//建立已存在隊伍
	public FTeam(UUID uuid, String teamName){
		this.teamName = teamName;
		this.leader = uuid;
		PlayerListener.onTeamChange();
	}
	//玩家加入隊伍
	public void playerJoin(Player player){
		UUID uuid = player.getUniqueId();
		onlinePlayers.add(uuid);
		AllManager.playerUUID_team_Map.put(uuid, teamName);
		//設置玩家設定
		TeamConfig.setPlayerData(player, teamName);
		PlayerListener.onTeamChange();
	}
	//玩家離開隊伍
	public void playerLeave(Player player){
		UUID uuid = player.getUniqueId();

		onlinePlayers.remove(uuid);
		AllManager.playerUUID_team_Map.remove(uuid);
		if(uuid.equals(leader)){
			leader = null;
			if(onlinePlayers.size() > 0){
				leader = onlinePlayers.stream().findAny().get();
			}else if(offlinePlayers.size() > 0){
				leader = offlinePlayers.stream().findAny().get();
			}
			if(leader == null){
				disbandTeam();
			}
		}
		//移除玩家設定
		TeamConfig.removePlayerData(player);
		PlayerListener.onTeamChange();
	}
	//解散隊伍
	public void disbandTeam(){
		TeamConfig.removeTeam(teamName);
		onlinePlayers.forEach(uuid -> {
			FancyTeam.fancyTeam.getLogger().info("移除"+uuid);
			AllManager.playerUUID_team_Map.remove(uuid);
			Player player = Bukkit.getPlayer(uuid);
			if(AllManager.playerUUID_GUI_Map.get(uuid) != null){
				GUI gui = AllManager.playerUUID_GUI_Map.get(uuid);
				if(gui.isOpen()){
					MainTeam.open(player);
				}
			}
		});
		PlayerListener.onTeamChange();
	}
	//玩家上線
	public void playerOnLine(Player player){
		UUID uuid = player.getUniqueId();
		onlinePlayers.add(uuid);
		offlinePlayers.remove(uuid);
	}
	//玩家下線
	public void playerOffLine(Player player){
		UUID uuid = player.getUniqueId();
		onlinePlayers.remove(uuid);
		offlinePlayers.add(uuid);
	}

	//獲取線上玩家
	public Set<UUID> getOnLinePlayers(){
		return onlinePlayers;
	}
	//獲取離線玩家
	public Set<UUID> getOffLinePlayers(){
		return offlinePlayers;
	}
	//獲取申請加入玩家
	public Set<UUID> getApplyPlayers() {
		return applyPlayers;
	}

	//獲取隊伍名稱
	public String getTeamName() {
		return teamName;
	}
	//獲取隊長
	public UUID getLeader() {
		return leader;
	}

	//設置離線玩家
	public void setOfflinePlayers(List<String> offlinePlayersString) {
		this.offlinePlayers = offlinePlayersString.stream().map(UUID::fromString).collect(Collectors.toSet());
	}

	//設置隊伍名稱
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	//設置隊長
	public void setLeader(UUID leader) {
		this.leader = leader;
	}

	//判斷是否為隊長
	public boolean isLeader(Player player){
		boolean b = false;
		UUID inUUID = player.getUniqueId();
		if(inUUID.equals(leader)){
			b = true;
		}
		return b;
	}
	//判斷是否為申請加入的玩家
	public boolean isApplyPlayer(Player player){
		UUID inUUID = player.getUniqueId();
		return applyPlayers.contains(inUUID);
	}
	//判斷是否在此隊伍
	public boolean isTeamPlayer(Player player){
		UUID inUUID = player.getUniqueId();
		if(onlinePlayers.contains(inUUID)){
			return true;
		}
		return offlinePlayers.contains(inUUID);
	}
}
