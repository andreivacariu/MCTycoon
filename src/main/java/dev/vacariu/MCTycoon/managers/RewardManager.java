package dev.vacariu.MCTycoon.managers;

import org.bukkit.inventory.ItemStack;
import dev.vacariu.MCTycoon.TycoonMain;
import dev.vacariu.MCTycoon.components.Generator;

import java.util.ArrayList;
import java.util.List;

public class RewardManager {
    private final TycoonMain pl;

    public RewardManager(TycoonMain main) {
        this.pl = main;
    }

    public List<ItemStack> GetRewardForGenerator(Generator generator){
        List<ItemStack> money = new ArrayList<>();
        money.add(pl.bankNoteItemManager.getBankNote(5 * generator.tier,1));
        return money;
    }


}
