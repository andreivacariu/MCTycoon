package dev.vacariu.MCTycoon.commands;

import dev.vacariu.MCTycoon.managers.items.ExplosiveItemManager;
import dev.vacariu.MCTycoon.managers.items.GeneratorItemManager;
import dev.vacariu.MCTycoon.managers.items.DefenseItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import dev.vacariu.MCTycoon.TycoonMain;
import dev.vacariu.MCTycoon.internalutils.InventoryUtils;
import dev.vacariu.MCTycoon.internalutils.Utils;

public class TycoonCommand implements CommandExecutor {
    private final TycoonMain pl;

    public TycoonCommand(TycoonMain pl) {
        this.pl = pl;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length==3&&args[0].equalsIgnoreCase("getbanknote")){
            if (sender.hasPermission("tycoon.admin") && sender instanceof Player){
                Player p = (Player) sender;
                int currency = 0;
                int amount = 0;

                try{
                    currency = Integer.parseInt(args[1]);
                }catch (Exception ex){
                    p.sendMessage(Utils.asColor("&cWrong currency number!"));
                    return true;
                }

                try{
                    amount = Integer.parseInt(args[2]);
                }catch (Exception ex){
                    p.sendMessage(Utils.asColor("&cWrong amount number!"));
                    return true;
                }


                ItemStack bankNote = pl.bankNoteItemManager.getBankNote(currency,amount);
                InventoryUtils.addItem(p,bankNote);
                return true;
            }
        }

        if (args.length==3&&args[0].equalsIgnoreCase("getgenerator")){
            if (sender.hasPermission("tycoon.admin") && sender instanceof Player){
                Player p = (Player) sender;
                int tier = 0;
                int amount = 0;

                try{
                    tier = Integer.parseInt(args[1]);
                }catch (Exception ex){
                    p.sendMessage(Utils.asColor("&cWrong tier number!"));
                    return true;
                }

                try{
                    amount = Integer.parseInt(args[2]);
                }catch (Exception ex){
                    p.sendMessage(Utils.asColor("&cWrong amount number!"));
                    return true;
                }
                if(tier > GeneratorItemManager.material.size()) {
                    p.sendMessage("§fYou reached §4MAXIMUM §fGenerator level.");
                    return true;
                }


                ItemStack generator = pl.generatorItemManager.getGenerator(tier,amount);
                InventoryUtils.addItem(p,generator);
                return true;
            }
        }

        if (args.length==3&&args[0].equalsIgnoreCase("getdefense")){
            if (sender.hasPermission("tycoon.admin") && sender instanceof Player){
                Player p = (Player) sender;
                int tier = 0;
                int amount = 0;

                try{
                    tier = Integer.parseInt(args[1]);
                }catch (Exception ex){
                    p.sendMessage(Utils.asColor("&cWrong tier number!"));
                    return true;
                }

                try{
                    amount = Integer.parseInt(args[2]);
                }catch (Exception ex){
                    p.sendMessage(Utils.asColor("&cWrong amount number!"));
                    return true;
                }
                if(tier > DefenseItemManager.protectionTiers.size()) {
                    p.sendMessage("§fYou reached §4MAXIMUM §fDefense level.");
                    return true;
                }


                ItemStack protection = pl.defenseItemManager.getProtection(tier,amount);
                InventoryUtils.addItem(p,protection);
                return true;
            }
        }
        if (args.length==3&&args[0].equalsIgnoreCase("getexplosive")){
            if (sender.hasPermission("tycoon.admin") && sender instanceof Player){
                Player p = (Player) sender;
                int tier = 0;
                int amount = 0;

                try{
                    tier = Integer.parseInt(args[1]);
                }catch (Exception ex){
                    p.sendMessage(Utils.asColor("&cWrong tier number!"));
                    return true;
                }

                try{
                    amount = Integer.parseInt(args[2]);
                }catch (Exception ex){
                    p.sendMessage(Utils.asColor("&cWrong amount number!"));
                    return true;
                }
                if(tier > ExplosiveItemManager.explosiveTiers.size()) {
                    p.sendMessage("§fYou reached §4MAXIMUM §fExplosive level.");
                    return true;
                }


                ItemStack explosive = pl.explosiveItemManager.getExplosive(tier,amount);
                InventoryUtils.addItem(p,explosive);
                return true;
            }
        }


        return true;
    }
}
