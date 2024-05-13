/*
    Luminance
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/Luminance
    Licence: GNU LGPLv3
*/

package com.mclegoman.luminance.client.shaders;

import com.google.gson.JsonObject;

public class ShaderRegistry {
	private final String namespace;
	private final String name;
	private final boolean translatable;
	private final boolean disableGameRendertype;
	private final JsonObject custom;
	public ShaderRegistry(String namespace, String name, boolean translatable, boolean disableGameRendertype, JsonObject custom) {
		this.namespace = namespace;
		this.name = name;
		this.translatable = translatable;
		this.disableGameRendertype = disableGameRendertype;
		this.custom = custom;
	}
	public String getId() {
		return this.namespace + ":" + this.name;
	}
	public String getNamespace() {
		return this.namespace;
	}
	public String getName() {
		return this.name;
	}
	public boolean getTranslatable() {
		return this.translatable;
	}
	public boolean getDisableGameRendertype() {
		return this.disableGameRendertype;
	}
	public JsonObject getCustom() {
		return this.custom;
	}
}
