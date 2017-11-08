package com.animal.souls.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.animal.souls.utils.Utils;


public class SoulsCommand implements CommandExecutor {
	
	private Utils utils = new Utils(null);
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String prefix = "&4&lSouls &8> ";
		
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("help")) {
				if (sender.hasPermission("souls.help")) {
					if (args.length == 1) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l&m--------&r &4&lSouls Help &8&l&m--------"));
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &7/souls &8- &7Find your amount of souls!"));
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &7/souls get (player) &8- &7Find out a player's souls!"));
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &7/souls set (player) (amount) &8- &7Set a player's souls count!"));
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &7/souls add (player) (amount) &8- &7Add to a player's soul count!"));
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "  &7/souls unban (player) &8- &7Reset a player's souls!"));
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cUsage /souls help"));
					}
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cYou do not have permission!"));
				}
			} else if (args[0].equalsIgnoreCase("get")) {
				if (sender.hasPermission("souls.get")) {
					if (args.length == 2) {
						OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
						if (target != null) {
							int amount = utils.getSouls(target);
							
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7" + target.getName() + " has " + amount + " souls!"));
						} else {
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cPlayer not found!"));
						}
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cUsage /souls get (player)"));
					}
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cYou do not have permission!"));
				}
			} else if (args[0].equalsIgnoreCase("set")) {
				if (sender.hasPermission("souls.set")) {
					if (args.length == 3) {
						OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
						if (target != null) {
							Integer amount = Integer.valueOf(args[2]);
							if (amount != null) {
								utils.setSouls(target, amount);
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7You have set " + target.getName() + "'s souls to " + amount + "!"));
							} else {
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cInvalid amount!"));
							}
						} else {
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cPlayer not found!"));
						}
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cUsage /souls set (player) (amount)"));
					}
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cYou do not have permission!"));
				}
			} else if (args[0].equalsIgnoreCase("add")) {
				if (sender.hasPermission("souls.add")) {
					if (args.length == 3) {
						OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
						if (target != null) {
							Integer amount = Integer.valueOf(args[2]);
							if (amount != null) {
								int souls = utils.getSouls(target);
								int newsouls = souls + amount;
								utils.setSouls(target, newsouls);
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7You have added " + amount + " to " + target.getName() 
								+ "'s soul count!"));
							} else {
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cInvalid amount!"));
							}
						}
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cUsage /souls add (player) (amount)"));
					}
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cYou do not have permission!"));
				}
			} else if (args[0].equalsIgnoreCase("unban")) {	
				if (sender.hasPermission("souls.unban")) {
					if (args.length == 2) {
						OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
						if (target != null) {
							if (utils.isBanned(target)) {
								utils.setUnbanned(target);
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7The player " + target.getName() + " has been unbanned!"));
							} else {
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7The player " + target.getName() + " is not banned!"));
							}
						} else {
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cPlayer not found!"));
						}
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cUsage /souls unban (player)"));
					}
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cYou do not have permission!"));
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cUnknown argument!"));
			}
		} else {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				int souls = utils.getSouls(p);
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&7You have " + souls + " soul(s)!"));
			} else {
				sender.sendMessage("You must be a player to use this command!");
			}
		}
		return false;
	}

}
