package dev.vacariu.MCTycoon.events;

import dev.vacariu.MCTycoon.TycoonMain;
import dev.vacariu.MCTycoon.components.ProtectionBlock;
import dev.vacariu.MCTycoon.internalutils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ProtectionEvents implements Listener {
    private final TycoonMain pl;

    public ProtectionEvents(TycoonMain pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        if (event.isCancelled()){
            return;
        }
        if (event.getItemInHand().hasItemMeta()&&event.getItemInHand().getItemMeta().getPersistentDataContainer().has(pl.protectionKey, PersistentDataType.INTEGER)){
            ItemStack i = event.getItemInHand();
            ItemMeta im = i.getItemMeta();
            int tier = im.getPersistentDataContainer().get(pl.protectionKey,PersistentDataType.INTEGER);
            i.setAmount(i.getAmount()-1);
            pl.defenseManager.addProtection(event.getBlockPlaced(),tier, event.getPlayer());
            String placeDefense = Utils.asColor(pl.getConfig().getString("messages.defense.placeDefense"));
            event.getPlayer().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(placeDefense));
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Block block = event.getBlock();
        Player owner = event.getPlayer();
        ProtectionBlock protectionBlock = pl.defenseManager.getProtection(block);
        if (protectionBlock == null) {
            return;
        }

        Player blockOwner = protectionBlock.owner;
        if (blockOwner.equals(owner)) {
            pl.defenseManager.removeProtection(block);
            String breakDefense = Utils.asColor(pl.getConfig().getString("messages.defense.breakDefense"));;
            event.getPlayer().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(breakDefense));
        } else {
            event.setCancelled(true);
        }
    }


}
