package me.gacekmpl2.gacekskyblockmanager.listeners;

import me.gacekmpl2.gacekskyblockmanager.GacekSkyblockManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack inMain = player.getInventory().getItemInMainHand();

        if (inMain == null || inMain.getType().equals(Material.AIR))
            return;

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (GacekSkyblockManager.getVoucherController().isVoucher(inMain) &&
                event.getHand() == EquipmentSlot.HAND) {
            if (player.isSneaking()) {
                GacekSkyblockManager.getVoucherController().exchangeVouchers(player, inMain);
            } else {
                GacekSkyblockManager.getVoucherController().exchangeVoucher(player, inMain);
            }
        }
    }
}