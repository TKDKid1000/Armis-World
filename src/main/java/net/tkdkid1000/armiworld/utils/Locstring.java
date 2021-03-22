package net.tkdkid1000.armiworld.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Locstring {

	public static Location stringToLoc(String s) {
		String[] locarray = s.split(",");
		return new Location(Bukkit.getWorld(locarray[0]),
				Double.parseDouble(locarray[1]),
				Double.parseDouble(locarray[2]),
				Double.parseDouble(locarray[3]));
	}
	
	public static String locToString(Location loc) {
		StringBuilder sb = new StringBuilder();
		sb.append(loc.getWorld().getName());
		sb.append(",");
		sb.append(loc.getX());
		sb.append(",");
		sb.append(loc.getY());
		sb.append(",");
		sb.append(loc.getZ());
		return sb.toString();
	}
}
