package com.github.telvarost.quickadditions.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.SoundHelper;
import net.minecraft.client.sound.SoundMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import paulscode.sound.SoundSystem;

@Environment(EnvType.CLIENT)
@Mixin(SoundHelper.class)
public interface SoundHelperAccessor {

    /** - Getters */
    @Accessor("soundSystem")
    SoundSystem getSoundSystem();
    @Accessor("streaming")
    SoundMap getStreaming();
    @Accessor("initialized")
    boolean getInitialized();

    /** - Setters */
    @Accessor("musicCountdown")
    public void setMusicCountdown(int musicCountdown);
}
