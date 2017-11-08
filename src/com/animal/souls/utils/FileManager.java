package com.animal.souls.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import com.animal.souls.Core;

public class FileManager implements Listener {
	
	private Core instance;
	
	public FileManager(Core instance) {
		this.instance = instance;
	}
	
	public static FileConfiguration cfg;
	
	public static File file;

	public void setup() {
		file = new File(instance.getDataFolder(), "playerinfo.yml");

		if (!file.exists()) {
			try {
				file.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage("Creating playerinfo.yml");
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.RED + "The playersinfo.yml could not be created!");
			}
		}

		cfg = YamlConfiguration.loadConfiguration(file);
	}

	public FileConfiguration getFile() {
		return cfg;
	}

	public void saveFile() {
		try {
			cfg.save(file);
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Could not save the playersinfo.yml file");
		}
	}

	public void reloadFile() {
		cfg = YamlConfiguration.loadConfiguration(file);
	}

}
