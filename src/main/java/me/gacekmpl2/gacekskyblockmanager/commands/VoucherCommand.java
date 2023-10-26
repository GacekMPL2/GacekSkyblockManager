package me.gacekmpl2.gacekskyblockmanager.commands;

import me.gacekmpl2.gacekskyblockmanager.GacekSkyblockManager;
import me.gacekmpl2.gacekskyblockmanager.essentials.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class VoucherCommand implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            if (!player.hasPermission("vouchers.admin")) {
                ChatUtils.sendMessage(player, "&cBrak uprawnie");
                return true;
            }
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target.getInventory().firstEmpty() != -1) {
                if (target == null || !target.isOnline()) {
                    ChatUtils.sendMessage(sender, "&cTen gracz jest offline!");
                    return true;
                }
                String ID = args[2];
                try {
                    ItemStack voucher = new ItemStack(GacekSkyblockManager.getVoucherController().getVoucherByID(ID));
                    target.getInventory().addItem(new ItemStack[] { voucher });
                    ChatUtils.sendMessage(target, "&aOtrzymano voucher &e&l" + ID.replace("_", " ") + "&a.");
                    return true;
                } catch (IllegalArgumentException e) {
                    ChatUtils.sendMessage(sender, "&cBrak kuponu o podanym ID!");
                    return true;
                }
            }
            ChatUtils.sendMessage(sender, "&cGracz ma ekwipunek!");
            return true;
        }
        ChatUtils.sendMessage(sender, "&7/" + label + " give <&enickname&7> <&eID&7>");
        return true;
    }
}
