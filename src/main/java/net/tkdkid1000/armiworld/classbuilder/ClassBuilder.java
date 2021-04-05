package net.tkdkid1000.armiworld.classbuilder;

import org.bukkit.entity.Player;

import me.lucko.helper.menu.Gui;
import me.lucko.helper.menu.scheme.MenuPopulator;
import me.lucko.helper.menu.scheme.MenuScheme;

public class ClassBuilder extends Gui {

	private static final MenuScheme TOPBAR = new MenuScheme().mask("000010000");
	private static final MenuScheme BUTTONS = new MenuScheme()
			.mask("000000000")
			.mask("000000001")
			.mask("111111111")
			.mask("111111111")
			.mask("011111110")
			.mask("000010000");
	
	public ClassBuilder(Player player) {
		super(player, 6, "&7Class Builder");
	}

	@Override
	public void redraw() {
		if (isFirstDraw()) {
			MenuPopulator populator = BUTTONS.newPopulator(this);
			
		}
		
	}

}
