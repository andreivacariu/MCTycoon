package dev.vacariu.MCTycoon.events;

import dev.vacariu.MCTycoon.TycoonMain;
import dev.vacariu.MCTycoon.internalutils.EconomyHandler;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class BanknoteEvents implements Listener {
    private final TycoonMain pl;
    public BanknoteEvents(TycoonMain pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }
        if(event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if(event.getItem() == null || event.getItem().getType() != Material.PAPER) {
            return;
        }
        ItemStack item = event.getPlayer().getItemInHand();
        ItemMeta im = item.getItemMeta();
        int amount = item.getAmount();
        int value = im.getPersistentDataContainer().get(pl.bankNoteKey, PersistentDataType.INTEGER);
        event.getPlayer().setItemInHand(new ItemStack(Material.AIR));
        EconomyHandler.economy.depositPlayer(event.getPlayer(),value * amount);
        event.getPlayer().sendMessage("You claimed $" + value * amount + "!");
    }
}
