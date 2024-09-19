/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.client.shaders;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.LogType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.gl.SimpleFramebufferFactory;
import net.minecraft.client.render.*;
import net.minecraft.client.util.ObjectAllocator;
import org.jetbrains.annotations.Nullable;
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

	@Shadow @Nullable protected abstract PostEffectProcessor getTransparencyPostEffectProcessor();

	@Inject(method = "render", at = @At("HEAD"))
	private void luminance$beforeRender(ObjectAllocator objectAllocator, RenderTickCounter tickCounter, boolean bl, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
		Events.BeforeWorldRender.registry.forEach(((id, runnable) -> {
			try {
				runnable.run();
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute BeforeWorldRender event with id: {}:{}:", id, error));
			}
		}));
	}
	@Inject(method = "render", at = @At("TAIL"))
	private void luminance$afterRender(ObjectAllocator objectAllocator, RenderTickCounter tickCounter, boolean bl, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
		Events.AfterWorldRender.registry.forEach(((id, runnable) -> {
			try {
				runnable.run();
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute AfterWorldRender event with id: {}:{}:", id, error));
			}
		}));
	}
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/FrameGraphBuilder;createPass(Ljava/lang/String;)Lnet/minecraft/client/render/RenderPass;", ordinal = 0))
	private void luminance$enableFramebuffers(ObjectAllocator objectAllocator, RenderTickCounter tickCounter, boolean bl, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
//		// We need to set the framebuffers if the fabulous shader isn't also being rendered.
//		if (!MinecraftClient.isFabulousGraphicsOrBetter() || this.getTransparencyPostEffectProcessor() == null) {
//			FrameGraphBuilder frameGraphBuilder = new FrameGraphBuilder();
//			this.framebufferSet.mainFramebuffer = frameGraphBuilder.createObjectNode("main", ClientData.minecraft.getFramebuffer());
//			int width = ClientData.minecraft.getFramebuffer().textureWidth;
//			int height = ClientData.minecraft.getFramebuffer().textureHeight;
//			SimpleFramebufferFactory simpleFramebufferFactory = new SimpleFramebufferFactory(width, height, true);
//			this.framebufferSet.translucentFramebuffer = frameGraphBuilder.createResourceHandle("translucent", simpleFramebufferFactory);
//			this.framebufferSet.itemEntityFramebuffer = frameGraphBuilder.createResourceHandle("item_entity", simpleFramebufferFactory);
//			this.framebufferSet.particlesFramebuffer = frameGraphBuilder.createResourceHandle("particles", simpleFramebufferFactory);
//			this.framebufferSet.weatherFramebuffer = frameGraphBuilder.createResourceHandle("weather", simpleFramebufferFactory);
//			this.framebufferSet.cloudsFramebuffer = frameGraphBuilder.createResourceHandle("clouds", simpleFramebufferFactory);
//		}
	}
}