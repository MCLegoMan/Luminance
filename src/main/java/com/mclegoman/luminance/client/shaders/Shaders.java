/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.shaders;

import com.google.gson.JsonObject;
import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.events.Runnables;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.Couple;
import com.mclegoman.luminance.common.util.IdentifierHelper;
import com.mclegoman.luminance.common.util.LogType;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.JsonEffectShaderProgram;
import net.minecraft.client.gl.Uniform;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class Shaders {
	public static float time = 0.0F;
	public static Framebuffer depthFramebuffer;
	public static void init() {
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new ShaderDataloader());
		Uniforms.init();
		Events.BeforeShaderRender.register(new Couple<>(Data.version.getID(), "main"), new Runnables.Shader() {
			public void run(JsonEffectShaderProgram program) {
				Events.ShaderUniform.registryFloat.forEach((uniform, callable) -> setFloat(program, uniform.getFirst(), uniform.getSecond(), callable));
				Events.ShaderUniform.registryFloatArray.forEach((uniform, callable) -> setFloatArray(program, uniform.getFirst(), uniform.getSecond(), callable));
				Events.ShaderUniform.registryVector3f.forEach((uniform, callable) -> setVector3f(program, uniform.getFirst(), uniform.getSecond(), callable));
			}
		});
		Events.AfterHandRender.add(new Couple<>(Data.version.getID(), "main"), () -> Events.ShaderRender.registry.forEach((id, shaders) -> {
			try {
				if (shaders != null) shaders.forEach(shader -> render(id, shader.getSecond(), Shader.RenderType.WORLD, true));
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterHandRender shader with id: {}:{}:", id.getFirst(), id.getSecond(), error));
			}
		}));
		Events.AfterWorldBorder.add(new Couple<>(Data.version.getID(), "main"), () -> Events.ShaderRender.registry.forEach((id, shaders) -> {
			try {
				if (shaders != null) shaders.forEach(shader -> render(id, shader.getSecond(), Shader.RenderType.WORLD, false));
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterWorldBorder shader with id: {}:{}:", id.getFirst(), id.getSecond(), error));
			}
		}));
		Events.AfterGameRender.add(new Couple<>(Data.version.getID(), "main"), () -> Events.ShaderRender.registry.forEach((id, shaders) -> {
			try {
				if (shaders != null) shaders.forEach(shader -> render(id, shader.getSecond(), Shader.RenderType.GAME, false));
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render AfterGameRender shader with id: {}:{}:", id.getFirst(), id.getSecond(), error));
			}
		}));
	}
	public static void render(Couple<String, String> id, Shader shader, Shader.RenderType renderType, boolean canDepthShader) {
		try {
			if ((!canDepthShader && shader.getUseDepth()) || (canDepthShader && !shader.getUseDepth())) {
					if (shader.getRenderType().call().equals(renderType)) {
						if (!(renderType.equals(Shader.RenderType.GAME) && ((boolean) get(shader.getShaderData(), ShaderRegistry.DISABLE_GAME_RENDERTYPE) || shader.getUseDepth()))) render(shader);
					} else {
						if (renderType.equals(Shader.RenderType.WORLD) && ((boolean) get(shader.getShaderData(), ShaderRegistry.DISABLE_GAME_RENDERTYPE) || shader.getUseDepth())) render(shader);
					}
			}
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render \"{}:{}\" shader: {}", id.getFirst(), id.getSecond(), error));
		}
	}
	public static void render(Shader shader) {
		try {
			if (shader.getShouldRender().call()) {
				if (shader.getPostProcessor() == null || !Objects.equals(shader.getPostProcessor().getName(), shader.getShaderId().toString()))
					shader.setPostProcessor();
				if (shader.getPostProcessor() != null) {
					RenderSystem.enableBlend();
					RenderSystem.defaultBlendFunc();
					shader.getPostProcessor().render(ClientData.minecraft.getTickDelta());
					RenderSystem.disableBlend();
					ClientData.minecraft.getFramebuffer().beginWrite(true);
				}
			}
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to render \"{}\" shader: {}", get(shader.getShaderData(), ShaderRegistry.ID), error));
		}
	}
	public static Object get(int shaderIndex, ShaderRegistry dataType) {
		List<Object> shaderData = ShaderDataloader.isValidIndex(shaderIndex) ? ShaderDataloader.registry.get(shaderIndex) : null;
		return get(shaderData, dataType);
	}
	public static List<Object> get(int shaderIndex) {
		return ShaderDataloader.isValidIndex(shaderIndex) ? ShaderDataloader.registry.get(shaderIndex) : null;
	}
	public static Object get(String id, ShaderRegistry dataType) {
		return get(getShaderIndex(id), dataType);
	}
	public static List<Object> get(String id) {
		return get(getShaderIndex(id));
	}
	public static Object get(String namespace, String name, ShaderRegistry dataType) {
		return get(getShaderIndex(namespace, name), dataType);
	}
	public static List<Object> get(String namespace, String name) {
		int index = getShaderIndex(namespace, name);
		return ShaderDataloader.isValidIndex(index) ? get(index) : null;
	}
	public static Object get(List<Object> shaderData, ShaderRegistry dataType) {
		switch (dataType) {
			case ID -> {
				return shaderData.get(0);
			}
			case NAMESPACE -> {
				return shaderData.get(1);
			}
			case SHADER_NAME -> {
				return shaderData.get(2);
			}
			case TRANSLATABLE -> {
				return shaderData.get(3);
			}
			case DISABLE_GAME_RENDERTYPE -> {
				return shaderData.get(4);
			}
			case CUSTOM -> {
				return shaderData.get(5);
			}
			default -> {
				return new Object();
			}
		}
	}
	public static List<Object> get(String namespace, String name, boolean translatable, boolean disableGameRenderType, JsonObject customData) {
		List<Object> shaderMap = new ArrayList<>();
		shaderMap.add(namespace + ":" + name);
		shaderMap.add(namespace);
		shaderMap.add(name);
		shaderMap.add(translatable);
		shaderMap.add(disableGameRenderType);
		shaderMap.add(customData);
		return shaderMap;
	}
	public static Shader get(List<Object> shaderData, Callable<Shader.RenderType> renderType, Callable<Boolean> shouldRender) {
		return new Shader(shaderData, renderType, shouldRender);
	}
	public static Shader get(List<Object> shaderData, Callable<Shader.RenderType> renderType) {
		return new Shader(shaderData, renderType);
	}
	public static Identifier getPostShader(String id) {
		String namespace = IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, id, "minecraft");
		String shader = IdentifierHelper.getStringPart(IdentifierHelper.Type.KEY, id);
		return getPostShader(namespace, shader);
	}
	public static Identifier getPostShader(String namespace, String name) {
		if (namespace != null && name != null) {
			name = name.replace("\"", "").toLowerCase();
			return new Identifier(namespace.toLowerCase(), ("shaders/post/" + name + ".json"));
		}
		return null;
	}
	public static int getShaderIndex(String id) {
		String namespace = IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, id, "minecraft");
		String name = IdentifierHelper.getStringPart(IdentifierHelper.Type.KEY, id);
		return getShaderIndex(namespace, name);
	}
	public static int getShaderIndex(String namespace, String name) {
		if (namespace != null && name != null) {
			for (List<Object> data : ShaderDataloader.registry) {
				if (get(data, ShaderRegistry.NAMESPACE).equals(namespace) && get(data, ShaderRegistry.SHADER_NAME).equals(name)) return ShaderDataloader.registry.indexOf(data);
			}
		}
		return -1;
	}
	public static JsonObject getCustom(int shaderIndex, String namespace) {
		JsonObject customData = (JsonObject) get(shaderIndex, ShaderRegistry.CUSTOM);
		if (customData != null) {
			if (customData.has(namespace)) {
				return JsonHelper.getObject(customData, namespace);
			}
		}
		return null;
	}
	public static Text getShaderName(int shaderIndex, boolean shouldShowNamespace) {
		if (get(shaderIndex) != null) return Translation.getShaderTranslation((String)get(shaderIndex, ShaderRegistry.NAMESPACE), (String)get(shaderIndex, ShaderRegistry.SHADER_NAME), (boolean)get(shaderIndex, ShaderRegistry.TRANSLATABLE), shouldShowNamespace);
		return Translation.getErrorTranslation(Data.version.getID());
	}
	public static Text getShaderName(int shaderIndex) {
		if (get(shaderIndex) != null) return Translation.getShaderTranslation((String)get(shaderIndex, ShaderRegistry.NAMESPACE), (String)get(shaderIndex, ShaderRegistry.SHADER_NAME), (boolean)get(shaderIndex, ShaderRegistry.TRANSLATABLE));
		return Translation.getErrorTranslation(Data.version.getID());
	}
	public static String guessPostShaderNamespace(String id) {
		// If the shader registry contains at least one shader with the name, the first detected instance will be used.
		if (!id.contains(":")) {
			for (List<Object> registry : ShaderDataloader.registry) {
				if (((String)registry.get(2)).equalsIgnoreCase(id)) return (String)registry.get(1);
			}
		}
		return IdentifierHelper.getStringPart(IdentifierHelper.Type.NAMESPACE, id, "minecraft");
	}
	public static void updateTime() {
		// Ideally, lu_time/lu_timeSmooth should be customizable from post/x.json, and if omitted, it would default to every 20 ticks (matching vanilla).
		// This would require Luminance to add a time variable for each pass, how big of a performance hit would this be?
		// If omitted, we could use the vanilla variable to help with performance.

		// Could we add something like this to the post/x.json and program/x.json files?
		// options {
		//     "lu_time": {
		//         "type": "int",
		//         "value": 20,
		//     }
		// }

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
