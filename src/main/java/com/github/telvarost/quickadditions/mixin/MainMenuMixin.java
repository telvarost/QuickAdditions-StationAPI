package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.client.gui.screen.menu.MainMenu;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.client.sound.SoundMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystem;

@Environment(EnvType.CLIENT)
@Mixin(MainMenu.class)
public class MainMenuMixin extends ScreenBase {

    @Shadow private float ticksOpened;

    @Inject(
            method = "init",
            at = @At("RETURN"),
            cancellable = true
    )
    public void init(CallbackInfo ci) {
        if (ticksOpened <= 0) {
            if (Config.config.mainMenuThemeEnabled) {
                playMainMenuTheme();
            }
        }
    }

    @Unique
    private void playMainMenuTheme() {
        SoundSystem soundSystem = ((SoundHelperAccessor)minecraft.soundHelper).getSoundSystem();
        SoundMap streaming = ((SoundHelperAccessor)minecraft.soundHelper).getStreaming();
        boolean initialized = ((SoundHelperAccessor)minecraft.soundHelper).getInitialized();
        if (initialized && minecraft.options.music != 0.0F) {
            SoundEntry var1 = streaming.getRandomSoundForId("mainmenu");
            if (var1 != null) {
                if (Config.config.mainMenuThemeOverridesBGM) {
                    if (soundSystem.playing("BgMusic")) {
                        soundSystem.stop("BgMusic");
                    }
                }

                if (  (!soundSystem.playing("BgMusic") || Config.config.mainMenuThemeOverridesBGM)
                   && !soundSystem.playing("streaming")
                   ) {
                    soundSystem.backgroundMusic("streaming", var1.soundUrl, var1.soundName, false);
                    soundSystem.setVolume("streaming", minecraft.options.music);
                    soundSystem.play("streaming");
                }
            }
        }
    }
}
