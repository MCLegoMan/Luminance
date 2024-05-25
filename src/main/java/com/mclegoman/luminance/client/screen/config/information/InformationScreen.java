/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.screen.config.information;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.logo.LuminanceLogo;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.Couple;
import com.mclegoman.luminance.common.util.LogType;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class InformationScreen extends Screen {
	private final Screen parentScreen;
	private final GridWidget grid;
	private boolean refresh;
	private boolean shouldClose;
	private boolean shouldRenderSplashText;
	private Couple<String, Boolean> splashText;
	private final boolean isPride;
	public InformationScreen(Screen parent, boolean refresh, Couple<String, Boolean> splashText, boolean isPride) {
		super(Text.literal(""));
		this.grid = new GridWidget();
		this.parentScreen = parent;
		this.refresh = refresh;
		if (splashText != null) {
			this.splashText = splashText;
			this.shouldRenderSplashText = true;
		}
		this.isPride = isPride;
	}
	public void init() {
		try {
			grid.getMainPositioner().alignHorizontalCenter().margin(0);
			GridWidget.Adder gridAdder = grid.createAdder(1);
			gridAdder.add(new LuminanceLogo.Widget(shouldRenderSplashText, splashText, isPride));
			gridAdder.add(createConfig());
			gridAdder.add(new EmptyWidget(4, 4));
			gridAdder.add(createFooter());
			grid.refreshPositions();
			grid.forEachChild(this::addDrawableChild);
			initTabNavigation();
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to initialize config screen: {}", error));
		}
	}
	public void tick() {
		try {
			if (this.refresh) {
				ClientData.minecraft.setScreen(new InformationScreen(parentScreen, false, this.splashText, this.isPride));
			}
			if (this.shouldClose) {
				ClientData.minecraft.setScreen(parentScreen);
			}
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to tick luminance$config screen: {}", error));
		}
	}
	private GridWidget createConfig() {
		GridWidget grid = new GridWidget();
		grid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder gridAdder = grid.createAdder(2);
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "information.documentation"), ConfirmLinkScreen.opening(this, "https://mclegoman.com/Luminance")).build(), 1);
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "information.source_code"), ConfirmLinkScreen.opening(this, "https://github.com/MCLegoMan/Luminance")).build(), 1);
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "information.report"), ConfirmLinkScreen.opening(this, "https://github.com/MCLegoMan/Luminance/issues")).build(), 1);
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "information.credits"), button -> ClientData.minecraft.setScreen(new CreditsAttributionScreen(ClientData.minecraft.currentScreen, isPride))).build(), 1);
		return grid;
	}
	private GridWidget createFooter() {
		GridWidget grid = new GridWidget();
		grid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder GRID_ADDER = grid.createAdder(1);
		GRID_ADDER.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "back"), (button) -> this.shouldClose = true).build());
		return grid;
	}
	public void initTabNavigation() {
		SimplePositioningWidget.setPos(grid, getNavigationFocus());
	}
	public Text getNarratedTitle() {
		return ScreenTexts.joinSentences();
	}
	public boolean shouldCloseOnEsc() {
		return false;
	}
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == GLFW.GLFW_KEY_ESCAPE) this.shouldClose = true;
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		super.render(context, mouseX, mouseY, delta);
	}
}