package dev.vacariu.MCTycoon.managers.runners;

import dev.vacariu.MCTycoon.internalutils.EconomyHandler;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import dev.vacariu.MCTycoon.TycoonMain;
import dev.vacariu.MCTycoon.components.Generator;
import org.bukkit.entity.Panda;

import java.util.HashSet;
import java.util.Set;

public class GeneratorManager {
    private final TycoonMain pl;

    public GeneratorManager(TycoonMain pl) {
        this.pl = pl;
        run();
    }


    public Set<Generator> generators = new HashSet<>();

    public void addGenerator(Block block,int tier){
        generators.add(new Generator(pl,block, tier));
    }
    public void removeGenerator(Block block){
        for (Generator generator : generators) {
            if (generator.getBlock().equals(block)) {
                block.getWorld().dropItemNaturally(block.getLocation(),pl.generatorItemManager.getGenerator(generator.getTier(),1));
                generators.remove(generator);
                break;
            }
        }
    }
    public Generator getGenerator(Block block){
        for (Generator generator : generators) {
            if (generator.getBlock().equals(block)) {
                return generator;
            }
        }
        return null;
    }
    public void upgradeGenerator(Block block){
        for (Generator generator : generators) {
            if (generator.getBlock().equals(block)) {
                int tier = generator.getTier();
                generators.remove(generator);
                generators.add(new Generator(pl,block, tier + 1));
                break;
            }
        }
    }
    public boolean checkGenerator(Block block) {
        for (Generator generator : generators) {
            if (generator.getBlock().equals(block)) {
                return true;
            }
        }
        return false;
    }



    private void run(){
        Bukkit.getScheduler().runTaskTimer(pl,()->{
            generators.forEach(Generator::spawnDrop);
        },0,5*20);
    }


}
