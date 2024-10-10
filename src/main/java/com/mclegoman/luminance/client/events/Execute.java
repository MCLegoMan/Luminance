/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.events;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.LogType;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.util.ObjectAllocator;

public class Execute {
	public static void beforeGameRender() {
		Events.BeforeGameRender.registry.forEach(((id, runnable) -> {
			try {
				runnable.run();
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute AfterGameRender event with id: {}:{}:", id, error));
			}
		}));
	}
	public static void afterHandRender(ObjectAllocator allocator) {
		Events.AfterHandRender.registry.forEach(((id, runnable) -> {
			try {
				runnable.run(ClientData.minecraft.getFramebuffer(), allocator);
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute AfterHandRender event with id: {}:{}:", id, error));
			}
		}));
	}
	public static void afterGameRender(ObjectAllocator allocator) {
		Events.AfterGameRender.registry.forEach(((id, runnable) -> {
			try {
				runnable.run(ClientData.minecraft.getFramebuffer(), allocator);
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute AfterGameRender event with id: {}:{}:", id, error));
			}
		}));
	}
	public static void resize(int width, int height) {
		Events.OnResized.registry.forEach((id, runnable) -> runnable.run(width, height));
	}
	public static void beforeWorldRender() {
		Events.BeforeWorldRender.registry.forEach(((id, runnable) -> {
			try {
				runnable.run();
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute BeforeWorldRender event with id: {}:{}:", id, error));
			}
		}));
	}
	public static void afterWeatherRender(FrameGraphBuilder frameGraphBuilder, PostEffectProcessor.FramebufferSet framebufferSet) {
		Events.AfterWeatherRender.registry.forEach(((id, runnable) -> {
			try {
				runnable.run(frameGraphBuilder, ClientData.minecraft.getFramebuffer().textureWidth, ClientData.minecraft.getFramebuffer().textureHeight, framebufferSet);
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute AfterWeatherRender event with id: {}:{}:", id, error));
			}
		}));
	}
	public static void afterWorldRender(ObjectAllocator allocator) {
		Events.AfterWorldRender.registry.forEach(((id, runnable) -> {
			try {
				runnable.run(ClientData.minecraft.getFramebuffer(), allocator);
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to execute AfterWorldRender event with id: {}:{}:", id, error));
			}
		}));
	}
}
