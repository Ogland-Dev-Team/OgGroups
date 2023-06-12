package oggroups.commands;

import oggroups.OgGroups;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class OgGroupsAdminCmd implements CommandExecutor {
    OgGroups ogGroups = OgGroups.getInstance();
    FileConfiguration config = ogGroups.getConfig();

    String pluginName = "OgGroups : ";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("oggroups.admin") || p.isOp()) {
                if (command.getName().equals("oggadmin")) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        p.sendMessage(pluginName + ChatColor.GREEN + "Reloading OgGroups config.yml!");
                        ogGroups.reloadConfig();
                        p.sendMessage(pluginName + ChatColor.GREEN + "config.yml has reloaded successfully!");
                    }
                }
            }
        }
        return false;
    }
}
