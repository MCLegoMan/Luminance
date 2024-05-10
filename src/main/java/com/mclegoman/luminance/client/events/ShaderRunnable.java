/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.events;

import net.minecraft.client.gl.JsonEffectShaderProgram;

public interface ShaderRunnable {
	default void run(JsonEffectShaderProgram program) {}
}