package net.tkdkid1000.armiworld.jobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import net.tkdkid1000.armiworld.ArmiWorldEconomy;
import net.tkdkid1000.armiworld.Job;
import net.tkdkid1000.armiworld.utils.Locstring;
import net.tkdkid1000.armiworld.utils.NumberUtil;

public class Chef implements Listener {

	private ArmiWorldEconomy armiworldeconomy;

	public Chef(ArmiWorldEconomy armiworldeconomy) {
		this.armiworldeconomy = armiworldeconomy;
	}
	
	public void register() {
		armiworldeconomy.getServer().getPluginManager().registerEvents(this, armiworldeconomy);
		new BukkitRunnable() {

			@Override
			public void run() {
				spawnCows();
			}
			
		}.runTaskTimer(armiworldeconomy, 60*20, 60*20);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player) event.getWhoClicked();
			if (Job.getJob(player) != Job.Jobs.CHEF) return;
			ItemStack item = event.getCurrentItem();
			Material mat = Material.AIR;
			if (item != null) {
				mat = item.getType();
			}
			if (event.getInventory().getType() == InventoryType.FURNACE) {
				FurnaceInventory furnace = (FurnaceInventory) event.getInventory();
				if (event.getRawSlot() == 1) {
					furnace.setFuel(new ItemStack(Material.STICK, 8));
					event.setCancelled(true);
				} else if (!(event.getRawSlot() == 0 || event.getRawSlot() == 2 || mat.equals(Material.RAW_BEEF))) {
					player.sendMessage(ChatColor.RED + "Click on the fuel slot to refuel it!");
					event.setCancelled(true);
				}
			}
		}
	}
	
	public void spawnCows() {
		for (int x=0; x<5; x++) {
			Location loc = Locstring.stringToLoc(
					ArmiWorldEconomy.getInstance().config.getString("jobs.chef.loc"));
			loc.add(NumberUtil.getRandomNumberRange(-20, 20), 
					0, 
					NumberUtil.getRandomNumberRange(-20, 20));
			
			loc.setY(loc.getWorld().getHighestBlockYAt(loc));
			Entity cow = loc.getWorld().spawnEntity(loc, EntityType.COW);
			loc.getWorld().spawnParticle(Particle.CLOUD, loc, 5);
			cow.setCustomNameVisible(true);
			cow.setCustomName(ChatColor.RED + "Moooooooo");
			((LivingEntity) cow).setHealth(1);
		}
	}
}
