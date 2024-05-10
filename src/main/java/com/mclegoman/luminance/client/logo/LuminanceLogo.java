/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.logo;

import com.mclegoman.luminance.client.util.CompatHelper;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.Couple;
import com.mclegoman.luminance.common.util.DateHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class LuminanceLogo {
	public static void init() {
		CompatHelper.addOverrideModMenuIcon(new Couple<>(Data.version.getID(), "pride"), "assets/" + Data.version.getID() + "/icon_pride.png", DateHelper::isPride);
	}
	public static Logo getLogo() {
		return new Logo(new Identifier(Data.version.getID(), Data.version.getID()), DateHelper.isPride() ? "pride" : "normal");
	}
	public static void renderLogo(DrawContext context, int x, int y, int width, int height) {
		context.drawTexture(getLogo().getTexture(), x, y, 0.0F, 0.0F, width, (int) (height * 0.6875), width, height);
		LogoHelper.renderDevelopmentOverlay(context, (int) ((x + (width / 2)) - ((width * 0.75F) / 2)), (int) (y + (height - (height * 0.45F))), width, height, Data.version.isDevelopmentBuild(), 0, 0);
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
		public Widget() {
			super(0, 0, 256, 64, Text.empty());
		}
		public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
			renderLogo(context, this.getX(), this.getY() + 7, this.getWidth(), this.getHeight());
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
