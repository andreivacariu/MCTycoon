package dev.vacariu.MCTycoon.managers.runners;

import dev.vacariu.MCTycoon.internalutils.InventoryUtils;
import dev.vacariu.MCTycoon.internalutils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import dev.vacariu.MCTycoon.TycoonMain;
import dev.vacariu.MCTycoon.components.Generator;
import org.bukkit.entity.Player;
import redempt.redlib.blockdata.DataBlock;

import java.util.HashMap;
import java.util.Map;


public class GeneratorManager {
    private final TycoonMain pl;
    private final Map<String, Generator> generators = new HashMap<>();

    public GeneratorManager(TycoonMain pl) {
        this.pl = pl;
        run();
    }

    public void addGenerator(Block block, int tier, Player owner) {
        String location = LocationUtils.locationToString(block.getLocation());
        generators.put(location, new Generator(pl, location, tier, owner.getName()));
    }

    public void removeGenerator(Block block, Player player) {
        Generator generator = generators.remove(LocationUtils.locationToString(block.getLocation()));
        if (generator != null) {
            InventoryUtils.addItem(player, pl.generatorItemManager.getGenerator(generator.tier, 1));
        }
    }

    public void removeGenerator(Block block) {
        generators.remove(LocationUtils.locationToString(block.getLocation()));
    }

    public Generator getGenerator(Block block) {
        return generators.get(LocationUtils.locationToString(block.getLocation()));
    }

    public void upgradeGenerator(Block block) {
        String location = LocationUtils.locationToString(block.getLocation());
        Generator generator = generators.get(location);
        if (generator != null) {
            generators.put(LocationUtils.locationToString(block.getLocation()), new Generator(pl, location, generator.tier + 1, generator.owner));
        }
    }

    private void run() {
        Bukkit.getScheduler().runTaskTimer(pl, () -> {
            generators.values().forEach(Generator::spawnDrop);
        }, 0, pl.getConfig().getLong("generator.dropInterval"));
    }

}
