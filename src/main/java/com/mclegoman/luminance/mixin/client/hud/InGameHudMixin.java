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
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 0, value = InGameHud.class)
public abstract class InGameHudMixin {
	@Inject(at = @At("RETURN"), method = "render")
	private void perspective$render(DrawContext context, float tickDelta, CallbackInfo ci) {
		if (!ClientData.minecraft.gameRenderer.isRenderingPanorama()) {
			float h = MessageOverlay.remaining - tickDelta;
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