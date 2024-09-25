/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.shaders;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.keybindings.Keybindings;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.client.util.Accessors;
import com.mclegoman.luminance.client.util.LuminanceIdentifier;
import com.mclegoman.luminance.client.util.MessageOverlay;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.luminance.config.ConfigHelper;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Formatting;

import java.util.List;

public class Uniforms {
	private static float prevTimeTickDelta;
	private static float time = 0.0F;
	private static int prevAlpha = (int)ConfigHelper.getConfig("alpha_level");
	public static void tick() {
		if (!updatingAlpha() && updatingAlpha) {
			updatingAlpha = false;
			if ((int)ConfigHelper.getConfig("alpha_level") != prevAlpha) ConfigHelper.saveConfig(true);
		}
		Events.ShaderUniform.registry.forEach((id, uniform) -> uniform.tick(ClientData.minecraft.getRenderTickCounter().getTickDelta(true)));
	}
	public static void init() {
		try {
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "viewDistance"), Uniforms::getViewDistance);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "fov"), Uniforms::getFov);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "fps"), Uniforms::getFps);
			// Time Uniform should be updated to be customizable.
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "time"), Uniforms::getTime);
			//Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "eyePosition"), Uniforms::getEyePosition);
			//Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "position"), Uniforms::getPosition);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "pitch"), Uniforms::getPitch);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "yaw"), Uniforms::getYaw);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "currentHealth"), Uniforms::getCurrentHealth);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "maxHealth"), Uniforms::getMaxHealth);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "currentAbsorption"), Uniforms::getCurrentAbsorption);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "maxAbsorption"), Uniforms::getMaxAbsorption);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "currentHurtTime"), Uniforms::getCurrentHurtTime);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "maxHurtTime"), Uniforms::getMaxHurtTime);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "currentAir"), Uniforms::getCurrentAir);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "maxAir"), Uniforms::getMaxAir);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "isSprinting"), Uniforms::getIsSprinting);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "isSwimming"), Uniforms::getIsSwimming);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "isSneaking"), Uniforms::getIsSneaking);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "isCrawling"), Uniforms::getIsCrawling);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "isInvisible"), Uniforms::getIsInvisible);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "isWithered"), (tickDelta) -> Uniforms.getHasEffect(StatusEffects.WITHER));
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "isPoisoned"), (tickDelta) -> Uniforms.getHasEffect(StatusEffects.POISON));
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "isBurning"), Uniforms::getIsBurning);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "isOnGround"), Uniforms::getIsOnGround);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "isOnLadder"), Uniforms::getIsOnLadder);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "isRiding"), Uniforms::getIsRiding);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "hasPassengers"), Uniforms::getHasPassengers);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "biomeTemperature"), Uniforms::getBiomeTemperature);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "alpha"), Uniforms::getAlpha);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "perspective"), Uniforms::getPerspective);
			Events.ShaderUniform.register(LuminanceIdentifier.of(Data.version.getID(), "selectedSlot"), Uniforms::getSelectedSlot);
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to initialize uniforms: {}", error));
		}
	}
	public static float getViewDistance(float tickDelta) {
		return ClientData.minecraft.options != null ? ClientData.minecraft.options.getViewDistance().getValue() : 12.0F;
	}
	public static float getFov(float tickDelta) {
		return Accessors.getGameRenderer() != null ? Accessors.getGameRenderer().invokeGetFov(ClientData.minecraft.gameRenderer.getCamera(), tickDelta, false) : 70.0F;
	}
	public static float getFps(float tickDelta) {
		return ClientData.minecraft.getCurrentFps();
	}
	public static float getTime(float tickDelta) {
		// Ideally, lu_time/lu_timeSmooth should be customizable from post_effect/x.json, and if omitted, it would default to every 20 ticks (matching vanilla).
		// This would require Luminance to add a time variable for each pass, how big of a performance hit would this be?
		// If omitted, we could use the vanilla variable to help with performance.

		// Could we add something like this to the post/x.json and program/x.json files?
		// options {
		//     "luminance_time": {
		//         "type": "int",
		//         "value": 20,
		//     }
		// }
		if (tickDelta < prevTimeTickDelta) {
			time += 1.0F - prevTimeTickDelta;
			time += tickDelta;
		} else time += tickDelta - prevTimeTickDelta;
		prevTimeTickDelta = tickDelta;
		// This time should be customizable per shader, could we have a global float for amount of ticks and calculate each shaders ticks based on their max?
		while (time > 3456000.0F) time = 0.0F;
		return time / 3456000.0F;
	}
	public static List<Float> getEyePosition(float tickDelta) {
		return ClientData.minecraft.player != null ? List.of((float) ClientData.minecraft.player.getEyePos().x, (float) ClientData.minecraft.player.getEyePos().y, (float) ClientData.minecraft.player.getEyePos().z) : List.of(0.0F, 66.0F, 0.0F);
	}
	public static List<Float> getPosition(float tickDelta) {
		return ClientData.minecraft.player != null ? List.of((float) ClientData.minecraft.player.getPos().x, (float) ClientData.minecraft.player.getPos().y, (float) ClientData.minecraft.player.getPos().z) : List.of(0.0F, 64.0F, 0.0F);
	}
	public static float getPitch(float tickDelta) {
		return ClientData.minecraft.player != null ? ClientData.minecraft.player.getPitch(tickDelta) % 360.0F : 0.0F;
	}
	public static float getYaw(float tickDelta) {
		return ClientData.minecraft.player != null ? ClientData.minecraft.player.getYaw(tickDelta) % 360.0F : 0.0F;
	}
	public static float getCurrentHealth(float tickDelta) {
		return ClientData.minecraft.player != null ? ClientData.minecraft.player.getHealth() : 20.0F;
	}
	public static float getMaxHealth(float tickDelta) {
		return ClientData.minecraft.player != null ? ClientData.minecraft.player.getMaxHealth() : 20.0F;
	}
	public static float getCurrentAbsorption(float tickDelta) {
		return ClientData.minecraft.player != null ? ClientData.minecraft.player.getAbsorptionAmount() : 0.0F;
	}
	public static float getMaxAbsorption(float tickDelta) {
		return ClientData.minecraft.player != null ? ClientData.minecraft.player.getMaxAbsorption() : 0.0F;
	}
	public static float getCurrentHurtTime(float tickDelta) {
		return ClientData.minecraft.player != null ? ClientData.minecraft.player.hurtTime : 0.0F;
	}
	public static float getMaxHurtTime(float tickDelta) {
		return ClientData.minecraft.player != null ? ClientData.minecraft.player.maxHurtTime : 10.0F;
	}
	public static float getCurrentAir(float tickDelta) {
		return ClientData.minecraft.player != null ? ClientData.minecraft.player.getAir() : 10.0F;
	}
	public static float getMaxAir(float tickDelta) {
		return ClientData.minecraft.player != null ? ClientData.minecraft.player.getMaxAir() : 10.0F;
	}
	public static float getIsSprinting(float tickDelta) {
		return ClientData.minecraft.player != null ? (ClientData.minecraft.player.isSprinting() ? 1.0F : 0.0F) : 0.0F;
	}
	public static float getIsSwimming(float tickDelta) {
		return ClientData.minecraft.player != null ? (ClientData.minecraft.player.isSwimming() ? 1.0F : 0.0F) : 0.0F;
	}
	public static float getIsSneaking(float tickDelta) {
		return ClientData.minecraft.player != null ? (ClientData.minecraft.player.isSneaking() ? 1.0F : 0.0F) : 0.0F;
	}
	public static float getIsCrawling(float tickDelta) {
		return ClientData.minecraft.player != null ? (ClientData.minecraft.player.isCrawling() ? 1.0F : 0.0F) : 0.0F;
	}
	public static float getIsInvisible(float tickDelta) {
		return ClientData.minecraft.player != null ? (ClientData.minecraft.player.isInvisible() ? 1.0F : 0.0F) : 0.0F;
	}
	public static float getHasEffect(RegistryEntry<StatusEffect> statusEffect) {
		return ClientData.minecraft.player != null ? (ClientData.minecraft.player.hasStatusEffect(statusEffect) ? 1.0F : 0.0F) : 0.0F;
	}
	public static float getIsBurning(float tickDelta) {
		return ClientData.minecraft.player != null ? (ClientData.minecraft.player.isOnFire() ? 1.0F : 0.0F) : 0.0F;
	}
	public static float getIsOnGround(float tickDelta) {
		return ClientData.minecraft.player != null ? (ClientData.minecraft.player.isOnGround() ? 1.0F : 0.0F) : 1.0F;
	}
	public static float getIsOnLadder(float tickDelta) {
		return ClientData.minecraft.player != null ? (ClientData.minecraft.player.isHoldingOntoLadder() ? 1.0F : 0.0F) : 1.0F;
	}
	public static float getIsRiding(float tickDelta) {
		return ClientData.minecraft.player != null ? (ClientData.minecraft.player.isRiding() ? 1.0F : 0.0F) : 0.0F;
	}
	public static float getHasPassengers(float tickDelta) {
		return ClientData.minecraft.player != null ? (ClientData.minecraft.player.hasPassengers() ? 1.0F : 0.0F) : 0.0F;
	}
	public static float getBiomeTemperature(float tickDelta) {
		return ClientData.minecraft.world != null && ClientData.minecraft.player != null ? ClientData.minecraft.world.getBiome(ClientData.minecraft.player.getBlockPos()).value().getTemperature() : 1.0F;
	}
	public static float getAlpha(float tickDelta) {
		return Math.clamp(((int)ConfigHelper.getConfig("alpha_level") / 100.0F), 0.0F, 1.0F);
	}
	public static void setAlpha(int value) {
		ConfigHelper.setConfig("alpha_level", Math.clamp(value, 0, 100));
	}
	public static void resetAlpha() {
		setAlpha(100);
		if ((boolean)ConfigHelper.getConfig("show_alpha_level_overlay")) MessageOverlay.setOverlay(Translation.getTranslation(Data.version.getID(), "alpha_level", new Object[]{ConfigHelper.getConfig("alpha_level") + "%"}, new Formatting[]{Formatting.GOLD}));
	}
	public static void adjustAlpha(int amount) {
		setAlpha((int)ConfigHelper.getConfig("alpha_level") + amount);
		if ((boolean)ConfigHelper.getConfig("show_alpha_level_overlay")) MessageOverlay.setOverlay(Translation.getTranslation(Data.version.getID(), "alpha_level", new Object[]{ConfigHelper.getConfig("alpha_level") + "%"}, new Formatting[]{Formatting.GOLD}));
	}
	public static boolean updatingAlpha = false;
	public static boolean updatingAlpha() {
		boolean value = Keybindings.adjustAlpha.isPressed();
		if (value) {
			if (!updatingAlpha) {
				prevAlpha = (int)ConfigHelper.getConfig("alpha_level");
			}
			updatingAlpha = true;
		}
		return value;
	}
	public static float getPerspective(float tickDelta) {
		if (ClientData.minecraft.options != null) {
			Perspective perspective = ClientData.minecraft.options.getPerspective();
			return perspective.equals(Perspective.THIRD_PERSON_FRONT) ? 3.0F : (perspective.equals(Perspective.THIRD_PERSON_BACK) ? 2.0F : (perspective.equals(Perspective.FIRST_PERSON) ? 1.0F : 0.0F));
		}
		return 1.0F;
	}
	public static float getSelectedSlot(float tickDelta) {
		return ClientData.minecraft.player != null ? ClientData.minecraft.player.getInventory().selectedSlot : 0.0F;
	}
}
