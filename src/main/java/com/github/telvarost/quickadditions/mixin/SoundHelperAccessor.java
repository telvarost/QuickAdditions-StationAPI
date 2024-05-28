package com.github.telvarost.quickadditions.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.SoundHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(SoundHelper.class)
public interface SoundHelperAccessor {
    @Accessor("musicCountdown")
    public void setMusicCountdown(int musicCountdown);
}
