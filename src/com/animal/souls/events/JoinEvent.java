package com.animal.souls.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import com.animal.souls.Core;
import com.animal.souls.utils.FileManager;
import com.animal.souls.utils.Utils;

public class JoinEvent implements Listener {
	
	private Core instance;
	
	public JoinEvent(Core instance) {
		this.instance = instance;
	}
	
	private FileManager file = new FileManager(instance);
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String uuid = p.getUniqueId().toString();
		int start = instance.getConfig().getInt("Souls");
		if (!(FileManager.cfg.contains(uuid + ".souls.souls"))) {
			FileManager.cfg.createSection(uuid + ".souls.souls");
			FileManager.cfg.set(uuid + ".souls.souls", start);
			file.saveFile();
		}				
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		String uuid = p.getUniqueId().toString();
		if (FileManager.cfg.contains(uuid + ".ban.time")) {
			if (System.currentTimeMillis() >= file.getFile().getLong(uuid + ".ban.time")) {
				Utils utils = new Utils(instance);
				utils.resetSouls(p);
				FileManager.cfg.set(uuid + ".ban.time", 0); 
				file.saveFile();
			} else {
				e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "Your souls haven't regenerated!");
			}
		}
	}
}
