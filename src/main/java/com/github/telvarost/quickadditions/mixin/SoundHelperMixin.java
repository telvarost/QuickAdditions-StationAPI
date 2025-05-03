package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import com.github.telvarost.quickadditions.ModHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.biome.Biome;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

@Environment(EnvType.CLIENT)
@Mixin(SoundManager.class)
public abstract class SoundHelperMixin {

    @Shadow private int timeUntilNextSong;

    @Shadow private Random random;

    @Shadow private SoundEntry music;

    @Shadow public abstract void loadMusic(String string, File file);

    @Shadow public abstract void loadStreaming(String string, File file);

    @Shadow private static SoundSystem soundSystem;

    @Shadow private GameOptions gameOptions;
    @Unique private int dimensionId = 0;

    @Unique private String biomeTag = "-unknown-";

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
                System.out.println("Looking for main menu songs in: main-menu-theme/");
                ModHelper.ModHelperFields.musicForMainMenu = new ArrayList<String>();

                File [] oggFiles = mainMenuThemeDir.listFiles(f-> f.toString().endsWith(".ogg"));
                if (null != oggFiles) {
                    for (int fileIndex = 0; fileIndex < oggFiles.length; fileIndex++) {
                        String songFileName = oggFiles[fileIndex].getName();
                        loadStreaming(songFileName, oggFiles[fileIndex]);
                        ModHelper.ModHelperFields.musicForMainMenu.add(songFileName.substring(0, songFileName.length() - 4));
                        System.out.println("Added: " + songFileName);
                    }
                }

                File [] musFiles = mainMenuThemeDir.listFiles(f-> f.toString().endsWith(".mus"));
                if (null != musFiles) {
                    for (int fileIndex = 0; fileIndex < musFiles.length; fileIndex++) {
                        String songFileName = musFiles[fileIndex].getName();
                        loadStreaming(songFileName, musFiles[fileIndex]);
                        ModHelper.ModHelperFields.musicForMainMenu.add(songFileName.substring(0, songFileName.length() - 4));
                        System.out.println("Added: " + songFileName);
                    }
                }

