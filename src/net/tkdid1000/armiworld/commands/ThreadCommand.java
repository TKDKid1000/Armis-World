package net.tkdid1000.armiworld.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.tkdkid1000.armiworld.ArmiWorldEconomy;

public class ThreadCommand implements CommandExecutor {

	private ArmiWorldEconomy armiworldeconomy;

	public ThreadCommand(ArmiWorldEconomy armiworldeconomy) {
		this.armiworldeconomy = armiworldeconomy;
	}
	
	public void register() {
		armiworldeconomy.getCommand("thread").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(ChatColor.GREEN + "Making a new thread...");
		Thread thread = new Thread(() -> {
			((Player) sender).getInventory().addItem(new ItemStack(Material.DIAMOND));
			System.out.println("This was not sent on the main thread.");
			sender.sendMessage("This was not sent on the main thread.");
		});
		thread.start();
		return true;
	}

	
}
