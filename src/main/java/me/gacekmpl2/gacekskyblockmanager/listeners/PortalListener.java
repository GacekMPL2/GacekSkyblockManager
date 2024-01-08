package me.gacekmpl2.gacekskyblockmanager.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalListener implements Listener {

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        if (event.getCause() == PlayerPortalEvent.TeleportCause.NETHER_PORTAL) {
            World fromWorld = event.getFrom().getWorld();
            if (fromWorld != null && fromWorld.getName().equals("world_nether")) {
                World destinationWorld = Bukkit.getWorld("spawn1");
                if (destinationWorld != null) {
                    Location spawnLocation = destinationWorld.getSpawnLocation();
                    event.setTo(spawnLocation);
                    event.setCancelled(true);
                } else {
                    event.getPlayer().sendMessage("Nie znaleziono świata spawn1. Skontaktuj się z administratorem serwera.");
                }
            }
        }
    }
}