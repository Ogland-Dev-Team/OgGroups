package oggroups.commands;

import net.milkbowl.vault.economy.Economy;
import oggroups.OgGroups;
import oggroups.configs.GroupConfig;
import org.bukkit.Bukkit;
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
                        } else {
                            double creationAmount = ogGroups.getConfig().getDouble("startup-fee");

                            // Checking if the player balance is greater than 0
                            if (econ.has(p, creationAmount)) {
                                String broadcastMsg = pluginName + p.getName() + "has created a new group called " + args[1];

                                // Withdrawing the group creation fee from the player that runs the command!
                                econ.withdrawPlayer(p, creationAmount);

                                //TODO: Create a custom config based off the name provide in args[1]
                                // Using the player as the Owner!


                                // Sending a confirmation that group was created then announcing the creation of a group
                                // to the server!
                                p.sendMessage(pluginName + "Created a new group!");
                                Bukkit.broadcast(broadcastMsg, "");
                            } else {
                                p.sendMessage(pluginName + "Insufficient Funds to create a new group!");
                            }
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
                        } else {
                            double deletionAmount = ogGroups.getConfig().getDouble("deletion-fee");

                            // Checking if the player has the amount set in the config
                            if (econ.has(p, deletionAmount)) {
                                String broadcastMsg = pluginName + p.getName() + " has just deleted the group " + args[1];

                                // Withdrawing the deletion fee from the player!
                                econ.withdrawPlayer(p, deletionAmount);

                                // TODO: Delete the custom config for the group that is being deleted!

                                p.sendMessage(pluginName + "Deleting the given group!");
                                Bukkit.broadcast(broadcastMsg, "");
                            } else {
                                p.sendMessage(pluginName + "Insufficient Funds to delete this group!");
                            }
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
                        } else {
                            double transferAmount = ogGroups.getConfig().getDouble("transfer-fee");

                            if (econ.has(p, transferAmount)) {
                                String pMsg = pluginName + "Transferring ownership of " + args[1] + " to " + args[2];
                                String broadcastMsg = pluginName + p.getName() + " has transferred ownership of " + args[1] + " to " + args[2];

                                // Withdraw the transfer fee from the player!
                                econ.withdrawPlayer(p, transferAmount);

                                // TODO: Make sure that args[1] is a valid group!
                                // TODO: Make sure that args[2] is a player!
                                // TODO: Transfer ownership of the given group to the given player!

                                p.sendMessage(pMsg);
                                Bukkit.broadcast(broadcastMsg, "");
                            } else {
                                p.sendMessage(pluginName + "Insufficient Funds to transfer this group!");
                            }
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
