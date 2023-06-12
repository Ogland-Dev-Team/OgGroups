package oggroups.configs;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class GroupConfig extends ConfigTemplate{

    public GroupConfig(JavaPlugin plugin, String name) {
        super(plugin, name);
    }

    // TODO: Create a method that deletes a config based off of either a group name or group id

}
