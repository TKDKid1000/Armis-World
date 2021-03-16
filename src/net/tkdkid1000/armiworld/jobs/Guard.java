package net.tkdkid1000.armiworld.jobs;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.md_5.bungee.api.ChatColor;
import net.tkdkid1000.armiworld.ArmiWorldEconomy;
import net.tkdkid1000.armiworld.Job;

public class Guard implements Listener {

	private ArmiWorldEconomy armiworldeconomy;
	public Guard(ArmiWorldEconomy armiworldeconomy) {
		this.armiworldeconomy = armiworldeconomy;
	}
	
	public void register() {
		armiworldeconomy.getServer().getPluginManager().registerEvents(this, armiworldeconomy);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (Job.getJob(player) == Job.Jobs.PRISONER) {
			if (player.getInventory().contains(Material.IRON_BARS)) {
				player.getInventory().remove(Material.IRON_BARS);
				if (Job.getJob(player.getKiller()) == Job.Jobs.GUARD) {
					player.getKiller().sendMessage(ChatColor.GREEN + "You stopped " + player.getDisplayName() + " from escaping!");
				}
			} else {
				player.getKiller().sendMessage(ChatColor.RED + "... You just killed a prisoner.");
			}
		}
	}
}
