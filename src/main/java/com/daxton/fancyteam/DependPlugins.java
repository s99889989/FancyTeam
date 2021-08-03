package com.daxton.fancyteam;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class DependPlugins {

    public static boolean depend(){

        FancyTeam fancyTeam = FancyTeam.fancyTeam;

        if (Bukkit.getServer().getPluginManager().getPlugin("FancyCore") != null && Bukkit.getPluginManager().isPluginEnabled("FancyCore")){
            fancyTeam.getLogger().info(ChatColor.GREEN+"Loaded FancyCore");
        }else {
            fancyTeam.getLogger().severe("*** FancyCore is not installed or not enabled. ***");
            fancyTeam.getLogger().severe("*** FancyItemsy will be disabled. ***");
            fancyTeam.getLogger().severe("*** FancyCore未安裝或未啟用。 ***");
            fancyTeam.getLogger().severe("*** FancyItems將被卸載。 ***");
            return false;
        }

        return true;
    }

}
