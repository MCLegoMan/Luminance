/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.client.shaders;

import com.google.gson.JsonElement;
import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.shaders.Shaders;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = PostEffectProcessor.class)
public abstract class PostEffectProcessorMixin {
	@Inject(at = @At(value = "INVOKE", target = "Ljava/lang/String;substring(II)Ljava/lang/String;"), method = "parsePass")
	public void luminance$detectDepth(TextureManager textureManager, JsonElement jsonPass, CallbackInfo ci) {
		Events.ShaderRender.registry.forEach((id, shaders) -> {
			shaders.forEach(shader -> {
				shader.getSecond().setUseDepth(true);
			});
		});
	}
	@Inject(at = @At(value = "HEAD"), method = "render")
	public void luminance$fixDepth(float tickDelta, CallbackInfo ci) {
		ClientData.minecraft.getFramebuffer().copyDepthFrom(Shaders.depthFramebuffer);
	}
}