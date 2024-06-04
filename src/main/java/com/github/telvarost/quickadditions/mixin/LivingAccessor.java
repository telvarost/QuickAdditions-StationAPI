package com.github.telvarost.quickadditions.mixin;

import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Living.class)
public interface LivingAccessor {
    @Accessor("movementSpeed")
    public void setMovementSpeed(float movementSpeed);
    @Accessor("field_1060")
    public void setMoveForwardSpeed(float field_1060);
    @Accessor("field_1029")
    public void setMoveStrafeSpeed(float field_1029);
//    protected float field_1060;
//    protected float field_1029;
}
