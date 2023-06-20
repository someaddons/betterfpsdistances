package com.betterfpsdist.mixin;

import com.betterfpsdist.BetterfpsdistMod;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSection;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSectionManager;
import me.jellysquid.mods.sodium.client.render.chunk.graph.ChunkGraphIterationQueue;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSectionManager.class)
public class Sodiummixin
{
    @Shadow(remap = false)
    private float cameraX;

    @Shadow(remap = false)
    private float cameraY;

    @Shadow(remap = false)
    private float cameraZ;

    @Shadow(remap = false)
    @Final
    private ChunkGraphIterationQueue iterationQueue;

    @Inject(method = "addVisible", at = @At(value = "HEAD"), remap = false, cancellable = true)
    private void isWithinRenderDistance(final RenderSection render, final Direction flow, final CallbackInfo ci)
    {
        iterationQueue.add(render, flow);
        if (distSqr(render.getOriginX(), render.getOriginY(), render.getOriginZ(), cameraX, cameraY, cameraZ)
              > (Minecraft.getInstance().options.renderDistance().get() * 16) * (
          Minecraft.getInstance().options.renderDistance().get() * 16))
        {
            ci.cancel();
        }
    }

    private double distSqr(float fromX, float fromY, float fromZ, float toX, float toY, float toZ)
    {
        double d0 = fromX - toX;
        double d1 = fromY - toY;
        double d2 = fromZ - toZ;
        return d0 * d0 + BetterfpsdistMod.config.getCommonConfig().stretch * (d1 * d1) + d2 * d2;
    }
}
