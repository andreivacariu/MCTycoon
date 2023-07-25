package dev.vacariu.MCTycoon.components;

import dev.vacariu.MCTycoon.internalutils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import dev.vacariu.MCTycoon.TycoonMain;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Generator {
    private final TycoonMain main;
    public final String location;
    public final String owner;
    public int tier;
    public Generator(TycoonMain main, String location, int tier, String owner) {
        this.main = main;
        this.location = location;
        this.tier = tier;
        this.owner = owner;
    }

    public void spawnDrop() {
        if (location != null) {
            for (int i = 1; i < 100; i++) {
                Block aboveBlock = LocationUtils.stringToLocation(location).add(0, i, 0).getBlock();
                if (aboveBlock.getType() == Material.AIR) {
                    var items = main.rewardManager.GetRewardForGenerator(this);
                    items.forEach(itemStack -> {
                        Location dropLocation = aboveBlock.getLocation().add(0.5, 0, 0.5);
                        Item droppedItem = aboveBlock.getWorld().dropItem(dropLocation, itemStack);
                        droppedItem.setVelocity(new Vector(0, 0, 0));
                    });
                    break;
                }
            }
        }
    }


}
