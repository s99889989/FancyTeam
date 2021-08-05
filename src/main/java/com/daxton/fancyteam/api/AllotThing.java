package com.daxton.fancyteam.api;

import com.daxton.fancyteam.manager.AllManager;
import io.lumine.xikage.mythicmobs.adapters.AbstractEntity;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class AllotThing {

	public static Set<Player> getPlayer(Player triggerPlayer, Entity mob){
		Set<Player> playerSet = new HashSet<>();

		if(checkHaveTeam(triggerPlayer)){
			FTeam fTeam = getPlayerFTeam(triggerPlayer);
			AllotType itemGetType = fTeam.getItem();
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
				if(getActiveMob(mob) != null){
					ActiveMob activeMob = getActiveMob(mob);
					Player mostPlayer = null;
					double most = 0;
					for(AbstractEntity abstractEntity : activeMob.getThreatTable().getAllThreatTargets()){
						Entity entity = abstractEntity.getBukkitEntity();
						if(entity instanceof Player){
							Player p = (Player) entity;
							if(checkIsSameTeam(triggerPlayer, p)){
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

	public static boolean checkIsSameTeam(Player player1, Player player2){
		if(checkHaveTeam(player1)){
			FTeam fTeam = getPlayerFTeam(player1);
			UUID uuid2 = player2.getUniqueId();
			return fTeam.getOnLinePlayers().stream().anyMatch(uuid -> uuid.equals(uuid2));
		}
		return false;
	}

	public static ActiveMob getActiveMob(Entity mob){
		UUID mobUUID = mob.getUniqueId();
		if(AllManager.mobUUID_ActiveMob_Map.get(mobUUID) != null){
			return AllManager.mobUUID_ActiveMob_Map.get(mobUUID);
		}
		return null;
	}

	public static FTeam getPlayerFTeam(Player player){
		UUID playerUUID = player.getUniqueId();
		if(AllManager.playerUUID_team_Map.get(playerUUID) != null){
			String teamName = AllManager.playerUUID_team_Map.get(playerUUID);
			return AllManager.teamName_FTeam_Map.get(teamName);
		}
		return null;
	}


	public static boolean checkHaveTeam(Player player){
		UUID playerUUID = player.getUniqueId();
		return AllManager.playerUUID_team_Map.get(playerUUID) != null;
	}

}
