package oggroups.commands;

import net.milkbowl.vault.economy.Economy;
import oggroups.OgGroups;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OgGroupsCmd implements CommandExecutor {
    OgGroups ogGroups = OgGroups.getInstance();
    Economy econ = OgGroups.getEcon();

    String pluginName = "OgGroups : ";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            boolean creationAllowed = ogGroups.getConfig().getBoolean("creation-allowed");
            boolean deletionAllowed = ogGroups.getConfig().getBoolean("deletion-allowed");
            boolean transferAllowed = ogGroups.getConfig().getBoolean("transfers-allowed");

            if (command.getName().equals("oggroups")) {
                p.sendMessage("Command has activated properly!");
                if (args[0].equalsIgnoreCase("create")) {
                    if (creationAllowed) {
                        if (args[1].equalsIgnoreCase("")) {
                            p.sendMessage(pluginName + "Please give a valid group name!");
                            return true;
                        }

                        double creationAmount = ogGroups.getConfig().getDouble("startup-fee");

                        // Checking if the player balance is greater than 0 and greater than the creation amount set
                        // in the config file!
                        if (econ.getBalance(p) < 0 && econ.getBalance(p) < creationAmount) {
                            p.sendMessage(pluginName + "Creating a new group!");
                        } else {
                            p.sendMessage(pluginName + "Insufficient Funds to create a new group!");
                        }
                    } else {
                        p.sendMessage(pluginName + "Creating groups has been disabled on the server!");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("delete")) {
                    if (deletionAllowed) {
                        if (args[1].equals("")) {
                            p.sendMessage(pluginName + "Please give a valid group name!");
                            return true;
                        }
                        double deletionAmount = ogGroups.getConfig().getDouble("deletion-fee");

                        // Checking if the player has more than 0 and more the the amount set in the config file
                        if (econ.getBalance(p) < 0 && econ.getBalance(p) < deletionAmount) {
                            p.sendMessage(pluginName + "Deleting the given group!");
                        } else {
                            p.sendMessage(pluginName + "Insufficient Funds to delete this group!");
                        }

                    } else {
                        p.sendMessage(pluginName + "Deleting groups has been disabled on the server!");
                        return true;
                    }
                } else if (args[0].equalsIgnoreCase("transfer")) {
                    if (transferAllowed) {
                        if (args[1].equals("") || args[2].equals("")) {
                            p.sendMessage(pluginName + "Please enter a valid value!");
                            return true;
                        }
                        double transferAmount = ogGroups.getConfig().getDouble("transfer-fee");

                        if (econ.getBalance(p) < 0 && econ.getBalance(p) < transferAmount) {
                            p.sendMessage(pluginName + "Transferring ownership of given group to given player!");
                        } else {
                            p.sendMessage(pluginName + "Insufficient Funds to transfer this group!");
                        }
                    } else {
                        p.sendMessage(pluginName + "Transfering groups has been disabled on the server!");
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
