package dev.vacariu.MCTycoon.managers.items;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import dev.vacariu.MCTycoon.TycoonMain;
import dev.vacariu.MCTycoon.internalutils.CustomConfig;
import dev.vacariu.MCTycoon.internalutils.RomanNumber;
import dev.vacariu.MCTycoon.internalutils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ExplosiveItemManager {
    private final TycoonMain pl;

    public static HashMap<Integer, Material> explosiveTiers = new HashMap<>();

    public CustomConfig explosiveConfig;
    public ExplosiveItemManager(TycoonMain pl) {
        this.pl = pl;
        explosiveConfig = new CustomConfig("configs","explosives",pl);
        loadData();
    }


    public static List<String> name = new ArrayList<>();
    public static List<String> texture = new ArrayList<>();
    public static List<List<String>> lore = new ArrayList<>();


    public ItemStack getExplosive(int tier,int amount){
        if (explosiveTiers.containsKey(tier)){
            ItemStack i = new ItemStack(explosiveTiers.get(tier),amount);
            SkullMeta im = (SkullMeta) i.getItemMeta();
            im.setDisplayName(Utils.asColor(name.get(tier-1).replaceAll("%tier%", RomanNumber.toRoman(tier)+"")));
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
            profile.setProperty(new ProfileProperty("textures", texture.get(tier-1)));
            im.setPlayerProfile(profile);
            List<String> il = new ArrayList<>();
            lore.get(tier-1).forEach(s->{
                il.add(Utils.asColor(s.replaceAll("%tier%",RomanNumber.toRoman(tier)+"")));
            });
            im.setLore(il);
            im.getPersistentDataContainer().set(pl.explosiveKey, PersistentDataType.INTEGER,tier);
            i.setItemMeta(im);
            return i;
        }else{
            return null;
        }
    }

    public static ItemStack getShopExplosive() {
        ItemStack i = new ItemStack(Material.PLAYER_HEAD,1);
        SkullMeta im = (SkullMeta) i.getItemMeta();
        im.setDisplayName(Utils.asColor(name.get(0).replaceAll("%tier%", RomanNumber.toRoman(1)+"")));
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        profile.setProperty(new ProfileProperty("textures", texture.get(0)));
        im.setPlayerProfile(profile);
        List<String> il = new ArrayList<>();
        lore.get(0).forEach(s->{
            il.add(Utils.asColor(s.replaceAll("%tier%",RomanNumber.toRoman(1)+"")));
        });
        im.setLore(il);
        i.setItemMeta(im);
        return i;
    }

    private void loadData(){
        FileConfiguration cfg = explosiveConfig.getConfig();
        if (cfg.contains("ExplosiveItem")){
            for(String id : cfg.getConfigurationSection("ExplosiveItem").getKeys(false)) {
                try{
                    explosiveTiers.put(cfg.getInt("ExplosiveItem." + id + ".tier"),Material.valueOf(cfg.getString("ExplosiveItem." + id + ".type")));
                }catch (Exception ex){
                    Bukkit.getConsoleSender().sendMessage(Utils.asColor("&c[Tycoon] Wrong explosive type, defaulting to PLAYER_HEAD"));
                    explosiveTiers.put(cfg.getInt("ExplosiveItem." + id + ".tier"),Material.PLAYER_HEAD);
                }
                name.add(Utils.translateHexColorCodes("&#","",cfg.getString("ExplosiveItem." + id + ".name")));
                lore.add(cfg.getStringList("ExplosiveItem." + id + ".lore"));
                texture.add(cfg.getString("ExplosiveItem." + id + ".texture"));
            }
        }else{
            loadDefData();
            loadData();
        }


    }
    private void loadDefData(){
        FileConfiguration cfg = explosiveConfig.getConfig();
        cfg.set("ExplosiveItem.tier_1.name","&7Explosive &7[&fT. %tier%&7]");
        cfg.set("ExplosiveItem.tier_1.lore", Arrays.asList("&7This TNT will break a defense of maximum &fT. %tier%&7"));
        cfg.set("ExplosiveItem.tier_1.type", "PLAYER_HEAD");
        cfg.set("ExplosiveItem.tier_1.texture", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDE5ZWYyNTQ2MDU2Zjk0OWI4NDMxY2RhMGQxMzg1ZmQzYmUyYjcxMWQ2YzQ1ZTQ0YmQ0OGNkZWE2OTBhN2RkIn19fQ==");
        cfg.set("ExplosiveItem.tier_1.tier", 1);

        explosiveConfig.save();
    }





}
