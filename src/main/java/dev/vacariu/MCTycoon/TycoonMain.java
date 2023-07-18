package dev.vacariu.MCTycoon;

import dev.vacariu.MCTycoon.events.BanknoteEvents;
import dev.vacariu.MCTycoon.internalutils.EconomyHandler;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import dev.vacariu.MCTycoon.commands.TycoonCommand;
import dev.vacariu.MCTycoon.events.GeneratorEvents;
import dev.vacariu.MCTycoon.managers.items.BankNoteManager;
import dev.vacariu.MCTycoon.managers.items.GeneratorItemManager;
import dev.vacariu.MCTycoon.managers.RewardManager;
import dev.vacariu.MCTycoon.managers.items.ProtectionItemManager;
import dev.vacariu.MCTycoon.managers.runners.GeneratorManager;
import dev.vacariu.MCTycoon.managers.statics.ProtectionManager;

public class TycoonMain extends JavaPlugin {


    public NamespacedKey bankNoteKey = new NamespacedKey(this,"tycoon.bank-note");
    public NamespacedKey generatorKey = new NamespacedKey(this,"tycoon.generator");
    public NamespacedKey protectionKey = new NamespacedKey(this,"tycoon.protection");


    public RewardManager rewardManager;
    public BankNoteManager bankNoteManager;
    public GeneratorItemManager generatorItemManager;
    public ProtectionItemManager protectionItemManager;

    public EconomyHandler economyHandler;

    public GeneratorManager generatorManager;
    public ProtectionManager protectionManager;

    @Override
    public void onEnable() {
        rewardManager = new RewardManager(this);
        bankNoteManager = new BankNoteManager(this);
        generatorItemManager = new GeneratorItemManager(this);
        protectionItemManager = new ProtectionItemManager(this);


        generatorManager = new GeneratorManager(this);
        protectionManager = new ProtectionManager(this);
        getCommand("tycoon").setExecutor(new TycoonCommand(this));

        economyHandler = new EconomyHandler(this);
        economyHandler.init();

        getServer().getPluginManager().registerEvents(new GeneratorEvents(this),this);
        getServer().getPluginManager().registerEvents(new BanknoteEvents(this),this);
    }

    @Override
    public void onDisable() {

    }
}
