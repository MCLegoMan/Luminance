/*
    Luminance
    Contributor(s): MCLegoMan, Nettakrim
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.shaders;

import com.google.gson.JsonObject;
import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.Callables;
import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.events.Runnables;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.client.util.Accessors;
import com.mclegoman.luminance.client.util.CompatHelper;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.Couple;
import com.mclegoman.luminance.common.util.LogType;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.gl.*;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

import java.util.concurrent.Callable;

public class Shaders {
	public static void init() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new ShaderDataloader());
		Uniforms.init();
		Events.BeforeShaderRender.register(Identifier.of(Data.version.getID(), "main"), new Runnables.Shader() {
			@Override
			public void run(ShaderProgram program) {
				Events.ShaderUniform.registry.forEach((id, uniform) -> {
					try {
						uniform.call(ClientData.minecraft.getRenderTickCounter().getTickDelta(true));
						program.getUniformOrDefault(id.toUnderscoreSeparatedString()).set(uniform.get());
						program.getUniformOrDefault(id.toUnderscoreSeparatedString() + "_prev").set(uniform.getPrev());
						program.getUniformOrDefault(id.toUnderscoreSeparatedString() + "_delta").set(uniform.getDelta());
						program.getUniformOrDefault(id.toUnderscoreSeparatedString() + "_smooth").set(uniform.getSmooth(ClientData.minecraft.getRenderTickCounter().getTickDelta(true)));
						program.getUniformOrDefault(id.toUnderscoreSeparatedString() + "_smoothPrev").set(uniform.getSmoothPrev());
						program.getUniformOrDefault(id.toUnderscoreSeparatedString() + "_smoothDelta").set(uniform.getSmoothDelta());
					} catch (Exception error) {
						Data.version.sendToLog(LogType.WARN, error.getLocalizedMessage());
					}
				});
			}
		});
		Events.AfterHandRender.register(Identifier.of(Data.version.getID(), "main"), () -> Events.ShaderRender.registry.forEach((id, shaders) -> {
			try {
				if (shaders != null) shaders.forEach(shader -> {
					try {
						if (shader.getSecond() != null) {
							if ((shader.getSecond().getRenderType().call().equals(Shader.RenderType.WORLD) || (shader.getSecond().getDisableGameRendertype() || shader.getSecond().getUseDepth())) && (!shader.getSecond().getUseDepth() || CompatHelper.isIrisShadersEnabled()))
								render(id, shader);
						}
					} catch (Exception error) {
						Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterHandRender shader with id: {}:{}", id, error));
					}
				});
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterHandRender shader with id: {}:{}", id, error));
			}
		}));
		// This renders the shader in the world if it has depth. We really should try to render the hand in-depth, but this works for now.
		Events.AfterWeatherRender.register(Identifier.of(Data.version.getID(), "main"), () -> Events.ShaderRender.registry.forEach((id, shaders) -> {
			try {
				if (shaders != null) shaders.forEach(shader -> {
					try {
						if (shader.getSecond() != null) {
							if (shader.getSecond().getUseDepth() && !CompatHelper.isIrisShadersEnabled())
								render(id, shader);
						}
					} catch (Exception error) {
						Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterWorldBorder shader with id: {}:{}", id, error));
					}
				});
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterWorldBorder shader with id: {}:{}", id, error));
			}
		}));
		Events.AfterGameRender.register(Identifier.of(Data.version.getID(), "main"), () -> Events.ShaderRender.registry.forEach((id, shaders) -> {
			try {
				if (shaders != null) shaders.forEach(shader -> {
					try {
						if (shader.getSecond() != null) {
							if (shader.getSecond().getRenderType().call().equals(Shader.RenderType.GAME) && !shader.getSecond().getDisableGameRendertype() && !shader.getSecond().getUseDepth())
								render(id, shader);
						}
					} catch (Exception error) {
						Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterGameRender shader with id: {}:{}", id, error));
					}
				});
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterGameRender shader with id: {}:{}", id, error));
			}
		}));
	}
	private static void render(Identifier id, Couple<String, Shader> shader) {
		try {
			if (shader != null && shader.getSecond() != null) {
				if (shader.getSecond().getShouldRender()) {
					if (shader.getSecond().getPostProcessor() == null) {
						try {
							shader.getSecond().setPostProcessor();
						} catch (Exception error) {
							Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set \"{}:{}:{}\" post processor: {}", id, shader.getFirst(), shader.getSecond().getShaderData().getID(), error));
							Events.ShaderRender.Shaders.remove(id, shader.getFirst());
						}
					}
					if (shader.getSecond().getPostProcessor() != null) render(shader.getSecond().getPostProcessor());
				}
			}
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render \"{}:{}\" shader: {}: {}", id, shader.getFirst(), shader.getSecond().getShaderData().getID(), error));
		}
	}
	public static void render(PostEffectProcessor processor) {
		try {
			if (processor != null) {
				try {
					RenderSystem.enableBlend();
					RenderSystem.defaultBlendFunc();
					processor.render(ClientData.minecraft.getFramebuffer(), Accessors.getGameRenderer().getPool());
					RenderSystem.disableBlend();
				} catch (Exception error) {
					Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render processor: {}", error.getLocalizedMessage()));
				}
			}
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render post effect processor: {}", error.getLocalizedMessage()));
		}
	}
	public static ShaderRegistry get(int shaderIndex) {
		return ShaderDataloader.isValidIndex(shaderIndex) ? ShaderDataloader.registry.get(shaderIndex) : null;
	}
	public static ShaderRegistry get(Identifier id) {
		int index = getShaderIndex(id);
		return ShaderDataloader.isValidIndex(index) ? get(index) : null;
	}
	public static Shader get(ShaderRegistry shaderData, Callable<Shader.RenderType> renderType, Callable<Boolean> shouldRender) {
		return new Shader(shaderData, renderType, shouldRender);
	}
	public static Shader get(ShaderRegistry shaderData, Callable<Shader.RenderType> renderType) {
		return new Shader(shaderData, renderType);
	}
	public static Identifier getPostShader(Identifier post_effect, boolean full) {
		return Identifier.of(post_effect.getNamespace(), ((full ? "post_effect/" : "") + post_effect.getPath() + (full ? ".json" : "")));
	}
	public static int getShaderIndex(Identifier id) {
		if (id != null) {
			for (ShaderRegistry data : ShaderDataloader.registry) {
				if (data.getID().equals(id)) return ShaderDataloader.registry.indexOf(data);
			}
		}
		return -1;
	}
	public static JsonObject getCustom(int shaderIndex, String namespace) {
		ShaderRegistry shader = get(shaderIndex);
		if (shader != null) {
			JsonObject customData = shader.getCustom();
			if (customData != null) {
				if (customData.has(namespace)) {
					return JsonHelper.getObject(customData, namespace);
				}
			}
		}
		return null;
	}
	public static Text getShaderName(int shaderIndex, boolean shouldShowNamespace) {
		ShaderRegistry shader = get(shaderIndex);
		if (shader != null) return Translation.getShaderTranslation(shader.getID(), shader.getTranslatable(), shouldShowNamespace);
		return Translation.getErrorTranslation(Data.version.getID());
	}
	public static Text getShaderName(int shaderIndex) {
		return getShaderName(shaderIndex, true);
	}
	public static Identifier guessPostShader(String id) {
		// If the shader registry contains at least one shader with the name, the first detected instance will be used.
		if (!id.contains(":")) {
			for (ShaderRegistry registry : ShaderDataloader.registry) {
				if (registry.getID().getPath().equalsIgnoreCase(id)) return registry.getID();
			}
		}
		return Identifier.of(id);
	}
	public static float getSmooth(float tickDelta, float prev, float current) {
		return MathHelper.lerp(tickDelta, prev, current);
	}
	public static float[] getSmooth(float tickDelta, float[] prev, float[] current) {
		if (prev.length == current.length) {
			return new float[]{getSmooth(tickDelta, prev[0], current[0]), getSmooth(tickDelta, prev[1], current[1]), getSmooth(tickDelta, prev[2], current[2])};
		}
		return new float[]{};
	}
	public static Uniform getUniform(ShaderProgram program, Identifier id) {
		return program.getUniform(getUniformName(id));
	}
	public static String getUniformName(Identifier id) {
		return id.toString();
	}
	public static void setFloatArray(ShaderProgram program, Identifier id,  Callables.ShaderRender<float[]> callable) {
		try {
			set(program, id, callable.call(ClientData.minecraft.getRenderTickCounter().getTickDelta(true)));
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader uniform: {}_{}: {}", id, error));
		}
	}
	public static void setVector3f(ShaderProgram program, Identifier id,  Callables.ShaderRender<Vector3f> callable) {
		try {
			set(program, id, callable.call(ClientData.minecraft.getRenderTickCounter().getTickDelta(true)));
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader uniform: {}_{}: {}", id, error));
		}
	}
	public static void set(ShaderProgram program, Identifier id, float... values) {
		try {
			if (program != null) {
				Uniform uniform = getUniform(program, id);
				if (uniform != null) uniform.set(values);
			}
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader uniform: {}_{}: {}", id, error));
		}
	}
	public static void set(ShaderProgram program, Identifier id, Vector3f values) {
		try {
			if (program != null) {
				Uniform uniform = getUniform(program, id);
				if (uniform != null) uniform.set(values);
			}
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader uniform: {}_{}: {}", id, error));
		}
	}
}
