package com.betterfpsdist.mixin;

import com.betterfpsdist.BetterfpsdistMod;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSection;
import me.jellysquid.mods.sodium.client.render.chunk.RenderSectionManager;
import me.jellysquid.mods.sodium.client.render.viewport.Viewport;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Unused for now
 */
@Mixin(RenderSectionManager.class)
public class SodiumRenderManagerMixin
{
    @Shadow(remap = false)
    @Final
    private int renderDistance;

    @Inject(method = "isOutsideViewport", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
    private void adjustDist(
      final RenderSection section,
      final Viewport viewport,
      final CallbackInfoReturnable<Boolean> cir)
    {
        if (Minecraft.getInstance().player == null)
        {
            return;
        }

        final BlockPos pos = Minecraft.getInstance().player.blockPosition();
        int xDiff = section.getChunkX() - (pos.getX() >> 4);
        int yDiff = section.getChunkY() - (pos.getY() >> 4);
        int zDiff = section.getChunkZ() - (pos.getZ() >> 4);
        cir.setReturnValue(xDiff * xDiff + BetterfpsdistMod.config.getCommonConfig().stretch * (yDiff * yDiff) + zDiff * zDiff
                             > renderDistance * renderDistance);
    }
}
