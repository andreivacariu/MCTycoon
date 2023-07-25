package dev.vacariu.MCTycoon;

import dev.vacariu.MCTycoon.commands.ShopCommand;
import dev.vacariu.MCTycoon.events.BanknoteEvents;
import dev.vacariu.MCTycoon.events.ExplosiveEvents;
import dev.vacariu.MCTycoon.events.ProtectionEvents;
import dev.vacariu.MCTycoon.internalutils.EconomyHandler;
import dev.vacariu.MCTycoon.managers.items.ExplosiveItemManager;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import dev.vacariu.MCTycoon.commands.TycoonCommand;
import dev.vacariu.MCTycoon.events.GeneratorEvents;
import dev.vacariu.MCTycoon.managers.items.BanknoteItemManager;
import dev.vacariu.MCTycoon.managers.items.GeneratorItemManager;
import dev.vacariu.MCTycoon.managers.RewardManager;
import dev.vacariu.MCTycoon.managers.items.DefenseItemManager;
import dev.vacariu.MCTycoon.managers.runners.GeneratorManager;
import dev.vacariu.MCTycoon.managers.statics.DefenseManager;
import redempt.redlib.blockdata.BlockDataManager;
import redempt.redlib.blockdata.DataBlock;

public class TycoonMain extends JavaPlugin {


    public NamespacedKey bankNoteKey = new NamespacedKey(this,"tycoon.bank-note");
    public NamespacedKey generatorKey = new NamespacedKey(this,"tycoon.generator");
    public NamespacedKey protectionKey = new NamespacedKey(this,"tycoon.defense");
    public NamespacedKey explosiveKey = new NamespacedKey(this,"tycoon.explosive");

    public GeneratorItemManager generatorItemManager;
    public DefenseItemManager defenseItemManager;
    public ExplosiveItemManager explosiveItemManager;
    public RewardManager rewardManager;
    public BanknoteItemManager bankNoteItemManager;
    public EconomyHandler economyHandler;
    public GeneratorManager generatorManager;
    public DefenseManager defenseManager;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        rewardManager = new RewardManager(this);
        bankNoteItemManager = new BanknoteItemManager(this);
        generatorItemManager = new GeneratorItemManager(this);
        explosiveItemManager = new ExplosiveItemManager(this);
        defenseItemManager = new DefenseItemManager(this);

        generatorManager = new GeneratorManager(this);
        defenseManager = new DefenseManager(this);
        getCommand("tycoon").setExecutor(new TycoonCommand(this));
        getCommand("shop").setExecutor(new ShopCommand(this));


        economyHandler = new EconomyHandler(this);
        economyHandler.init();

        getServer().getPluginManager().registerEvents(new GeneratorEvents(this),this);
        getServer().getPluginManager().registerEvents(new BanknoteEvents(this),this);
        getServer().getPluginManager().registerEvents(new ProtectionEvents(this),this);
        getServer().getPluginManager().registerEvents(new ExplosiveEvents(this),this);



    }

    @Override
    public void onDisable() {
    }
}
