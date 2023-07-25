package dev.vacariu.MCTycoon.events;

import dev.vacariu.MCTycoon.TycoonMain;
import dev.vacariu.MCTycoon.internalutils.EconomyHandler;
import dev.vacariu.MCTycoon.internalutils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if(event.getItem().getType() != Material.PAPER) return;
        Player player = event.getPlayer();
        ItemStack[] inventoryContents = player.getInventory().getContents();
        int totalValue = 0;
        int totalBanknotes = 0;

        for (ItemStack item : inventoryContents) {
            if (item != null && item.getType() == Material.PAPER) {
                ItemMeta im = item.getItemMeta();
                if (im != null && im.getPersistentDataContainer().has(pl.bankNoteKey, PersistentDataType.INTEGER)) {
                    int amount = item.getAmount();
                    int value = im.getPersistentDataContainer().get(pl.bankNoteKey, PersistentDataType.INTEGER);
                    totalValue += value * amount;
                    totalBanknotes += amount;
                    player.getInventory().remove(item);
                }
            }
        }
        if (totalBanknotes > 0) {
            EconomyHandler.economy.depositPlayer(player, totalValue);
            String soldBanknotes = Utils.asColor(pl.getConfig().getString("messages.banknote.soldBanknotes"));
            String soldBank1 = soldBanknotes.replaceAll("%totalBanknotes%", String.valueOf(totalBanknotes));
            String soldBank2 = soldBank1.replaceAll("%totalValue%", String.valueOf(totalValue));
            player.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Utils.translateHexColorCodes("&#","",soldBank2)));
        }
    }

}
