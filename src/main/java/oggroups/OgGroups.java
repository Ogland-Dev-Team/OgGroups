package oggroups;

import net.milkbowl.vault.economy.Economy;
import oggroups.commands.OgGroupsAdminCmd;
import oggroups.commands.OgGroupsCmd;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class OgGroups extends JavaPlugin {
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

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
}
