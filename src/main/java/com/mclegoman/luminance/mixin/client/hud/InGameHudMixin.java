/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.client.hud;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.util.MessageOverlay;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 0, value = GameRenderer.class)
public abstract class InGameHudMixin {
	@Shadow @Final private BufferBuilderStorage buffers;
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"), method = "render")
	private void luminance$render(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci) {
		if (!ClientData.minecraft.gameRenderer.isRenderingPanorama()) {
			DrawContext context = new DrawContext(ClientData.minecraft, this.buffers.getEntityVertexConsumers());
			float h = MessageOverlay.remaining - ClientData.minecraft.getRenderTickCounter().getTickDelta(true);
			int l = (int) (h * 255.0F / 20.0F);
			if (l > 255) l = 255;
			if (l > 10) {
				context.getMatrices().push();
				context.getMatrices().translate((float) (ClientData.minecraft.getWindow().getScaledWidth() / 2), 27, 0.0F);
				int k = 16777215;
				int m = l << 24 & -16777216;
				int n = ClientData.minecraft.textRenderer.getWidth(MessageOverlay.message);
				context.drawTextWithShadow(ClientData.minecraft.textRenderer, MessageOverlay.message, -n / 2, -4, k | m);
				context.getMatrices().pop();
			}
		}
	}
}