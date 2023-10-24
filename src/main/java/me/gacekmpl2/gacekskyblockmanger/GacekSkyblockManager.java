package me.gacekmpl2.gacekskyblockmanger;

import me.gacekmpl2.gacekskyblockmanger.commands.GSkyBlockCommand;
import me.gacekmpl2.gacekskyblockmanger.essentials.ConfigUtils;
import me.gacekmpl2.gacekskyblockmanger.essentials.TabCompleter;
import me.gacekmpl2.gacekskyblockmanger.listeners.ArmorSystemEvent;
import me.gacekmpl2.gacekskyblockmanger.listeners.BreakEvent;
import me.gacekmpl2.gacekskyblockmanger.listeners.PortalListener;
import org.bukkit.Color;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public final class GacekSkyblockManager extends JavaPlugin {

    private static GacekSkyblockManager instance;
    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("[GacekSkyblockManager] Plugin enabled.");

        Objects.requireNonNull(getCommand("gskyblock")).setExecutor(new GSkyBlockCommand());

        Objects.requireNonNull(getCommand("gskyblock")).setTabCompleter(new TabCompleter());
        getServer().getPluginManager().registerEvents(new PortalListener(), this);
        getServer().getPluginManager().registerEvents(new BreakEvent(), instance);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            Random r = new Random();

            public void run() {
                Color c = Color.fromBGR(r.nextInt(255), r.nextInt(255), r.nextInt(255));
                ArmorSystemEvent.setCustomArmor(c);
            }
        }, 0L, 5L);

        try {
            ConfigUtils.loadconfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {

        getLogger().info("[GacekSkyblockManager] Plugin disabled.");
    }

    public static GacekSkyblockManager getInstance() {
        return instance;
    }
}