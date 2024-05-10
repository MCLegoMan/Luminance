/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.events;

import net.minecraft.client.gl.JsonEffectShaderProgram;

import java.util.List;

public class Runnables {
	public interface Shader {
		default void run(JsonEffectShaderProgram program) {}
	}
	public interface ShaderData {
		default void run(List<Object> shaderData) {}
	}
}