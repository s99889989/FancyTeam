package com.daxton.fancyteam.api;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FTeamSetting {
	//列表類別
	private TeamListType teamListType = TeamListType.TeamPlayers;
	//經驗分配機制
	private AllotType experience = AllotType.Each;
	//物品分配機制
	private AllotType item = AllotType.Each;
	//錢分配機制
	private AllotType money = AllotType.Each;
	//是否自動同意加入隊伍
	private boolean autoJoin = false;
	//攻擊隊友
	private boolean damageTeamPlayer = false;
	//隊伍聊天開啟狀態
	private Map<UUID, Boolean> team_Chat = new HashMap<>();

	public FTeamSetting(){

	}

	public void setTeam_Chat(Player player, boolean in){
		UUID uuid = player.getUniqueId();
		team_Chat.put(uuid, in);
	}

	public boolean getTeam_Chat(Player player) {
		UUID uuid = player.getUniqueId();
		team_Chat.putIfAbsent(uuid, false);
		return team_Chat.get(uuid);
	}

	//切換列表顯示
	public void setTeamListTypeNext(){
		if(teamListType == TeamListType.TeamPlayers){
			teamListType = TeamListType.InvitePlayer;
		}else if(teamListType == TeamListType.InvitePlayer){
			teamListType = TeamListType.ApplyInvitePlayer;
		}else if(teamListType == TeamListType.ApplyInvitePlayer){
			teamListType = TeamListType.TeamPlayers;
		}
	}

	//把經驗分配設置為下一個
	public void setExperienceNext(){
		if(experience == AllotType.Each){
			experience = AllotType.Average;
		}else if(experience == AllotType.Average){
			experience = AllotType.Random;
		}else if(experience == AllotType.Random){
			experience = AllotType.Damage;
		}else if(experience == AllotType.Damage){
			experience = AllotType.Each;
		}
	}

	//設置經驗分配
	public void setExperience(String experienceString) {
		AllotType experience;
		try {
			experience = Enum.valueOf(AllotType.class, experienceString);
		}catch (Exception exception){
			experience = AllotType.Each;
		}
		this.experience = experience;
	}

	//把物品分配設置為下一個
	public void setItemNext(){
		if(item == AllotType.Each){
			item = AllotType.Average;
		}else if(item == AllotType.Average){
			item = AllotType.Random;
		}else if(item == AllotType.Random){
			item = AllotType.Damage;
		}else if(item == AllotType.Damage){
			item = AllotType.Each;
		}
	}

	//設置物品分配
	public void setItem(String itemString) {
		AllotType item;
		try {
			item = Enum.valueOf(AllotType.class, itemString);
		}catch (Exception exception){
			item = AllotType.Each;
		}
		this.item = item;
	}

	//把金錢分配設置為下一個
	public void setMoneyNext(){
		if(money == AllotType.Each){
			money = AllotType.Average;
		}else if(money == AllotType.Average){
			money = AllotType.Random;
		}else if(money == AllotType.Random){
			money = AllotType.Damage;
		}else if(money == AllotType.Damage){
			money = AllotType.Each;
		}
	}

	//設置金錢分配
	public void setMoney(String moneyString) {
		AllotType money;
		try {
			money = Enum.valueOf(AllotType.class, moneyString);
		}catch (Exception exception){
			money = AllotType.Each;
		}
		this.money = money;
	}
	//設置使否自動同意加入隊伍
	public void setAutoJoin(boolean autoJoin) {
		this.autoJoin = autoJoin;
	}
	//設置是否可以攻擊隊友
	public void setDamageTeamPlayer(boolean damageTeamPlayer) {
		this.damageTeamPlayer = damageTeamPlayer;
	}


	//獲取經驗分配類型
	public AllotType getExperience() {
		return experience;
	}
	//獲取物品分配類型
	public AllotType getItem() {
		return item;
	}
	//獲取金錢分配類型
	public AllotType getMoney() {
		return money;
	}
	//獲取列表顯示類型
	public TeamListType getTeamListType() {
		return teamListType;
	}
	//是否自動加入
	public boolean isAutoJoin() {
		return autoJoin;
	}
	//是否攻擊隊友
	public boolean isDamageTeamPlayer() {
		return damageTeamPlayer;
	}

}
