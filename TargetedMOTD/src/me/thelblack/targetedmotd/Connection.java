package me.thelblack.targetedmotd;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Players;
import net.md_5.bungee.api.ServerPing.Protocol;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Connection implements Listener {

	public Connection() {}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = (byte) 1)
	public void ping(ProxyPingEvent e) {
		Motd desc;
		
		String ip = e.getConnection().getSocketAddress().toString();
		if ((desc = Motd.DESC.get(ip.substring(1, ip.indexOf(":")))) != null) {
			ServerPing reponse = e.getResponse();
			
			Players players = reponse.getPlayers();
			players.setMax(desc.getMaxPlayers());
			players.setOnline(desc.getPlayers());
			players.setSample(desc.getSamples());
			
			Protocol version = reponse.getVersion();
			version.setProtocol(desc.getProtocol());
			version.setName(desc.getProtocolName());
			
			reponse.setPlayers(players);
			reponse.setVersion(version);
			reponse.setDescription(desc.getMotd());
			
			if (desc.overrideIcon()) reponse.setFavicon(desc.getIcon());
		}
	}
}
