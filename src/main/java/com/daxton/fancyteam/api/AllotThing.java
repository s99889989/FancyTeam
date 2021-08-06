package com.daxton.fancyteam.api;

import com.daxton.fancyteam.api.check.OnLineTeamCheck;
import com.daxton.fancyteam.api.get.OnLineTeamGet;
import com.daxton.fancyteam.api.mob.GetMob;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.api.teamenum.AllotType;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class AllotThing {

	//獲取各類型玩家
	public static Set<Player> getPlayer(Player triggerPlayer, Entity mob, AllotType itemGetType){
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
				if(GetMob.activeMob(mob) != null){
					ActiveMob activeMob = GetMob.activeMob(mob);
					Player mostPlayer = null;
					double most = 0;
					for(AbstractEntity abstractEntity : activeMob.getThreatTable().getAllThreatTargets()){
						Entity entity = abstractEntity.getBukkitEntity();
						if(entity instanceof Player){
							Player p = (Player) entity;
							if(OnLineTeamCheck.isSameTeam(triggerPlayer, p)){
								//FancyTeam.fancyTeam.getLogger().info(entity.getName());
								double d = activeMob.getThreatTable().getThreat(abstractEntity);
								if(d > most){
									most = d;
									mostPlayer = p;
								}
							}
						}
					}
					if(mostPlayer != null){
						playerSet.add(mostPlayer);
					}
				}
			}

		}

		return playerSet;
	}

}
