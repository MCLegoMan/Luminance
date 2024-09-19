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
import net.minecraft.client.gl.SimpleFramebufferFactory;
import net.minecraft.client.render.DefaultFramebufferSet;
import net.minecraft.util.Identifier;

import java.util.concurrent.Callable;

public class Shader {
	private PostEffectProcessor postProcessor;
	private boolean useDepth;
	private Identifier shaderId;
	private Callable<RenderType> renderType;
	private Callable<Boolean> shouldRender;
	private ShaderRegistry shaderData;
	private Framebuffer framebuffer;
	public Shader(ShaderRegistry shaderData, Callable<RenderType> renderType, Callable<Boolean> shouldRender) {
		reload(shaderData, renderType, shouldRender);
	}
	public Shader(ShaderRegistry shaderData, Callable<RenderType> renderType) {
		this(shaderData, renderType, () -> true);
	}
	public PostEffectProcessor getPostProcessor() {
		return this.postProcessor;
	}
	public Framebuffer getFramebuffer() {
		return this.framebuffer;
	}
	public void setPostProcessor() {
		try {
			this.postProcessor = ClientData.minecraft.getShaderLoader().loadPostEffect(shaderId, DefaultFramebufferSet.STAGES);
		} catch (Exception error) {
			Data.version.sendToLog(LogType.ERROR, Translation.getString("Failed to set post processor: {}", error));
			if (this.postProcessor != null) this.postProcessor = null;
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
		return getShaderData().getDisableGameRendertype();
	}
	public Boolean getShouldRender() {
		try {
			return this.shouldRender.call();
		} catch (Exception error) {
			return false;
		}
	}
	public void setShouldRender(Callable<Boolean> shouldRender) {
		setUseDepth(false);
		this.shouldRender = shouldRender;
	}
	public ShaderRegistry getShaderData() {
		return this.shaderData;
	}
	public void setShaderData(ShaderRegistry shaderData) {
		setUseDepth(false);
		this.shaderData = shaderData;
		setShaderId(this.shaderData.getPostEffect());
	}
	public void setFramebuffer() {
		framebuffer = new SimpleFramebufferFactory(ClientData.minecraft.getFramebuffer().textureWidth, ClientData.minecraft.getFramebuffer().textureHeight, true).create();
	}
	public enum RenderType {
		WORLD,
		GAME
	}
	public void reload() {
		reload(shaderData, renderType, shouldRender);
	}
	public void reload(ShaderRegistry shaderData, Callable<RenderType> renderType, Callable<Boolean> shouldRender) {
		setUseDepth(false);
		setRenderType(renderType);
		setShouldRender(shouldRender);
		setShaderData(shaderData);
	}
}
