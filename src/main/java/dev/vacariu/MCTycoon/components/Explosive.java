package dev.vacariu.MCTycoon.components;

import dev.vacariu.MCTycoon.TycoonMain;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Explosive {
    private final TycoonMain main;
    public final Block block;
    public int tier;

    public Explosive(TycoonMain main, Block block, int tier) {
        this.main = main;
        this.block = block;
        this.tier = tier;
    }


}
