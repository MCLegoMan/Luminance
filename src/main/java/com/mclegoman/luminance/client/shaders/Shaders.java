/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.shaders;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.RenderEvents;
import com.mclegoman.luminance.client.events.ShaderRunnable;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.Couple;
import com.mclegoman.luminance.common.util.LogType;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.JsonEffectShaderProgram;
import net.minecraft.client.gl.Uniform;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

import java.util.Objects;
import java.util.concurrent.Callable;

public class Shaders {
	public static float time = 0.0F;
	public static void init() {
		Uniforms.init();
		RenderEvents.BeforeShaderRender.register(new Couple<>(Data.version.getID(), "main"), new ShaderRunnable() {
			public void run(JsonEffectShaderProgram program) {
				RenderEvents.ShaderUniform.registryFloat.forEach((uniform, callable) -> setFloat(program, uniform.first(), uniform.second(), callable));
				RenderEvents.ShaderUniform.registryFloatArray.forEach((uniform, callable) -> setFloatArray(program, uniform.first(), uniform.second(), callable));
				RenderEvents.ShaderUniform.registryVector3f.forEach((uniform, callable) -> setVector3f(program, uniform.first(), uniform.second(), callable));
			}
		});
		RenderEvents.AfterWorldBorder.add(new Couple<>(Data.version.getID(), "main"), () -> RenderEvents.ShaderRender.registry.forEach((id, shaders) -> shaders.forEach(shader -> render(id, shader.getSecond(), Shader.RenderType.WORLD))));
		RenderEvents.AfterGameRender.add(new Couple<>(Data.version.getID(), "main"), () -> RenderEvents.ShaderRender.registry.forEach((id, shaders) -> shaders.forEach(shader -> render(id, shader.getSecond(), Shader.RenderType.GAME))));
	}
	public static void render(Couple<String, String> id, Shader shader, Shader.RenderType renderType) {
		try {
			if (shader.getRenderType().equals(renderType)) {
				if (shader.getPostProcessor() == null || !Objects.equals(shader.getPostProcessor().getName(), shader.getShaderId().toString())) shader.setPostProcessor();
				if (shader.getPostProcessor() != null) {
					RenderSystem.enableBlend();
					RenderSystem.defaultBlendFunc();
					shader.getPostProcessor().render(ClientData.minecraft.getTickDelta());
					RenderSystem.disableBlend();
					ClientData.minecraft.getFramebuffer().beginWrite(true);
				}
			}
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render \"{}:{}\" shader: {}", id.getFirst(), id.getSecond(), error));
		}
	}
	public static void updateTime() {
		// Ideally, lu_time/lu_timeSmooth should be customizable from post/x.json, and if omitted, it would default to every 20 ticks (matching vanilla).
		// This would require Luminance to add a time variable for each pass, how big of a performance hit would this be?
		// If omitted, we could use the vanilla variable to help with performance.

		// This will get reset every 48 hours to prevent shader stuttering/freezing on some shaders.
		// This may still stutter/freeze on weaker systems.
		// This was tested using i5-11400@2.60GHz/8GB Allocated(of 32GB RAM)/RTX3050(31.0.15.5212).
		time = (time + 1.00F) % 3456000.0F;
	}
	public static float getSmooth(float prev, float current) {
		float tickDelta = ClientData.minecraft.getTickDelta();
		return MathHelper.lerp(tickDelta, prev, current);
	}
	public static float[] getSmooth(float[] prev, float[] current) {
		if (prev.length == current.length) {
			return new float[]{getSmooth(prev[0], current[0]), getSmooth(prev[1], current[1]), getSmooth(prev[2], current[2])};
		}
		return new float[]{};
	}
	public static Uniform getUniform(JsonEffectShaderProgram program, String prefix, String uniformName) {
		return program.getUniformByNameOrDummy(getUniformName(prefix, uniformName));
	}
	public static String getUniformName(String prefix, String uniformName) {
		return prefix + "_" + uniformName;
	}
	public static void setFloat(JsonEffectShaderProgram program, String prefix, String uniformName, Callable<Float> callable) {
		try {
			set(program, prefix, uniformName, callable.call());
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader uniform: {}_{}: {}", prefix, uniformName, error));
		}
	}
	public static void setFloatArray(JsonEffectShaderProgram program, String prefix, String uniformName, Callable<float[]> callable) {
		try {
			set(program, prefix, uniformName, callable.call());
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader uniform: {}_{}: {}", prefix, uniformName, error));
		}
	}
	public static void setVector3f(JsonEffectShaderProgram program, String prefix, String uniformName, Callable<Vector3f> callable) {
		try {
			set(program, prefix, uniformName, callable.call());
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader uniform: {}_{}: {}", prefix, uniformName, error));
		}
	}
	public static void set(JsonEffectShaderProgram program, String prefix, String uniformName, float... values) {
		try {
			getUniform(program, prefix, uniformName).set(values);
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader uniform: {}_{}: {}", prefix, uniformName, error));
		}
	}
	public static void set(JsonEffectShaderProgram program, String prefix, String uniformName, Vector3f values) {
		try {
			getUniform(program, prefix, uniformName).set(values);
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader uniform: {}_{}: {}", prefix, uniformName, error));
		}
	}
}
