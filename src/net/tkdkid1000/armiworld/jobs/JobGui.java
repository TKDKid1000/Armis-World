package net.tkdkid1000.armiworld.jobs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.Essentials;

import net.ess3.api.MaxMoneyException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.tkdkid1000.armiworld.ArmiWorldEconomy;
import net.tkdkid1000.armiworld.Job;
import net.tkdkid1000.armiworld.utils.ItemBuilder;

@SuppressWarnings("deprecation")
public class JobGui implements Listener {

	private List<UUID> infochatinput = new ArrayList<UUID>();
	private List<UUID> hirechatinput = new ArrayList<UUID>();
	private ArmiWorldEconomy armiworldeconomy;
	private Essentials ess;

	public JobGui(ArmiWorldEconomy armiworldeconomy) {
		this.armiworldeconomy = armiworldeconomy;
		this.ess = armiworldeconomy.ess;
	}
	
	public void register() {
		armiworldeconomy.getServer().getPluginManager().registerEvents(this, armiworldeconomy);
	}
	
	String guiname = ChatColor.GOLD + "" + ChatColor.BOLD + "Job " + ChatColor.GREEN + ChatColor.BOLD + "Gui";
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!event.getInventory().getName().equals(guiname)) return;
		if (!(event.getWhoClicked() instanceof Player)) return;
		Player player = (Player) event.getWhoClicked();
		event.setCancelled(true);
		if (event.getCurrentItem() == null) return;
		ItemStack clicked = event.getCurrentItem();
		if (clicked.getType() == Material.BANNER) {
			player.sendMessage(ChatColor.GREEN + "Please select a job: (click to select)");
			for (Job.Jobs job : Job.Jobs.values()) {
				
				player.spigot().sendMessage(new ComponentBuilder(job.toString().substring(0, 1).toUpperCase() + job.toString().substring(1))
						.color(ChatColor.GREEN)
						.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, job.toString()))
						.create());
			}
			infochatinput.add(player.getUniqueId());
		} else if (clicked.getType() == Material.EMERALD) {
			player.sendMessage(ChatColor.GREEN + "Please select a job: (click to select)");
			for (Job.Jobs job : Job.Jobs.values()) {
				
				player.spigot().sendMessage(new ComponentBuilder(job.toString().substring(0, 1).toUpperCase() + job.toString().substring(1))
						.color(ChatColor.GREEN)
						.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, job.toString()))
						.create());
			}
			hirechatinput.add(player.getUniqueId());
		} else if (clicked.getType() == Material.PAPER) {
			if (buyitem(Job.getJob(player).getMaterial(),
					Job.getJob(player).getCount(),
					player)) {
				try {
					player.sendMessage(ChatColor.GREEN + "Successfully payed you " + Job.getJob(player).getPay() 
							+ " for the cost of " + Job.getJob(player).getCount() + " " + Job.getJob(player).getMaterial().name().toLowerCase().replace("_", " ") + ".");
					ess.getUser(player).giveMoney(BigDecimal.valueOf(Job.getJob(player).getPay()));
				} catch (MaxMoneyException e) {
					e.printStackTrace();
				}
			} else {
				player.sendMessage(ChatColor.GREEN + "You do not have enough " + Job.getJob(player).getMaterial().name().toLowerCase().replace("_", " ") + "!");
			}
		}
	}
	
	public void open(Player player) {
		Inventory inv = Bukkit.createInventory(null, 9, guiname);
		inv.addItem(new ItemBuilder(Material.BANNER, 1)
				.setName(ChatColor.GREEN + "Info")
				.addLore(ChatColor.GRAY + "Gets the info of the specified job.")
				.addLore(ChatColor.GRAY + "Contains the pay, and items needed.")
				.build());
		inv.addItem(new ItemBuilder(Material.EMERALD, 1)
				.setName(ChatColor.GREEN + "Hire")
				.addLore(ChatColor.GRAY + "Hires you for the specified job.")
				.build());
		inv.addItem(new ItemBuilder(Material.PAPER, Job.getJob(player).getCount())
				.setName(ChatColor.GREEN + "Pay")
				.addLore(ChatColor.GRAY + "Pays you for your current job.")
				.build());
		player.openInventory(inv);
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent event) {
		Player player = event.getPlayer();
		String text = event.getMessage();
		if (infochatinput.contains(player.getUniqueId())) {
			event.setCancelled(true);
			if (text.equalsIgnoreCase("end") || text.equalsIgnoreCase("cancel") || text.equalsIgnoreCase("exit") || text.equalsIgnoreCase("stop")) {
				player.sendMessage(ChatColor.GREEN + "Cancelled getting info.");
				infochatinput.remove(player.getUniqueId());
				return;
			}
			if (Job.Jobs.getJobsListString().contains(text.toLowerCase())) {
				Job.Jobs job = Job.Jobs.valueOf(text.toUpperCase());
				player.sendMessage(ChatColor.GRAY + "----------------");
				player.sendMessage(ChatColor.GREEN + "Name: " + job.toString().substring(0, 1).toUpperCase() + job.toString().substring(1));
				player.sendMessage(ChatColor.GREEN + "Pay: " + job.getPay());
				player.sendMessage(ChatColor.GREEN + "Items needed for pay:");
				player.sendMessage(ChatColor.GREEN + "Material: " + job.getMaterial().name().toLowerCase().replace("_", " "));
				player.sendMessage(ChatColor.GREEN + "Amount: " + job.getCount());
				player.sendMessage(ChatColor.GRAY + "----------------");
				infochatinput.remove(player.getUniqueId());
				return;
			} else {
				player.sendMessage(ChatColor.RED + "That job doesn't exist! Here is a list: (click to select)");
				for (Job.Jobs job : Job.Jobs.values()) {
					
					player.spigot().sendMessage(new ComponentBuilder(job.toString().substring(0, 1).toUpperCase() + job.toString().substring(1))
							.color(ChatColor.GREEN)
							.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, job.toString()))
							.create());
				}
			}
		}
		if (hirechatinput.contains(player.getUniqueId())) {
			event.setCancelled(true);
			if (text.equalsIgnoreCase("end") || text.equalsIgnoreCase("cancel") || text.equalsIgnoreCase("exit") || text.equalsIgnoreCase("stop")) {
				player.sendMessage(ChatColor.GREEN + "Cancelled hiring.");
				infochatinput.remove(player.getUniqueId());
				return;
			}
			if (Job.Jobs.getJobsListString().contains(text.toLowerCase())) {
				player.sendMessage(ChatColor.GREEN + "Successfully hired for job " + text + "! Are you ready?");
				Job.setJob(player, Job.Jobs.valueOf(text.toUpperCase()));
				hirechatinput.remove(player.getUniqueId());
				return;
			} else {
				player.sendMessage(ChatColor.RED + "That job doesn't exist! Here is a list: (click to select)");
				for (Job.Jobs job : Job.Jobs.values()) {
					
					player.spigot().sendMessage(new ComponentBuilder(job.toString().substring(0, 1).toUpperCase() + job.toString().substring(1))
							.color(ChatColor.GREEN)
							.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, job.toString()))
							.create());
				}
			}
		}
	}
	
	public boolean buyitem(Material mat, int amount, Player player) {
		int count = amount;
		Inventory playerinv = player.getInventory();
		if (playerinv.contains(mat, count)) {
			if (count <= 0) return false;
	        int size = playerinv.getSize();
	        for (int slot = 0; slot < size; slot++) {
	            ItemStack is = playerinv.getItem(slot);
	            if (is == null) continue;
	            if (mat == is.getType()) {
	                int newAmount = is.getAmount() - count;
	                if (newAmount > 0) {
	                    is.setAmount(newAmount);
	                    break;
	                } else {
	                    playerinv.clear(slot);
	                    count = -newAmount;
	                    if (count == 0) break;
	                }
	            }
	        }
		} else {
			return false;
		}
		return true;
	}
}
