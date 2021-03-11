package net.tkdid1000.armiworld.commands;

//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

//import com.earth2me.essentials.Essentials;

//import net.ess3.api.MaxMoneyException;
import net.tkdkid1000.armiworld.ArmiWorldEconomy;
//import net.tkdkid1000.armiworld.Job;
import net.tkdkid1000.armiworld.jobs.JobGui;

public class JobCommand implements CommandExecutor {
	
	private ArmiWorldEconomy armiworldeconomy;
//	private Essentials ess;

	public JobCommand(ArmiWorldEconomy armiworldeconomy) {
		this.armiworldeconomy = armiworldeconomy;
//		this.ess = armiworldeconomy.ess;
	}
	
	public void register() {
		armiworldeconomy.getCommand("job").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			new JobGui(armiworldeconomy).open(player);
//			List<String> joblist = new ArrayList<String>();
//			for (Job.Jobs j : Job.Jobs.values()) {
//				joblist.add(j.toString());
//			}
//			if (args.length == 0) {
//				player.sendMessage("No args");
//			} else if (args.length == 1) {
//				if (!joblist.contains(args[0])) {
//					player.sendMessage("that is not a job");
//				} else {
//					player.sendMessage("specify a sub command");
//				}
//			} else if (args.length == 2) {
//				if (joblist.contains(args[0])) {
//				} else {
//					player.sendMessage("That is not a job");
//				}
//				if (args[1].equalsIgnoreCase("hire")) {
//					player.sendMessage("Hired for job " + args[0]);
//					Job.setJob(player, Job.Jobs.valueOf(args[0].toUpperCase()));
//				} else if (args[1].equalsIgnoreCase("pay")) {
//					player.sendMessage("Pay for job " + args[0] + " is " + ArmiWorldEconomy.getInstance().config.getDouble("jobs.lumberjack.pay"));
//				} else if (args[1].equalsIgnoreCase("earn")) {
//					if (Job.getJob(player).equals(Job.Jobs.valueOf(args[0].toUpperCase()))) {
//						if (buyitem(Job.Jobs.valueOf(args[0].toUpperCase()).getMaterial(),
//								Job.Jobs.valueOf(args[0].toUpperCase()).getCount(),
//								player)) {
//							try {
//								ess.getUser(player).giveMoney(BigDecimal.valueOf(Job.Jobs.valueOf(args[0].toUpperCase()).getPay()));
//							} catch (MaxMoneyException e) {
//								e.printStackTrace();
//							}
//						}
//						
//					} else {
//						player.sendMessage("You aren't in job " + args[0]);
//					}
//				}
//			} else {
//				player.sendMessage(args[0]);
//			}
		}
		return true;
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
