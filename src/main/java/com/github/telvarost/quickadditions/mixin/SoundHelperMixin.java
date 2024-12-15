package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.biome.Biome;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import paulscode.sound.SoundSystem;

import java.io.File;
import java.util.Random;

@Environment(EnvType.CLIENT)
@Mixin(SoundManager.class)
public abstract class SoundHelperMixin {

    @Shadow private int timeUntilNextSong;

    @Shadow private Random random;

    @Shadow private SoundEntry music;

    @Shadow public abstract void loadMusic(String string, File file);

    @Shadow public abstract void loadStreaming(String string, File file);

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    public void SoundHelper(CallbackInfo ci) {
        File mainMenuThemeDir = new File("main-menu-theme");
        if (mainMenuThemeDir.exists()) {
            if (!mainMenuThemeDir.isDirectory()) {
                mainMenuThemeDir.delete();
                mainMenuThemeDir.mkdirs();
            } else {
                System.out.println("Looking for mainmenu song in: main-menu-theme/");

                File [] oggFiles = mainMenuThemeDir.listFiles(f-> f.toString().endsWith(".ogg"));
                if (null != oggFiles) {
                    for (int fileIndex = 0; fileIndex < oggFiles.length; fileIndex++) {
                        System.out.println("Added: " + oggFiles[fileIndex].getName());
                        if (oggFiles[fileIndex].getName().contains("mainmenu")) {
                            loadStreaming(oggFiles[fileIndex].getName(), oggFiles[fileIndex]);
                        }
                    }
                }

                File [] musFiles = mainMenuThemeDir.listFiles(f-> f.toString().endsWith(".mus"));
                if (null != musFiles) {
                    for (int fileIndex = 0; fileIndex < musFiles.length; fileIndex++) {
                        System.out.println("Added: " + musFiles[fileIndex].getName());
                        if (musFiles[fileIndex].getName().contains("mainmenu")) {
                            loadStreaming(musFiles[fileIndex].getName(), musFiles[fileIndex]);
                        }
                    }
                }

                File [] wavFiles = mainMenuThemeDir.listFiles(f-> f.toString().endsWith(".wav"));
                if (null != wavFiles) {
                    for (int fileIndex = 0; fileIndex < wavFiles.length; fileIndex++) {
                        System.out.println("Added: " + wavFiles[fileIndex].getName());
                        if (wavFiles[fileIndex].getName().contains("mainmenu")) {
                            loadStreaming(wavFiles[fileIndex].getName(), wavFiles[fileIndex]);
                        }
                    }
                }
            }
        } else {
            mainMenuThemeDir.mkdirs();
        }

        File customMusicDir = new File("custom-music");
        if (customMusicDir.exists()) {
            if (!customMusicDir.isDirectory()) {
                customMusicDir.delete();
                customMusicDir.mkdirs();
            } else {
                System.out.println("Looking for songs in: custom-music/");

                File [] oggFiles = customMusicDir.listFiles(f-> f.toString().endsWith(".ogg"));
                if (null != oggFiles) {
                    for (int fileIndex = 0; fileIndex < oggFiles.length; fileIndex++) {
                        System.out.println("Added: " + oggFiles[fileIndex].getName());
                        loadMusic(oggFiles[fileIndex].getName(), oggFiles[fileIndex]);
                    }
                }

                File [] musFiles = customMusicDir.listFiles(f-> f.toString().endsWith(".mus"));
                if (null != musFiles) {
                    for (int fileIndex = 0; fileIndex < musFiles.length; fileIndex++) {
                        System.out.println("Added: " + musFiles[fileIndex].getName());
                        loadMusic(musFiles[fileIndex].getName(), musFiles[fileIndex]);
                    }
                }

                File [] wavFiles = customMusicDir.listFiles(f-> f.toString().endsWith(".wav"));
                if (null != wavFiles) {
                    for (int fileIndex = 0; fileIndex < wavFiles.length; fileIndex++) {
                        System.out.println("Added: " + wavFiles[fileIndex].getName());
                        loadMusic(wavFiles[fileIndex].getName(), wavFiles[fileIndex]);
                    }
                }
            }
        } else {
            customMusicDir.mkdirs();
        }

        this.timeUntilNextSong = this.random.nextInt(Config.config.MUSIC_CONFIG.musicCoundownRandomIntervalMax);
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sound/SoundEntry;getSounds()Lnet/minecraft/client/sound/Sound;"
            ),
            cancellable = true
    )
    public void tick(CallbackInfo ci) {
        PlayerEntity player = PlayerHelper.getPlayerFromGame();
        Sound currentMusic = this.music.getSounds();

        if (  (null != player)
           && (null != currentMusic)
           && (null != currentMusic.id)
           && (12 < currentMusic.id.length())
        ) {
            String songName = currentMusic.id.substring(0, currentMusic.id.length() - 3);

            if (songName.endsWith("-specific.")) {
                String levelTag = "-level" + player.dimensionId + '-';
                String biomeTag = "-unknown-";

                if (songName.contains(levelTag)) {
                    return;
                }

                if (0 == player.dimensionId) {
                    if (songName.contains("-overworld-")) {
                        return;
                    }
                } else if (-1 == player.dimensionId) {
                    if (songName.contains("-nether-")) {
                        return;
                    }
                }

                if (null != player.world) {
                    if (null != player.world.method_1781()) {
                        Biome biome = player.world.method_1781().getBiome((int)Math.floor(player.x), (int)Math.floor(player.z));
                        if (null != biome && null != biome.name) {
                            biomeTag = '-' + biome.name.toLowerCase() + '-';
                        }
                    }
                }

                if (songName.contains(biomeTag)) {
                    return;
                }

                ci.cancel();
            }
        }
    }

    @ModifyConstant(
            method = "tick",
            constant = @Constant(
                    intValue = 12000,
                    ordinal = 0
            )
    )
    private int quickAdditions_handleBackgroundMusicRandomIntervalMax(int constant) {
        return Config.config.MUSIC_CONFIG.musicCoundownRandomIntervalMax;
    }


    @ModifyConstant(
            method = "tick",
            constant = @Constant(
                    intValue = 12000,
                    ordinal = 1
            )
    )
    private int quickAdditions_handleBackgroundMusicRandomIntervalMin(int constant) {
        return Config.config.MUSIC_CONFIG.musicCoundownRandomIntervalMin;
    }

    @WrapOperation(
            method = "playSound(Ljava/lang/String;FF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lpaulscode/sound/SoundSystem;setVolume(Ljava/lang/String;F)V"
            )
    )
    public void quickAdditions_playSoundPositional(
            SoundSystem instance, String f, float v, Operation<Void> original,
            String id, float volume, float pitch
    ) {
        if (id.startsWith("portal")) {
            System.out.println("Lowering portal volume");
            original.call(instance, f, (v / 10.0f));
        } else {
            original.call(instance, f, v);
        }
    }

    @WrapOperation(
            method = "playSound(Ljava/lang/String;FFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lpaulscode/sound/SoundSystem;setVolume(Ljava/lang/String;F)V"
            )
    )
    public void quickAdditions_playSound(
            SoundSystem instance, String f, float v, Operation<Void> original,
            String id, float x, float y, float z, float volume, float pitch
    ) {
        if (id.startsWith("portal")) {
            System.out.println("Lowering portal volume2");
            original.call(instance, f, (v / 10.0f));
        } else {
            original.call(instance, f, v);
        }
    }
}
