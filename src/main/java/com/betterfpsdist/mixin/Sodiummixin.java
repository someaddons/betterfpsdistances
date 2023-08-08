package com.betterfpsdist.mixin;

import com.betterfpsdist.BetterfpsdistMod;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSection;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSectionManager;
import me.jellysquid.mods.sodium.client.render.chunk.lists.SortedRenderListBuilder;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSectionManager.class)
public class Sodiummixin
{
    @Inject(method = "addToRenderLists", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private void isWithinRenderDistance(
      final SortedRenderListBuilder renderListBuilder,
      final RenderSection section,
      final CallbackInfo ci)
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
                ci.cancel();
            }
        }
    }

    @Unique
    private double distSqr(float fromX, float fromY, float fromZ, double toX, double toY, double toZ)
    {
        double d0 = fromX - toX;
        double d1 = fromY - toY;
        double d2 = fromZ - toZ;
        return d0 * d0 + BetterfpsdistMod.config.getCommonConfig().stretch * (d1 * d1) + d2 * d2;
    }
}
