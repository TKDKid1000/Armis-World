package net.tkdkid1000.armiworld.jobs;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.md_5.bungee.api.ChatColor;
import net.tkdkid1000.armiworld.ArmiWorldEconomy;
import net.tkdkid1000.armiworld.Job;

public class Prisoner implements Listener {

	private ArmiWorldEconomy armiworldeconomy;
	public Prisoner(ArmiWorldEconomy armiworldeconomy) {
		this.armiworldeconomy = armiworldeconomy;
	}
	
	public void register() {
		armiworldeconomy.getServer().getPluginManager().registerEvents(this, armiworldeconomy);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (Job.getJob(player) == Job.Jobs.PRISONER) {
			if (ArmiWorldEconomy.getInstance().config.getStringList("jobs.prisoner.commands")
					.contains(event.getMessage().toLowerCase())) {
				event.setCancelled(true);
				player.sendMessage(ChatColor.RED + "You can't run commands as a prisoner!");
			}
		}
	}
}
