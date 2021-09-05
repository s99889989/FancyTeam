package com.daxton.fancyteam.listener;

import com.daxton.fancycore.hook.Vault.Currency;
import com.daxton.fancymobs.api.event.FancyMobDeathEvent;
import com.daxton.fancyteam.api.AllotThing;

import com.daxton.fancyteam.api.check.OnLineTeamCheck;
import com.daxton.fancyteam.api.get.OnLineTeamGet;
import com.daxton.fancyteam.api.teamenum.AllotType;
import com.daxton.fancyteam.api.team.FTeam;
import com.daxton.fancyteam.config.FileConfig;
import com.daxton.fancyteam.gui.MainTeam;
import com.google.common.collect.Lists;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FancyMobListener implements Listener {

	@EventHandler(priority = EventPriority.LOW)//當FancyMob的怪物死亡時
	public void onFancyMobDeath(FancyMobDeathEvent event){

		Entity mob = event.getFancyMob().getEntity();
		Player killer = event.getKiller();

		if(OnLineTeamCheck.isHaveTeam(killer)){
			FTeam fTeam = OnLineTeamGet.playerFTeam(killer);

			FileConfiguration config = FileConfig.config_Map.get("config.yml");
			boolean dropInventory= config.getBoolean("DropInventory");
			if(dropInventory){
				//修改物品掉落
				AllotType itemGetType = fTeam.getItem();
				List<Player> playerItemList = Lists.newArrayList(AllotThing.getPlayer(killer, mob.getUniqueId(), itemGetType));
				List<ItemStack> itemStacks = event.getDropItems();
				changeItem(itemGetType, playerItemList, itemStacks);
				event.setDropItems(new ArrayList<>());
			}

			//修改經驗掉落
			AllotType expGetType = fTeam.getExperience();
			List<Player> playerExpList = Lists.newArrayList(AllotThing.getPlayer(killer, mob.getUniqueId(), expGetType));
			changeExp(expGetType, killer, playerExpList, event.getDropExp());
			event.setDropExp(0);
			//修改金錢掉落
			AllotType moneyGetType = fTeam.getMoney();
			List<Player> playerMoneyList = Lists.newArrayList(AllotThing.getPlayer(killer, mob.getUniqueId(), moneyGetType));
			changeMoney(moneyGetType, playerMoneyList, event.getMoney());
			event.setMoney(0);
		}



	}

	//改變獲取金錢
	public static void changeMoney(AllotType moneyGetType, List<Player> playerList, double amount){

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


	//改變經驗
	public static void changeExp(AllotType expGetType, Player player, List<Player> playerList, double amount){

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

	//修改物品掉落
	public static void changeItem(AllotType itemGetType, List<Player> playerList, List<ItemStack> itemStacks){
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
	}



}
