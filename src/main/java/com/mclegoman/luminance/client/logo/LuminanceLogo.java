/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.logo;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.Couple;
import com.mclegoman.luminance.common.util.DateHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class LuminanceLogo {
	public static Logo getLogo() {
		return getLogo(DateHelper.isPride());
	}
	public static Logo getLogo(boolean isPride) {
		return new Logo(new Identifier(Data.version.getID(), Data.version.getID()), isPride ? "pride" : "normal");
	}
	public static void renderLogo(DrawContext context, int x, int y, int width, int height, boolean isPride) {
		context.drawTexture(getLogo(isPride).getTexture(), x, y, 0.0F, 0.0F, width, (int) (height * 0.6875), width, height);
		LogoHelper.renderDevelopmentOverlay(context, (int) ((x + (width / 2)) - ((width * 0.75F) / 2)), (int) (y + (height - (height * 0.45F))), width, height, Data.version.isDevelopmentBuild(), 0, 0);
	}
	public static void renderLogo(DrawContext context, int x, int y, int width, int height) {
		renderLogo(context, x, y, width, height, DateHelper.isPride());
	}
	public Logo Logo(Identifier id) {
		return new Logo(id, "");
	}
	public record Logo(Identifier id, String type) {
		public String getNamespace() {
			return this.id.getNamespace();
		}
		public String getName() {
			return this.id.getPath();
		}
		public String getType() {
			return this.type;
		}
		public Identifier getTexture() {
			return new Identifier(getNamespace(), "textures/gui/logo/" + this.type + (this.type.endsWith("/") || this.type.equals("") ? "" : "/") + getName() + ".png");
		}
	}
	public static class Widget extends ClickableWidget {
		private final boolean shouldRenderSplashText;
		private final Couple<String, Boolean> splashText;
		private final boolean isPride;
		public Widget(boolean shouldRenderSplashText, Couple<String, Boolean> splashText, boolean isPride) {
			super(0, 0, 256, 64, Text.empty());
			this.shouldRenderSplashText = shouldRenderSplashText;
			this.splashText = splashText;
			this.isPride = isPride;
		}
		public Widget(boolean shouldRenderSplashText, Couple<String, Boolean> splashText) {
			this (shouldRenderSplashText, splashText, DateHelper.isPride());
		}
		public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
			renderLogo(context, this.getX(), this.getY(), this.getWidth(), this.getHeight(), isPride);
			if (shouldRenderSplashText) LogoHelper.createSplashText(context, this.getWidth(), this.getX(), this.getY() + 32, ClientData.minecraft.textRenderer, splashText, -20.0F);
		}
		@Override
		protected void appendClickableNarrations(NarrationMessageBuilder builder) {
		}
		@Override
		protected boolean isValidClickButton(int button) {
			return false;
		}
	}
}
