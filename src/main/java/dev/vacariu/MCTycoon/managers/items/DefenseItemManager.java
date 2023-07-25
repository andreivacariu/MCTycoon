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
import java.util.HashMap;
import java.util.List;

public class DefenseItemManager {
    private final TycoonMain pl;

    public static HashMap<Integer, Material> protectionTiers = new HashMap<>();

    public CustomConfig protectionConfig;
    public DefenseItemManager(TycoonMain pl) {
        this.pl = pl;
        protectionConfig = new CustomConfig("configs","defenses",pl);
        loadData();
    }


    public static List<String> name = new ArrayList<>();
    public static List<Double> price = new ArrayList<>();
    public static List<List<String>> lore = new ArrayList<>();


    public ItemStack getProtection(int tier,int amount){
        if (protectionTiers.containsKey(tier)){
            ItemStack i = new ItemStack(protectionTiers.get(tier),amount);
            ItemMeta im = i.getItemMeta();
            im.setDisplayName(Utils.asColor(name.get(tier-1).replaceAll("%tier%", RomanNumber.toRoman(tier)+"")));
            List<String> il = new ArrayList<>();
            lore.get(tier-1).forEach(s->{
                il.add(Utils.asColor(s.replaceAll("%tier%",RomanNumber.toRoman(tier)+"")));
            });
            im.setLore(il);
            im.getPersistentDataContainer().set(pl.protectionKey, PersistentDataType.INTEGER,tier);
            i.setItemMeta(im);

            return i;
        }else{
            return null;
        }
    }

    private void loadData(){
        FileConfiguration cfg = protectionConfig.getConfig();
        if (cfg.contains("ProtectionItem")){
            for(String id : cfg.getConfigurationSection("ProtectionItem").getKeys(false)) {
                try{
                    protectionTiers.put(cfg.getInt("ProtectionItem." + id + ".tier"),Material.valueOf(cfg.getString("ProtectionItem." + id + ".type")));
                }catch (Exception ex){
                    Bukkit.getConsoleSender().sendMessage(Utils.asColor("&c[Tycoon] Wrong block type, defaulting to STONE_BRICKS"));
                    protectionTiers.put(cfg.getInt("ProtectionItem." + id + ".tier"),Material.STONE_BRICKS);
                }
                name.add(Utils.translateHexColorCodes("&#","",cfg.getString("ProtectionItem." + id + ".name")));
                lore.add(cfg.getStringList("ProtectionItem." + id + ".lore"));
                price.add(cfg.getDouble("ProtectionItem." + id + ".price"));
            }
        }else{
            loadDefData();
            loadData();
        }


    }
    private void loadDefData(){
        FileConfiguration cfg = protectionConfig.getConfig();
        cfg.set("ProtectionItem.tier_1.name","&7Defense Block &7[&fT. %tier%&7]");
        cfg.set("ProtectionItem.tier_1.lore", Arrays.asList("&7This defense will require a TNT of at least &fT. %tier%&7", "&7to be broken"));
        cfg.set("ProtectionItem.tier_1.type", "STONE_BRICKS");
        cfg.set("ProtectionItem.tier_1.tier", 1);
        cfg.set("ProtectionItem.tier_1.price", 10000);

        protectionConfig.save();
    }





}
