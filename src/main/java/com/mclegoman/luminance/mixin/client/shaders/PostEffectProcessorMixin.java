/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.client.shaders;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.shaders.Shaders;
import net.minecraft.client.gl.*;
import net.minecraft.client.util.ObjectAllocator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = PostEffectProcessor.class)
public abstract class PostEffectProcessorMixin {
//	@Inject(at = @At(value = "INVOKE", target = "Ljava/lang/String;substring(II)Ljava/lang/String;"), method = "parsePass")
//	private static void luminance$detectDepth(TextureManager textureManager, ShaderLoader shaderLoader, PostEffectPipeline.Pass pass, CallbackInfoReturnable<PostEffectPass> cir) {
//		Events.ShaderRender.registry.forEach((id, shaders) -> shaders.forEach(shader -> shader.getSecond().setUseDepth(true)));
//	}
	@Inject(at = @At(value = "HEAD"), method = "render")
	public void luminance$fixDepth(Framebuffer framebuffer, ObjectAllocator objectAllocator, CallbackInfo ci) {
		ClientData.minecraft.getFramebuffer().copyDepthFrom(Shaders.depthFramebuffer);
	}
}