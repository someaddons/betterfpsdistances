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

    @Inject(method = "isSectionVisible", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
    private void adjustDist(
      final int x, final int y, final int z, final CallbackInfoReturnable<Boolean> cir)
    {
        if (Minecraft.getInstance().player == null)
        {
            return;
        }

        final BlockPos pos = Minecraft.getInstance().player.blockPosition();
        int xDiff = x- (pos.getX() >> 4);
        int yDiff = y - (pos.getY() >> 4);
        int zDiff = z - (pos.getZ() >> 4);
        cir.setReturnValue(xDiff * xDiff + BetterfpsdistMod.config.getCommonConfig().stretch * (yDiff * yDiff) + zDiff * zDiff
                             > renderDistance * renderDistance);
    }
}
