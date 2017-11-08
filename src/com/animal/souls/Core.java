package com.animal.souls;

import org.bukkit.plugin.java.JavaPlugin;

import com.animal.souls.commands.SoulsCommand;
import com.animal.souls.events.DeathEvent;
import com.animal.souls.events.JoinEvent;
import com.animal.souls.utils.FileManager;

public class Core extends JavaPlugin {
	
	/**
	 * Made by Animal 
	 */
	
	public void onEnable() {
		registerEvents();
		registerCommands();
		loadConfig();
		setup();

	}
	
	public void registerEvents() {
		getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
		getServer().getPluginManager().registerEvents(new DeathEvent(this), this);
	}
	
	public void registerCommands() {
		getCommand("souls").setExecutor(new SoulsCommand());
	}
	
	public void loadConfig() {
		this.saveDefaultConfig();
	}
	
	public void setup() {
		FileManager file = new FileManager(this);
		file.setup();
	}
}
