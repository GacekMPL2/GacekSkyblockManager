package me.gacekmpl2.gacekskyblockmanager.listeners;

import me.gacekmpl2.gacekskyblockmanager.GacekSkyblockManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import static me.gacekmpl2.gacekskyblockmanager.essentials.ConfigUtils.worldName;

public class BreakEvent implements Listener {

    @EventHandler
    public void breakEvent(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (!event.getPlayer().getWorld().getName().equalsIgnoreCase(worldName)) {
            return;
        }

        if (!block.getType().equals(Material.WHEAT)) {
            return;
        }

        if (!isFullyGrown(block)) {
            event.setCancelled(true);
            return;
        }

        event.setDropItems(false);
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.WHEAT, 1));
        Bukkit.getScheduler().runTaskLater(GacekSkyblockManager.getInstance(),() -> {
            block.setType(Material.WHEAT);
            BlockData blockData = block.getBlockData();
            if (blockData instanceof Ageable) {
                Ageable ageable = (Ageable) blockData;
                ageable.setAge(0);
                block.setBlockData(ageable);
            }
        }, 1);

    }

    public boolean isFullyGrown(Block block) {
        Ageable ageable = (Ageable) block.getBlockData();
        return ageable.getAge() == ageable.getMaximumAge();
    }
}