package me.gacekmpl2.gacekskyblockmanger.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @org.jetbrains.annotations.NotNull String[] args) {
        List<String> skyblockTab = new ArrayList<>();
        if (args.length == 1) {
            skyblockTab.add("reload");
        }
        return skyblockTab;
    }
}
