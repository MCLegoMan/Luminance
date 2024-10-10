/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.client.shaders;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.Execute;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.gl.SimpleFramebufferFactory;
import net.minecraft.client.render.*;
import net.minecraft.client.util.Handle;
import net.minecraft.client.util.ObjectAllocator;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = WorldRenderer.class)
public abstract class WorldRendererMixin {
	@Unique private FrameGraphBuilder frameGraphBuilder;
	@Shadow @Final private DefaultFramebufferSet framebufferSet;
	@Shadow @Nullable protected abstract PostEffectProcessor getTransparencyPostEffectProcessor();
	@Inject(method = "render", at = @At("HEAD"))
	private void luminance$beforeRender(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean bl, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
		Execute.beforeWorldRender();
	}
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/FrameGraphBuilder;createObjectNode(Ljava/lang/String;Ljava/lang/Object;)Lnet/minecraft/client/util/Handle;", ordinal = 0))
	private Handle<?> luminance$enableFramebuffers(FrameGraphBuilder frameGraphBuilder, String name, Object object) {
		Handle<Framebuffer> framebuffer = frameGraphBuilder.createObjectNode(name, (Framebuffer) object);
		if (getTransparencyPostEffectProcessor() == null) {
			SimpleFramebufferFactory simpleFramebufferFactory = new SimpleFramebufferFactory(ClientData.minecraft.getFramebuffer().textureWidth, ClientData.minecraft.getFramebuffer().textureHeight, true);
			this.framebufferSet.translucentFramebuffer = frameGraphBuilder.createResourceHandle("translucent", simpleFramebufferFactory);
			this.framebufferSet.itemEntityFramebuffer = frameGraphBuilder.createResourceHandle("item_entity", simpleFramebufferFactory);
			this.framebufferSet.particlesFramebuffer = frameGraphBuilder.createResourceHandle("particles", simpleFramebufferFactory);
			this.framebufferSet.weatherFramebuffer = frameGraphBuilder.createResourceHandle("weather", simpleFramebufferFactory);
			this.framebufferSet.cloudsFramebuffer = frameGraphBuilder.createResourceHandle("clouds", simpleFramebufferFactory);
		}
		this.frameGraphBuilder = frameGraphBuilder;
		return framebuffer;
	}
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderWeather(Lnet/minecraft/client/render/FrameGraphBuilder;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Vec3d;FLnet/minecraft/client/render/Fog;)V", shift = At.Shift.AFTER))
	private void luminance$afterRenderWeather(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
		if (this.frameGraphBuilder != null) Execute.afterWeatherRender(this.frameGraphBuilder, this.framebufferSet);
	}
	@Inject(method = "render", at = @At("TAIL"))
	private void luminance$afterRender(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean bl, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
		Execute.afterWorldRender(allocator);
	}
}