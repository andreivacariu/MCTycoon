package dev.vacariu.MCTycoon.components;

import org.bukkit.Material;
import org.bukkit.block.Block;
import dev.vacariu.MCTycoon.TycoonMain;

public class Generator {
    private final TycoonMain main;
    public final Block block;

    public int tier;

    public Generator(TycoonMain main, Block block, int tier) {
        this.main = main;
        this.block = block;
        this.tier = tier;
    }
    public Block getBlock() {
        return block;
    }
    public int getTier() {
        return tier;
    }

    public void spawnDrop() {
        if (block != null) {
            for (int i=1; i<100; i++) {
                Block aboveBlock = block.getLocation().add(0, i, 0).getBlock();
                if (aboveBlock.getType() == Material.AIR) {
                    var items = main.rewardManager.GetRewardForGenerator(this);
                    items.forEach(itemStack -> {
                        aboveBlock.getWorld().dropItemNaturally(aboveBlock.getLocation().add(0, 1, 0), itemStack);
                    });
                    break;
                }
            }
        }
    }
}
