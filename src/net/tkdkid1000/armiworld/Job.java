package net.tkdkid1000.armiworld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Job implements Listener {
	
	private ArmiWorldEconomy armiworldeconomy;

	public Job(ArmiWorldEconomy armiworldeconomy) {
		this.armiworldeconomy = armiworldeconomy;
	}
	
	public void register() {
		armiworldeconomy.getServer().getPluginManager().registerEvents(this, armiworldeconomy);
	}
	
	public enum Jobs {
		CHEF("chef", Material.COOKED_BEEF, 32, 10.0),
		POLICE("police", Material.EMERALD, 10, 50.0),
		GUARD("guard", Material.IRON_PLATE, 1, 50.0),
		LUMBERJACK("lumberjack", Material.LOG, 32, 10.0),
		DEFAULT("default", Material.DIAMOND, 10, 5.0),
		PRISONER("prisoner", Material.DIAMOND, 64, 1.0);

		private String string;
		private Material mat;
		private int count;
		private double pay;

		@Override
		public String toString() {
			return this.string;
		}
		
		public Material getMaterial() {
			return this.mat;
		}
		
		public int getCount() {
			return this.count;
		}
		
		public double getPay() {
			return this.pay;
		}
		
		public static List<String> getJobsListString() {
			List<String> jobs = new ArrayList<String>();
			Arrays.asList(Jobs.values()).forEach(job -> jobs.add(job.toString()));
			return jobs;
		}
		
		Jobs(String string, Material mat, int count, double pay) {
			this.string = string;
			this.mat = mat;
			this.count = count;
			this.pay = pay;
		}
	}
	
	public static Job.Jobs getJob(Player player) {
		return Jobs.valueOf(ArmiWorldEconomy.getInstance().members.getConfig().getString(player.getUniqueId().toString()+".job"));
	}
	
	public static void setJob(Player player, Job.Jobs job) {
		ArmiWorldEconomy.getInstance().members.getConfig().set(player.getUniqueId().toString()+".job", job.name());
		ArmiWorldEconomy.getInstance().members.save();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (ArmiWorldEconomy.getInstance().members.getConfig().getString(player.getUniqueId().toString()+".job") == null) {
			setJob(player, Job.Jobs.DEFAULT);
		}
	}
}
