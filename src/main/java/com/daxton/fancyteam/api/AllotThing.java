package com.daxton.fancyteam.api;

import com.daxton.fancymobs.api.FancyMob;
import com.daxton.fancymobs.manager.MobManager;
import com.daxton.fancyteam.api.check.OnLineTeamCheck;
import com.daxton.fancyteam.api.get.OnLineTeamGet;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.api.teamenum.AllotType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class AllotThing {

	//獲取各類型玩家
	public static Set<Player> getPlayer(Player triggerPlayer, UUID mobUUID, AllotType itemGetType){
		Set<Player> playerSet = new HashSet<>();

		if(OnLineTeamCheck.isHaveTeam(triggerPlayer)){
			FTeam fTeam = OnLineTeamGet.playerFTeam(triggerPlayer);
			if(itemGetType == AllotType.Each){
				//FancyTeam.fancyTeam.getLogger().info("個自");
				playerSet.add(triggerPlayer);
			}
			if(itemGetType == AllotType.Average){
				//FancyTeam.fancyTeam.getLogger().info("均分");
				playerSet.addAll(fTeam.getOnLinePlayers().stream().map(Bukkit::getPlayer).collect(Collectors.toList()));
			}
			if(itemGetType == AllotType.Random){
				//FancyTeam.fancyTeam.getLogger().info("隨機");
				List<Player> pList = fTeam.getOnLinePlayers().stream().map(Bukkit::getPlayer).collect(Collectors.toList());
				Collections.shuffle(pList);
				playerSet.add(pList.get(0));
			}
			if(itemGetType == AllotType.Damage){
				//FancyTeam.fancyTeam.getLogger().info("傷害");
				FancyMob fancyMob = MobManager.fancy_Mob_Map.get(mobUUID);
				if(fancyMob != null){
					Player mostPlayer = triggerPlayer;
					double highest = 0;
					for(UUID uuid : fancyMob.getThreatTable().keySet()){
						if(OnLineTeamCheck.isSameTeam(triggerPlayer, Bukkit.getPlayer(uuid))){
							double amount = fancyMob.getThreatTable().get(uuid);
							if(amount > highest){
								highest = amount;
								mostPlayer = Bukkit.getPlayer(uuid);
							}
						}
					}
					playerSet.add(mostPlayer);
				}else {
					playerSet.add(triggerPlayer);
				}

			}

		}

		return playerSet;
	}

}
