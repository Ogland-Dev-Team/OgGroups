package oggroups;

import net.milkbowl.vault.economy.Economy;
import oggroups.commands.OgGroupsAdminCmd;
import oggroups.commands.OgGroupsCmd;
import oggroups.configs.GroupConfig;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public final class OgGroups extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static GroupConfig groupConfig;
    private static List<Group> groups = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

        try {
            groupConfig = new GroupConfig("groups.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getCommand("oggroups").setExecutor(new OgGroupsCmd());
        getCommand("oggadmin").setExecutor(new OgGroupsAdminCmd());



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            groupConfig.saveConfig(groups);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static OgGroups getInstance() {
        return OgGroups.getPlugin(OgGroups.class);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEcon() {
        return econ;
    }

    public static void addGroup(Group group) {
        groups.add(groups.size(), group);
    }

    public static void removeGroup(Group group) {
        groups.remove(group);
    }

    public static List<Group> getGroups() {
        return groups;
    }

    public static GroupConfig getGroupConfig() {
        return groupConfig;
    }

}
