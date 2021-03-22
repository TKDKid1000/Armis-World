package net.tkdkid1000.armiworld.resources;

import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;
import net.tkdkid1000.armiworld.ArmiWorldEconomy;
import net.tkdkid1000.armiworld.utils.ItemBuilder;

public class Piano implements Listener, CommandExecutor {

	private ArmiWorldEconomy armiworldeconomy;

	public Piano(ArmiWorldEconomy armiworldeconomy) {
		this.armiworldeconomy = armiworldeconomy;
	}
	
	public void register() {
		armiworldeconomy.getServer().getPluginManager().registerEvents(this, armiworldeconomy);
		armiworldeconomy.getCommand("piano").setExecutor(this);
	}
	
	String guiname = ChatColor.RED + "Piano";
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			open(player);
		}
		return true;
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!event.getView().getTitle().equals(guiname)) return;
		if (!(event.getWhoClicked() instanceof Player)) return;
		Player player = (Player) event.getWhoClicked();
		event.setCancelled(true);
		if (event.getCurrentItem() == null) return;
		ItemStack clicked = event.getCurrentItem();
		// naturals
		if (clicked.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "C")) {
			player.playNote(player.getLocation(), Instrument.PIANO, new Note(0, Note.Tone.C, false));
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "D")) {
			player.playNote(player.getLocation(), Instrument.PIANO, new Note(0, Note.Tone.D, false));
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "E")) {
			player.playNote(player.getLocation(), Instrument.PIANO, new Note(0, Note.Tone.E, false));
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "F")) {
			player.playNote(player.getLocation(), Instrument.PIANO, new Note(0, Note.Tone.F, false));
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "G")) {
			player.playNote(player.getLocation(), Instrument.PIANO, new Note(1, Note.Tone.G, false));
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "A")) {
			player.playNote(player.getLocation(), Instrument.PIANO, new Note(1, Note.Tone.A, false));
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "B")) {
			player.playNote(player.getLocation(), Instrument.PIANO, new Note(1, Note.Tone.B, false));
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "High C")) {
			player.playNote(player.getLocation(), Instrument.PIANO, new Note(1, Note.Tone.C, false));
		}
		// sharps
		else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "C#")) {
			player.playNote(player.getLocation(), Instrument.PIANO, new Note(0, Note.Tone.C, true));
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "D#")) {
			player.playNote(player.getLocation(), Instrument.PIANO, new Note(0, Note.Tone.D, true));
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "F#")) {
			player.playNote(player.getLocation(), Instrument.PIANO, new Note(1, Note.Tone.F, true));
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "G#")) {
			player.playNote(player.getLocation(), Instrument.PIANO, new Note(1, Note.Tone.G, true));
		} else if (clicked.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "A#")) {
			player.playNote(player.getLocation(), Instrument.PIANO, new Note(1, Note.Tone.A, true));
		}
	}
	
	public void open(Player player) {
		Inventory inv = Bukkit.createInventory(null, 18, guiname);
		inv.setItem(0, new ItemBuilder(Material.BLACK_WOOL, 1)
				.setName(ChatColor.GRAY + "C#")
				.build());
		inv.setItem(1, new ItemBuilder(Material.BLACK_WOOL, 1)
				.setName(ChatColor.GRAY + "D#")
				.build());
		inv.setItem(3, new ItemBuilder(Material.BLACK_WOOL, 1)
				.setName(ChatColor.GRAY + "F#")
				.build());
		inv.setItem(4, new ItemBuilder(Material.BLACK_WOOL, 1)
				.setName(ChatColor.GRAY + "G#")
				.build());
		inv.setItem(5, new ItemBuilder(Material.BLACK_WOOL, 1)
				.setName(ChatColor.GRAY + "A#")
				.build());
		inv.setItem(9, new ItemBuilder(Material.WHITE_WOOL, 1)
				.setName(ChatColor.GRAY + "C")
				.build());
		inv.setItem(10, new ItemBuilder(Material.WHITE_WOOL, 1)
				.setName(ChatColor.GRAY + "D")
				.build());
		inv.setItem(11, new ItemBuilder(Material.WHITE_WOOL, 1)
				.setName(ChatColor.GRAY + "E")
				.build());
		inv.setItem(12, new ItemBuilder(Material.WHITE_WOOL, 1)
				.setName(ChatColor.GRAY + "F")
				.build());
		inv.setItem(13, new ItemBuilder(Material.WHITE_WOOL, 1)
				.setName(ChatColor.GRAY + "G")
				.build());
		inv.setItem(14, new ItemBuilder(Material.WHITE_WOOL, 1)
				.setName(ChatColor.GRAY + "A")
				.build());
		inv.setItem(15, new ItemBuilder(Material.WHITE_WOOL, 1)
				.setName(ChatColor.GRAY + "B")
				.build());
		inv.setItem(16, new ItemBuilder(Material.WHITE_WOOL, 1)
				.setName(ChatColor.GRAY + "High C")
				.build());
		player.openInventory(inv);
	}
}
