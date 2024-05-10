/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.events;

import com.mclegoman.luminance.common.util.Couple;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class ShaderRenderEvents {
	public static class BeforeRender {
		public static final Map<Couple<String, String>, ShaderRunnable> registry = new HashMap<>();
		public static void register(Couple<String, String> id, ShaderRunnable runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, ShaderRunnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static void get(Couple<String, String> id) {
			registry.get(id);
		}
		public static void modify(Couple<String, String> id, ShaderRunnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
	public static class AfterRender {
		public static final Map<Couple<String, String>, ShaderRunnable> registry = new HashMap<>();
		public static void register(Couple<String, String> id, ShaderRunnable runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, ShaderRunnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static void get(Couple<String, String> id) {
			registry.get(id);
		}
		public static void modify(Couple<String, String> id, ShaderRunnable runnable) {
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
}
