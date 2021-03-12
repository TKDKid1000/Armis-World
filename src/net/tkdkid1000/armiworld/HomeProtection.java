package net.tkdkid1000.armiworld;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import net.md_5.bungee.api.ChatColor;
import net.tkdkid1000.armiworld.utils.BoundingBox;

public class HomeProtection implements Listener {

	private ArmiWorldEconomy armiworldeconomy;
	private FileConfiguration config;

	public HomeProtection(ArmiWorldEconomy armiworldeconomy) {
		this.armiworldeconomy = armiworldeconomy;
		this.config = armiworldeconomy.config;
	}
	
	public void register() {
		armiworldeconomy.getServer().getPluginManager().registerEvents(this, armiworldeconomy);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Location loc = event.getBlock().getLocation();
		if (player.hasPermission("armisworld.admin") || !loc.getWorld().getName().equalsIgnoreCase(config.getString("world"))) return;
		Home home = Home.getHome(player);
		BoundingBox house = home.getBoundingBox();
		if (!house.contains(loc.toVector())) {
			if (Home.getHome(loc) != null) {
				if (!Home.getHome(loc).canbuild()) {
					player.sendMessage(ChatColor.RED + "You can't build in " + Home.getHome(loc).getOwner().getDisplayName() + ChatColor.RED + "'s house!");
					event.setCancelled(true);
				}
			} else {
				player.sendMessage(ChatColor.RED + "You can't build outside your proprty!");
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Location loc = event.getBlock().getLocation();
		if (player.hasPermission("armisworld.admin") || !loc.getWorld().getName().equalsIgnoreCase(config.getString("world"))) return;
		Home home = Home.getHome(player);
		BoundingBox house = home.getBoundingBox();
		if (!house.contains(loc.toVector())) {
			if (Home.getHome(loc) != null) {
				if (!Home.getHome(loc).canbuild()) {
					player.sendMessage(ChatColor.RED + "You can't build in " + Home.getHome(loc).getOwner().getDisplayName() + ChatColor.RED + "'s house!");
					event.setCancelled(true);
				}
			} else {
				player.sendMessage(ChatColor.RED + "You can't build outside your proprty!");
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent event) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			Player player = (Player) event.getEntity();
			Location loc = player.getLocation();
			if (Home.getHome(loc) != null) {
				if (!Home.getHome(loc).canpvp()) {
					damager.sendMessage(ChatColor.RED + "You can't pvp in " + Home.getHome(loc).getOwner().getDisplayName() + ChatColor.RED + "'s house!");
					event.setCancelled(true);
				}
			}
		}
	}
}
