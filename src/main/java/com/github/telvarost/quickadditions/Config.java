package com.github.telvarost.quickadditions;

import blue.endless.jankson.Comment;
import net.glasslauncher.mods.api.gcapi.api.*;

public class Config {

    @GConfig(value = "config", visibleName = "QuickAdditions")
    public static ConfigFields config = new ConfigFields();

    public static class ConfigFields {
        @ConfigName("Enable Soul Sand Recipe")
        @Comment("Restart required for changes to take effect")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean ENABLE_SOUL_SAND_RECIPE = true;

        @ConfigName("Soul Sand Crafting Recipe Output: 1-16")
        @Comment("Restart required for changes to take effect")
        @MaxLength(16)
        @MultiplayerSynced
        public static Integer SOUL_SAND_OUTPUT = 4;
    }
}
