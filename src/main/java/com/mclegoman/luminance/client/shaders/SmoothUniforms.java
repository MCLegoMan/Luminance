/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.shaders;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.Events;
import net.minecraft.entity.effect.StatusEffects;

public class SmoothUniforms extends Uniforms {
	private static float tickDelta;
	public static float prevViewDistance = getViewDistance(tickDelta);
	public static float viewDistance = getViewDistance(tickDelta);
	public static float prevFov = getFov(tickDelta);
	public static float fov = getFov(tickDelta);
	public static float prevTime = getTime(tickDelta);
	public static float time = getTime(tickDelta);
	public static float[] prevEyePosition = getEyePosition(tickDelta);
	public static float[] eyePosition = getEyePosition(tickDelta);
	public static float[] prevPosition = getPosition(tickDelta);
	public static float[] position = getPosition(tickDelta);
	public static float prevPitch = getPitch(tickDelta);
	public static float pitch = getPitch(tickDelta);
	public static float prevYaw = getYaw(tickDelta);
	public static float yaw = getYaw(tickDelta);
	public static float prevCurrentHealth = getCurrentHealth(tickDelta);
	public static float currentHealth = getCurrentHealth(tickDelta);
	public static float prevMaxHealth = getMaxHealth(tickDelta);
	public static float maxHealth = getMaxHealth(tickDelta);
	public static float prevCurrentAbsorption = getCurrentAbsorption(tickDelta);
	public static float currentAbsorption = getCurrentAbsorption(tickDelta);
	public static float prevMaxAbsorption = getMaxAbsorption(tickDelta);
	public static float maxAbsorption = getMaxAbsorption(tickDelta);
	public static float prevCurrentHurtTime = getCurrentHurtTime(tickDelta);
	public static float currentHurtTime = getCurrentHurtTime(tickDelta);
	public static float prevMaxHurtTime = getMaxHurtTime(tickDelta);
	public static float maxHurtTime = getMaxHurtTime(tickDelta);
	public static float prevCurrentAir = getCurrentAir(tickDelta);
	public static float currentAir = getCurrentAir(tickDelta);
	public static float prevMaxAir = getMaxAir(tickDelta);
	public static float maxAir = getMaxAir(tickDelta);
	public static float prevIsSprinting = getIsSprinting(tickDelta);
	public static float isSprinting = getIsSprinting(tickDelta);
	public static float prevIsSwimming = getIsSwimming(tickDelta);
	public static float isSwimming = getIsSwimming(tickDelta);
	public static float prevIsSneaking = getIsSneaking(tickDelta);
	public static float isSneaking = getIsSneaking(tickDelta);
	public static float prevIsCrawling = getIsCrawling(tickDelta);
	public static float isCrawling = getIsCrawling(tickDelta);
	public static float prevIsInvisible = getIsInvisible(tickDelta);
	public static float isInvisible = getIsInvisible(tickDelta);
	public static float prevIsWithered = getHasEffect(StatusEffects.WITHER);
	public static float isWithered = getHasEffect(StatusEffects.WITHER);
	public static float prevIsPoisoned = getHasEffect(StatusEffects.POISON);
	public static float isPoisoned = getHasEffect(StatusEffects.POISON);
	public static float prevIsBurning = getIsBurning(tickDelta);
	public static float isBurning = getIsBurning(tickDelta);
	public static float prevIsOnGround = getIsOnGround(tickDelta);
	public static float isOnGround = getIsOnGround(tickDelta);
	public static float prevIsOnLadder = getIsOnLadder(tickDelta);
	public static float isOnLadder = getIsOnLadder(tickDelta);
	public static float prevIsRiding = getIsRiding(tickDelta);
	public static float isRiding = getIsRiding(tickDelta);
	public static float prevHasPassengers = getHasPassengers(tickDelta);
	public static float hasPassengers = getHasPassengers(tickDelta);
	public static float prevBiomeTemperature = getBiomeTemperature(tickDelta);
	public static float biomeTemperature = getBiomeTemperature(tickDelta);
	public static float prevAlpha = getAlpha(tickDelta);
	public static float alpha = getAlpha(tickDelta);
	public static void tick(float tickDelta) {
		SmoothUniforms.tickDelta = tickDelta;
		prevViewDistance = viewDistance;
		viewDistance = (prevViewDistance + getViewDistance(tickDelta)) * 0.5F;
		prevFov = fov;
		fov = (prevFov + getFov(tickDelta)) * 0.5F;
		if (getTime(tickDelta) < 0.01F) {
			prevTime = getTime(tickDelta);
			time = getTime(tickDelta);
		} else {
			prevTime = time;
			time = (prevTime + getTime(tickDelta)) * 0.5F;
		}
		prevEyePosition = eyePosition;
		float[] currentEyePosition = getEyePosition(tickDelta);
		eyePosition = new float[]{((prevEyePosition[0] + currentEyePosition[0]) * 0.5F), ((prevEyePosition[1] + currentEyePosition[1]) * 0.5F), ((prevEyePosition[2] + currentEyePosition[2]) * 0.5F)};
		prevPosition = position;
		float[] currentPosition = getPosition(tickDelta);
		position = new float[]{((prevPosition[0] + currentPosition[0]) * 0.5F), ((prevPosition[1] + currentPosition[1]) * 0.5F), ((prevPosition[2] + currentPosition[2]) * 0.5F)};
		prevPitch = pitch;
		pitch = (prevPitch + getPitch(tickDelta)) * 0.5F;
		prevYaw = yaw;
		yaw = (prevYaw + getYaw(tickDelta)) * 0.5F;
		prevCurrentHealth = currentHealth;
		currentHealth = (prevCurrentHealth + getCurrentHealth(tickDelta)) * 0.5F;
		prevMaxHealth = maxHealth;
		maxHealth = (prevMaxHealth + getMaxHealth(tickDelta)) * 0.5F;
		prevCurrentAbsorption = currentAbsorption;
		currentAbsorption = (prevCurrentAbsorption + getCurrentAbsorption(tickDelta)) * 0.5F;
		prevMaxAbsorption = maxAbsorption;
		maxAbsorption = (prevMaxAbsorption + getMaxAbsorption(tickDelta)) * 0.5F;
		prevCurrentHurtTime = currentHurtTime;
		currentHurtTime = (prevCurrentHurtTime + getCurrentHurtTime(tickDelta)) * 0.5F;
		prevMaxHurtTime = maxHurtTime;
		maxHurtTime = (prevMaxHurtTime + getMaxHurtTime(tickDelta)) * 0.5F;
		prevCurrentAir = currentAir;
		currentAir = (prevCurrentAir + getCurrentAir(tickDelta)) * 0.5F;
		prevMaxAir = maxAir;
		maxAir = (prevMaxAir + getMaxAir(tickDelta)) * 0.5F;
		prevIsSprinting = isSprinting;
		isSprinting = (prevIsSprinting + getIsSprinting(tickDelta)) * 0.5F;
		prevIsSwimming = isSwimming;
		isSwimming = (prevIsSwimming + getIsSwimming(tickDelta)) * 0.5F;
		prevIsSneaking = isSneaking;
		isSneaking = (prevIsSneaking + getIsSneaking(tickDelta)) * 0.5F;
		prevIsCrawling = isCrawling;
		isCrawling = (prevIsCrawling + getIsCrawling(tickDelta)) * 0.5F;
		prevIsInvisible = isInvisible;
		isInvisible = (prevIsInvisible + getIsInvisible(tickDelta)) * 0.5F;
		prevIsWithered = isWithered;
		isWithered = (prevIsWithered + getHasEffect(StatusEffects.WITHER)) * 0.5F;
		prevIsPoisoned = isPoisoned;
		isPoisoned = (prevIsPoisoned + getHasEffect(StatusEffects.POISON)) * 0.5F;
		prevIsBurning = isBurning;
		isBurning = (prevIsBurning + getIsBurning(tickDelta)) * 0.5F;
		prevIsOnGround = isOnGround;
		isOnGround = (prevIsOnGround + getIsOnGround(tickDelta)) * 0.5F;
		prevIsOnLadder = isOnLadder;
		isOnLadder = (prevIsOnLadder + getIsOnLadder(tickDelta)) * 0.5F;
		prevIsRiding = isRiding;
		isRiding = (prevIsRiding + getIsRiding(tickDelta)) * 0.5F;
		prevHasPassengers = hasPassengers;
		hasPassengers = (prevHasPassengers + getHasPassengers(tickDelta)) * 0.5F;
		prevBiomeTemperature = biomeTemperature;
		biomeTemperature = (prevBiomeTemperature + getBiomeTemperature(tickDelta)) * 0.5F;
		prevAlpha = alpha;
		alpha = (prevAlpha + getAlpha(tickDelta)) * 0.5F;
	}
	public static void init() {
		Events.ShaderUniform.registerFloat("lu", "viewDistanceSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevViewDistance, viewDistance));
		Events.ShaderUniform.registerFloat("lu", "fovSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevFov, fov));
		Events.ShaderUniform.registerFloat("lu", "timeSmooth", SmoothUniforms::getSmoothTime);
		Events.ShaderUniform.registerFloats("lu", "eyePositionSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevEyePosition, eyePosition));
		Events.ShaderUniform.registerFloats("lu", "positionSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevPosition, position));
		Events.ShaderUniform.registerFloat("lu", "pitchSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevPitch, pitch));
		Events.ShaderUniform.registerFloat("lu", "yawSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevYaw, yaw));
		Events.ShaderUniform.registerFloat("lu", "currentHealthSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevCurrentHealth, currentHealth));
		Events.ShaderUniform.registerFloat("lu", "maxHealthSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevMaxHealth, maxHealth));
		Events.ShaderUniform.registerFloat("lu", "currentAbsorptionSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevCurrentAbsorption, currentAbsorption));
		Events.ShaderUniform.registerFloat("lu", "maxAbsorptionSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevMaxAbsorption, maxAbsorption));
		Events.ShaderUniform.registerFloat("lu", "currentHurtTimeSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevCurrentHurtTime, currentHurtTime));
		Events.ShaderUniform.registerFloat("lu", "maxHurtTimeSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevMaxHurtTime, maxHurtTime));
		Events.ShaderUniform.registerFloat("lu", "currentAirSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevCurrentAir, currentAir));
		Events.ShaderUniform.registerFloat("lu", "maxAirSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevMaxAir, maxAir));
		Events.ShaderUniform.registerFloat("lu", "isSprintingSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevIsSprinting, isSprinting));
		Events.ShaderUniform.registerFloat("lu", "isSwimmingSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevIsSwimming, isSwimming));
		Events.ShaderUniform.registerFloat("lu", "isSneakingSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevIsSneaking, isSneaking));
		Events.ShaderUniform.registerFloat("lu", "isCrawlingSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevIsCrawling, isCrawling));
		Events.ShaderUniform.registerFloat("lu", "isInvisibleSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevIsInvisible, isInvisible));
		Events.ShaderUniform.registerFloat("lu", "isWitheredSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevIsWithered, isWithered));
		Events.ShaderUniform.registerFloat("lu", "isPoisonedSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevIsPoisoned, isPoisoned));
		Events.ShaderUniform.registerFloat("lu", "isBurningSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevIsBurning, isBurning));
		Events.ShaderUniform.registerFloat("lu", "isOnGroundSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevIsOnGround, isOnGround));
		Events.ShaderUniform.registerFloat("lu", "isOnLadderSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevIsOnLadder, isOnLadder));
		Events.ShaderUniform.registerFloat("lu", "isRidingSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevIsRiding, isRiding));
		Events.ShaderUniform.registerFloat("lu", "hasPassengersSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevHasPassengers, hasPassengers));
		Events.ShaderUniform.registerFloat("lu", "biomeTemperatureSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevBiomeTemperature, biomeTemperature));
		Events.ShaderUniform.registerFloat("lu", "alphaSmooth", (tickDelta) -> Shaders.getSmooth(tickDelta, prevAlpha, alpha));
	}
	public static float getSmoothTime(float tickDelta) {
		if (getTime(tickDelta) <= 1.0F) return getTime(tickDelta);
		return Shaders.getSmooth(tickDelta, prevTime, time);
	}
}