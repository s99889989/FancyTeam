package com.daxton.fancyteam.api.mob;

import com.daxton.fancyteam.manager.AllManager;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class GetMob {

	//獲取活動的MythicMob
	public static ActiveMob activeMob(Entity mob){
		UUID mobUUID = mob.getUniqueId();
		if(AllManager.mobUUID_ActiveMob_Map.get(mobUUID) != null){
			return AllManager.mobUUID_ActiveMob_Map.get(mobUUID);
		}
		return null;
	}

}
