/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.screen.config;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.logo.LuminanceLogo;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.config.ConfigHelper;
import com.mclegoman.luminance.common.util.LogType;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ConfigScreen extends Screen {
	private final Screen parentScreen;
	private final GridWidget grid;
	private boolean refresh;
	private boolean shouldClose;
	private boolean saveConfig;
	public ConfigScreen(Screen parent, boolean refresh) {
		super(Text.literal(""));
		this.grid = new GridWidget();
		this.parentScreen = parent;
		this.refresh = refresh;
	}

	public void init() {
		try {
			grid.getMainPositioner().alignHorizontalCenter().margin(0);
			GridWidget.Adder gridAdder = grid.createAdder(1);
			gridAdder.add(new LuminanceLogo.Widget());
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
				ClientData.minecraft.setScreen(new ConfigScreen(parentScreen, false));
			}
			if (this.shouldClose) {
				if (this.saveConfig) ConfigHelper.saveConfig(true);
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
		gridAdder.add(new SliderWidget(gridAdder.getGridWidget().getX(), gridAdder.getGridWidget().getY(), 150, 20, Translation.getConfigTranslation(Data.version.getID(), "alpha", new Object[]{Text.literal((int) ConfigHelper.getConfig("alpha_level") + "%")}, false), (int)ConfigHelper.getConfig("alpha_level") / 100.0F) {
			@Override
			protected void updateMessage() {
				setMessage(Translation.getConfigTranslation(Data.version.getID(),  "alpha", new Object[]{Text.literal((int) ConfigHelper.getConfig("alpha_level") + "%")}, false));
			}
			@Override
			protected void applyValue() {
				ConfigHelper.setConfig("alpha_level", (int) ((value) * 100));
				saveConfig = true;
			}
		}, 1).setTooltip(Tooltip.of(Translation.getConfigTranslation(Data.version.getID(), "alpha", true)));
		gridAdder.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "alpha.show_overlay", new Object[]{Translation.getVariableTranslation(Data.version.getID(), "onff", (boolean) ConfigHelper.getConfig("show_alpha_level_overlay"))}), (button) -> {
			ConfigHelper.setConfig("show_alpha_level_overlay", !(boolean) ConfigHelper.getConfig("show_alpha_level_overlay"));
			this.refresh = true;
		}).build(), 1);
		return grid;
	}
	private GridWidget createFooter() {
		GridWidget grid = new GridWidget();
		grid.getMainPositioner().alignHorizontalCenter().margin(2);
		GridWidget.Adder GRID_ADDER = grid.createAdder(2);
		GRID_ADDER.add(ButtonWidget.builder(Translation.getConfigTranslation(Data.version.getID(), "reset"), (button) -> {
			if (com.mclegoman.luminance.config.ConfigHelper.resetConfig()) this.refresh = true;
		}).build());
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