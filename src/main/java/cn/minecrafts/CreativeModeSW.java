package cn.minecrafts;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.GameMode;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CreativeModeSW extends JavaPlugin implements CommandExecutor, Listener {

    private boolean creativeEnabled = true;

    @Override
    public void onEnable() {
        getLogger().info("Creative Mode Plugin has been enabled!");
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("creativemodesw")).setExecutor(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Creative Mode Plugin has been disabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!creativeEnabled && player.isOp()) {
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        if (!creativeEnabled && player.isOp() && event.getNewGameMode() == GameMode.CREATIVE) {
            player.sendMessage(ChatColor.RED + "无法切换到创造模式！创造模式已经被管理员禁用！");
            getLogger().info(ChatColor.RED + "已将玩家 " + player.getName() + " 切换到生存模式。");
            event.setCancelled(true);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("creativemodesw")) {
            if (sender.hasPermission("creativesw.toggle")) {
                creativeEnabled = !creativeEnabled;
                sender.sendMessage("创造模式已经 " + (creativeEnabled ? ChatColor.GREEN + "启用" : ChatColor.RED + "禁用"));
            } else {
                sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            }
            return true;
        }
        return false;
    }
}
