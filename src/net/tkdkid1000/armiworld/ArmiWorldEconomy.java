package net.tkdkid1000.armiworld;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;

import net.luckperms.api.LuckPerms;
import net.tkdid1000.armiworld.commands.HomeCommand;
import net.tkdid1000.armiworld.commands.JobCommand;
import net.tkdid1000.armiworld.commands.ThreadCommand;
import net.tkdid1000.armiworld.utils.YamlConfig;
import net.tkdkid1000.armiworld.jobs.Chef;
import net.tkdkid1000.armiworld.jobs.JobGui;
import net.tkdkid1000.armiworld.jobs.Lumberjack;
import net.tkdkid1000.armiworld.resources.HomeGui;
import net.tkdkid1000.armiworld.resources.Sidebar;

public class ArmiWorldEconomy extends JavaPlugin {

	public LuckPerms luckperms;
	public Essentials ess;
	public FileConfiguration config = getConfig();
	public YamlConfig homes = new YamlConfig(getDataFolder(), "homes");
	public YamlConfig members = new YamlConfig(getDataFolder(), "members");
	private static ArmiWorldEconomy armiworldeconomy;
	
	@Override
	public void onEnable() {
		luckperms = getServer().getServicesManager().load(LuckPerms.class);
		ess = (Essentials) getServer().getPluginManager().getPlugin("Essentials");
		saveDefaultConfig();
		armiworldeconomy = this;
		homes.createConfig();
		members.createConfig();
		new HomeCommand(this).register();
		new HomeGui(this).register();
		new HomeProtection(this).register();
		new Sidebar().subscribe();
		new JobCommand(this).register();
		new Job(this).register();
		new Lumberjack(this).register();
		new JobGui(this).register();
		new ThreadCommand(this).register();
		new Chef(this).register();
		if (Bukkit.getWorld(config.getString("world")) != null) {
			new WorldCreator(config.getString("world"))
					.environment(Environment.NORMAL)
					.generateStructures(false)
					.type(WorldType.FLAT)
					.createWorld();
			getLogger().info("Creating new world.");
		} else {
			getLogger().info("World already exists.");
		}
	}
	
	@Override
	public void onDisable() {
		
	}
	
	public static ArmiWorldEconomy getInstance() {
		return armiworldeconomy;
	}
}
