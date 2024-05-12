/*
    Luminance
    Contributor(s): MCLegoMan, Nettakrim
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.shaders;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mclegoman.luminance.client.events.Events;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.LogType;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.client.gl.ShaderStage;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShaderDataloader extends JsonDataLoader implements IdentifiableResourceReloadListener {
	protected static boolean isReloading;
	public static final List<List<Object>> registry = new ArrayList<>();
	public static final String resourceLocation = "shaders/shaders";
	public ShaderDataloader() {
		super(new Gson(), resourceLocation);
	}
	private void reset() {
		registry.clear();
		Events.OnShaderDataReset.registry.forEach((id, runnable) -> {
			try {
				runnable.run();
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute OnShaderDataReset event with id: {}:{}:", id.getFirst(), id.getSecond(), error));
			}
		});
	}
	private void add(String namespace, String shaderName, boolean translatable, boolean disableGameRendertype, JsonObject custom, ResourceManager manager) {
		try {
			String id = namespace.toLowerCase() + ":" + shaderName.toLowerCase();
			manager.getResourceOrThrow(Shaders.getPostShader(id));
			List<Object> shaderMap = new ArrayList<>();
			shaderMap.add(id);
			shaderMap.add(namespace.toLowerCase());
			shaderMap.add(shaderName.toLowerCase());
			shaderMap.add(translatable);
			shaderMap.add(disableGameRendertype);
			shaderMap.add(custom);
			boolean alreadyRegistered = false;
			for (List<Object> shaderData : registry) {
				if (shaderData.contains(id)) {
					alreadyRegistered = true;
					Data.version.sendToLog(LogType.WARN, Translation.getString("Failed to add \"{}\" shader to registry: This shader has already been registered!", id));
					break;
				}
			}
			if (!alreadyRegistered) registry.add(shaderMap);
		} catch (Exception error) {
			Data.version.sendToLog(LogType.WARN, "Failed to add shader to registry: " + error);
		}
	}
	public static int getShaderAmount() {
		return registry.size();
	}
	public static boolean isValidIndex(int index) {
		return index <= getShaderAmount() && index >= 0;
	}
	private void remove(String namespace, String name) {
		registry.removeIf((shader) -> shader.get(1).toString().equalsIgnoreCase(namespace) && shader.get(2).toString().equalsIgnoreCase(name));
	}
	@Override
	public void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		try {
			isReloading = true;
			releaseShaders();
			reset();
			prepared.forEach((identifier, jsonElement) -> {
				try {
					JsonObject reader = jsonElement.getAsJsonObject();
					String namespace = JsonHelper.getString(reader, "namespace", Data.version.getID());
					String name = JsonHelper.getString(reader, "name");
					boolean enabled = JsonHelper.getBoolean(reader, "enabled", true);
					boolean translatable = JsonHelper.getBoolean(reader, "translatable", false);
					boolean disableGameRenderType = JsonHelper.getBoolean(reader, "disable_game_rendertype", false);
					JsonObject customData = JsonHelper.getObject(reader, "customData", new JsonObject());
					if (enabled) {
						add(namespace, name, translatable, disableGameRenderType, customData, manager);
						Events.OnShaderDataRegistered.registry.forEach((id, runnable) -> {
							try {
								runnable.run(Shaders.get(namespace, name, translatable, disableGameRenderType, customData));
							} catch (Exception error) {
								Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute OnShaderDataRegistered event with id: {}:{}:", id.getFirst(), id.getSecond(), error));
							}
						});
					} else {
						remove(namespace, name);
						Events.OnShaderDataRemoved.registry.forEach((id, runnable) -> {
							try {
								runnable.run(Shaders.get(namespace, name, translatable, disableGameRenderType, customData));
							} catch (Exception error) {
								Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute OnShaderDataRemoved event with id: {}:{}:", id.getFirst(), id.getSecond(), error));
							}
						});
					}
				} catch (Exception error) {
					Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to load luminance shader: {}", error));
				}
			});
			Events.AfterShaderDataRegistered.registry.forEach((id, runnable) -> {
				try {
					runnable.run();
				} catch (Exception error) {
					Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute AfterShaderDataRegistered event with id: {}:{}:", id.getFirst(), id.getSecond(), error));
				}
			});
			isReloading = false;
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to apply shaders dataloader: {}", error));
		}
	}
	@Override
	public Identifier getFabricId() {
		return new Identifier(Data.version.getID(), resourceLocation);
	}
	protected static void releaseShaders() {
		try {
			List<ShaderStage.Type> shaderTypes = new ArrayList<>();
			shaderTypes.add(ShaderStage.Type.VERTEX);
			shaderTypes.add(ShaderStage.Type.FRAGMENT);
			for (ShaderStage.Type type : shaderTypes) {
				List<Map.Entry<String, ShaderStage>> loadedShaders = type.getLoadedShaders().entrySet().stream().toList();
				for (int index = loadedShaders.size() - 1; index > -1; index--) {
					Map.Entry<String, ShaderStage> loadedShader = loadedShaders.get(index);
					String name = loadedShader.getKey();
					if (name.startsWith("rendertype_")) continue;
					if (name.startsWith("position_")) continue;
					if (name.equals("position") || name.equals("particle")) continue;
					loadedShader.getValue().release();
				}
			}
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to release shaders: {}", error));
		}
	}
}