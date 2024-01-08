package me.gacekmpl2.gacekskyblockmanager.essentials;

import me.gacekmpl2.gacekskyblockmanager.GacekSkyblockManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> tabCompletions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("gskyblock")) {
            if (args.length == 1) {
                tabCompletions.add("reload");
                return tabCompletions;
            }
        } else if (command.getName().equalsIgnoreCase("voucher")) {
            Player player = (Player) sender;
            List<String> vouchersTab = new ArrayList<>();
            YamlConfiguration yaml = ConfigUtils.load("vouchers.yml", (Plugin) GacekSkyblockManager.getInstance());

            if (sender.hasPermission("vouchers.admin")) {
                if (args.length == 1) {
                    tabCompletions.add("give");
                    return tabCompletions;
                } else if (args.length == 2) {
                    for (Player list : Bukkit.getOnlinePlayers())
                        tabCompletions.add(list.getName());
                        return tabCompletions;
                } else if (args.length == 3) {
                    assert yaml != null;
                    tabCompletions.addAll(yaml.getConfigurationSection("vouchers").getKeys(false));
                    return tabCompletions;
                }
            }
        }

        return tabCompletions;
    }
}
