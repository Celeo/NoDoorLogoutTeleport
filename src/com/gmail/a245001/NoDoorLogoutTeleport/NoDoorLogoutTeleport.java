package com.gmail.a245001.NoDoorLogoutTeleport;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NoDoorLogoutTeleport extends JavaPlugin implements Listener {

	public Map<String, Location> playersToCheck = new HashMap<String, Location>();

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		Logger.getLogger("Minecraft").info("Enabled");
	}

	@Override
	public void onDisable() {
		Logger.getLogger("Minecraft").info("Disabled");
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		AddPlayer(event.getPlayer().getName(), event.getPlayer().getLocation());
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player pl = event.getPlayer();
		if (PlayerExists(pl.getName())) {
			pl.teleport(GetPlayer(pl.getName()));
			Logger.getLogger("Minecraft").log(
					Level.INFO,
					"NoDoorLogoutTeleport: Teleported player " + pl.getName()
					+ " to:" + GetPlayer(pl.getName()));
			RemovePlayer(pl.getName());
		}
	}

	private void AddPlayer(String _name, Location _pos) {
		String name = _name.toLowerCase();
		if (playersToCheck.get(name) == null)
			playersToCheck.remove(name);
		playersToCheck.put(name, _pos);
		Logger.getLogger("Minecraft").log(
				Level.INFO,
				"NoDoorLogoutTeleport: Added player " + name
				+ " to list, position:" + _pos);
	}

	private boolean PlayerExists(String _name) {
		String name = _name.toLowerCase();
		return (playersToCheck.containsKey(name));
	}

	public void Clear() {
		playersToCheck.clear();
	}

	public Location GetPlayer(String _name) {
		String name = _name.toLowerCase();
		return playersToCheck.get(name);
	}

	public void RemovePlayer(String _name) {
		String name = _name.toLowerCase();
		playersToCheck.remove(name);
	}

}
