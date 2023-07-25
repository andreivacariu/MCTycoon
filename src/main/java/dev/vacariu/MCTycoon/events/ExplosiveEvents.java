package dev.vacariu.MCTycoon.events;

import dev.vacariu.MCTycoon.TycoonMain;
import dev.vacariu.MCTycoon.components.ProtectionBlock;
import dev.vacariu.MCTycoon.internalutils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.EulerAngle;

public class ExplosiveEvents implements Listener {
    private final TycoonMain pl;
    public ExplosiveEvents(TycoonMain pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.isCancelled()){
            return;
        }
        if (event.getItemInHand().hasItemMeta()&&event.getItemInHand().getItemMeta().getPersistentDataContainer().has(pl.explosiveKey, PersistentDataType.INTEGER)){
            ItemStack i = event.getItemInHand();
            ItemMeta im = i.getItemMeta();
            int tier = im.getPersistentDataContainer().get(pl.explosiveKey,PersistentDataType.INTEGER);
            event.getBlockPlaced().setType(Material.AIR);
            animateHeadAndExplode(event.getBlockPlaced(), tier);
        }
    }
    private void animateHeadAndExplode(Block block, int tier) {
        Location headLocation = block.getLocation().add(0.5, 0, 0.5);
        ArmorStand armorStand = (ArmorStand) block.getWorld().spawnEntity(headLocation, EntityType.ARMOR_STAND);

        armorStand.setInvisible(true);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setSmall(true);
        armorStand.setHelmet(new ItemStack(pl.explosiveItemManager.getExplosive(tier,1)));
        armorStand.setHeadPose(new EulerAngle(0, 0, 0));

         int animate = Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, () -> {
            animateArmorStand(armorStand);
        }, 1L, 1L);
        int sound = Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, () -> {
            armorStand.getLocation().getWorld().playSound(armorStand.getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 1.0f, 0.5f);
        }, 1L, 2L);

        Bukkit.getScheduler().runTaskLater(pl, () -> {
            Bukkit.getScheduler().cancelTask(animate);
            Bukkit.getScheduler().cancelTask(sound);
            armorStand.getWorld().createExplosion(armorStand.getLocation(), 4.0f, false, false);
            for (Block blocks : LocationUtils.getSphere(block.getLocation(), 2)) {
                ProtectionBlock protectionBlock = pl.defenseManager.getProtection(blocks);
                if (protectionBlock != null) {
                    if (protectionBlock.tier <= tier) {
                        pl.defenseManager.deleteProtection(blocks);
                    }
                }
            }
            armorStand.remove();
        }, 60L);
    }

    private void animateArmorStand(ArmorStand armorStand) {
        double radiansPerTick = Math.PI / 20;
        double yOffset = 0.05 * Math.sin(armorStand.getTicksLived() * radiansPerTick);
        double yawDegrees = armorStand.getTicksLived() * 10.0;
        double pitchDegrees = 0.0;
        armorStand.setHeadPose(new EulerAngle(Math.toRadians(pitchDegrees), Math.toRadians(yawDegrees), 0));
        armorStand.teleport(armorStand.getLocation().add(0, yOffset, 0));
    }
}