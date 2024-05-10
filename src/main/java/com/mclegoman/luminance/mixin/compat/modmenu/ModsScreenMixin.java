/*
    Luminance
    Author: MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.mixin.compat.modmenu;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.client.util.CompatHelper;
import com.mclegoman.luminance.common.data.Data;
import com.terraformersmc.modmenu.util.DrawingUtil;
import com.terraformersmc.modmenu.util.mod.Mod;
import com.terraformersmc.modmenu.util.mod.ModBadgeRenderer;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(priority = 100, value = ModBadgeRenderer.class)
public abstract class ModsScreenMixin {
	@Shadow(remap = false) protected int badgeX;
	@Shadow(remap = false) protected int badgeMax;
	@Shadow(remap = false) protected int badgeY;
	@Shadow(remap = false) protected Mod mod;
	@Inject(method = "draw", at = @At("RETURN"), remap = false)
	private void luminance$draw(DrawContext DrawContext, int mouseX, int mouseY, CallbackInfo ci) {
		if (CompatHelper.getLuminanceModMenuBadge(this.mod.getId())) {
			int width = ClientData.minecraft.textRenderer.getWidth(Translation.getTranslation(Data.version.getID(), "name")) + 6;
			if (badgeX + width < badgeMax) {
				DrawingUtil.drawBadge(DrawContext, badgeX, badgeY, width, Translation.getTranslation(Data.version.getID(), "name").asOrderedText(), 0xFFFF8F8F, 0xFFB73A3A, 0xFFFFFF);
				badgeX += width + 3;
			}
		}
	}
}
