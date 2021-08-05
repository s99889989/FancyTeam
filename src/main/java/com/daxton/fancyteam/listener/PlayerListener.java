package com.daxton.fancyteam.listener;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancyteam.api.AllotThing;
import com.daxton.fancyteam.api.FTeam;
import com.daxton.fancyteam.gui.CreateTeam;
import com.daxton.fancyteam.gui.MainTeam;
import com.daxton.fancyteam.manager.AllManager;
import com.daxton.fancyteam.task.TeamStates;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;


public class PlayerListener implements Listener {

    @EventHandler//當玩家登入
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        AllManager.playerUUID_chat_Map.put(uuid, false);
        TeamStates.onLine(player);
        PlayerListener.onOnLinePlaeyrChange();
    }
    @EventHandler//當玩家登出
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        TeamStates.offLine(player);
        if(AllManager.playerUUID_GUI_Map.get(uuid) != null){
            AllManager.playerUUID_GUI_Map.remove(uuid);
        }
        PlayerListener.onOnLinePlaeyrChange();
    }

    @EventHandler//玩家聊天監聽
    public void onPlayerChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(AllManager.playerUUID_chat_Map.get(uuid)){
            event.setCancelled(true);
            String chatString = event.getMessage();
            GUI gui = AllManager.playerUUID_GUI_Map.get(uuid);
            if(gui.getAction(1, 1) != null){
                CreateTeam createTeam = (CreateTeam) gui.getAction(1, 1);
                createTeam.chat(chatString);
            }

        }
    }
    @EventHandler(priority = EventPriority.LOW)//攻擊
    public void onPhysicalDamage(EntityDamageByEntityEvent event){
        Entity attacker = event.getDamager();
        Entity attacked = event.getEntity();
        if(!(attacker instanceof Player) || !(attacked instanceof Player)){
            return;
        }
        Player attackerPlayer = (Player) attacker;
        Player attackedPlayer = (Player) attacked;
        if(AllotThing.checkHaveTeam(attackerPlayer)){
            if(AllotThing.checkIsSameTeam(attackerPlayer, attackedPlayer)){
                FTeam fTeam = AllotThing.getPlayerFTeam(attackerPlayer);
                event.setCancelled(!fTeam.isDamageTeamPlayer());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)//當玩家聊天
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if(AllotThing.checkHaveTeam(player)){
            FTeam fTeam = AllotThing.getPlayerFTeam(player);
            if(fTeam.getTeam_Chat(player)){
                String chatMessage = event.getMessage();
                event.setCancelled(true);
                String playerName = player.getName();

                fTeam.getOnLinePlayers().stream().map(Bukkit::getPlayer).forEach(player1 -> {
                    player1.sendMessage(playerName+"->"+chatMessage);
                });

            }
        }
    }

    //當隊伍改變
    public static void onTeamChange(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            UUID uuid = player.getUniqueId();
            if(AllManager.playerUUID_GUI_Map.get(uuid) != null){
                GUI gui = AllManager.playerUUID_GUI_Map.get(uuid);
                if(gui.isOpen()){
                    MainTeam.open(player);
                }
            }
        });
    }
    //當線上玩家改變
    public static void onOnLinePlaeyrChange(){
        onTeamChange();
    }
//    @EventHandler//玩家移動
//    public void onPlayerMove(PlayerMoveEvent event){
//        Player player = event.getPlayer();
//        String uuidString = player.getUniqueId().toString();
//        if(AllManager.player_chat_Map.get(uuidString)){
//
//        }
//    }



}
