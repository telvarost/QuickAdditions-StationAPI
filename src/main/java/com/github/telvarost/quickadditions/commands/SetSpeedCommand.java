package com.github.telvarost.quickadditions.commands;

import com.github.telvarost.quickadditions.ModHelper;
import com.github.telvarost.quickadditions.mixin.LivingAccessor;
import com.matthewperiut.spc.api.Command;
import com.matthewperiut.spc.util.SharedCommandSource;
import net.minecraft.entity.player.PlayerBase;

public class SetSpeedCommand implements Command {
    @Override
    public void command(SharedCommandSource commandSource, String[] parameters) {


        PlayerBase player = commandSource.getPlayer();
        if (player != null) {
            if (parameters.length > 1) {
                float speed = Float.parseFloat(parameters[1]);
                System.out.println("Speed: " + speed);
                ModHelper.ModHelperFields.currentSpeed = speed;
                //player.setVelocity(speed, speed, speed);
//                player.player_setMovementSpeed(speed);
//                ((LivingAccessor)player).setMovementSpeed(speed);
//                ((LivingAccessor)player).setMoveForwardSpeed(speed);
//                ((LivingAccessor)player).setMoveStrafeSpeed(speed);
//                player.travel(speed, speed);
//                player.velocityX *= speed;
//                player.velocityY *= speed;
//                player.velocityZ *= speed;

            } else {
                manual(commandSource);
            }
        } else {
            commandSource.sendFeedback("You must be a player");
        }
    }

    @Override
    public String name() {
        return "setspeed";
    }

    @Override
    public void manual(SharedCommandSource commandSource) {
        commandSource.sendFeedback("Usage: /setspeed {speed}");
        commandSource.sendFeedback("Info: Changes living entity speed");
    }
}