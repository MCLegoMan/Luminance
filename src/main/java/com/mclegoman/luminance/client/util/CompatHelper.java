/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.util;

import com.mclegoman.luminance.common.data.Data;
import net.irisshaders.iris.api.v0.IrisApi;

public class CompatHelper {
	public static boolean isIrisShadersEnabled() {
		return Data.isModInstalled("iris") && IrisApi.getInstance().isShaderPackInUse();
	}
}
