package dev.vacariu.MCTycoon.events;

import dev.vacariu.MCTycoon.components.Generator;
import dev.vacariu.MCTycoon.internalutils.EconomyHandler;
import dev.vacariu.MCTycoon.internalutils.Utils;
import dev.vacariu.MCTycoon.managers.items.GeneratorItemManager;
import dev.vacariu.MCTycoon.managers.runners.GeneratorManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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
import redempt.redlib.blockdata.DataBlock;


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
            pl.generatorManager.addGenerator(event.getBlockPlaced(),tier, event.getPlayer());
            String placeGenerator = Utils.asColor(pl.getConfig().getString("messages.generator.placeGenerator"));
            event.getPlayer().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(placeGenerator));
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if (event.isCancelled()){
            return;
        }
        Block block = event.getBlock();
        Generator generator = pl.generatorManager.getGenerator(block);
        if (generator != null) {
            pl.generatorManager.removeGenerator(block, event.getPlayer());
            String breakGenerator = null;
            if (generator.owner.equals(event.getPlayer().getName()))
                breakGenerator = Utils.asColor(pl.getConfig().getString("messages.generator.breakGenerator"));
            else
                breakGenerator = Utils.asColor(pl.getConfig().getString("messages.generator.breakOthersGenerator").replaceAll("%player%",generator.owner ));
            event.getPlayer().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(breakGenerator));
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

        Generator generator = pl.generatorManager.getGenerator(block);

        if (generator == null)  {
            return;
        }

        int tier = generator.tier;
        if(tier+1  > GeneratorItemManager.material.size()) {
            String maxLevel = Utils.asColor(pl.getConfig().getString("messages.generator.maxLevel"));
            event.getPlayer().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(maxLevel));
            return;
        }
        Double upgradePrice = 250 * Math.pow(tier, 3);
        if (EconomyHandler.economy.getBalance(event.getPlayer()) < upgradePrice) {
            String notEnoughMoney = Utils.asColor(pl.getConfig().getString("messages.generator.notEnoughMoney").replaceAll("%moneyNeeded%", upgradePrice.toString()));
            event.getPlayer().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(notEnoughMoney));
            return;
        }
        pl.generatorManager.upgradeGenerator(block);

        EconomyHandler.economy.withdrawPlayer(event.getPlayer(),upgradePrice);
        block.setType(GeneratorItemManager.material.get(tier));
        String upgradeGen = Utils.asColor(pl.getConfig().getString("messages.generator.upgradeGenerator").replaceAll("%moneyPaid%", upgradePrice.toString()));
        event.getPlayer().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(upgradeGen));

    }
}
