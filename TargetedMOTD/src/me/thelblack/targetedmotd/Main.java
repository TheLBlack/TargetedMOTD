package me.thelblack.targetedmotd;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

	public static Main INSTANCE;

	public Main() {
		Main.INSTANCE = this;
	}

	@Override
	public void onEnable() {
		super.getProxy().getPluginManager().registerListener(this, new Connection());
		super.getProxy().getPluginManager().registerCommand(this, new Reload());

		if (!getDataFolder().exists()) getDataFolder().mkdir();
			
		File file = new File(getDataFolder(), "config.yml");
		if (!file.exists()) {
			try (InputStream in = getResourceAsStream("config.yml")) {
				Files.copy(in, file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Motd.reloadConfig();
	}
}
