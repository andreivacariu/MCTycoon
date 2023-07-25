package dev.vacariu.MCTycoon.components;

import dev.vacariu.MCTycoon.TycoonMain;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ProtectionBlock {
    private final TycoonMain main;
    public final Block block;
    public final Player owner;

    public int tier;

    public ProtectionBlock(TycoonMain main, Block block, int tier, Player owner) {
        this.main = main;
        this.block = block;
        this.tier = tier;
        this.owner = owner;
    }


}
