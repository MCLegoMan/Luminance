/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.events;

public class Callables {
	public interface ShaderRender<V> {
		V call(float tickDelta) throws Exception;
	}
}