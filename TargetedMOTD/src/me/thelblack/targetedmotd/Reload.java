package me.thelblack.targetedmotd;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Reload extends Command {

	public Reload() {
		super("reloadtargetedmotd");
	}

	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		if (!(arg0 instanceof ProxiedPlayer)) {
			arg0.sendMessage(new ComponentBuilder("reloading config...").create());  
			
			Motd.reloadConfig();
		}
	}
}
