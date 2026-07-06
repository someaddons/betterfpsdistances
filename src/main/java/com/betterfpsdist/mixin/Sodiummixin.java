package com.betterfpsdist.mixin;

import com.betterfpsdist.BetterfpsdistMod;
import com.betterfpsdist.event.ClientEventHandler;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;
import net.caffeinemc.mods.sodium.client.render.chunk.occlusion.OcclusionCuller;
import net.caffeinemc.mods.sodium.client.util.collections.WriteQueue;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OcclusionCuller.class)
public class Sodiummixin
{
    @Unique
    private RenderSection section = null;

    @Inject(method = "visitNode", at = @At("HEAD"))
    private void storeSection(
        final WriteQueue<RenderSection> queue,
        final RenderSection section,
        final int outgoingDirection,
        final boolean hasLocalPath,
        final boolean hasRegularPath,
        final boolean hasWidePath,
        final CallbackInfo ci)
    {
        this.section = section;
    }

    @WrapOperation(method = "visitNode", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/occlusion/OcclusionCuller;testDistance(FFF)Z"), remap = false)
    private boolean betterfps$renderdistance(
        final float xzThreshold, final float yThreshold, final float maxDistance, final Operation<Boolean> original)
    {
        if (Minecraft.getInstance().player != null)
        {
            if (ClientEventHandler.adjustedDistance(section.getOriginX(),
                section.getOriginY(),
                section.getOriginZ(),
                Minecraft.getInstance().player.getX(),
                Minecraft.getInstance().player.getY(),
                Minecraft.getInstance().player.getZ())
                > ClientEventHandler.maxSqDist)
            {
                if (BetterfpsdistMod.config.getCommonConfig().debugMode)
                {
                    ClientEventHandler.hiddenSections.add(new BlockPos(section.getOriginX(), section.getOriginY(), section.getOriginZ()));
                }
                return false;
            }
        }
        return original.call(xzThreshold, yThreshold, maxDistance);
    }
}
