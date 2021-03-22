package net.tkdkid1000.armiworld.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.tkdkid1000.armiworld.ArmiWorldEconomy;
import net.tkdkid1000.armiworld.resources.HomeGui;

public class HomeCommand implements CommandExecutor {

	private ArmiWorldEconomy armiworldeconomy;

	public HomeCommand(ArmiWorldEconomy armiworldeconomy) {
		this.armiworldeconomy = armiworldeconomy;
	}
	
	public void register() {
		armiworldeconomy.getCommand("house").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			new HomeGui(armiworldeconomy).open(player);
		}
		return true;
	}
}
