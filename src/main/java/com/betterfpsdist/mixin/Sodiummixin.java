package com.betterfpsdist.mixin;

import com.betterfpsdist.BetterfpsdistMod;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSection;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSectionManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.spongepowered.asm.mixin.injection.At.Shift.AFTER;

@Mixin(RenderSectionManager.class)
public class Sodiummixin
{
    @Shadow(remap = false)
    private float cameraX;

    @Shadow(remap = false)
    private float cameraY;

    @Shadow(remap = false)
    private float cameraZ;

    @Inject(method = "addVisible", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/render/chunk/graph/ChunkGraphIterationQueue;add(Lme/jellysquid/mods/sodium/client/render/chunk/RenderSection;Lnet/minecraft/util/math/Direction;)V", shift = AFTER), remap = false, cancellable = true)
    private void isWithinRenderDistance(final RenderSection render, final Direction flow, final CallbackInfo ci)
    {
        if (distSqr(render.getOriginX(), render.getOriginY(), render.getOriginZ(), cameraX, cameraY, cameraZ) > (MinecraftClient.getInstance().options.viewDistance * 16) * (
          MinecraftClient.getInstance().options.viewDistance * 16))
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
