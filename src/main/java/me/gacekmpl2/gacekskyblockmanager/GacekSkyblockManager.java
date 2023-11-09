package me.gacekmpl2.gacekskyblockmanager;

import me.gacekmpl2.gacekskyblockmanager.commands.GSkyBlockCommand;
import me.gacekmpl2.gacekskyblockmanager.commands.VoucherCommand;
import me.gacekmpl2.gacekskyblockmanager.essentials.ConfigUtils;
import me.gacekmpl2.gacekskyblockmanager.essentials.Debug;
import me.gacekmpl2.gacekskyblockmanager.essentials.TabCompleter;
import me.gacekmpl2.gacekskyblockmanager.listeners.ArmorSystemEvent;
import me.gacekmpl2.gacekskyblockmanager.listeners.BreakEvent;
import me.gacekmpl2.gacekskyblockmanager.listeners.InteractListener;
import me.gacekmpl2.gacekskyblockmanager.listeners.PortalListener;
import me.gacekmpl2.gacekskyblockmanager.voucher.VoucherController;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Color;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public final class GacekSkyblockManager extends JavaPlugin {

    private static GacekSkyblockManager instance;

    private static Economy econ = null;

    private static VoucherController voucherController;
    @Override
    public void onEnable() {
        instance = this;
        voucherController = new VoucherController();
        ConfigUtils.setPlugin((Plugin)this);
        if (!setupEconomy()) {
            Debug.log(String.format("[%s] - Disabled due to no Vault dependency found!", new Object[] { getDescription().getName() }));
            getServer().getPluginManager().disablePlugin((Plugin)this);
            return;
        }
        loadStuff();
        getLogger().info("[GacekSkyblockManager] Plugin enabled.");

        getCommand("voucher").setExecutor((CommandExecutor)new VoucherCommand());
        Objects.requireNonNull(getCommand("voucher")).setTabCompleter((TabCompleter)new TabCompleter());
        getServer().getPluginManager().registerEvents((Listener)new InteractListener(), (Plugin)this);


        getCommand("gskyblock").setExecutor(new GSkyBlockCommand());
        Objects.requireNonNull(getCommand("gskyblock")).setTabCompleter((TabCompleter)new TabCompleter());
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

    public void loadStuff() {
        voucherController.load();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        econ = (Economy)rsp.getProvider();
        return (econ != null);
    }
    public static Economy getEcon() {
        return econ;
    }

    public static VoucherController getVoucherController() {
        return voucherController;
    }
    public static GacekSkyblockManager getInstance() {
        return instance;
    }
}
