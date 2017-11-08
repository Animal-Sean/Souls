package com.animal.souls.utils;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.animal.souls.Core;

public class Utils implements Listener {
	
	private Core instance;
	
	public Utils(Core instance) {
		this.instance = instance;
	}
	
	private FileManager file = new FileManager(instance);
	
	public int getSouls(OfflinePlayer p) {
			String uuid = p.getUniqueId().toString();
			return FileManager.cfg.getInt(uuid + ".souls.souls");	
	}

	public void setSouls(OfflinePlayer p, int amount) {
			String uuid = p.getUniqueId().toString();
			FileManager.cfg.set(uuid + ".souls.souls", amount);
			file.saveFile();
		
	}

	

	public void resetSouls(OfflinePlayer p) {
			String uuid = p.getUniqueId().toString();
			int start = instance.getConfig().getInt("Souls");
			FileManager.cfg.set(uuid + ".souls.souls", start);
			file.saveFile();
		
	}
	
	public void setBanned(Player p) {

		String uuid = p.getUniqueId().toString();
		
		long bantime = System.currentTimeMillis() + (instance.getConfig().getLong("DeathBanTime") * 1000);
		
		FileManager.cfg.createSection(uuid + ".ban.time");
		FileManager.cfg.set(uuid + ".ban.time", bantime);
		
		//Time Format
		if (instance.getConfig().getLong("DeathBanTime") >= 86400) {
			int days = (int) (instance.getConfig().getLong("DeathBanTime") / 86400);
			p.kickPlayer("You have run out of souls! Please wait " + days + " days");
			
		} else if (instance.getConfig().getLong("DeathBanTime") >= 3600) {
			int hours = (int) (instance.getConfig().getLong("DeathBanTime") / 3600);
			p.kickPlayer("You have run out of souls! Please wait " + hours + " hours");
			
		} else if (instance.getConfig().getLong("DeathBanTime") >= 60) {
			int mins = (int) (instance.getConfig().getLong("DeathBanTime") / 60);
			p.kickPlayer("You have run out of souls! Please wait " + mins + " minutes");
			
		} else {
			int secs = (int) (instance.getConfig().getLong("DeathBanTime"));
			p.kickPlayer("You have run out of souls! Please wait " + secs + " seconds");
			
		}
		
		file.saveFile();
		
		
	}
	
	public void setUnbanned(OfflinePlayer p) {
		if (isBanned(p)) {
			String uuid = p.getUniqueId().toString();
			long bantime = System.currentTimeMillis() - 1;
			FileManager.cfg.set(uuid + ".ban.time", bantime);
			
		}
	}
	
	public boolean isBanned(OfflinePlayer p) {
		String uuid = p.getUniqueId().toString();
		if (FileManager.cfg.contains(uuid + ".ban.time")) {
			if (System.currentTimeMillis() < file.getFile().getLong(uuid + ".ban.time")) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
}
