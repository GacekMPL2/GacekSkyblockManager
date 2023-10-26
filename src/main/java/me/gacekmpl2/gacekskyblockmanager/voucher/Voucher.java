package me.gacekmpl2.gacekskyblockmanager.voucher;

import me.gacekmpl2.gacekskyblockmanager.GacekSkyblockManager;
import me.gacekmpl2.gacekskyblockmanager.essentials.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class Voucher {
    private String ID;

    private VoucherType voucherType;

    private double money;

    private List<String> commands;

    private ItemStack itemStack;

    private static final NamespacedKey VOUCHER_KEY = new NamespacedKey(GacekSkyblockManager.getInstance(), "voucherID");

    public Voucher(String ID, VoucherType voucherType, double money, ItemStack itemStack) {
        this.ID = ID;
        this.voucherType = voucherType;
        this.money = money;
        ItemBuilder itemBuilder = new ItemBuilder(itemStack);
        itemBuilder.setTag("voucherID", ID);
        itemStack = itemBuilder.build();
        this.itemStack = itemStack;
    }

    public Voucher(String ID, VoucherType voucherType, List<String> commands, ItemStack itemStack) {
        this.ID = ID;
        this.voucherType = voucherType;
        this.commands = commands;
        this.itemStack = itemStack;
    }

    public void exchange(Player player, int amount) {
        if (this.voucherType.equals(VoucherType.MONEY)) {
            double value = this.money * amount;
            GacekSkyblockManager.getEcon().depositPlayer((OfflinePlayer)player, value);
        } else {
            String command = "";
            for (int i = 0; i < amount; i++) {
                for (String a : this.commands) {
                    command = a;
                    command = command.replace("%player%", player.getName());
                    Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), command);
                }
            }
        }
    }
    public static Voucher getVoucherFromItemStack(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return null;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer data = itemMeta.getPersistentDataContainer();

        for (NamespacedKey key : data.getKeys()) {
            if (key.getNamespace().equals(GacekSkyblockManager.getInstance().getName()) && key.getKey().startsWith("voucherID_")) {
                String voucherID = data.get(key, PersistentDataType.STRING);
            }
        }

        return null;
    }
    public VoucherType getVoucherType() {
        return this.voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public double getMoney() {
        return this.money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public List<String> getCommands() {
        return this.commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}