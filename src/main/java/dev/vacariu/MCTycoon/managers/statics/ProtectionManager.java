package dev.vacariu.MCTycoon.managers.statics;

import dev.vacariu.MCTycoon.TycoonMain;
import dev.vacariu.MCTycoon.components.ProtectionBlock;

import java.util.HashSet;
import java.util.Set;

public class ProtectionManager {
    private final TycoonMain pl;
    public ProtectionManager(TycoonMain pl) {
        this.pl = pl;
    }


    private Set<ProtectionBlock> protectionBlocks = new HashSet<>();


}
