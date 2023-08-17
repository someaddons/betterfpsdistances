package com.betterfpsdist.mixin;

import com.betterfpsdist.BetterfpsdistMod;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSection;
import me.jellysquid.mods.sodium.client.render.chunk.occlusion.OcclusionCuller;
import me.jellysquid.mods.sodium.client.render.viewport.CameraTransform;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OcclusionCuller.class)
public class Sodiummixin
{
    @Inject(method = "isOutsideRenderDistance", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private static void isWithinRenderDistance(
      final CameraTransform camera, final RenderSection section, final int maxDistance, final CallbackInfoReturnable<Boolean> cir)
    {
        if (Minecraft.getInstance().player != null)
        {
            if (distSqr(section.getOriginX(),
              section.getOriginY(),
              section.getOriginZ(),
              Minecraft.getInstance().player.getX(),
              Minecraft.getInstance().player.getY(),
              Minecraft.getInstance().player.getZ())
                  > (Minecraft.getInstance().options.renderDistance().get() * 16) * (
              Minecraft.getInstance().options.renderDistance().get() * 16))
            {
                cir.setReturnValue(true);
            }
        }
    }

    @Unique
    private static double distSqr(float fromX, float fromY, float fromZ, double toX, double toY, double toZ)
    {
        double d0 = fromX - toX;
        double d1 = fromY - toY;
        double d2 = fromZ - toZ;
        return d0 * d0 + BetterfpsdistMod.config.getCommonConfig().stretch * (d1 * d1) + d2 * d2;
    }
}
