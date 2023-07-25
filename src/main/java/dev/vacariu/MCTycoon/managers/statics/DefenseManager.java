package dev.vacariu.MCTycoon.managers.statics;

import dev.vacariu.MCTycoon.TycoonMain;
import dev.vacariu.MCTycoon.components.ProtectionBlock;
import dev.vacariu.MCTycoon.internalutils.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;


public class DefenseManager {
    private final TycoonMain pl;
    public DefenseManager(TycoonMain pl) {
        this.pl = pl;
    }

    private final Map<Block, ProtectionBlock> protectionBlocks = new HashMap<>();
    public void addProtection(Block block, int tier, Player owner){
        protectionBlocks.put(block, new ProtectionBlock(pl,block, tier, owner));
    }
    public void removeProtection(Block block) {
        ProtectionBlock protection = protectionBlocks.remove(block);
        if (protection != null) {
            InventoryUtils.addItem(protection.owner, pl.defenseItemManager.getProtection(protection.tier, 1));
        }
    }
    public void deleteProtection(Block block) {
        protectionBlocks.remove(block);
        block.setType(Material.AIR);
    }
    public ProtectionBlock getProtection(Block block) {
        return protectionBlocks.get(block);
    }

}
