/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.debug;

import com.mclegoman.luminance.client.shaders.Shader;

public class Debug {
	public static boolean debugShader;
	public static Shader.RenderType debugRenderType;
	static {
		debugShader = false;
		debugRenderType = Shader.RenderType.WORLD;
	}
}
