package com.reussy.development.plugin;

import com.reussy.development.plugin.commands.ExplodeCommand;
import com.reussy.development.plugin.commands.FireCommand;
import com.reussy.development.plugin.commands.LightningCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public final class FunCommands extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getConsoleSender().sendMessage("Loading plugin...");
        Bukkit.getConsoleSender().sendMessage("Developed by reussy ft. OrbitalStudios");
        BukkitCommand[] commands = {new FireCommand("fire", this), new LightningCommand("lightning", this), new ExplodeCommand("explode", this)};
        populateCommands(commands, "fire", "lightning", "explode");
        Bukkit.getConsoleSender().sendMessage("FunCommands has been enabled!");

    }

    private void populateCommands(BukkitCommand[] command, String... fallback){

        for (int i = 0; i < fallback.length; i++){
            try {
                Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                bukkitCommandMap.setAccessible(true);
                CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
                commandMap.register(fallback[i], command[i]);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void colorize(Player player, String message){
        if (player == null || message == null) return;
        if (message.isEmpty()) return;
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean commonCommandChecks(CommandSender sender, String permission){
        if (!(sender instanceof Player player)) return false;
        return player.hasPermission(permission);
    }

    public boolean targetIsNull(Player target) {
        return target == null;
    }
}
