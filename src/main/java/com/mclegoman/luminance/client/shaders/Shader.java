/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.shaders;

import com.mclegoman.luminance.client.data.ClientData;
import com.mclegoman.luminance.client.translation.Translation;
import com.mclegoman.luminance.common.data.Data;
import com.mclegoman.luminance.common.util.LogType;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class Shader {
	// TODO: Add Depth and Shader Framebuffers.
	private PostEffectProcessor postProcessor;
	private Identifier shaderId;
	private RenderType renderType;
	public Shader(Identifier id, RenderType renderType) {
		setShaderId(id);
		setRenderType(renderType);
	}
	public PostEffectProcessor getPostProcessor() {
		return postProcessor;
	}
	public void setPostProcessor() {
		setPostProcessor(ClientData.minecraft.getTextureManager(), ClientData.minecraft.getResourceManager(), ClientData.minecraft.getFramebuffer(), ClientData.minecraft.getWindow().getFramebufferWidth(), ClientData.minecraft.getWindow().getFramebufferHeight());
	}
	public void setPostProcessor(TextureManager textureManager, ResourceManager resourceManager, Framebuffer framebuffer, int width, int height) {
		try {
			this.postProcessor = new PostEffectProcessor(textureManager, resourceManager, framebuffer, shaderId);
			this.postProcessor.setupDimensions(width, height);
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set post processor: {}", error));
			if (this.postProcessor != null) this.postProcessor.close();
		}
	}
	public Identifier getShaderId() {
		return this.shaderId;
	}
	public void setShaderId(Identifier id) {
		this.shaderId = id;
	}
	public RenderType getRenderType() {
		return this.renderType;
	}
	public void setRenderType(RenderType renderType) {
		this.renderType = renderType;
	}
	public enum RenderType {
		// Depth Shaders should only be able to use WORLD, as you can't have depth outside of the world.
		WORLD,
		GAME
	}
}
