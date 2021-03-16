package net.tkdkid1000.armiworld.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;

import com.earth2me.essentials.Essentials;

import me.lucko.helper.Schedulers;
import me.lucko.helper.Services;
import me.lucko.helper.metadata.Metadata;
import me.lucko.helper.metadata.MetadataKey;
import me.lucko.helper.metadata.MetadataMap;
import me.lucko.helper.scoreboard.Scoreboard;
import me.lucko.helper.scoreboard.ScoreboardObjective;
import me.lucko.helper.scoreboard.ScoreboardProvider;
import net.md_5.bungee.api.ChatColor;
import net.tkdkid1000.armiworld.Home;
import net.tkdkid1000.armiworld.Job;

public class Sidebar {


	private Essentials ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
	
	public void subscribe() {
		MetadataKey<ScoreboardObjective> SCOREBOARD_KEY = MetadataKey.create("economy", ScoreboardObjective.class);
		BiConsumer<Player, ScoreboardObjective> updater = (p, obj) -> {
		    obj.setDisplayName("&6&lArmi's World Economy");
		    List<String> lines = new ArrayList<String>();
		    lines.add("&7" + new java.sql.Date(System.currentTimeMillis()).toString().replace("-", "/"));
		    lines.add("");
		    if (Home.getHome(p.getLocation()) == null) {
			   	lines.add(ChatColor.BLUE + "Home: " + ChatColor.RED + "NONE");
			} else {
			   	lines.add(ChatColor.BLUE + "Home: " + ChatColor.RED + Home.getHome(p.getLocation()).getOwner().getDisplayName());
			}
		    lines.add(ChatColor.BLUE + "Money: " + ChatColor.GREEN + ess.getUser(p).getMoney().doubleValue());
		    lines.add(ChatColor.BLUE + "Rank: " + ChatColor.GRAY + ess.getUser(p).getGroup().substring(0, 1).toUpperCase() + ess.getUser(p).getGroup().substring(1));
		    if (Home.getHome(p.getLocation()) == null) {
		    	if (Home.getHome(p) != null) {
		    		lines.add(ChatColor.BLUE + "Likes: " + ChatColor.GREEN + Home.getHome(p).getLikes() + ChatColor.GRAY + " (you)");
		    	} else {
		    		lines.add(ChatColor.BLUE + "Likes: " + ChatColor.GREEN + "N/A" + ChatColor.GRAY + " (you)");
		    	}
		    } else {
		    	lines.add(ChatColor.BLUE + "Likes: " + ChatColor.GREEN + Home.getHome(p.getLocation()).getLikes());
		    }
		    lines.add(ChatColor.BLUE + "Job: " + ChatColor.GRAY + Job.getJob(p).toString().substring(0, 1).toUpperCase() + Job.getJob(p).toString().substring(1));
		    lines.add("");
		    lines.add(ChatColor.YELLOW + "armi.tkdkid1000.net");
		    obj.applyLines(lines);
		};

		Scoreboard sb = Services.load(ScoreboardProvider.class).getScoreboard();

		me.lucko.helper.Events.subscribe(PlayerJoinEvent.class)
		        .handler(e -> {
		            ScoreboardObjective obj = sb.createPlayerObjective(e.getPlayer(), "null", DisplaySlot.SIDEBAR);
		            Metadata.provideForPlayer(e.getPlayer()).put(SCOREBOARD_KEY, obj);
		            updater.accept(e.getPlayer(), obj);
		        });

		Schedulers.async().runRepeating(() -> {
		    for (Player player : Bukkit.getOnlinePlayers()) {
		        MetadataMap metadata = Metadata.provideForPlayer(player);
		        ScoreboardObjective obj = metadata.getOrNull(SCOREBOARD_KEY);
		        if (obj != null) {
		            updater.accept(player, obj);
		            updater.accept(player, obj);
		        }
		    }
		}, 3L, 3L);
	}
}
