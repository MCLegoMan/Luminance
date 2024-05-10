/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.events;

import com.mclegoman.luminance.client.shaders.Shader;
import com.mclegoman.luminance.common.util.Couple;

import java.util.HashMap;
import java.util.Map;

public class GameRenderEvents {
	public static class BeforeWorldRender {
		public static final Map<Couple<String, String>, Runnable> registry = new HashMap<>();
		public static void register(Couple<String, String> id, Runnable runnable) {
			add(id, runnable);
		}
		public static void add(Couple<String, String> id, Runnable runnable) {
			if (!registry.containsKey(id)) registry.put(id, runnable);
		}
		public static void get(Couple<String, String> id) {
			registry.get(id);
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
		public static void get(Couple<String, String> id) {
			registry.get(id);
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
		public static void get(Couple<String, String> id) {
			registry.get(id);
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
		public static void get(Couple<String, String> id) {
			registry.get(id);
		}
		public static void modify(Couple<String, String> id, Runnable runnable) {
			registry.replace(id, runnable);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
	public static class ShaderRender {
		public static final Map<Couple<String, String>, Shader> registry = new HashMap<>();
		public static void register(Couple<String, String> id, Shader shader) {
			add(id, shader);
		}
		public static void add(Couple<String, String> id, Shader shader) {
			if (!registry.containsKey(id)) registry.put(id, shader);
		}
		public static Shader get(Couple<String, String> id) {
			return exists(id) ? registry.get(id) : null;
		}
		public static boolean exists(Couple<String, String> id) {
			return registry.containsKey(id);
		}
		public static void modify(Couple<String, String> id, Shader shader) {
			registry.replace(id, shader);
		}
		public static void remove(Couple<String, String> id) {
			registry.remove(id);
		}
	}
}
