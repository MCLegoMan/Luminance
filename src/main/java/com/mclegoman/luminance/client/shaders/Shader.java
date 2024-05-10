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
import net.minecraft.client.gl.PostEffectProcessor;
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
		try {
			this.postProcessor = new PostEffectProcessor(ClientData.minecraft.getTextureManager(), ClientData.minecraft.getResourceManager(), ClientData.minecraft.getFramebuffer(), shaderId);
			this.postProcessor.setupDimensions(ClientData.minecraft.getWindow().getFramebufferWidth(), ClientData.minecraft.getWindow().getFramebufferHeight());
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
		WORLD,
		GAME
	}
}
