package net.tkdkid1000.armiworld.resources;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.luckperms.api.node.Node;
import net.md_5.bungee.api.ChatColor;
import net.tkdkid1000.armiworld.ArmiWorldEconomy;
import net.tkdkid1000.armiworld.Home;
import net.tkdkid1000.armiworld.utils.ItemBuilder;

@SuppressWarnings("deprecation")
public class HomeGui implements Listener {

	private ArmiWorldEconomy armiworldeconomy;

	public HomeGui(ArmiWorldEconomy armiworldeconomy) {
		this.armiworldeconomy = armiworldeconomy;
	}
	
	public void register() {
		armiworldeconomy.getServer().getPluginManager().registerEvents(this, armiworldeconomy);
	}
	
	String guiname = ChatColor.GOLD + "" + ChatColor.BOLD + "Home " + ChatColor.GREEN + ChatColor.BOLD + "Gui";
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!event.getView().getTitle().equals(guiname)) return;
		if (!(event.getWhoClicked() instanceof Player)) return;
		Player player = (Player) event.getWhoClicked();
		event.setCancelled(true);
		if (event.getCurrentItem() == null) return;
		ItemStack clicked = event.getCurrentItem();
		Home home = Home.getHome(player);
		if (clicked.getType() == Material.DARK_OAK_DOOR) {
			if (home != null) {
				player.sendMessage(ChatColor.GREEN + "Teleporting you to your house...");
				player.teleport(Home.getHome(player).getLocation());
			} else {
				player.sendMessage(ChatColor.RED + "You do not own a house yet!");
			}
		} else if (clicked.getType() == Material.GOLDEN_APPLE) {
			Location loc = new Location(Bukkit.getWorld(ArmiWorldEconomy.getInstance().config.getString("world")), 0, ArmiWorldEconomy.getInstance().config.getInt("height"), 0);
			loc.setX((ArmiWorldEconomy.getInstance().homes.getConfig().getKeys(false).size()*10000)+10000);
			loc.setZ((ArmiWorldEconomy.getInstance().homes.getConfig().getKeys(false).size()*10000)+10000);
			new Home(loc, player, loc.clone().add(100, 50, 100), loc.clone().subtract(100, 50, 100)).create();
			player.sendMessage(ChatColor.GREEN + "Your house location has been created!");
		} else if (clicked.getType() == Material.DIAMOND_PICKAXE) {
			if (home.canbuild()) {
				home.setBuild(false);
				player.sendMessage(ChatColor.GREEN + "Disabled build.");
			} else {
				home.setBuild(true);
				player.sendMessage(ChatColor.GREEN + "Enabled build.");
			}
		} else if (clicked.getType() == Material.DIAMOND_SWORD) {
			if (home.canpvp()) {
				home.setPvp(false);
				player.sendMessage(ChatColor.GREEN + "Disabled PvP.");
			} else {
				home.setPvp(true);
				player.sendMessage(ChatColor.GREEN + "Enabled PvP.");
			}
		}
	}
	
	public void open(Player player) {
		Inventory inv = Bukkit.createInventory(null, 9, guiname);
		inv.addItem(new ItemBuilder(Material.DARK_OAK_DOOR, 1)
				.setName(ChatColor.GREEN + "Travel Home")
				.addLore(ChatColor.GRAY + "Sends you to your house!")
				.addLore(ChatColor.GRAY + "If you don't have one this won't work.")
				.build());
		inv.addItem(new ItemBuilder(Material.GOLDEN_APPLE, 1)
				.setName(ChatColor.GREEN + "Create Home")
				.addLore(ChatColor.GRAY + "Creates a new home!")
				.addLore(ChatColor.GRAY + "After running you will be prompted")
				.addLore(ChatColor.GRAY + "to enter the island schematic name.")
				.addLore(ChatColor.DARK_RED + "" + ChatColor.BOLD + "WARNING! You will lose access to old")
				.addLore(ChatColor.DARK_RED + "" + ChatColor.BOLD + "houses by creating a new one.")
				.build());
		inv.addItem(new ItemBuilder(Material.DIAMOND_PICKAXE, 1)
				.setName(ChatColor.GREEN + "Toggle Build")
				.addLore(ChatColor.GRAY + "Toggles build on or off.")
				.addLore(ChatColor.GRAY + "You can always build, this is just for")
				.addLore(ChatColor.GRAY + "other players.")
				.build());
		inv.addItem(new ItemBuilder(Material.DIAMOND_SWORD, 1)
				.setName(ChatColor.GREEN + "Toggle PvP")
				.addLore(ChatColor.GRAY + "Toggles PvP on or off.")
				.addLore(ChatColor.GRAY + "This is for all players. Even you.")
				.addLore(ChatColor.DARK_RED + "" + ChatColor.BOLD + "WARNING! Players will be able to kill")
				.addLore(ChatColor.DARK_RED + "" + ChatColor.BOLD + "others and take items. Use with caution.")
				.build());
		player.openInventory(inv);
	}
	
}
