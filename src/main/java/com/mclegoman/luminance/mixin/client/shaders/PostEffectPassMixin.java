/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.client.shaders;

import com.mclegoman.luminance.client.events.Events;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectPass;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.util.Handle;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(priority = 100, value = PostEffectPass.class)
public abstract class PostEffectPassMixin {
	@Shadow @Final private ShaderProgram program;
	@Inject(method = "render", at = @At("HEAD"))
	private void luminance$beforeRender(FrameGraphBuilder frameGraphBuilder, Map<Identifier, Handle<Framebuffer>> map, Matrix4f matrix4f, CallbackInfo ci) {
		Events.BeforeShaderRender.registry.forEach(((id, runnable) -> runnable.run(program)));
	}
	@Inject(method = "render", at = @At("TAIL"))
	private void luminance$afterRender(FrameGraphBuilder frameGraphBuilder, Map<Identifier, Handle<Framebuffer>> map, Matrix4f matrix4f, CallbackInfo ci) {
		Events.AfterShaderRender.registry.forEach(((id, runnable) -> runnable.run(program)));
	}
}