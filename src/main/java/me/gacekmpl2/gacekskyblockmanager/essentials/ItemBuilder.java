package me.gacekmpl2.gacekskyblockmanager.essentials;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTCompound;
import org.bukkit.inventory.ItemStack;

public class ItemBuilder {
    private ItemStack itemStack;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder setTag(String key, String value) {
        NBTItem nbti = new NBTItem(itemStack);
        NBTCompound compound = nbti.getOrCreateCompound("customData");
        compound.setString(key, value);
        itemStack = nbti.getItem();
        return this;
    }
    public static void addTag(ItemStack itemStack, String key, String value) {
        NBTItem nbti = new NBTItem(itemStack);
        NBTCompound compound = nbti.getOrCreateCompound("customData");
        compound.setString(key, value);
        itemStack = nbti.getItem();
    }
    public String getTag(String key) {
        NBTItem nbti = new NBTItem(itemStack);
        return nbti.getString(key);
    }

    public ItemStack build() {
        return itemStack;
    }
}