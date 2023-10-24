package me.gacekmpl2.gacekskyblockmanger.essentials;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static me.gacekmpl2.gacekskyblockmanger.essentials.Debug.log;

public class ConfigUtils {

    public static String prefix = "&a&lSKYBLOCK &f»";
    public static String worldName = "spawn";

    public static void loadconfig() throws IOException {
        YamlConfiguration config = load("config");

        assert config != null;
        prefix = config.getString("sbmanager.prefix");
        worldName = config.getString("sbmanager.worldName");
    }

    public static YamlConfiguration load(String configName) throws IOException {

        File file = new File("plugins/" + File.separator + "GacekSkyblockManger", configName + ".yml");

        if (file.exists()) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            if (yaml.getValues(false).isEmpty()) {
                log("File configuration empty.");
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

    public static YamlConfiguration setValues() {
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("sbmanager.prefix", prefix);
        yaml.set("sbmanager.worldName", worldName);
        return yaml;
    }
}