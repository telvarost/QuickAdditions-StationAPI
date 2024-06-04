package com.github.telvarost.quickadditions.mixin;

import com.github.telvarost.quickadditions.PlayerInterface;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerBase.class)
public abstract class PlayerBaseMixin extends Living implements PlayerInterface {
    public PlayerBaseMixin(Level arg) {
        super(arg);
    }

    @Override
    public void player_setMovementSpeed(float movementSpeed2) {
        movementSpeed = movementSpeed2;
    }
}
