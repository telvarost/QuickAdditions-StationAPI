package com.github.telvarost.quickadditions;

import blue.endless.jankson.Comment;
import net.glasslauncher.mods.api.gcapi.api.*;

public class Config {

    @GConfig(value = "config", visibleName = "QuickAdditions")
    public static ConfigFields config = new ConfigFields();

    public static class ConfigFields {
        @ConfigCategory("Add Missing Achievements Config")
        @Comment("Must be in a world when marking achievements")
        public MissingAchievementsConfig MISSING_ACHIEVEMENTS_CONFIG = new MissingAchievementsConfig();

        @ConfigName("Beds Speed Up Night Rather Than Skip It")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean bedsSpeedUpNightRatherThanSkipIt = false;

        @ConfigName("Click Or Swing Hand To Exit Beds Enabled")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean handSwingClickToExitBedsEnabled = false;

        @ConfigName("Force Display Active")
        @Comment("Game will not pause when tabbing away")
        public Boolean forceDisplayActive = false;

        @ConfigName("Main Menu Theme Enabled")
        @Comment("Plays 'mainmenu' song file if one is found")
        public Boolean mainMenuThemeEnabled = true;

        @ConfigName("Main Menu Theme Overrides Background Music")
        public Boolean mainMenuThemeOverridesBGM = false;

        @ConfigName("Minecraft Default Background Music Disabled")
        @Comment("Restart required for changes to take effect")
        public Boolean disableDefaultMinecraftBGM = false;

        @ConfigName("Music Countdown Random Interval Min")
        @MaxLength(36863)
        @Comment("Default Value: 12000")
        public Integer musicCoundownRandomIntervalMin = 12000;

        @ConfigName("Music Countdown Random Interval Max")
        @MaxLength(36863)
        @Comment("Default Value: 12000")
        public Integer musicCoundownRandomIntervalMax = 12000;

        @ConfigName("Soul Sand Crafting Recipe Enable")
        @Comment("Restart required for changes to take effect")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean ENABLE_SOUL_SAND_RECIPE = true;

        @ConfigName("Soul Sand Recipe Output: 1-16")
        @Comment("Restart required for changes to take effect")
        @MaxLength(16)
        @MultiplayerSynced
        public static Integer SOUL_SAND_OUTPUT = 4;
    }

    public static class MissingAchievementsConfig {
        @ConfigName("Mark Achieved: Open Inventory")
        public Boolean OPEN_INVENTORY = false;

        @ConfigName("Mark Achieved: Mine Wood")
        public Boolean MINE_WOOD = false;

        @ConfigName("Mark Achieved: Craft Workbench")
        public Boolean CRAFT_WORKBENCH = false;

        @ConfigName("Mark Achieved: Craft Pickaxe")
        public Boolean CRAFT_PICKAXE = false;

        @ConfigName("Mark Achieved: Craft Furnace")
        public Boolean CRAFT_FURNACE = false;

        @ConfigName("Mark Achieved: Aquire Iron")
        public Boolean AQUIRE_IRON = false;

        @ConfigName("Mark Achieved: Craft Hoe")
        public Boolean CRAFT_HOE = false;

        @ConfigName("Mark Achieved: Make Bread")
        public Boolean MAKE_BREAD = false;

        @ConfigName("Mark Achieved: Bake Cake")
        public Boolean BAKE_CAKE = false;

        @ConfigName("Mark Achieved: Craft Better Pickaxe")
        public Boolean CRAFT_BETTER_PICKAXE = false;

        @ConfigName("Mark Achieved: Cook Fish")
        public Boolean COOK_FISH = false;

        @ConfigName("Mark Achieved: On A Rail")
        public Boolean ON_A_RAIL = false;

        @ConfigName("Mark Achieved: Craft Sword")
        public Boolean CRAFT_SWORD = false;

        @ConfigName("Mark Achieved: Kill Enemy")
        public Boolean KILL_ENEMY = false;

        @ConfigName("Mark Achieved: Kill Cow")
        public Boolean KILL_COW = false;

        @ConfigName("Mark Achieved: Fly Pig")
        public Boolean FLY_PIG = false;
    }
}
