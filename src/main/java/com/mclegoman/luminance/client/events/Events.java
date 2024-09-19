/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.events;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.shaders.Shader;
import com.mclegoman.luminance.client.shaders.uniforms.LuminanceUniform;
import com.mclegoman.luminance.client.shaders.uniforms.Uniform;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.Couple;
import com.mclegoman.luminance.common.util.LogType;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

import java.util.*;

public class Events {
	public static class OnShaderDataReset {
		public static final Map<Identifier, Runnable> registry = new HashMap<>();
		public static void register(Identifier id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Identifier id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Identifier id) {
			return registry.get(id);
		}
		public static void modify(Identifier id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
	}
	public static class OnShaderDataRegistered {
		public static final Map<Identifier, Runnables.ShaderData> registry = new HashMap<>();
		public static void register(Identifier id, Runnables.ShaderData runnable) {
			add(id, runnable);
		}
		public static void add(Identifier id, Runnables.ShaderData runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnables.ShaderData get(Identifier id) {
			return registry.get(id);
		}
		public static void modify(Identifier id, Runnables.ShaderData runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
	}
	public static class OnShaderDataRemoved {
		public static final Map<Identifier, Runnables.ShaderData> registry = new HashMap<>();
		public static void register(Identifier id, Runnables.ShaderData runnable) {
			add(id, runnable);
		}
		public static void add(Identifier id, Runnables.ShaderData runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnables.ShaderData get(Identifier id) {
			return registry.get(id);
		}
		public static void modify(Identifier id, Runnables.ShaderData runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
	}
	public static class AfterShaderDataRegistered {
		public static final Map<Identifier, Runnable> registry = new HashMap<>();
		public static void register(Identifier id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Identifier id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Identifier id) {
			return registry.get(id);
		}
		public static void modify(Identifier id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
	}
	public static class BeforeWorldRender {
		public static final Map<Identifier, Runnable> registry = new HashMap<>();
		public static void register(Identifier id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Identifier id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Identifier id) {
			return registry.get(id);
		}
		public static void modify(Identifier id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
	}
	public static class AfterWorldBorder {
		public static final Map<Identifier, Runnable> registry = new HashMap<>();
		public static void register(Identifier id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Identifier id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Identifier id) {
			return registry.get(id);
		}
		public static void modify(Identifier id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
	}
	public static class AfterWorldRender {
		public static final Map<Identifier, Runnable> registry = new HashMap<>();
		public static void register(Identifier id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Identifier id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Identifier id) {
			return registry.get(id);
		}
		public static void modify(Identifier id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
	}
	public static class AfterHandRender {
		public static final Map<Identifier, Runnable> registry = new HashMap<>();
		public static void register(Identifier id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Identifier id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Identifier id) {
			return registry.get(id);
		}
		public static void modify(Identifier id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
	}
	public static class BeforeGameRender {
		public static final Map<Identifier, Runnable> registry = new HashMap<>();
		public static void register(Identifier id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Identifier id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Identifier id) {
			return registry.get(id);
		}
		public static void modify(Identifier id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
	}
	public static class AfterGameRender {
		public static final Map<Identifier, Runnable> registry = new HashMap<>();
		public static void register(Identifier id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Identifier id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Identifier id) {
			return registry.get(id);
		}
		public static void modify(Identifier id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
	}
	public static class OnResized {
		public static final Map<Identifier, Runnables.OnResized> registry = new HashMap<>();
		public static void register(Identifier id, Runnables.OnResized runnable) {
			add(id, runnable);
		}
		public static void add(Identifier id, Runnables.OnResized runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnables.OnResized get(Identifier id) {
			return registry.get(id);
		}
		public static void modify(Identifier id, Runnables.OnResized runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
	}
	public static class BeforeShaderRender {
		public static final Map<Identifier, Runnables.Shader> registry = new HashMap<>();
		public static void register(Identifier id, Runnables.Shader runnable) {
			add(id, runnable);
		}
		public static void add(Identifier id, Runnables.Shader runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnables.Shader get(Identifier id) {
			return registry.get(id);
		}
		public static void modify(Identifier id, Runnables.Shader runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
	}
	public static class AfterShaderRender {
		public static final Map<Identifier, Runnables.Shader> registry = new HashMap<>();
		public static void register(Identifier id, Runnables.Shader runnable) {
			add(id, runnable);
		}
		public static void add(Identifier id, Runnables.Shader runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnables.Shader get(Identifier id) {
			return registry.get(id);
		}
		public static void modify(Identifier id, Runnables.Shader runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
	}
	public static class ShaderUniform {
		public static final Map<Identifier, Uniform> registry = new HashMap<>();
		public static void register(Identifier id, Uniform uniform) {
			if (!registry.containsKey(id)) registry.put(id, uniform);
		}
		public static void register(Identifier id, Callables.ShaderRender<Float> callable) {
			if (!registry.containsKey(id)) registry.put(id, new LuminanceUniform(callable));
		}
		public static void modify(Identifier id, Uniform uniform) {
			if (registry.containsKey(id)) registry.replace(id, uniform);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
	}
	public static class ShaderRender {
		public static final Map<Identifier, List<Couple<String, Shader>>> registry = new HashMap<>();
		public static void register(Identifier id, List<Couple<String, Shader>> shaders) {
			add(id, shaders);
		}
		public static void register(Identifier id) {
			add(id, null);
		}
		public static void add(Identifier id, List<Couple<String, Shader>> shaders) {
			if (!registry.containsKey(id)) registry.put(id, shaders);
		}
		public static void add(Identifier id) {
			if (!registry.containsKey(id)) registry.put(id, null);
		}
		public static List<Couple<String, Shader>> get(Identifier id) {
			return exists(id) ? registry.get(id) : null;
		}
		public static boolean exists(Identifier id) {
			return registry.containsKey(id);
		}
		public static void modify(Identifier id, List<Couple<String, Shader>> shaders) {
			registry.replace(id, shaders);
		}
		public static void remove(Identifier id) {
			registry.remove(id);
		}
		public static class Shaders {
			// Using these functions is optional, but makes it easier for mod developers to add shaders to their shader list.
			public static boolean add(Identifier registryId, String shaderName, Shader shader) {
				if (ShaderRender.exists(registryId)) {
					List<Couple<String, Shader>> shaders = ShaderRender.get(registryId);
					if (shaders == null) shaders = new ArrayList<>();
					for (Couple<String, Shader> data : shaders) {
						if (data.getFirst().equalsIgnoreCase(shaderName)) {
							return false;
						}
					}
					shaders.add(new Couple<>(shaderName, shader));
					ShaderRender.modify(registryId, shaders);
					return true;
				}
				return false;
			}
			public static Couple<String, Shader> get(Identifier registryId, String shaderName) {
				if (ShaderRender.exists(registryId)) {
					List<Couple<String, Shader>> shaders = ShaderRender.get(registryId);
					if (shaders == null) shaders = new ArrayList<>();
					for (Couple<String, Shader> data : shaders) {
						if (data.getFirst().equalsIgnoreCase(shaderName)) {
							return data;
						}
					}
				}
				return null;
			}
			public static boolean modify(Identifier registryId, String shaderName, Shader shader) {
				try {
					List<Couple<String, Shader>> shaders = ShaderRender.get(registryId);
					if (shaders != null) {
						for (Couple<String, Shader> data : shaders) {
							if (data.getFirst().equals(shaderName)) {
								data.setSecond(shader);
								break;
							}
						}
					}
					ShaderRender.modify(registryId, shaders);
					return true;
				} catch (Exception error) {
					Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader: {}:{}: {}", registryId, shaderName, error));
				}
				return false;
			}
			public static boolean set(Identifier registryId, String shaderName, Shader shader) {
				try {
					if (!ShaderRender.exists(registryId)) ShaderRender.register(registryId, new ArrayList<>());
					return !exists(registryId, shaderName) ? add(registryId, shaderName, shader) : modify(registryId, shaderName, shader);
				} catch (Exception error) {
					Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader: {}:{}: {}", registryId, shaderName, error));
				}
				return false;
			}
			public static boolean exists(Identifier id, String shader) {
				if (ShaderRender.exists(id)) {
					List<Couple<String, Shader>> shaders = ShaderRender.get(id);
					if (shaders != null) {
						for (Couple<String, Shader> data : shaders) {
							if (data.getFirst().equalsIgnoreCase(shader)) return true;
						}
					}
				} return false;
			}
			public static boolean remove(Identifier id, String shader) {
				List<Couple<String, Shader>> shaders = ShaderRender.get(id);
				if (shaders != null) {
					if (shaders.removeIf(shaderData -> shaderData.getFirst().equalsIgnoreCase(shader))) {
						ShaderRender.modify(id, shaders);
						return true;
					}
				}
				return false;
			}
		}
		public static boolean remove(Identifier id, Couple<String, Shader> shader) {
			List<Couple<String, Shader>> shaders = ShaderRender.get(id);
			if (shaders != null) shaders.remove(shader);
			return false;
		}
	}
}
