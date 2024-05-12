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

import java.util.List;
import java.util.concurrent.Callable;

public class Shader {
	// TODO: Add Depth and Shader Framebuffers.
	private PostEffectProcessor postProcessor;
	private boolean useDepth;
	private Identifier shaderId;
	private Callable<RenderType> renderType;
	private Callable<Boolean> shouldRender;
	private List<Object> shaderData;
	public Shader(List<Object> shaderData, Callable<RenderType> renderType, Callable<Boolean> shouldRender) {
		setUseDepth(false);
		setRenderType(renderType);
		setShouldRender(shouldRender);
		setShaderData(shaderData);
	}
	public Shader(List<Object> shaderData, Callable<RenderType> renderType) {
		this(shaderData, renderType, () -> true);
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
	public boolean getUseDepth() {
		return this.useDepth;
	}
	public void setUseDepth(boolean useDepth) {
		this.useDepth = useDepth;
	}
	public Identifier getShaderId() {
		return this.shaderId;
	}
	private void setShaderId(Identifier id) {
		setUseDepth(false);
		this.shaderId = id;
	}
	public Callable<RenderType> getRenderType() {
		return this.renderType;
	}
	public void setRenderType(Callable<RenderType> renderType) {
		setUseDepth(false);
		this.renderType = renderType;
	}
	public boolean getDisableGameRendertype() {
		return (boolean)Shaders.get(getShaderData(), ShaderRegistry.DISABLE_GAME_RENDERTYPE) || getUseDepth();
	}
	public Callable<Boolean> getShouldRender() {
		return this.shouldRender;
	}
	public void setShouldRender(Callable<Boolean> shouldRender) {
		setUseDepth(false);
		this.shouldRender = shouldRender;
	}
	public List<Object> getShaderData() {
		return this.shaderData;
	}
	public void setShaderData(List<Object> shaderData) {
		this.shaderData = shaderData;
		setShaderId(Shaders.getPostShader((String)Shaders.get(getShaderData(), ShaderRegistry.ID)));
	}
	public enum RenderType {
		WORLD,
		GAME
	}
}
