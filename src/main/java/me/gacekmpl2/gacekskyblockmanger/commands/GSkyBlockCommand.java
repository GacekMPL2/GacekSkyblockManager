package me.gacekmpl2.gacekskyblockmanger.commands;

import jdk.javadoc.internal.doclets.formats.html.markup.Text;
import me.gacekmpl2.gacekskyblockmanger.essentials.ChatUtils;
import me.gacekmpl2.gacekskyblockmanger.essentials.ConfigUtils;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class GSkyBlockCommand implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (args.length == 1) {
            if (sender.hasPermission("skyblockmanager.command.reload")) {
                if (args[0].equalsIgnoreCase("reload")) {
                    try {
                        ConfigUtils.loadconfig();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    sender.sendMessage(ChatUtils.fixColor(ConfigUtils.prefix + " " + "&aPrzeładowano."));
                    return true;
                }
            } else {
                ChatUtils.sendMessage(sender, "&cBrak uprawnień!");
                return true;
            }

        }
        return true;
    }
}