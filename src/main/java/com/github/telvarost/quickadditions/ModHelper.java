package com.github.telvarost.quickadditions;

import com.github.telvarost.quickadditions.commands.SPCCommands;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class ModHelper implements ModInitializer {

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("spc")) {
            SPCCommands.register();
        }
    }

    public static class ModHelperFields {
        public static Float currentSpeed = 1.0f;
    }
}
