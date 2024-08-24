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
import com.mclegoman.luminance.client.util.CompatHelper;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.Couple;
import com.mclegoman.luminance.common.util.IdentifierHelper;
import com.mclegoman.luminance.common.util.LogType;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.gl.Uniform;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

import java.util.Objects;
import java.util.concurrent.Callable;

public class Shaders {
	public static Framebuffer depthFramebuffer;
	public static void init() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new ShaderDataloader());
		Uniforms.init();
		Events.BeforeShaderRender.register(new Couple<>(Data.version.getID(), "main"), new Runnables.Shader() {
			public void run(ShaderProgram program) {
				Events.ShaderUniform.registryFloat.forEach((uniform, callable) -> setFloat(program, uniform.getFirst(), uniform.getSecond(), callable));
				Events.ShaderUniform.registryFloatArray.forEach((uniform, callable) -> setFloatArray(program, uniform.getFirst(), uniform.getSecond(), callable));
				Events.ShaderUniform.registryVector3f.forEach((uniform, callable) -> setVector3f(program, uniform.getFirst(), uniform.getSecond(), callable));
			}
		});
		Events.AfterHandRender.add(new Couple<>(Data.version.getID(), "main"), () -> Events.ShaderRender.registry.forEach((id, shaders) -> {
			try {
				if (shaders != null) shaders.forEach(shader -> {
					try {
						if ((shader.getSecond().getRenderType().call().equals(Shader.RenderType.WORLD) || (shader.getSecond().getDisableGameRendertype() || shader.getSecond().getUseDepth())) && (!shader.getSecond().getUseDepth() || CompatHelper.isIrisShadersEnabled())) render(id, shader);
					} catch (Exception error) {
						Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterHandRender shader with id: {}:{}:{}", id.getFirst(), id.getSecond(), error));
					}
				});
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterHandRender shader with id: {}:{}:{}", id.getFirst(), id.getSecond(), error));
			}
		}));
		// This renders the shader in the world if it has depth. We really should try to render the hand in-depth, but this works for now.
		Events.AfterWorldBorder.add(new Couple<>(Data.version.getID(), "main"), () -> Events.ShaderRender.registry.forEach((id, shaders) -> {
			try {
				if (shaders != null) shaders.forEach(shader -> {
					try {
						if (shader.getSecond().getUseDepth() && !CompatHelper.isIrisShadersEnabled()) render(id, shader);
					} catch (Exception error) {
						Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterWorldBorder shader with id: {}:{}:{}", id.getFirst(), id.getSecond(), error));
					}
				});
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterWorldBorder shader with id: {}:{}:{}", id.getFirst(), id.getSecond(), error));
			}
		}));
		Events.AfterGameRender.add(new Couple<>(Data.version.getID(), "main"), () -> Events.ShaderRender.registry.forEach((id, shaders) -> {
			try {
				if (shaders != null) shaders.forEach(shader -> {
					try {
						if (shader.getSecond().getRenderType().call().equals(Shader.RenderType.GAME) && !shader.getSecond().getDisableGameRendertype() && !shader.getSecond().getUseDepth()) render(id, shader);
					} catch (Exception error) {
						Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterGameRender shader with id: {}:{}:{}", id.getFirst(), id.getSecond(), error));
					}
				});
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterGameRender shader with id: {}:{}:{}", id.getFirst(), id.getSecond(), error));
			}
		}));
		Events.OnResized.register(new Couple<>(Data.version.getID(), "main"), new Runnables.OnResized() {
			public void run(int width, int height) {
				Events.ShaderRender.registry.forEach((id, shaders) -> {
					if (shaders != null) shaders.forEach(shader -> {
						try {
							//if (shader.getSecond() != null && shader.getSecond().getPostProcessor() != null) shader.getSecond().getPostProcessor().setupDimensions(width, height);
						} catch (Exception error) {
							Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to resize shader with id: {}:{}:", id.getFirst(), id.getSecond(), error));
						}
					});
				});
				if (Shaders.depthFramebuffer == null) {
					Shaders.depthFramebuffer = new SimpleFramebuffer(width, height, true);
				} else {
					Shaders.depthFramebuffer.resize(width, height);
				}
			}
		});
	}
	private static void render(Couple<String, String> id, Couple<String, Shader> shader) {
		try {
			if (shader.getSecond().getShouldRender()) {
				if (shader.getSecond().getPostProcessor() == null) {// || !Objects.equals(shader.getSecond().getPostProcessor().getName(), shader.getSecond().getShaderId().toString())) {
					try {
						shader.getSecond().setPostProcessor();
					} catch (Exception error) {
						Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set \"{}\" post processor: {}", shader.getSecond().getShaderData().getId(), error));
						Events.ShaderRender.Shaders.remove(id, shader.getFirst());
					}
				}
				render(shader);
			}
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render \"{}:{}:{}\" shader: {}", shader.getFirst(), shader.getSecond(), shader.getSecond().getShaderData().getId(), error));
		}
	}
	private static void render(Couple<String, Shader> shader) {
		try {
			if (shader.getSecond().getPostProcessor() != null) {
				RenderSystem.enableBlend();
				RenderSystem.defaultBlendFunc();
				//shader.getSecond().getPostProcessor().render(ClientData.minecraft.getRenderTickCounter().getTickDelta(true));
				RenderSystem.disableBlend();
				ClientData.minecraft.getFramebuffer().beginWrite(false);
			}
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render \"{}:{}:{}\" shader: {}", shader.getFirst(), shader.getSecond(), shader.getSecond().getShaderData().getId(), error));
		}
	}
	public static ShaderRegistry get(int shaderIndex) {
		return ShaderDataloader.isValidIndex(shaderIndex) ? ShaderDataloader.registry.get(shaderIndex) : null;
	}
	public static ShaderRegistry get(String id) {
		return get(getShaderIndex(id));
	}
	public static ShaderRegistry get(String namespace, String name) {
		int index = getShaderIndex(namespace, name);
		return ShaderDataloader.isValidIndex(index) ? get(index) : null;
	}
	public static Shader get(ShaderRegistry shaderData, Callable<Shader.RenderType> renderType, Callable<Boolean> shouldRender) {
		return new Shader(shaderData, renderType, shouldRender);
	}
	public static Shader get(ShaderRegistry shaderData, Callable<Shader.RenderType> renderType) {
		return new Shader(shaderData, renderType);
	}
	public static Identifier getPostShader(String id) {
		String namespace = IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, id);
		String shader = IdentifierHelper.getStringPart(IdentifierHelper.Type.KEY, id);
		return getPostShader(namespace, shader);
	}
	public static Identifier getPostShader(String namespace, String name) {
		if (namespace != null && name != null) {
			name = name.replace("\"", "").toLowerCase();
			return Identifier.of(namespace.toLowerCase(), ("shaders/post/" + name + ".json"));
		}
		return null;
	}
	public static int getShaderIndex(String id) {
		String namespace = IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, id);
		String name = IdentifierHelper.getStringPart(IdentifierHelper.Type.KEY, id);
		return getShaderIndex(namespace, name);
	}
	public static int getShaderIndex(String namespace, String name) {
		if (namespace != null && name != null) {
			for (ShaderRegistry data : ShaderDataloader.registry) {
				if (data.getNamespace().equals(namespace) && data.getName().equals(name)) return ShaderDataloader.registry.indexOf(data);
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
		if (shader != null) return Translation.getShaderTranslation(shader.getNamespace(), shader.getName(), shader.getTranslatable(), shouldShowNamespace);
		return Translation.getErrorTranslation(Data.version.getID());
	}
	public static Text getShaderName(int shaderIndex) {
		return getShaderName(shaderIndex, true);
	}
	public static String guessPostShaderNamespace(String id) {
		// If the shader registry contains at least one shader with the name, the first detected instance will be used.
		if (!id.contains(":")) {
			for (ShaderRegistry registry : ShaderDataloader.registry) {
				if (registry.getName().equalsIgnoreCase(id)) return registry.getNamespace();
			}
		}
		return IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, id);
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
	public static Uniform getUniform(ShaderProgram program, String prefix, String uniformName) {
		return program.getUniform(getUniformName(prefix, uniformName));
	}
	public static String getUniformName(String prefix, String uniformName) {
		return prefix + "_" + uniformName;
	}
	public static void setFloat(ShaderProgram program, String prefix, String uniformName, Callables.ShaderRender<Float> callable) {
		try {
			set(program, prefix, uniformName, callable.call(ClientData.minecraft.getRenderTickCounter().getTickDelta(true)));
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader uniform: {}_{}: {}", prefix, uniformName, error));
		}
	}
	public static void setFloatArray(ShaderProgram program, String prefix, String uniformName,  Callables.ShaderRender<float[]> callable) {
		try {
			set(program, prefix, uniformName, callable.call(ClientData.minecraft.getRenderTickCounter().getTickDelta(true)));
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader uniform: {}_{}: {}", prefix, uniformName, error));
		}
	}
	public static void setVector3f(ShaderProgram program, String prefix, String uniformName,  Callables.ShaderRender<Vector3f> callable) {
		try {
			set(program, prefix, uniformName, callable.call(ClientData.minecraft.getRenderTickCounter().getTickDelta(true)));
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader uniform: {}_{}: {}", prefix, uniformName, error));
		}
	}
	public static void set(ShaderProgram program, String prefix, String uniformName, float... values) {
		try {
			getUniform(program, prefix, uniformName).set(values);
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader uniform: {}_{}: {}", prefix, uniformName, error));
		}
	}
	public static void set(ShaderProgram program, String prefix, String uniformName, Vector3f values) {
		try {
			getUniform(program, prefix, uniformName).set(values);
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader uniform: {}_{}: {}", prefix, uniformName, error));
		}
	}
}
