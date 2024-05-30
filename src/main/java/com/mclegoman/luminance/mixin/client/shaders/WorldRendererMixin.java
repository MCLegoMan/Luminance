/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.client.shaders;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.shaders.Shaders;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.LogType;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.render.*;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = WorldRenderer.class)
public abstract class WorldRendererMixin {
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/PostEffectProcessor;render(F)V", ordinal = 0), method = "render")
	public void luminance$saveDepthOutlines(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
		Shaders.depthFramebuffer.copyDepthFrom(ClientData.minecraft.getFramebuffer());
		ClientData.minecraft.getFramebuffer().beginWrite(false);
	}
	@Inject(at = {
			@At(value = "INVOKE", target = "Lnet/minecraft/client/gl/PostEffectProcessor;render(F)V", ordinal = 1),
			@At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderWorldBorder(Lnet/minecraft/client/render/Camera;)V", ordinal = 1)
	}, method = "render")
	public void luminance$saveDepth(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
		Shaders.depthFramebuffer.copyDepthFrom(ClientData.minecraft.getFramebuffer());
		if (ClientData.minecraft.options.getGraphicsMode().getValue().getId() <= GraphicsMode.FANCY.getId()) ClientData.minecraft.getFramebuffer().beginWrite(false);
	}
	@Inject(method = "render", at = @At("HEAD"))
	private void luminance$beforeRender(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
		Events.BeforeWorldRender.registry.forEach(((id, runnable) -> {
			try {
				runnable.run();
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute BeforeWorldRender event with id: {}:{}:", id.getFirst(), id.getSecond(), error));
			}
		}));
	}
	@Inject(at = {
			@At(value = "INVOKE", target = "Lnet/minecraft/client/gl/PostEffectProcessor;render(F)V", ordinal = 1, shift = At.Shift.AFTER),
			@At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderWorldBorder(Lnet/minecraft/client/render/Camera;)V", ordinal = 1, shift = At.Shift.AFTER)
	}, method = "render")
	private void luminance$afterWorldBorder(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
		Events.AfterWorldBorder.registry.forEach(((id, runnable) -> {
			try {
				runnable.run();
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute AfterWorldBorder event with id: {}:{}:", id.getFirst(), id.getSecond(), error));
			}
		}));
	}
	@Inject(method = "render", at = @At("TAIL"))
	private void luminance$afterRender(RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo ci) {
		Events.AfterWorldRender.registry.forEach(((id, runnable) -> {
			try {
				runnable.run();
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute AfterWorldRender event with id: {}:{}:", id.getFirst(), id.getSecond(), error));
			}
		}));
	}
}