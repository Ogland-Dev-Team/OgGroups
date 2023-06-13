package oggroups.commands;

import oggroups.OgGroups;
import oggroups.configs.GroupConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class OgGroupsAdminCmd implements CommandExecutor {
    OgGroups ogGroups = OgGroups.getInstance();
    FileConfiguration config = ogGroups.getConfig();
    GroupConfig groupConfig = OgGroups.getGroupConfig();

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
                        try {
                            p.sendMessage(ChatColor.GOLD + pluginName + ChatColor.GREEN + "Reloading OgGroups groups.json!");
                            groupConfig.saveConfig(OgGroups.getGroups());
                            groupConfig.loadConfig();
                            p.sendMessage(ChatColor.GOLD + pluginName + ChatColor.GREEN + "OgGroups groups.json has reloaded successfully!");
                        } catch (IOException | ParseException e) {
                            p.sendMessage(ChatColor.GOLD + pluginName + ChatColor.RED + "An error has occurred well reload the groups.json file!");
                            p.sendMessage(ChatColor.RED + "Please consult the consoule for more details!");
                            e.printStackTrace();
                        }

                    }
                    if (args[0].equalsIgnoreCase("set")) {
                        if (args[1].equalsIgnoreCase("creation")) {
                            if (args[2].equalsIgnoreCase("true")) {
                                config.set("creation-allowed", true);
                                p.sendMessage(ChatColor.GOLD + pluginName + ChatColor.GREEN + "Group creation has been enabled!");
                            }
                            if (args[2].equalsIgnoreCase("false")) {
                                config.set("creation-allowed", false);
                                p.sendMessage(ChatColor.GOLD + pluginName + ChatColor.RED + "Group creation has been disabled!");
                            }
                        }
                        else if (args[1].equalsIgnoreCase("deletion")) {
                            if (args[2].equalsIgnoreCase("true")) {
                                config.set("creation-allowed", true);
                                p.sendMessage(ChatColor.GOLD + pluginName + ChatColor.GREEN + "Group deletion has been enabled!");
                            }
                            if (args[2].equalsIgnoreCase("false")) {
                                config.set("creation-allowed", false);
                                p.sendMessage(ChatColor.GOLD + pluginName + ChatColor.RED + "Group deletion has been disabled!");
                            }
                        }
                        else if (args[1].equalsIgnoreCase("transfers")) {
                            if (args[2].equalsIgnoreCase("true")) {
                                config.set("creation-allowed", true);
                                p.sendMessage(ChatColor.GOLD + pluginName + ChatColor.GREEN + "Group transfers has been enabled!");
                            }
                            if (args[2].equalsIgnoreCase("false")) {
                                config.set("creation-allowed", false);
                                p.sendMessage(ChatColor.GOLD + pluginName + ChatColor.RED + "Group transfers has been disabled!");
                            }
                        }
                        else if (args[1].equalsIgnoreCase("creation-fee")) {
                            Double givenFee = Double.parseDouble(args[2]);
                            config.set("startup-fee", givenFee);
                            p.sendMessage(ChatColor.GOLD + pluginName + ChatColor.GREEN + "Group startup fee has been changed to " + args[2]);
                        }
                        else if (args[1].equalsIgnoreCase("deletion-fee")) {
                            Double givenFee = Double.parseDouble(args[2]);
                            config.set("deletion-fee", givenFee);
                            p.sendMessage(ChatColor.GOLD + pluginName + ChatColor.GREEN + "Group deletion fee has been changed to " + args[2]);
                        }
                        else if (args[1].equalsIgnoreCase("transfer-fee")) {
                            Double givenFee = Double.parseDouble(args[2]);
                            config.set("transfer-fee", givenFee);
                            p.sendMessage(ChatColor.GOLD + pluginName + ChatColor.GREEN + "Group transfer fee has been changed to " + args[2]);
                        }
                    }
                }
            }
        }
        return false;
    }
}
