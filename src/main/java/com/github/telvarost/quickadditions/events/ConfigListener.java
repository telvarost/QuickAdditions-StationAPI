package com.github.telvarost.quickadditions.events;

import blue.endless.jankson.JsonObject;
import com.github.telvarost.quickadditions.Config;
import com.github.telvarost.quickadditions.mixin.SoundHelperAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.glasslauncher.mods.api.gcapi.api.PreConfigSavedListener;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.Minecraft;

import java.util.Random;

@EventListener
public class ConfigListener implements PreConfigSavedListener {

    @Override
    public void onPreConfigSaved(int var1, JsonObject var2, JsonObject var3) {
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT){
            Minecraft minecraft = (Minecraft)FabricLoader.getInstance().getGameInstance();
            if (null != minecraft) {
                Random rand = new Random();
                ((SoundHelperAccessor)minecraft.soundHelper).setMusicCountdown(rand.nextInt(Config.config.musicCoundownRandomIntervalMax) + Config.config.musicCoundownRandomIntervalMin);
            }
        }
    }
}
