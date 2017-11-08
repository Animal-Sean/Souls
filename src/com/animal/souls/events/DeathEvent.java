package com.animal.souls.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.animal.souls.Core;
import com.animal.souls.utils.FileManager;
import com.animal.souls.utils.Utils;

public class DeathEvent implements Listener {
	
	private Core instance;
	
	public DeathEvent(Core instance) {
		this.instance = instance;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		FileManager file = new FileManager(instance);
		Utils utils = new Utils(instance);
		
		Player p = (Player) e.getEntity();
		String uuid = p.getUniqueId().toString();
		
		
		if (FileManager.cfg.getInt(uuid + ".souls.souls") > 0) {
			FileManager.cfg.set(uuid + ".souls.souls", FileManager.cfg.getInt(uuid + ".souls.souls") - 1);
			file.saveFile();
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Souls &8> &7You now have " + utils.getSouls(p) + " soul(s)!"));
		} else if (FileManager.cfg.getInt(uuid + ".souls.souls") == 0) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(instance, new Runnable() {

				@Override
				public void run() {
					utils.setBanned(p);
				}
			}, 10L);
			
		}
	}

}