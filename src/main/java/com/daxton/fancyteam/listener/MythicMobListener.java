package com.daxton.fancyteam.listener;

import com.daxton.fancycore.api.hook.Vault.Currency;
import com.daxton.fancyteam.FancyTeam;
import com.daxton.fancyteam.api.AllotThing;

import com.daxton.fancyteam.api.check.OnLineTeamCheck;
import com.daxton.fancyteam.api.get.OnLineTeamGet;
import com.daxton.fancyteam.api.teamenum.AllotType;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.gui.MainTeam;
import com.daxton.fancyteam.manager.AllManager;
import com.google.common.collect.Lists;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobLootDropEvent;
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobSpawnEvent;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MythicMobListener implements Listener {

	@EventHandler
	public void onMythicMobSpawn(MythicMobSpawnEvent event){
		ActiveMob activeMob = event.getMob();
		UUID uuid = activeMob.getUniqueId();
		AllManager.mobUUID_ActiveMob_Map.put(uuid, activeMob);


	}

	@EventHandler(priority = EventPriority.LOW)//當MythicMob的怪物死亡時
	public void onMythicMobDeath(MythicMobDeathEvent event){
		Entity mob = event.getEntity();
		LivingEntity killer = event.getKiller();
		if(killer instanceof Player){
			Player player = (Player) killer;
			if(OnLineTeamCheck.isHaveTeam(player)){
				FTeam fTeam = OnLineTeamGet.playerFTeam(player);
				AllotType itemGetType = fTeam.getItem();
				List<Player> playerList = Lists.newArrayList(AllotThing.getPlayer(player, mob, itemGetType));
				List<ItemStack> itemStacks = event.getDrops();

				if(itemGetType == AllotType.Each){
					//各自
					if(playerList.size() == 1){
						Player getPlayer = playerList.get(0);
						Inventory inventory = getPlayer.getInventory();
						itemStacks.forEach(inventory::addItem);
					}
				}
				if(itemGetType == AllotType.Average){
					//均分情況
					if(playerList.size() > 1){
						Collections.shuffle(playerList);
						int s = 0;
						for(int k = 0 ; k < itemStacks.size() ; k++){
							if(s >= playerList.size()){
								s = 0;
							}
							Inventory inventory = playerList.get(s).getInventory();
							inventory.addItem(itemStacks.get(k));
							s++;
						}
					}
				}
				if(itemGetType == AllotType.Random){
					if(playerList.size() == 1){
						Player getPlayer = playerList.get(0);
						Inventory inventory = getPlayer.getInventory();
						itemStacks.forEach(inventory::addItem);
					}
				}
				if(itemGetType == AllotType.Damage){
					if(playerList.size() == 1){
						Player getPlayer = playerList.get(0);
						Inventory inventory = getPlayer.getInventory();
						itemStacks.forEach(inventory::addItem);
					}
				}

				event.getDrops().clear();
			}
		}

	}
	@EventHandler//在生成戰利品表之前調用
	public void onMythicMobLootDrop(MythicMobLootDropEvent event){
		Entity mob = event.getEntity();
		LivingEntity killer = event.getKiller();

		if(killer instanceof Player){
			Player player = (Player) killer;
//			player.sendMessage("獲得經驗:"+event.getExp());
//			player.sendMessage("獲得金錢:"+event.getMoney());
			if(OnLineTeamCheck.isHaveTeam(player)){
				changeMoney(player, mob, event.getMoney());
				changeExp(player, mob, event.getExp());
				event.setMoney(0);
				event.setExp(0);
			}

		}

	}
	//改變經驗
	public static void changeExp(Player player, Entity mob, double amount){
		FTeam fTeam = OnLineTeamGet.playerFTeam(player);
		AllotType expGetType = fTeam.getExperience();
		List<Player> playerList = Lists.newArrayList(AllotThing.getPlayer(player, mob, expGetType));


		if(expGetType == AllotType.Each){
			//各自或看傷害情況
			if(!playerList.isEmpty()){
				Player getPlayer = playerList.get(0);
				getPlayer.giveExp((int) amount);
				if(MainTeam.deBug()){
					player.sendMessage("exp: "+amount);
				}
			}
		}
		if(expGetType == AllotType.Average){
			double d = amount / playerList.size();
			//均分情況
			playerList.forEach(player1 -> {
				player1.giveExp((int) d);
				if(MainTeam.deBug()){
					player1.sendMessage("exp: "+d);
				}
			});
		}
		if(expGetType == AllotType.Random){
			if(!playerList.isEmpty()){
				Player getPlayer = playerList.get(0);
				getPlayer.giveExp((int) amount);
				if(MainTeam.deBug()){
					getPlayer.sendMessage("exp: "+amount);
				}
			}
		}
		if(expGetType == AllotType.Damage){
			if(!playerList.isEmpty()){
				Player getPlayer = playerList.get(0);
				getPlayer.giveExp((int) amount);
				if(MainTeam.deBug()){
					getPlayer.sendMessage("exp: "+amount);
				}
			}
		}
	}
	//改變獲取金錢
	public static void changeMoney(Player player, Entity mob, double amount){
		FTeam fTeam = OnLineTeamGet.playerFTeam(player);
		AllotType moneyGetType = fTeam.getMoney();
		List<Player> playerList = Lists.newArrayList(AllotThing.getPlayer(player, mob, moneyGetType));

		if(moneyGetType == AllotType.Each){
			//各自或看傷害情況
			if(!playerList.isEmpty()){
				Player getPlayer = playerList.get(0);
				Currency.giveMoney(getPlayer, amount);
				if(MainTeam.deBug()){
					getPlayer.sendMessage("money: "+amount);
				}
			}
		}
		if(moneyGetType == AllotType.Average){
			double d = amount / playerList.size();
			//均分情況
			playerList.forEach(player1 -> {
				Currency.giveMoney(player1, d);
				if(MainTeam.deBug()){
					player1.sendMessage("AVmoney: "+d);
				}
			});
		}
		if(moneyGetType == AllotType.Random){
			if(!playerList.isEmpty()){
				Player getPlayer = playerList.get(0);
				Currency.giveMoney(getPlayer, amount);
				if(MainTeam.deBug()){
					getPlayer.sendMessage("money: "+amount);
				}
			}
		}
		if(moneyGetType == AllotType.Damage){
			if(!playerList.isEmpty()){
				Player getPlayer = playerList.get(0);
				Currency.giveMoney(getPlayer, amount);
				if(MainTeam.deBug()){
					getPlayer.sendMessage("money: "+amount);
				}
			}
		}
	}

}
