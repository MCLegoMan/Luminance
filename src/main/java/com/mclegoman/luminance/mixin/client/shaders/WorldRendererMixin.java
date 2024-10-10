/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.client.shaders;

import com.mclegoman.luminance.client.events.Execute;
import net.minecraft.client.render.*;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = WorldRenderer.class)
public abstract class WorldRendererMixin {
	@Shadow @Final private DefaultFramebufferSet framebufferSet;
	@Inject(method = "render", at = @At("HEAD"))
	private void luminance$beforeRender(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean bl, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
		Execute.beforeWorldRender();
	}
	@Inject(method = "renderWeather", at = @At("RETURN"))
	private void luminance$afterWeather(FrameGraphBuilder frameGraphBuilder, LightmapTextureManager lightmapTextureManager, Vec3d pos, float tickDelta, Fog fog, CallbackInfo ci) {
		Execute.afterWeatherRender(frameGraphBuilder, framebufferSet);
	}
	@Inject(method = "render", at = @At("TAIL"))
	private void luminance$afterRender(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean bl, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
		Execute.afterWorldRender(allocator);
	}
}