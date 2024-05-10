/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.client.shaders;

import com.mclegoman.luminance.client.events.Events;
import net.minecraft.client.gl.JsonEffectShaderProgram;
import net.minecraft.client.gl.PostEffectPass;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = PostEffectPass.class)
public abstract class PostEffectPassMixin {
	@Shadow @Final private JsonEffectShaderProgram program;
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/JsonEffectShaderProgram;enable()V"))
	private void luminance$beforeRender(float time, CallbackInfo ci) {
		Events.BeforeShaderRender.registry.forEach(((id, runnable) -> runnable.run(program)));
	}
	@Inject(method = "render", at = @At(value = "TAIL"))
	private void luminance$afterRender(float time, CallbackInfo ci) {
		Events.AfterShaderRender.registry.forEach(((id, runnable) -> runnable.run(program)));
	}
}