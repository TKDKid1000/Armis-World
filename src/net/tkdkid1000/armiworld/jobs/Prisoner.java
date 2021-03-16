package net.tkdkid1000.armiworld.jobs;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.md_5.bungee.api.ChatColor;
import net.tkdkid1000.armiworld.ArmiWorldEconomy;
import net.tkdkid1000.armiworld.Job;
import net.tkdkid1000.armiworld.utils.ItemBuilder;

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
		if (event.getTo().clone().subtract(0, 1, 0).getBlock().getType() == Material.GOLD_BLOCK) {
			player.getInventory().setItem(17, new ItemBuilder(Material.IRON_BARS, 1)
					.setName(ChatColor.RED + "Escape Artist!")
					.addLore(ChatColor.GRAY + "Tryin to escape are ya?")
					.addLore(ChatColor.GRAY + "Good luck! Don't get caught.")
					.build());
		}
		if (event.getTo().clone().subtract(0, 1, 0).getBlock().getType() == Material.DIAMOND_BLOCK) {
			player.getInventory().setItem(17, new ItemStack(Material.AIR));
		}
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
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			if (Job.getJob(player) == Job.Jobs.PRISONER) {
				if (event.getClickedInventory() instanceof PlayerInventory) {
					if (((PlayerInventory)event.getClickedInventory()).equals(player.getInventory())) {
						if (event.getSlot() == 17) {
							event.setCancelled(true);
							player.sendMessage(ChatColor.RED + "You can't change that slot!");
						}
					}
				}
			}
		}
	}
}
