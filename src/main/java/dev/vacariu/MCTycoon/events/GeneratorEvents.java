package dev.vacariu.MCTycoon.events;

import dev.vacariu.MCTycoon.components.Generator;
import dev.vacariu.MCTycoon.internalutils.EconomyHandler;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import dev.vacariu.MCTycoon.TycoonMain;

public class GeneratorEvents implements Listener {
    private final TycoonMain pl;

    public GeneratorEvents(TycoonMain pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if (event.isCancelled()){
            return;
        }


        if (event.getItemInHand().hasItemMeta()&&event.getItemInHand().getItemMeta().getPersistentDataContainer().has(pl.generatorKey, PersistentDataType.INTEGER)){
            ItemStack i = event.getItemInHand();
            ItemMeta im = i.getItemMeta();
            int tier = im.getPersistentDataContainer().get(pl.generatorKey,PersistentDataType.INTEGER);
            i.setAmount(i.getAmount()-1);
            pl.generatorManager.addGenerator(event.getBlockPlaced(),tier);

            event.getPlayer().sendMessage("You placed down a tier " + tier + " generator!");
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if (event.isCancelled()){
            return;
        }
        Block block = event.getBlock();
        if (pl.generatorManager.checkGenerator(block)) {
            pl.generatorManager.removeGenerator(block);
            event.getPlayer().sendMessage("You broke down a generator!");
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.isCancelled()){
            return;
        }
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(event.getHand() != EquipmentSlot.HAND) return;
        if (!event.getPlayer().isSneaking()) return;

        Block block = event.getClickedBlock();
        if(event.getPlayer().getItemInHand().getType() != Material.AIR) {
            return;
        }
        if (!pl.generatorManager.checkGenerator(block)) {
            return;
        }
        int tier = pl.generatorManager.getGenerator(block).getTier();
        if (EconomyHandler.economy.getBalance(event.getPlayer()) < (250 * (tier ^ 3))) {
            event.getPlayer().sendMessage("You don't have enough money!");
            return;
        }
        pl.generatorManager.upgradeGenerator(block);

        EconomyHandler.economy.withdrawPlayer(event.getPlayer(),250 * Math.pow(tier, 3));
        event.getPlayer().sendMessage("You upgraded a generator from tier " + tier + " to tier " + (tier + 1) + " for $" + 250 * Math.pow(tier, 3) + "!");
    }
}
