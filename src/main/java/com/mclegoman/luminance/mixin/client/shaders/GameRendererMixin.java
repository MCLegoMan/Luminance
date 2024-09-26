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
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.Pool;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = GameRenderer.class)
public abstract class GameRendererMixin {
	@Shadow @Final private Pool pool;
	@Inject(method = "render", at = @At("HEAD"))
	private void luminance$beforeGameRender(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
		Events.BeforeGameRender.registry.forEach(((id, runnable) -> {
			try {
				runnable.run();
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute AfterGameRender event with id: {}:{}:", id, error));
			}
		}));
	}
	@Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gl/Framebuffer;beginWrite(Z)V"))
	private void luminance$afterHandRender(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
		Events.AfterHandRender.registry.forEach(((id, runnable) -> {
			try {
				runnable.run(ClientData.minecraft.getFramebuffer(), this.pool);
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute AfterHandRender event with id: {}:{}:", id, error));
			}
		}));
	}
	@Inject(method = "render", at = @At("TAIL"))
	private void luminance$afterGameRender(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
		Events.AfterGameRender.registry.forEach(((id, runnable) -> {
			try {
				runnable.run(ClientData.minecraft.getFramebuffer(), this.pool);
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute AfterGameRender event with id: {}:{}:", id, error));
			}
		}));
	}
	@Inject(method = "onResized", at = @At(value = "TAIL"))
	private void luminance$onResized(int width, int height, CallbackInfo ci) {
		Events.OnResized.registry.forEach((id, runnable) -> runnable.run(width, height));
	}
}