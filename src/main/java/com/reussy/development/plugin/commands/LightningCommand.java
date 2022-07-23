package com.reussy.development.plugin.commands;

import com.reussy.development.plugin.FunCommands;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LightningCommand extends BukkitCommand {

    private final FunCommands plugin;

    public LightningCommand(@NotNull String name, FunCommands plugin) {
        super(name);
        this.plugin = plugin;
    }

    /**
     * Executes the command, returning its success
     *
     * @param sender       Source object which is executing this command
     * @param commandLabel The alias of the command used
     * @param args         All arguments passed to the command, split via ' '
     * @return true if the command was successful, otherwise false
     */
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {

        if (!plugin.commonCommandChecks(sender, "lightning.command")) {
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0){
            plugin.colorize(player, "&cYou must specify a player to fire.");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (plugin.targetIsNull(target)){
            plugin.colorize(player, "&cThe player &6" + args[0] + "&c is not online.");
            return false;
        }

        target.getWorld().strikeLightning(target.getLocation());
        plugin.colorize(player, "&aLightning struck in &6 " + target.getName());
        return true;
    }
}
