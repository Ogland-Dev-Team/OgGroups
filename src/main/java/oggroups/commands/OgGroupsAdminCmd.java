package oggroups.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OgGroupsAdminCmd implements CommandExecutor {
    String pluginName = "OgGroups : ";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("oggroups.admin") || p.isOp()) {
                if (command.getName().equals("oggadmin")) {
                    if (args[0].equalsIgnoreCase("reload")) {
                        p.sendMessage(pluginName + "Reloading OgGroups config.yml!");

                        p.sendMessage(pluginName + "config.yml has reloaded successfully!");
                    }
                }
            }
        }
        return false;
    }
}
