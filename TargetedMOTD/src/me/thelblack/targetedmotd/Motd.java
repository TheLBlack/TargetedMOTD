package me.thelblack.targetedmotd;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.ServerPing.PlayerInfo;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Motd {

	@SuppressWarnings("unchecked")
	public static void reloadConfig() {
		try {
			HashMap<String, Motd> ov = new HashMap<>();
			Configuration c = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.INSTANCE.getDataFolder(), "config.yml"));
			if (!c.getString("Author").equals("TheLBlack")) throw new IllegalArgumentException("Do not touch author part nigga!");
			
			List<HashMap<Object, Object>> conf = (List<HashMap<Object, Object>>) c.getList("Targets");
			for (HashMap<Object, Object> m : conf) {
				String name = m.keySet().toArray(String[]::new)[0];
				HashMap<Object, Object> su = (HashMap<Object, Object>) m.get(name);

				Motd motd = new Motd();
				motd.players = (int) su.get("players");
				motd.max_players = (int) su.get("max-players");
				motd.protocol = (int) su.get("protocol");
				motd.motd = (String) su.get("motd");
				motd.protocol_name = (String) su.get("protocol-name");
				motd.overrideIcon = !((boolean) su.get("default-favicon"));
				
				if (motd.overrideIcon) motd.icon = Favicon.create(ImageIO.read(new File((String) su.get("favicon-path"))));
				
				List<PlayerInfo> samples = new ArrayList<>();
				for (HashMap<Object, Object> a : (List<HashMap<Object, Object>>) su.get("players-sample")) {
					String uuid = a.keySet().toArray(String[]::new)[0];
					
					samples.add(new PlayerInfo((String) a.get(uuid), UUID.fromString(uuid)));
				}
				motd.samples = samples.toArray(PlayerInfo[]::new);
				
				List<Object> ips = (List<Object>) su.get("ips");
				for (Object ip : ips) ov.put((String) ip, motd);
			}
			
			Motd.DESC = ov;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static HashMap<String, Motd> DESC = new HashMap<>();
	
	private int players, max_players, protocol;
	private String motd, protocol_name;
	private boolean overrideIcon;
	private PlayerInfo[] samples;
	private Favicon icon = null;
	
	public Motd() {}
	
	public int getPlayers() {
		return this.players;
	}
	
	public int getMaxPlayers() {
		return this.max_players;
	}
	
	public int getProtocol() {
		return this.protocol;
	}
	
	public String getMotd() {
		return this.motd;
	}
	
	public String getProtocolName() {
		return this.protocol_name;
	}
	
	public Favicon getIcon() {
		return this.icon;
	}
	
	public boolean overrideIcon() {
		return this.overrideIcon;
	}
	
	public PlayerInfo[] getSamples() {
		return this.samples;
	}
}