                File [] wavFiles = mainMenuThemeDir.listFiles(f-> f.toString().endsWith(".wav"));
                if (null != wavFiles) {
                    for (int fileIndex = 0; fileIndex < wavFiles.length; fileIndex++) {
                        String songFileName = wavFiles[fileIndex].getName();
                        loadStreaming(songFileName, wavFiles[fileIndex]);
                        ModHelper.ModHelperFields.musicForMainMenu.add(songFileName.substring(0, songFileName.length() - 4));
                        System.out.println("Added: " + songFileName);
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
            at = @At("HEAD"),
            cancellable = true
    )
    public void quickAdditions_tickCancelSong(CallbackInfo ci) {
        if (ModHelper.ModHelperFields.cancelCurrentBGM) {
            ModHelper.ModHelperFields.cancelCurrentBGM = false;
            soundSystem.stop("BgMusic");
        }
    }

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lpaulscode/sound/SoundSystem;playing(Ljava/lang/String;)Z",
                    ordinal = 0
            )
    )
    public boolean quickAdditions_tickRegionSpecific(SoundSystem instance, String string, Operation<Boolean> original) {
        if (gameOptions.debugHud) {
            if (!instance.playing("BgMusic") && !instance.playing("streaming")) {
                ModHelper.ModHelperFields.currentBGM = "none";
            }
        }

        return original.call(instance, string);
    }

    @WrapOperation(
            method = "playStreaming",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sound/SoundEntry;get(Ljava/lang/String;)Lnet/minecraft/client/sound/Sound;"
            )
    )
    public Sound quickAdditions_tickRegionSpecific(SoundEntry instance, String string, Operation<Sound> original) {
        Sound streamingSong = original.call(instance, string);

        ModHelper.ModHelperFields.currentBGM = streamingSong.id;

        return streamingSong;
    }

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sound/SoundEntry;getSounds()Lnet/minecraft/client/sound/Sound;"
            )
    )
    public Sound quickAdditions_tickRegionSpecific(SoundEntry instance, Operation<Sound> original) {
        Sound currentMusic = original.call(instance);
        ModHelper.ModHelperFields.currentBGM = currentMusic.id;

        if (  (null != currentMusic)
           && (null != currentMusic.id)
           && (currentMusic.id.contains("-specific."))
        ) {
            PlayerEntity player = PlayerHelper.getPlayerFromGame();
            if (null != player) {
                dimensionId = player.dimensionId;
            }

            if (currentMusic.id.contains("-level" + dimensionId + "-")) {
                ModHelper.ModHelperFields.songLevelId = dimensionId;
                return currentMusic;
            }

            if (0 == dimensionId) {
                if (currentMusic.id.contains("-overworld-")) {
                    ModHelper.ModHelperFields.songLevelId = dimensionId;
                    return currentMusic;
                }
            } else if (-1 == dimensionId) {
                if (currentMusic.id.contains("-nether-")) {
                    ModHelper.ModHelperFields.songLevelId = dimensionId;
                    return currentMusic;
                }
            }

            if (null != player) {
                if (null != player.world) {
                    if (null != player.world.method_1781()) {
                        Biome biome = player.world.method_1781().getBiome((int) Math.floor(player.x), (int) Math.floor(player.z));
                        if (null != biome && null != biome.name) {
                            biomeTag = '-' + biome.name.toLowerCase() + '-';
                        }
                    }
                }
            }

            if (currentMusic.id.contains(biomeTag)) {
                ModHelper.ModHelperFields.songLevelId = Integer.MAX_VALUE;
                return currentMusic;
            }

            System.out.println("Skipping: " + currentMusic.id);
            ModHelper.ModHelperFields.songLevelId = Integer.MAX_VALUE;
            return null;
        } else if (  (null != currentMusic)
                  && (null != currentMusic.soundFile)
                  && (currentMusic.soundFile.toString().contains("-specific."))
        ) {
            PlayerEntity player = PlayerHelper.getPlayerFromGame();
            if (null != player) {
                dimensionId = player.dimensionId;
            }

            if (currentMusic.soundFile.toString().contains("-level" + dimensionId + "-")) {
                ModHelper.ModHelperFields.songLevelId = dimensionId;
                return currentMusic;
            }

            if (0 == dimensionId) {
                if (currentMusic.soundFile.toString().contains("-overworld-")) {
                    ModHelper.ModHelperFields.songLevelId = dimensionId;
                    return currentMusic;
                }
            } else if (-1 == dimensionId) {
                if (currentMusic.soundFile.toString().contains("-nether-")) {
                    ModHelper.ModHelperFields.songLevelId = dimensionId;
                    return currentMusic;
                }
            }

            if (null != player) {
                if (null != player.world) {
                    if (null != player.world.method_1781()) {
                        Biome biome = player.world.method_1781().getBiome((int) Math.floor(player.x), (int) Math.floor(player.z));
                        if (null != biome && null != biome.name) {
                            biomeTag = '-' + biome.name.toLowerCase() + '-';
                        }
                    }
                }
            }

            if (currentMusic.soundFile.toString().contains(biomeTag)) {
                ModHelper.ModHelperFields.songLevelId = Integer.MAX_VALUE;
                return currentMusic;
            }

            System.out.println("Skipping: " + currentMusic.soundFile);
            ModHelper.ModHelperFields.songLevelId = Integer.MAX_VALUE;
            return null;
        }

        ModHelper.ModHelperFields.songLevelId = Integer.MAX_VALUE;
        return currentMusic;
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
        float adjustedVolume = v;

        if (id.startsWith("portal.portal")) {
            adjustedVolume = v * Config.config.MUSIC_CONFIG.volumeNetherPortalAmbient;
        } else if (id.startsWith("ambient")) {
            if (id.startsWith("ambient.weather.rain")) {
                adjustedVolume = v * Config.config.MUSIC_CONFIG.volumeRainAmbient;
            } else if (id.startsWith("ambient.cave.cave")) {
                adjustedVolume = v * Config.config.MUSIC_CONFIG.volumeCaveAmbient;
            }
        } else if (id.startsWith("mob.ghast.moan")) {
            adjustedVolume = v * Config.config.MUSIC_CONFIG.volumeGhastAmbient;
        }

        original.call(instance, f, adjustedVolume);
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
        float adjustedVolume = v;

        if (id.startsWith("portal.portal")) {
            adjustedVolume = v * Config.config.MUSIC_CONFIG.volumeNetherPortalAmbient;
        } else if (id.startsWith("ambient")) {
            if (id.startsWith("ambient.weather.rain")) {
                adjustedVolume = v * Config.config.MUSIC_CONFIG.volumeRainAmbient;
            } else if (id.startsWith("ambient.cave.cave")) {
                adjustedVolume = v * Config.config.MUSIC_CONFIG.volumeCaveAmbient;
            }
        } else if (id.startsWith("mob.ghast.moan")) {
            adjustedVolume = v * Config.config.MUSIC_CONFIG.volumeGhastAmbient;
        }

        original.call(instance, f, adjustedVolume);
    }
}
