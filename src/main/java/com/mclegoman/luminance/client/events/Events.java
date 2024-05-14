/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.events;

import com.mclegoman.luminance.client.shaders.Shader;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.Couple;
import com.mclegoman.luminance.common.util.LogType;
import org.joml.Vector3f;

import java.util.*;
import java.util.concurrent.Callable;

public class Events {
	public static class OnShaderDataReset {
		public static final Map<Couple<String, String>, Runnable> registry = new HashMap<>();
		public static void register(Couple<String, String> id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Couple<String, String> id) {
			return registry.get(id);
		}
		public static void modify(Couple<String, String> id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
	public static class OnShaderDataRegistered {
		public static final Map<Couple<String, String>, Runnables.ShaderData> registry = new HashMap<>();
		public static void register(Couple<String, String> id, Runnables.ShaderData runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, Runnables.ShaderData runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnables.ShaderData get(Couple<String, String> id) {
			return registry.get(id);
		}
		public static void modify(Couple<String, String> id, Runnables.ShaderData runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
	public static class OnShaderDataRemoved {
		public static final Map<Couple<String, String>, Runnables.ShaderData> registry = new HashMap<>();
		public static void register(Couple<String, String> id, Runnables.ShaderData runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, Runnables.ShaderData runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnables.ShaderData get(Couple<String, String> id) {
			return registry.get(id);
		}
		public static void modify(Couple<String, String> id, Runnables.ShaderData runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
	public static class AfterShaderDataRegistered {
		public static final Map<Couple<String, String>, Runnable> registry = new HashMap<>();
		public static void register(Couple<String, String> id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Couple<String, String> id) {
			return registry.get(id);
		}
		public static void modify(Couple<String, String> id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
	public static class BeforeWorldRender {
		public static final Map<Couple<String, String>, Runnable> registry = new HashMap<>();
		public static void register(Couple<String, String> id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Couple<String, String> id) {
			return registry.get(id);
		}
		public static void modify(Couple<String, String> id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
	public static class AfterWorldBorder {
		public static final Map<Couple<String, String>, Runnable> registry = new HashMap<>();
		public static void register(Couple<String, String> id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Couple<String, String> id) {
			return registry.get(id);
		}
		public static void modify(Couple<String, String> id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
	public static class AfterWorldRender {
		public static final Map<Couple<String, String>, Runnable> registry = new HashMap<>();
		public static void register(Couple<String, String> id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Couple<String, String> id) {
			return registry.get(id);
		}
		public static void modify(Couple<String, String> id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
	public static class AfterHandRender {
		public static final Map<Couple<String, String>, Runnable> registry = new HashMap<>();
		public static void register(Couple<String, String> id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Couple<String, String> id) {
			return registry.get(id);
		}
		public static void modify(Couple<String, String> id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
	public static class AfterGameRender {
		public static final Map<Couple<String, String>, Runnable> registry = new HashMap<>();
		public static void register(Couple<String, String> id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnable get(Couple<String, String> id) {
			return registry.get(id);
		}
		public static void modify(Couple<String, String> id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
	public static class OnResized {
		public static final Map<Couple<String, String>, Runnables.OnResized> registry = new HashMap<>();
		public static void register(Couple<String, String> id, Runnables.OnResized runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, Runnables.OnResized runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnables.OnResized get(Couple<String, String> id) {
			return registry.get(id);
		}
		public static void modify(Couple<String, String> id, Runnables.OnResized runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
	public static class BeforeShaderRender {
		public static final Map<Couple<String, String>, Runnables.Shader> registry = new HashMap<>();
		public static void register(Couple<String, String> id, Runnables.Shader runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, Runnables.Shader runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnables.Shader get(Couple<String, String> id) {
			return registry.get(id);
		}
		public static void modify(Couple<String, String> id, Runnables.Shader runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
	public static class AfterShaderRender {
		public static final Map<Couple<String, String>, Runnables.Shader> registry = new HashMap<>();
		public static void register(Couple<String, String> id, Runnables.Shader runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, Runnables.Shader runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static Runnables.Shader get(Couple<String, String> id) {
			return registry.get(id);
		}
		public static void modify(Couple<String, String> id, Runnables.Shader runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
	public static class ShaderUniform {
		public static final Map<Couple<String, String>, Callable<Float>> registryFloat = new HashMap<>();
		public static final Map<Couple<String, String>, Callable<float[]>> registryFloatArray = new HashMap<>();
		public static final Map<Couple<String, String>, Callable<Vector3f>> registryVector3f = new HashMap<>();
		public static void registerFloat(String modId, String uniform, Callable<Float> callable) {
			registerFloat(new Couple<>(modId, uniform), callable);
		}
		public static void modifyFloat(String modId, String uniform, Callable<Float> callable) {
			modifyFloat(new Couple<>(modId, uniform), callable);
		}
		public static void removeFloat(String modId, String uniform) {
			removeFloat(new Couple<>(modId, uniform));
		}
		public static void registerFloats(String modId, String uniform, Callable<float[]> callable) {
			registerFloatArray(new Couple<>(modId, uniform), callable);
		}
		public static void modifyFloats(String modId, String uniform, Callable<float[]> callable) {
			modifyFloatArray(new Couple<>(modId, uniform), callable);
		}
		public static void removeFloats(String modId, String uniform) {
			removeFloatArray(new Couple<>(modId, uniform));
		}
		public static void registerVector3f(String modId, String uniform, Callable<Vector3f> callable) {
			registerVector3f(new Couple<>(modId, uniform), callable);
		}
		public static void modifyVector3f(String modId, String uniform, Callable<Vector3f> callable) {
			modifyVector3f(new Couple<>(modId, uniform), callable);
		}
		public static void removeVector3f(String modId, String uniform) {
			removeVector3f(new Couple<>(modId, uniform));
		}
		public static void registerFloat(Couple<String, String> shader, Callable<Float> callable) {
			if (!registryFloat.containsKey(shader)) registryFloat.put(shader, callable);
		}
		public static float getFloat(Couple<String, String> shader) throws Exception {
			return registryFloat.get(shader).call();
		}
		public static void modifyFloat(Couple<String, String> shader, Callable<Float> callable) {
			if (registryFloat.containsKey(shader)) registryFloat.replace(shader, callable);
		}
		public static void removeFloat(Couple<String, String> shader) {
			registryFloat.remove(shader);
		}
		public static void registerFloatArray(Couple<String, String> shader, Callable<float[]> callable) {
			if (!registryFloatArray.containsKey(shader)) registryFloatArray.put(shader, callable);
		}
		public static float[] getFloats(Couple<String, String> shader) throws Exception {
			return registryFloatArray.get(shader).call();
		}
		public static void modifyFloatArray(Couple<String, String> shader, Callable<float[]> callable) {
			if (registryFloatArray.containsKey(shader)) registryFloatArray.replace(shader, callable);
		}
		public static void removeFloatArray(Couple<String, String> shader) {
			registryFloatArray.remove(shader);
		}
		public static void registerVector3f(Couple<String, String> shader, Callable<Vector3f> callable) {
			if (!registryVector3f.containsKey(shader)) registryVector3f.put(shader, callable);
		}
		public static Vector3f getVector3f(Couple<String, String> shader) throws Exception {
			return registryVector3f.get(shader).call();
		}
		public static void modifyVector3f(Couple<String, String> shader, Callable<Vector3f> callable) {
			if (registryVector3f.containsKey(shader)) registryVector3f.replace(shader, callable);
		}
		public static void removeVector3f(Couple<String, String> shader) {
			registryVector3f.remove(shader);
		}
	}
	public static class ShaderRender {
		public static final Map<Couple<String, String>, List<Couple<String, Shader>>> registry = new HashMap<>();
		public static void register(Couple<String, String> id, List<Couple<String, Shader>> shaders) {
			add(id, shaders);
		}
		public static void register(Couple<String, String> id) {
			add(id, null);
		}
		public static void add(Couple<String, String> id, List<Couple<String, Shader>> shaders) {
			if (!registry.containsKey(id)) registry.put(id, shaders);
		}
		public static void add(Couple<String, String> id) {
			if (!registry.containsKey(id)) registry.put(id, null);
		}
		public static List<Couple<String, Shader>> get(Couple<String, String> id) {
			return exists(id) ? registry.get(id) : null;
		}
		public static boolean exists(Couple<String, String> id) {
			return registry.containsKey(id);
		}
		public static void modify(Couple<String, String> id, List<Couple<String, Shader>> shaders) {
			registry.replace(id, shaders);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
		public static class Shaders {
			// Using these functions is optional, but makes it easier for mod developers to add shaders to their shader list.
			public static boolean add(Couple<String, String> registryId, String shaderName, Shader shader) {
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
			public static Couple<String, Shader> get(Couple<String, String> registryId, String shaderName) {
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
			public static boolean modify(Couple<String, String> registryId, String shaderName, Shader shader) {
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
			public static boolean set(Couple<String, String> registryId, String shaderName, Shader shader) {
				try {
					if (!ShaderRender.exists(registryId)) ShaderRender.register(registryId, new ArrayList<>());
					return !exists(registryId, shaderName) ? add(registryId, shaderName, shader) : modify(registryId, shaderName, shader);
				} catch (Exception error) {
					Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set shader: {}:{}: {}", registryId, shaderName, error));
				}
				return false;
			}
			public static boolean exists(Couple<String, String> id, String shader) {
				if (ShaderRender.exists(id)) {
					List<Couple<String, Shader>> shaders = ShaderRender.get(id);
					if (shaders != null) {
						for (Couple<String, Shader> data : shaders) {
							if (data.getFirst().equalsIgnoreCase(shader)) return true;
						}
					}
				} return false;
			}
			public static boolean remove(Couple<String, String> id, String shader) {
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
		public static boolean remove(Couple<String, String> id, Couple<String, Shader> shader) {
			List<Couple<String, Shader>> shaders = ShaderRender.get(id);
			if (shaders != null) shaders.remove(shader);
			return false;
		}
	}
}
