/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.events;

import com.mclegoman.luminance.client.shaders.ShaderRegistry;
import net.minecraft.client.gl.JsonEffectShaderProgram;

public class Runnables {
	public interface Shader {
		default void run(JsonEffectShaderProgram program) {}
	}
	public interface ShaderData {
		default void run(ShaderRegistry shaderData) {}
	}
	public interface OnResized {
		default void run(int width, int height) {}
	}
}