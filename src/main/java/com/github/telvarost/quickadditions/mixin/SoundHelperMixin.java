package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.SoundHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.Random;

@Environment(EnvType.CLIENT)
@Mixin(SoundHelper.class)
public abstract class SoundHelperMixin {

    @Shadow private int musicCountdown;

    @Shadow private Random rand;

    @Shadow public abstract void addMusic(String string, File file);

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    public void SoundHelper(CallbackInfo ci) {
        File customMusicDir = new File("custom-music");

        if (customMusicDir.exists())
        {
            if (!customMusicDir.isDirectory())
            {
                customMusicDir.delete();
                customMusicDir.mkdirs();
            }
            else
            {
                System.out.println("Looking for songs in: custom-music/");

                File [] oggFiles = customMusicDir.listFiles(f-> f.toString().endsWith(".ogg"));
                if (null != oggFiles) {
                    for (int fileIndex = 0; fileIndex < oggFiles.length; fileIndex++) {
                        System.out.println("Added: " + oggFiles[fileIndex].getName());
                        addMusic(oggFiles[fileIndex].getName(), oggFiles[fileIndex]);
                    }
                }

                File [] musFiles = customMusicDir.listFiles(f-> f.toString().endsWith(".wav"));
                if (null != musFiles) {
                    for (int fileIndex = 0; fileIndex < musFiles.length; fileIndex++) {
                        System.out.println("Added: " + musFiles[fileIndex].getName());
                        addMusic(musFiles[fileIndex].getName(), musFiles[fileIndex]);
                    }
                }

                File [] wavFiles = customMusicDir.listFiles(f-> f.toString().endsWith(".wav"));
                if (null != wavFiles) {
                    for (int fileIndex = 0; fileIndex < wavFiles.length; fileIndex++) {
                        System.out.println("Added: " + wavFiles[fileIndex].getName());
                        addMusic(wavFiles[fileIndex].getName(), wavFiles[fileIndex]);
                    }
                }
            }
        }
        else
        {
            customMusicDir.mkdirs();
        }

        this.musicCountdown = this.rand.nextInt(Config.config.musicCoundownRandomIntervalMax);
    }

    @ModifyConstant(
            method = "handleBackgroundMusic",
            constant = @Constant(
                    intValue = 12000,
                    ordinal = 0
            )
    )
    private int quickAdditions_handleBackgroundMusicRandomIntervalMax(int constant) {
        return Config.config.musicCoundownRandomIntervalMax;
    }


    @ModifyConstant(
            method = "handleBackgroundMusic",
            constant = @Constant(
                    intValue = 12000,
                    ordinal = 1
            )
    )
    private int quickAdditions_handleBackgroundMusicRandomIntervalMin(int constant) {
        return Config.config.musicCoundownRandomIntervalMin;
    }
}
