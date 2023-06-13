package oggroups;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Group {
    private int id;
    private String name;
    private Player owner;
    private double bal;
    private Player[] members;


    public Group(int id, String name, Player owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.bal = 0;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Player getOwner() {
        return this.owner;
    }

    public double getBal() {
        return this.bal;
    }

    public Player[] getMembers() {
        return this.members;
    }

    public String toString() {
        return this.name + "{" + "owner='" + this.owner.getName() + "\'" + ", balance=" + this.bal + "}";
    }
}
