package me.gacekmpl2.gacekskyblockmanager.essentials;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ConfigUtils {

    public static String prefix = "&a&lSKYBLOCK &f»";
    public static String worldName = "spawn";

    private static Plugin plugin;

    public static void setPlugin(Plugin plugin) {
        ConfigUtils.plugin = plugin;
    }

    public static void loadconfig() throws IOException {
        YamlConfiguration config = load("config");

        assert config != null;
        prefix = config.getString("sbmanager.prefix");
        worldName = config.getString("sbmanager.worldName");
    }

    public static YamlConfiguration load(String configName) throws IOException {
        File file = new File(plugin.getDataFolder() + File.separator , configName + ".yml");

        if (file.exists()) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            if (yaml.getValues(false).isEmpty()) {
                setValues().save(file);
                return yaml;
            }
            try {
                yaml.load(file);
                return yaml;
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            setValues().save(file);
            return setValues();
        }
    }

    public static YamlConfiguration load(String configName, org.bukkit.plugin.Plugin plugin) {
        File file = new File(plugin.getDataFolder() + File.separator + configName);
        if (!file.exists())
            plugin.saveResource(configName, false);
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        return config;
    }

    public static YamlConfiguration setValues() {
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("sbmanager.prefix", prefix);
        yaml.set("sbmanager.worldName", worldName);
        return yaml;
    }

    public static Location getLocationFromConfig(YamlConfiguration configuration, String path) {
        double locationX = configuration.getDouble(path + ".x");
        double locationY = configuration.getDouble(path + ".y");
        double locationZ = configuration.getDouble(path + ".z");
        String worldName = configuration.getString(path + ".world");
        Location location = new Location(Bukkit.getWorld(worldName), locationX, locationY, locationZ);
        return location;
    }

    public static void saveLocationToConfig(YamlConfiguration configuration, String path, Location location) {
        configuration.set(path + ".x", Double.valueOf(location.getX()));
        configuration.set(path + ".y", Double.valueOf(location.getY()));
        configuration.set(path + ".z", Double.valueOf(location.getZ()));
        configuration.set(path + ".world", location.getWorld().getName());
    }

    public static ItemStack getItemstack(YamlConfiguration configuration, String path) {
        Material material = Material.valueOf(configuration.getString(path + ".material"));
        List<String> lore = new ArrayList<>();
        if (configuration.contains(path + ".lore"))
            for (String a : configuration.getStringList(path + ".lore"))
                lore.add(ChatUtils.fixColor(a));
        int amount = 1;
        if (configuration.contains(path + ".amount"))
            amount = configuration.getInt(path + ".amount");
        String itemName = material.name();
        if (configuration.contains(path + ".name"))
            itemName = ChatUtils.fixColor(configuration.getString(path + ".name"));
        ItemCreator creator = (new ItemCreator(material, amount)).setName(itemName).setLore(lore);
        if (configuration.contains(path + ".enchants"))
            for (String enchant : configuration.getConfigurationSection(path + ".enchants").getKeys(false))
                creator.addUnsafeEnchantment(Enchantment.getByName(enchant), configuration.getInt(path + ".enchants." + enchant));
        if (configuration.contains(path + ".hideenchants"))
            creator.hideEnchants(configuration.getBoolean(path + ".hideenchants"));
        return creator.toItemStack();
    }

    public static void save(YamlConfiguration c, String file) {
        try {
            c.save(new File(plugin.getDataFolder(), file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save(YamlConfiguration c, String folder, String file) {
        try {
            c.save(new File(plugin.getDataFolder() + File.separator + folder, file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}