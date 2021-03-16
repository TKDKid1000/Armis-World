package net.tkdkid1000.armiworld.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
//import org.bukkit.scheduler.BukkitRunnable;
//import org.bukkit.util.Vector;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import net.tkdkid1000.armiworld.ArmiWorldEconomy;
import net.tkdkid1000.armiworld.utils.ItemBuilder;

public class Portal implements Listener, CommandExecutor {
	
	Map<UUID, Block> orange = new HashMap<UUID, Block>();
	Map<UUID, Block> blue = new HashMap<UUID, Block>();
	List<UUID> delay = new ArrayList<UUID>();
	private ArmiWorldEconomy armiworldeconomy;
	
	public Portal(ArmiWorldEconomy armiworldeconomy) {
		this.armiworldeconomy = armiworldeconomy;
	}
	
	public void register() {
		armiworldeconomy.getServer().getPluginManager().registerEvents(this, armiworldeconomy);
		armiworldeconomy.getCommand("portal").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			player.sendMessage(ChatColor.GRAY + "Now you're thinking with " + 
					ChatColor.GOLD + "Por" + ChatColor.BLUE + "tal" + 
					ChatColor.GRAY + "s!");
			player.getInventory().addItem(new ItemBuilder(Material.IRON_HORSE_ARMOR, 1)
					.setName(ChatColor.GOLD + "Por" + ChatColor.BLUE + "tal" + ChatColor.GRAY + " Gun")
					.addLore(ChatColor.GRAY + "Left click for orange.")
					.addLore(ChatColor.GRAY + "Right click for blue")
					.build());
			player.getInventory().addItem(new ItemBuilder(Material.IRON_BOOTS, 1)
					.setName(ChatColor.GOLD + "Long " + ChatColor.BLUE + "Fall " + ChatColor.GRAY + "Boots")
					.addLore(ChatColor.GRAY + "No fall damage!")
					.build());
			defaultPortal(player);
		}
		return true;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		defaultPortal(player);
		if (delay.contains(player.getUniqueId())) return;
		for (int x=-1; x<1; x++) {
			for (int y=-1; y<1; y++) {
				for (int z=-1; z<1; z++) {
					if (player.getLocation().add(x, y, z).getBlock().equals(blue.get(player.getUniqueId()))) {
						Vector vel = player.getVelocity();
						player.teleport(orange.get(player.getUniqueId()).getLocation().add(0, 3, 0).setDirection(player.getLocation().getDirection()));
						player.setVelocity(vel);
						delay.add(player.getUniqueId());
						new BukkitRunnable() {

							@Override
							public void run() {
								delay.remove(player.getUniqueId());
							}
							
						}.runTaskLater(armiworldeconomy, 40);
					} else if (player.getLocation().add(x, y, z).getBlock().equals(orange.get(player.getUniqueId()))) {
						Vector vel = player.getVelocity();
						player.teleport(blue.get(player.getUniqueId()).getLocation().add(0, 3, 0).setDirection(player.getLocation().getDirection()));
						player.setVelocity(vel);
						delay.add(player.getUniqueId());
						new BukkitRunnable() {

							@Override
							public void run() {
								delay.remove(player.getUniqueId());
							}
							
						}.runTaskLater(armiworldeconomy, 40);
					}
				}
			}
		}
	}
	
	public void defaultPortal(Player player) {
		orange.putIfAbsent(player.getUniqueId(), new Location(player.getWorld(), 0, 0, 0).getBlock());
		blue.putIfAbsent(player.getUniqueId(), new Location(player.getWorld(), 0, 0, 0).getBlock());
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if (player.getInventory().getItemInMainHand() == null) return;
		if (!player.getInventory().getItemInMainHand().isSimilar(new ItemBuilder(Material.IRON_HORSE_ARMOR, 1)
					.setName(ChatColor.GOLD + "Por" + ChatColor.BLUE + "tal" + ChatColor.GRAY + " Gun")
					.addLore(ChatColor.GRAY + "Left click for orange.")
					.addLore(ChatColor.GRAY + "Right click for blue")
					.build())) {
			return;
		}
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = player.getTargetBlock(null, 64);
			if (block.getType() == Material.QUARTZ_BLOCK) {
				block.setType(Material.BLUE_WOOL);
				blue.get(player.getUniqueId()).setType(Material.QUARTZ_BLOCK);
				blue.replace(player.getUniqueId(), block);
			}
		} else if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Block block = player.getTargetBlock(null, 64);
			if (block.getType() == Material.QUARTZ_BLOCK) {
				block.setType(Material.ORANGE_WOOL);
				orange.get(player.getUniqueId()).setType(Material.QUARTZ_BLOCK);
				orange.replace(player.getUniqueId(), block);
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (event.getCause() == DamageCause.FALL) {
				if (player.getInventory().getBoots() != null) {
					if (player.getInventory().getBoots().isSimilar(new ItemBuilder(Material.IRON_BOOTS, 1)
					.setName(ChatColor.GOLD + "Long " + ChatColor.BLUE + "Fall " + ChatColor.GRAY + "Boots")
					.addLore(ChatColor.GRAY + "No fall damage!")
					.build())) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
}
