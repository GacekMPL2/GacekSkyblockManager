package me.gacekmpl2.gacekskyblockmanager.voucher;


import me.gacekmpl2.gacekskyblockmanager.GacekSkyblockManager;
import me.gacekmpl2.gacekskyblockmanager.essentials.ChatUtils;

import me.gacekmpl2.gacekskyblockmanager.essentials.ConfigUtils;
import me.gacekmpl2.gacekskyblockmanager.essentials.ItemCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class VoucherController {
    private HashMap<ItemStack, Voucher> vouchers;

    public void load() {
        this.vouchers = new HashMap<>();
        YamlConfiguration configuration = ConfigUtils.load("vouchers.yml", (Plugin) GacekSkyblockManager.getInstance());
        for (String key : configuration.getConfigurationSection("vouchers").getKeys(false)) {
            ItemStack itemStack = ConfigUtils.getItemstack(configuration, "vouchers." + key + ".item");
            VoucherType voucherType = VoucherType.valueOf(configuration.getString("vouchers." + key + ".type"));
            if (voucherType.equals(VoucherType.MONEY)) {
                this.vouchers.put(itemStack, new Voucher(key, voucherType, configuration.getDouble("vouchers." + key + ".value"), itemStack));
                continue;
            }
            if (voucherType.equals(VoucherType.COMMAND))
                this.vouchers.put(itemStack, new Voucher(key, voucherType, configuration.getStringList("vouchers." + key + ".commands"), itemStack));
        }
    }

    public ItemStack getVoucherByValue(double value) {
        for (ItemStack itemStack : this.vouchers.keySet()) {
            if (((Voucher)this.vouchers.get(itemStack)).getVoucherType().equals(VoucherType.MONEY) && ((Voucher)this.vouchers
                    .get(itemStack)).getMoney() == value)
                return itemStack;
        }
        return null;
    }

    public ItemStack getVoucherByID(String ID) {
        for (ItemStack itemStack : this.vouchers.keySet()) {
            if (((Voucher)this.vouchers.get(itemStack)).getID().equalsIgnoreCase(ID))
                return itemStack;
        }
        return null;
    }

    public boolean isVoucher(ItemStack itemStack) {
        for (ItemStack stack : this.vouchers.keySet()) {
            if (itemStack.isSimilar(stack))
                return true;
        }
        return false;
    }

    public void exchangeVoucher(Player player, ItemStack itemStack) {
        Voucher voucherObject = this.vouchers.get((new ItemCreator(new ItemStack(itemStack))).setAmount(1).toItemStack());
        voucherObject.exchange(player, 1);
        player.getInventory().getItemInMainHand().setAmount(itemStack.getAmount() - 1);
        ChatUtils.sendMessage(player, "&aWykorzystano voucher/y!");
    }

    public void exchangeVouchers(Player player, ItemStack itemStack) {
        Voucher voucherObject = this.vouchers.get((new ItemCreator(new ItemStack(itemStack))).setAmount(1).toItemStack());
        voucherObject.exchange(player, itemStack.getAmount());
        player.getInventory().getItemInMainHand().setAmount(0);
        ChatUtils.sendMessage(player, "&aWykorzystano voucher/y!");
    }
}
