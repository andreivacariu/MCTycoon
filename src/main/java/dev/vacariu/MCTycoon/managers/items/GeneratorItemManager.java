package dev.vacariu.MCTycoon.managers.items;

import dev.vacariu.MCTycoon.internalutils.RomanNumber;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import dev.vacariu.MCTycoon.TycoonMain;
import dev.vacariu.MCTycoon.internalutils.CustomConfig;
import dev.vacariu.MCTycoon.internalutils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneratorItemManager {
    private final TycoonMain pl;



    public CustomConfig generatorConfig;
    public GeneratorItemManager(TycoonMain pl) {
        this.pl = pl;
        generatorConfig = new CustomConfig("configs","generators",pl);
        loadData();
    }

    public static List<Material> material = new ArrayList<>();
    public static List<Integer> tier = new ArrayList<>();
    public static List<String> name = new ArrayList<>();
    public static List<List<String>> lore = new ArrayList<>();




    public ItemStack getGenerator(int tier,int amount){
        ItemStack i = new ItemStack(GeneratorItemManager.material.get(tier-1),amount);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(Utils.asColor(name.get(tier-1).replaceAll("%tier%", RomanNumber.toRoman(tier) +"")));
        List<String> il = new ArrayList<>();
        lore.get(tier-1).forEach(s->{
            il.add(Utils.asColor(s.replaceAll("%tier%",RomanNumber.toRoman(tier)+"")));
        });
        im.setLore(il);
        im.getPersistentDataContainer().set(pl.generatorKey, PersistentDataType.INTEGER,tier);
        i.setItemMeta(im);
        return i;
    }

    private void loadData(){
        FileConfiguration cfg = generatorConfig.getConfig();

        if (cfg.contains("GeneratorItem")){
            for(String id : cfg.getConfigurationSection("GeneratorItem").getKeys(false)) {
                try{
                    material.add(Material.valueOf(cfg.getString("GeneratorItem." + id + ".type")));
                }catch (Exception ex){
                    Bukkit.getConsoleSender().sendMessage(Utils.asColor("&c[Tycoon] Wrong money type for bank note no such type, defaulting to GLASS"));
                    material.add(Material.GLASS);
                }
                name.add(Utils.translateHexColorCodes("&#","",cfg.getString("GeneratorItem." + id + ".name")));
                lore.add(cfg.getStringList("GeneratorItem." + id + ".lore"));
                tier.add(Integer.valueOf(id));
            }

        }else{
            loadDefData();
            loadData();
        }
    }

    private void loadDefData(){
        FileConfiguration cfg = generatorConfig.getConfig();
        cfg.set("GeneratorItem.1.type","GLASS");
        cfg.set("GeneratorItem.1.name","&fGenerator &7[%tier%]");
        cfg.set("GeneratorItem.1.lore", Arrays.asList("&7Place me down to start generating money!"));
        generatorConfig.save();
    }
}
