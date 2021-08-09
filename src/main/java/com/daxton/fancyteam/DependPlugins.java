package com.daxton.fancyteam;


import com.daxton.fancyteam.listener.FancyMobListener;
import org.bukkit.Bukkit;

import static com.daxton.fancyteam.config.FileConfig.languageConfig;


public class DependPlugins {

    public static boolean depend(){

        FancyTeam fancyTeam = FancyTeam.fancyTeam;

        if (Bukkit.getServer().getPluginManager().getPlugin("FancyCore") != null && Bukkit.getPluginManager().isPluginEnabled("FancyCore")){
            fancyTeam.getLogger().info(languageConfig.getString("LogMessage.LoadFancyCore"));
        }else {
            languageConfig.getStringList("LogMessage.UnLoadFancyCore").forEach(message-> fancyTeam.getLogger().info(message));
            return false;
        }
        if (Bukkit.getServer().getPluginManager().getPlugin("FancyMobs") != null && Bukkit.getPluginManager().isPluginEnabled("FancyMobs")) {
            fancyTeam.getLogger().info(languageConfig.getString("LogMessage.LoadFancyMobs"));
            Bukkit.getPluginManager().registerEvents(new FancyMobListener(), fancyTeam);
        }else {
            languageConfig.getStringList("LogMessage.UnLoadFancyMobs").forEach(message-> fancyTeam.getLogger().info(message));
            return false;
        }

        return true;
    }

}
