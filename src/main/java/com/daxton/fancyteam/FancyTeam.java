package com.daxton.fancyteam;

import com.daxton.fancyteam.command.MainCommand;
import com.daxton.fancyteam.command.TabCommand;
import com.daxton.fancyteam.config.FileConfig;
import com.daxton.fancyteam.listener.PlayerListener;
import com.daxton.fancyteam.task.Start;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static com.daxton.fancyteam.config.FileConfig.languageConfig;

public final class FancyTeam extends JavaPlugin {

	public static FancyTeam fancyTeam;

	@Override
	public void onEnable() {
		fancyTeam = this;
		//設定檔
		FileConfig.execute();
		//前置插件
		if(!DependPlugins.depend()){
			fancyTeam.setEnabled(false);
			return;
		}
		//指令
		Objects.requireNonNull(Bukkit.getPluginCommand("fancyteam")).setExecutor(new MainCommand());
		Objects.requireNonNull(Bukkit.getPluginCommand("fancyteam")).setTabCompleter(new TabCommand());
		//開服執行任務
		Start.execute();
		//監聽
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), fancyTeam);

	}

	@Override
	public void onDisable() {
		fancyTeam.getLogger().info(languageConfig.getString("LogMessage.Disable"));
	}
}
