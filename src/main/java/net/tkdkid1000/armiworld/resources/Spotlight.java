package net.tkdkid1000.armiworld.resources;

//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.tkdkid1000.armiworld.ArmiWorldEconomy;
import ru.beykerykt.lightapi.LightAPI;
import ru.beykerykt.lightapi.LightType;

public class Spotlight implements Listener {

	private ArmiWorldEconomy armiworld;

	public Spotlight(ArmiWorldEconomy armiworld) {
		this.armiworld = armiworld;
	}
	
	public void register() {
		armiworld.getServer().getPluginManager().registerEvents(this, armiworld);
	}
	
//	private Map<UUID, Location> light = new HashMap<UUID, Location>();

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
//		if (!(event.getFrom().getBlockX() != event.getTo().getBlockX() ||
//				event.getFrom().getBlockZ() != event.getTo().getBlockZ() ||
//            		event.getFrom().getBlockY() != event.getTo().getBlockY() ||
//            !event.getFrom().getWorld().equals(event.getTo().getWorld()))) {
			if (player.getInventory().getItemInMainHand() != null) {
				if (player.getInventory().getItemInMainHand().getType() == Material.TORCH
						|| player.getInventory().getItemInMainHand().getType() == Material.LANTERN
						|| player.getInventory().getItemInMainHand().getType() == Material.SOUL_LANTERN) {
					LightAPI.createLight(event.getTo(), LightType.BLOCK, 15, false);
					event.getTo().getBlock().setType(Material.TORCH);
					LightAPI.deleteLight(event.getFrom(), LightType.BLOCK, false);
					event.getTo().getBlock().setType(Material.AIR);
				}
			}
//		}
	}
}
