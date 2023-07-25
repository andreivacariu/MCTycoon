package dev.vacariu.MCTycoon.commands;

import dev.vacariu.MCTycoon.TycoonMain;
import dev.vacariu.MCTycoon.managers.items.GeneratorItemManager;
import dev.vacariu.MCTycoon.managers.items.ExplosiveItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import redempt.redlib.inventorygui.InventoryGUI;
import redempt.redlib.inventorygui.ItemButton;
import redempt.redlib.itemutils.ItemBuilder;

import java.util.ArrayList;
import java.util.List;

public class ShopCommand implements CommandExecutor {
    private final TycoonMain pl;

    public ShopCommand(TycoonMain pl) {
        this.pl = pl;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (sender.hasPermission("tycoon.shop") && sender instanceof Player){

            //gens shop
            List<Integer> availableSlots = new ArrayList<Integer>();
            availableSlots.add(9);
            availableSlots.add(18);
            availableSlots.add(27);
            availableSlots.add(36);
            availableSlots.add(45);

            InventoryGUI gensShop = null;
            for (Integer number : availableSlots) {
                if (GeneratorItemManager.material.size() <= number)
                    gensShop = new InventoryGUI(Bukkit.createInventory(null, number+9, "Gens Shop"));
                else
                    gensShop = new InventoryGUI(Bukkit.createInventory(null, 54, "Gens Shop"));
            }
            for (Integer tier : GeneratorItemManager.tier)
                gensShop.addButton(ItemButton.create(new ItemBuilder(Material.LIME_STAINED_GLASS)
                        .setName("&fGenerator Shop").setLore("Click here to purchase a generator!"), e -> {
                    e.getWhoClicked();
                }), tier);



            InventoryGUI mainShop = new InventoryGUI(Bukkit.createInventory(null, 36, "Shop"));
            ItemButton info = ItemButton.create(new ItemBuilder(Material.BARREL)
                    .setName("&fTycoon Info").setLore("Click here to get info about the server!"), e -> {
                e.getWhoClicked().sendMessage("You clicked me!");
            });
            ItemButton gens = ItemButton.create(new ItemBuilder(Material.LIME_STAINED_GLASS)
                    .setName("&fGenerator Shop").setLore("Click here to purchase a generator!"), e -> {
                e.getWhoClicked();
            });
            ItemButton explosive = ItemButton.create(new ItemBuilder(ExplosiveItemManager.getShopExplosive()), e -> {
                e.getWhoClicked().sendMessage("You clicked me!");
            });
            ItemButton defense = ItemButton.create(new ItemBuilder(Material.STONE_BRICKS)
                    .setName("&fDefense Block").setLore("Click here to purchase a defense block!"), e -> {
                e.getWhoClicked().sendMessage("You clicked me!");
            });
            ItemButton others = ItemButton.create(new ItemBuilder(Material.SANDSTONE)
                    .setName("&fBlocks Shop").setLore("Click here to purchase blocks!"), e -> {
                e.getWhoClicked().sendMessage("You clicked me!");
            });
            ItemButton exit = ItemButton.create(new ItemBuilder(Material.DARK_OAK_DOOR)
                    .setName("&f EXIT").setLore("Click here to close the shop!"), e -> {
                e.getWhoClicked().closeInventory();
            });
            mainShop.fill(0, 36, new ItemStack(Material.GRAY_STAINED_GLASS_PANE));
            mainShop.addButton(info, 10);
            mainShop.addButton(gens, 12);
            mainShop.addButton(others, 13);
            mainShop.addButton(defense, 14);
            mainShop.addButton(explosive, 15);
            mainShop.addButton(exit, 31);
            mainShop.open(((Player) sender).getPlayer());






            return true;
        }
        return true;
    }
}
