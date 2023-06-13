package oggroups.configs;

import oggroups.Group;
import oggroups.OgGroups;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

public class GroupConfig{
    OgGroups ogGroups = OgGroups.getInstance();
    File config;

    public GroupConfig(String fileName) throws IOException {
        config = new File(ogGroups.getDataFolder() + "/" + fileName);

        FileWriter fw = new FileWriter(config);
        fw.write("");
        fw.close();
    }

    public Object loadConfig() throws IOException, ParseException {
        // This will be the method used to load the configuration!
        //TODO: load a json file with simple json
        //TODO: create a group object for each json object
        //TODO: add each group to a List of Groups


        // Creating a FileReader to read the config file then creating a JSONParser to
        // parse the json into usable variables!
        FileReader fr = new FileReader(config);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(fr);
    }

    public void saveConfig(Group group) throws IOException {
        Player owner = group.getOwner();
        String stringUUID = String.valueOf(owner.getUniqueId());

        JSONObject groupObj = new JSONObject();
        groupObj.put("id", group.getId());
        groupObj.put("name", group.getName());
        groupObj.put("owner", owner.getName() + " " + stringUUID);
        groupObj.put("balance", group.getBal());

        JSONArray membersList = new JSONArray();
        Player[] members = group.getMembers();

        for (int i = 0; i < members.length; i++) {
            String memberStringUUID = String.valueOf(members[i].getUniqueId());

            membersList.add(members[i].getName() + " " + memberStringUUID);
        }

        groupObj.put("members", membersList);

        Files.write(Paths.get(config.getPath()), groupObj.toJSONString().getBytes(), StandardOpenOption.APPEND);
    }

    public void saveConfig(List<Group> groups) throws IOException {
        //TODO: Create a List of JsonObjects from a List of Groups
        //TODO: Save all JsonObjects to Group Config File

        // Looping through each group in a given Group[] this will be all the groups created
        // and loaded at the enable of the plugin!
        for (int i = 0; i < groups.size(); i++) {
            Player owner = groups.get(groups.indexOf(i)).getOwner();

            String stringUUID = String.valueOf(owner.getUniqueId());
            UUID ownerUUID = UUID.fromString(stringUUID);

            // Creating a new JSONObject witht the fields id, name, owner, balance, and members
            // A new JSONObject is created for each group!
            JSONObject group = new JSONObject();
            group.put("id", groups.get(groups.indexOf(i)).getId());
            group.put("name", groups.get(groups.indexOf(i)).getName());
            group.put("owner", owner.getName() + " " + stringUUID);
            group.put("balance", groups.get(groups.indexOf(i)).getBal());

            JSONArray members = new JSONArray();
            Player[] membersList = groups.get(groups.indexOf(i)).getMembers();

            //Loop through the list of members for each group and create a new
            // JSONArray for each groups member list!
            for (int j = 0; j < membersList.length; j++) {
                members.add(membersList[j]);
            }

            // Add the JSONArray members to the group JSONObject
            group.put("members", members);

            // Write the JSONObject to the config file path after we have converted it to bytes!
            // This function will append new JSONObjects to the end of the file! Ex. StandardOpenOption.APPEND
            Files.write(Paths.get(config.getPath()), group.toJSONString().getBytes(), StandardOpenOption.APPEND);
        }
    }
}
