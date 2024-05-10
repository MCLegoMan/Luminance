/*
    Luminance
    Contributor(s): MCLegoMan, Nettakrim
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.client.shaders;

import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.LogType;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = GameRenderer.class)
public abstract class GameRendererMixin {
	@Inject(method = "render", at = @At("TAIL"))
	private void luminance$afterGameRender(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
		Events.AfterGameRender.registry.forEach(((id, runnable) -> {
			try {
				runnable.run();
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute AfterGameRender event with id: {}:{}:", id.getFirst(), id.getSecond(), error));
			}
		}));
	}
	@Inject(method = "onResized", at = @At(value = "TAIL"))
	private void luminance$onResized(int width, int height, CallbackInfo ci) {
		Events.ShaderRender.registry.forEach((id, shaders) -> {
			if (shaders != null) shaders.forEach(shader -> {
				try {
					shader.getSecond().getPostProcessor().setupDimensions(width, height);
				} catch (Exception error) {
					Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to resize shader with id: {}:{}:", id.getFirst(), id.getSecond(), error));
				}
			});
		});
	}
}