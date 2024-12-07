package com.github.telvarost.quickadditions;

import net.glasslauncher.mods.gcapi3.api.*;

public class Config {

    @ConfigRoot(value = "config", visibleName = "QuickAdditions")
    public static ConfigFields config = new ConfigFields();

    public static class ConfigFields {
        @ConfigCategory(
                name = "Add Missing Achievements Config",
                description = "Must be in a world when marking achievements"
        )
        public MissingAchievementsConfig MISSING_ACHIEVEMENTS_CONFIG = new MissingAchievementsConfig();

        @ConfigEntry(
                name = "Always Snow Above Set Y Level Enabled",
                multiplayerSynced = true
        )
        public Boolean enableAlwaysSnowAboveSetYLevel = false;

        @ConfigEntry(
                name = "Always Snow Above This Y Level",
                multiplayerSynced = true,
                maxLength = 256
        )
        public Integer alwaysSnowAboveThisYLevel = 96;

        @ConfigEntry(
                name = "Beds Speed Up Night Rather Than Skip It",
                multiplayerSynced = true
        )
        public Boolean bedsSpeedUpNightRatherThanSkipIt = false;

        @ConfigEntry(
                name = "Click Or Swing Hand To Exit Beds Enabled",
                multiplayerSynced = true
        )
        public Boolean handSwingClickToExitBedsEnabled = false;

        @ConfigEntry(
                name = "Force Display Active",
                description = "Game will not pause when tabbing away"
        )
        public Boolean forceDisplayActive = false;

        @ConfigEntry(
                name = "Main Menu Theme Enabled",
                description = "Plays 'mainmenu' song file if one is found"
        )
        public Boolean mainMenuThemeEnabled = true;

        @ConfigEntry(name = "Main Menu Theme Overrides Background Music")
        public Boolean mainMenuThemeOverridesBGM = false;

        @ConfigEntry(
                name = "Minecraft Default Background Music Disabled",
                description = "Restart required for changes to take effect"
        )
        public Boolean disableDefaultMinecraftBGM = false;

        @ConfigEntry(
                name = "Music Countdown Random Interval Min",
                description = "Default Value: 12000",
                maxLength = 36863
        )
        public Integer musicCoundownRandomIntervalMin = 12000;

        @ConfigEntry(
                name = "Music Countdown Random Interval Max",
                description = "Default Value: 12000",
                maxLength = 36863
        )
        public Integer musicCoundownRandomIntervalMax = 12000;

        @ConfigEntry(
                name = "Sleep Only Resets Weather When Raining",
                multiplayerSynced = true
        )
        public Boolean sleepOnlyResetsWeatherWhenRaining = false;

        @ConfigEntry(
                name = "Soul Sand Crafting Recipe Enable",
                description = "Restart required for changes to take effect",
                multiplayerSynced = true
        )
        public Boolean ENABLE_SOUL_SAND_RECIPE = false;

        @ConfigEntry(
                name = "Soul Sand Recipe Output: 1-16",
                description = "Restart required for changes to take effect",
                multiplayerSynced = true,
                maxLength = 16
        )
        public Integer SOUL_SAND_OUTPUT = 4;

        @ConfigEntry(
                name = "Spawn Group Size",
                description = "Does not effect ghasts, wolves cap at 8",
                multiplayerSynced = true,
                maxLength = 16
        )
        public Integer SPAWN_GROUP_SIZE = 4;

        @ConfigEntry(
                name = "Spawn Capacity Max: Monster",
                multiplayerSynced = true,
                maxLength = 255
        )
        public Integer MAX_SPAWN_CAPACITY_MONSTER = 70;

        @ConfigEntry(
                name = "Spawn Capacity Max: Passive",
                multiplayerSynced = true,
                maxLength = 255
        )
        public Integer MAX_SPAWN_CAPACITY_PASSIVE = 15;

        @ConfigEntry(
                name = "Spawn Capacity Max: Squid",
                multiplayerSynced = true,
                maxLength = 255
        )
        public Integer MAX_SPAWN_CAPACITY_SQUID = 5;
    }

    public static class MissingAchievementsConfig {
        @ConfigEntry(name = "Mark Achieved: Open Inventory")
        public Boolean OPEN_INVENTORY = false;

        @ConfigEntry(name = "Mark Achieved: Mine Wood")
        public Boolean MINE_WOOD = false;

        @ConfigEntry(name = "Mark Achieved: Craft Workbench")
        public Boolean CRAFT_WORKBENCH = false;

        @ConfigEntry(name = "Mark Achieved: Craft Pickaxe")
        public Boolean CRAFT_PICKAXE = false;

        @ConfigEntry(name = "Mark Achieved: Craft Furnace")
        public Boolean CRAFT_FURNACE = false;

        @ConfigEntry(name = "Mark Achieved: Aquire Iron")
        public Boolean AQUIRE_IRON = false;

        @ConfigEntry(name = "Mark Achieved: Craft Hoe")
        public Boolean CRAFT_HOE = false;

        @ConfigEntry(name = "Mark Achieved: Make Bread")
        public Boolean MAKE_BREAD = false;

        @ConfigEntry(name = "Mark Achieved: Bake Cake")
        public Boolean BAKE_CAKE = false;

        @ConfigEntry(name = "Mark Achieved: Craft Better Pickaxe")
        public Boolean CRAFT_BETTER_PICKAXE = false;

        @ConfigEntry(name = "Mark Achieved: Cook Fish")
        public Boolean COOK_FISH = false;

        @ConfigEntry(name = "Mark Achieved: On A Rail")
        public Boolean ON_A_RAIL = false;

        @ConfigEntry(name = "Mark Achieved: Craft Sword")
        public Boolean CRAFT_SWORD = false;

        @ConfigEntry(name = "Mark Achieved: Kill Enemy")
        public Boolean KILL_ENEMY = false;

        @ConfigEntry(name = "Mark Achieved: Kill Cow")
        public Boolean KILL_COW = false;

        @ConfigEntry(name = "Mark Achieved: Fly Pig")
        public Boolean FLY_PIG = false;
    }
}
