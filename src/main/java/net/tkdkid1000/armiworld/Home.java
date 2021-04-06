package net.tkdkid1000.armiworld;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;

import net.tkdkid1000.armiworld.utils.BoundingBox;
import net.tkdkid1000.armiworld.utils.Locstring;
import net.tkdkid1000.armiworld.utils.YamlConfig;

public class Home {

	private Location loc;
	private Location max;
	private Location min;
	private Player player;
	private boolean build;
	private int likes;
	private boolean pvp;
	YamlConfig homes = ArmiWorldEconomy.getInstance().homes;
	
	public Home(Location loc, Player player, Location min, Location max) {
		this.loc = loc;
		this.player = player;
		this.min = min;
		this.max = max;
		this.build = false;
		this.likes = 0;
		this.pvp = false;
	}
	
	public static Home getHome(Player player) {
		YamlConfig homes = ArmiWorldEconomy.getInstance().homes;
		if (homes.getConfig().getKeys(false).contains(player.getUniqueId().toString())) {
			ConfigurationSection home = homes.getConfig().getConfigurationSection(player.getUniqueId().toString());
			Location loc = Locstring.stringToLoc(home.getString("location"));
			Location min = Locstring.stringToLoc(home.getString("min"));
			Location max = Locstring.stringToLoc(home.getString("max"));
			return new Home(loc, player, min, max);
		} else {
			return null;
		}
	}
	
	public static Home getHome(Location loc) {
		for (String section : ArmiWorldEconomy.getInstance().homes.getConfig().getKeys(false)) {
			ConfigurationSection home = ArmiWorldEconomy.getInstance().homes.getConfig().getConfigurationSection(section);
			BoundingBox box = BoundingBox.of(Locstring.stringToLoc(home.getString("min")), Locstring.stringToLoc(home.getString("max")));
			if (box.contains(loc.toVector())) {
				return getHome(Bukkit.getPlayer(UUID.fromString(section)));
			}
		}
		return null;
	}
	
	public BoundingBox getBoundingBox() {
		return BoundingBox.of(this.min, this.max);
	}
	
	public Location getLocation() {
		return Locstring.stringToLoc(homes.getConfig().getString(player.getUniqueId().toString()+".location"));
	}
	
	public Location getMax() {
		return Locstring.stringToLoc(homes.getConfig().getString(player.getUniqueId().toString()+".max"));
	}
	
	public Location getMin() {
		return Locstring.stringToLoc(homes.getConfig().getString(player.getUniqueId().toString()+".min"));
	}
	
	public boolean canbuild() {
		return homes.getConfig().getBoolean(player.getUniqueId().toString()+".build");
	}
	
	public int getLikes() {
		return homes.getConfig().getInt(player.getUniqueId().toString()+".likes");
	}
	
	public boolean canpvp() {
		return homes.getConfig().getBoolean(player.getUniqueId().toString()+".pvp");
	}
	
	public Player getOwner() {
		return this.player;
	}
	
	public void setLocation(Location loc) {
		this.loc = loc;
		homes.getConfig().set(player.getUniqueId().toString()+".location", Locstring.locToString(loc));
		homes.save();
	}
	
	public void setMax(Location loc) {
		this.max = loc;
		homes.getConfig().set(player.getUniqueId().toString()+".max", Locstring.locToString(loc));
		homes.save();
	}
	
	public void setMin(Location loc) {
		this.min = loc;
		homes.getConfig().set(player.getUniqueId().toString()+".min", Locstring.locToString(loc));
		homes.save();
	}
	
	public void setBuild(boolean canbuild) {
		this.build = canbuild;
		homes.getConfig().set(player.getUniqueId().toString()+".build", canbuild);
		homes.save();
	}
	
	public void setLikes(int likes) {
		this.likes = likes;
		homes.getConfig().set(player.getUniqueId().toString()+".likes", likes);
		homes.save();
	}
	
	public void setPvp(boolean canpvp) {
		this.pvp = canpvp;
		homes.getConfig().set(player.getUniqueId().toString()+".pvp", canpvp);
		homes.save();
	}
	
	public String toString() {
		return "House{"+"Location="+Locstring.locToString(loc)+",Min="+Locstring.locToString(min)+
				",Max="+Locstring.locToString(max)+",Build="+build+",Likes="+likes+",PvP"+pvp+"}";
	}
	
	public void create() {
		homes.getConfig().set(this.player.getUniqueId().toString()+".location", Locstring.locToString(this.loc));
		homes.getConfig().set(this.player.getUniqueId().toString()+".min", Locstring.locToString(this.min));
		homes.getConfig().set(this.player.getUniqueId().toString()+".max", Locstring.locToString(this.max));
		homes.getConfig().set(this.player.getUniqueId().toString()+".build", false);
		homes.getConfig().set(this.player.getUniqueId().toString()+".likes", 0);
		homes.getConfig().set(this.player.getUniqueId().toString()+".pvp", false);
		homes.save();
		
		new BukkitRunnable() {

			@Override
			public void run() {
				player.teleport(loc);
			}
			
		}.runTaskLater(ArmiWorldEconomy.getInstance(), 20);
	}
}
